import lombok.Data;
import lombok.With;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        Order newOrder = new Order(UUID.randomUUID().toString(), products);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getListOfOrdersByStatus(OrderStatus orderStatus) {

        return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(orderStatus))
                .toList();
    }
}
