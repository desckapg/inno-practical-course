# Sales Customer Analysis

A small utility for computing sales/customer metrics from a list of orders. The core entry point is `MetricsCollector`, which provides methods to aggregate common analytics.

## Requirements
- Java 17+ (Stream.toList is used)

## Domain Model (expected)
- `Order`: has `Customer getCustomer()`, `List<OrderItem> getItems()`, `OrderStatus getStatus()`
- `Customer`: has `String getCity()` (and other customer data)
- `OrderItem`: has `String getProductName()`, `int getQuantity()`, `double getPrice()`
- `OrderStatus`: enum containing at least `DELIVERED`

## Provided Metrics
- `getUniqueCities()`: distinct customer cities across all orders
- `getTotalIncome()`: total revenue for orders with status `DELIVERED`
- `getMostPopularProduct()`: most frequently ordered product name (all orders)
- `getAverageCheck()`: average order amount for `DELIVERED` orders
- `getCustomersWhoHaveMoreThanFiveOrders()`: customers with more than five orders

