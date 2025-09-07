package by.desckapg.salescustomeranalysis;

import by.desckapg.salescustomeranalysis.domain.Customer;
import by.desckapg.salescustomeranalysis.domain.Order;
import by.desckapg.salescustomeranalysis.domain.OrderItem;
import by.desckapg.salescustomeranalysis.domain.OrderStatus;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Collects analytical metrics over a list of orders.
 * The instance holds a reference to the provided orders list.
 */
public class MetricsCollector {

    private final List<Order> orders;

    /**
     * Creates a new metrics collector over the given orders.
     *
     * @param orders source orders to analyze; must be non-null
     */
    public MetricsCollector(List<Order> orders) {
        this.orders = validateOrders(orders);
    }

    private List<Order> validateOrders(List<Order> orders) {
        Objects.requireNonNull(orders, "Orders must not be null");
        if (orders.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("orders contains null elements");
        }
        return List.copyOf(orders);
    }

    /**
     * Returns distinct customer cities found across all orders.
     *
     * @return list of unique cities (encounter order)
     */
    public List<String> getUniqueCities() {
        return orders.stream()
                .map(order -> order.getCustomer().getCity())
                .distinct()
                .toList();
    }

    /**
     * Calculates total income from delivered orders only.
     * Income is the sum of quantity * price over all items in delivered orders.
     *
     * @return total income; 0.0 if none
     */
    public double getTotalIncome() {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }

    /**
     * Finds the most frequently ordered product by name across all orders.
     *
     * @return product name if present; empty if there are no items
     */
    public Optional<String> getMostPopularProduct() {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors
                        .groupingBy(OrderItem::getProductName, Collectors.summingInt(OrderItem::getQuantity)))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey);

    }

    /**
     * Computes the average check for delivered orders.
     * The check is the sum of quantity * price per order's items.
     *
     * @return average delivered order amount; 0.0 if none
     */
    public double getAverageCheck() {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(item -> item.getQuantity() * item.getPrice())
                        .sum())
                .average()
                .orElse(0.0);
    }

    /**
     * Returns customers who have placed more than five orders.
     *
     * @return list of customers with order count > 5
     */
    public List<Customer> getCustomersWhoHaveMoreThanFiveOrders() {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 5)
                .map(Map.Entry::getKey)
                .toList();

    }

}
