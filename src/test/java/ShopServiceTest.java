import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")));
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1","4");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void getListOfProductsByStatusEmptyList() {
        //GIVEN
        ShopService shopService = new ShopService();
        OrderStatus status = OrderStatus.PROCESSING;

        //WHEN
        List<Order> actual = shopService.getListOfOrdersByStatus(status);

        //THEN
        List<Order> expected = Collections.emptyList();
        assertEquals(actual, expected);
    }

    //check
    @Test
    void getListOfProductsByStatus() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productIds = List.of("1");
        OrderStatus status = OrderStatus.PROCESSING;

        //WHEN
        Order expextedOrder = shopService.addOrder(productIds);
        List<Order> expected = List.of(expextedOrder);

        //THEN
        List<Order> actual = shopService.getListOfOrdersByStatus(status);
        assertEquals(expected, actual);
    }

    @Test
    void getListOfProductsByStatusProcessing() {
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        Order order = shopService.addOrder(productsIds);
        List<Order> expected = new ArrayList<>();
        expected.add(order);

        List<Order> actual = shopService.getListOfOrdersByStatus(OrderStatus.PROCESSING);

        assertEquals(expected, actual);
    }

    @Test
    void getListOfProductsByStatusInDelivery() {
        //GIVEN
        ShopService shopService = new ShopService();
        shopService.addOrder(List.of("1"));
        OrderStatus status = OrderStatus.IN_DELIVERY;

        //WHEN
        List<Order> actual = shopService.getListOfOrdersByStatus(status);

        //THEN
        List<Order> expected = Collections.emptyList();
        assertEquals(expected, actual);
    }

    @Test
    void updateOrderInDelivery() {
        //GIVEN
        ShopService shopService = new ShopService();
        Order order = shopService.addOrder(List.of("1"));
        OrderStatus status = OrderStatus.IN_DELIVERY;

        //WHEN
        Order expected = new Order("1", order.products(), OrderStatus.IN_DELIVERY);

        //THEN
        Order actual = shopService.updateOrder(order, status);
        assertEquals(expected.orderStatus(), actual.orderStatus());
    }


//    @Test
//    void getListOfProductsByStatusCompleted() {
//        ProductRepo productRepo = new ProductRepo();
//        productRepo.addProduct(new Product("2", "Bananen"));
//        productRepo.addProduct(new Product("3", "Orangen"));
//        OrderRepo orderRepo = new OrderMapRepo();
//        ShopService shopService = new ShopService(productRepo, orderRepo);
//        Order order = shopService.addOrder(List.of("2", "3"));
//
//        shopService.updateOrderStatus(order.id(), OrderStatus.COMPLETED);
//        List<Order> expected = List.of(order.withOrderStatus(OrderStatus.COMPLETED));
//
//        List<Order> actual = shopService.getListOfOrdersByStatus(OrderStatus.COMPLETED);
//
//        assertEquals(expected, actual);
//    }
}
