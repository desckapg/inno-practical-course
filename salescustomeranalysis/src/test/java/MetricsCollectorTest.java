import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MetricsCollectorTest {

    @Test
    void getUniqueCities_existsDuplicatedCities_shouldReturnDistinctCities() {
        Customer c1Minsk = mockCustomer("Minsk");
        Customer c2Minsk = mockCustomer("Minsk");
        Customer cGrodno = mockCustomer("Grodno");

        Order o1 = mockOrder(OrderStatus.DELIVERED, c1Minsk,
                List.of(mockItem("Bread", 2, 1.5)));
        Order o2 = mockOrder(null, c2Minsk,
                List.of(mockItem("Milk", 1, 2.0)));
        Order o3 = mockOrder(OrderStatus.DELIVERED, cGrodno,
                List.of(mockItem("Eggs", 10, 0.2)));

        MetricsCollector mc = new MetricsCollector(List.of(o1, o2, o3));

        List<String> cities = mc.getUniqueCities();

        assertThat(cities.size()).isEqualTo(2);
        assertThat(cities).containsExactlyInAnyOrder("Minsk", "Grodno");
    }

    @Test
    void getTotalIncome_existsNotDeliveredItems_shouldSumOnlyDeliveredItems() {
        Customer c1 = mockCustomer("Minsk");

        Order delivered1 = mockOrder(OrderStatus.DELIVERED, c1,
                List.of(
                        mockItem("Bread", 2, 1.5),
                        mockItem("Milk", 1, 2.0)
                ));
        Order notDelivered = mockOrder(OrderStatus.CANCELLED, c1, List.of(
                mockItem("Bread", 1, 1.5)
        ));
        Order delivered2 = mockOrder(OrderStatus.DELIVERED, c1,
                List.of(mockItem("Eggs", 10, 0.2)));

        MetricsCollector mc = new MetricsCollector(List.of(delivered1, notDelivered, delivered2));

        double total = mc.getTotalIncome();

        assertThat(total).isCloseTo(7.0, offset(1e-9));
    }

    @Test
    void getMostPopularProduct_breadIsMostPopular_shouldReturnMostFrequentProductName() {
        Customer c1 = mockCustomer("Minsk");

        Order o1 = mockOrder(OrderStatus.DELIVERED, c1,
                List.of(
                        mockItem("Bread", 2, 1.5),
                        mockItem("Milk", 1, 2.0)
                ));
        Order o2 = mockOrder(null, c1,
                List.of(
                        mockItem("Bread", 1, 1.5),
                        mockItem("Eggs", 10, 0.2)
                ));

        MetricsCollector mc = new MetricsCollector(List.of(o1, o2));

        Optional<String> mostPopular = mc.getMostPopularProduct();

        assertThat(mostPopular.isPresent()).isTrue();
        assertThat(mostPopular.get()).isEqualTo("Bread");
    }

    @Test
    void getAverageCheck_existsNotDeliveredOrders_shouldComputeAvgDeliveredOrders() {
        Customer c1 = mockCustomer("Minsk");

        Order d1 = mockOrder(OrderStatus.DELIVERED, c1,
                List.of(
                        mockItem("Bread", 2, 1.5),
                        mockItem("Milk", 1, 2.0)
                ));
        Order nd = mockOrder(OrderStatus.CANCELLED, c1,
                List.of(mockItem("Bread", 100, 100.0)));
        Order d2 = mockOrder(OrderStatus.DELIVERED, c1,
                List.of(mockItem("Eggs", 10, 0.2)));

        MetricsCollector mc = new MetricsCollector(List.of(d1, nd, d2));

        double avg = mc.getAverageCheck();

        assertThat(avg).isCloseTo(3.5, offset(1e-9));
    }

    @Test
    void getCustomersWhoHaveMoreThanFiveOrders_existsCustomerWithLessThen5Orders_shouldFilterByCountGreaterThan5() {
        Customer frequent = mockCustomer("Minsk");
        Customer other = mockCustomer("Brest");

        List<Order> sixForFrequent = List.of(
                mockOrder(OrderStatus.DELIVERED, frequent, List.of()),
                mockOrder(OrderStatus.CANCELLED, frequent, List.of()),
                mockOrder(OrderStatus.DELIVERED, frequent, List.of()),
                mockOrder(OrderStatus.CANCELLED, frequent, List.of()),
                mockOrder(OrderStatus.DELIVERED, frequent, List.of()),
                mockOrder(OrderStatus.CANCELLED, frequent, List.of())
        );

        List<Order> twoForOther = List.of(
                mockOrder(OrderStatus.DELIVERED, other, List.of()),
                mockOrder(null, other, List.of())
        );

        MetricsCollector mc = new MetricsCollector(
                new java.util.ArrayList<>() {{
                    addAll(sixForFrequent);
                    addAll(twoForOther);
                }}
        );

        List<Customer> result = mc.getCustomersWhoHaveMoreThanFiveOrders();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getFirst()).isEqualTo(frequent);
    }

    @Test
    void whenNoOrders_shouldReturnDefaults() {
        MetricsCollector mc = new MetricsCollector(List.of());

        assertThat(mc.getUniqueCities()).isEmpty();
        assertThat(mc.getTotalIncome()).isEqualTo(0.0, offset(1e-9));
        assertThat(mc.getMostPopularProduct()).isEmpty();
        assertThat(mc.getUniqueCities()).isEmpty();
        assertThat(mc.getAverageCheck()).isEqualTo(0.0, offset(1e-9));
        assertThat(mc.getCustomersWhoHaveMoreThanFiveOrders()).isEmpty();
    }


    private Customer mockCustomer(String city) {
        Customer customer = mock(Customer.class);
        when(customer.getCity()).thenReturn(city);
        return customer;
    }

    private OrderItem mockItem(String productName, int quantity, double price) {
        OrderItem item = mock(OrderItem.class);
        when(item.getProductName()).thenReturn(productName);
        when(item.getQuantity()).thenReturn(quantity);
        when(item.getPrice()).thenReturn(price);
        return item;
    }

    private Order mockOrder(OrderStatus status, Customer customer, List<OrderItem> items) {
        Order order = mock(Order.class);
        when(order.getStatus()).thenReturn(status);
        when(order.getCustomer()).thenReturn(customer);
        when(order.getItems()).thenReturn(items);
        return order;
    }

}

