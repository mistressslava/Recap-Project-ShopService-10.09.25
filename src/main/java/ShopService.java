import lombok.Data;
import lombok.With;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public ShopService() {}

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if(productToOrder.isEmpty()) {
                return null;
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, Instant.now().truncatedTo(ChronoUnit.MINUTES));

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getListOfOrdersByStatus(OrderStatus orderStatus) {

        return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(orderStatus))
                .toList();
    }
    public Order updateOrder(Order order, OrderStatus orderStatus) {
        if (order == null || order.id() == null || order.id().isBlank()|| orderStatus == null) {
            throw new NoSuchOrderException("Such order does not exist");
        }

        Order orderForChanges = orderRepo.getOrderById(order.id()).orElseThrow(
                () -> new NoSuchElementException("Order with id: " + order.id() + "does not exist!"));

        Order updatedOrder = orderForChanges.withOrderStatus(orderStatus);
        orderRepo.addOrder(updatedOrder);

        return updatedOrder;
    }
}
