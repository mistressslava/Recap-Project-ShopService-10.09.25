import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Instant time = Instant.now().truncatedTo(ChronoUnit.MINUTES);

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), time);
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product("1", "Apfel");
        expected.add(new Order("1", List.of(product1), time));

        assertEquals(actual, expected);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Instant time = Instant.now().truncatedTo(ChronoUnit.MINUTES);

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), time);
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1").orElseThrow(() -> new NoSuchOrderException("No such Order"));

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), time);

        assertEquals(actual, expected);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Instant time = Instant.now().truncatedTo(ChronoUnit.MINUTES);
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), time);

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), time);
        assertEquals(actual, expected);
        assertEquals(repo.getOrderById("1").get(), expected);
    }

    @Test
    void addOrderNull() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        //THEN
        assertThrows(NoSuchOrderException.class, () -> repo.addOrder(null));
    }

    @Test
    void addOrderNotExist() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        Instant time = Instant.now().truncatedTo(ChronoUnit.MINUTES);

        //THEN
        assertThrows(NoSuchOrderException.class, () -> repo.addOrder(new Order("2", null, time)));
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertTrue(repo.getOrderById("1").isEmpty());
    }
}
