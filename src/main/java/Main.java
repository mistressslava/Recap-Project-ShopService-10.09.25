import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderRepo orderMapRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();

        ShopService shopService = new ShopService(productRepo, orderMapRepo);

        shopService.addOrder(List.of("1"));
        shopService.addOrder(List.of("1"));
        shopService.addOrder(List.of("1"));

        for (Order o : shopService.getOrders()) {
            System.out.println(o);
        }
    }
}
