import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
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
        OrderMapRepo repo = new OrderMapRepo();
        Instant time = Instant.now().truncatedTo(ChronoUnit.MINUTES);

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), time);
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1").get();

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), time);

        assertEquals(actual, expected);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
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
    void addOrderThatNull() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        //THEN
        assertThrows(NoSuchOrderException.class, () -> repo.addOrder(null));
    }

    @Test
    void addOrderNotExist() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Instant time = Instant.now().truncatedTo(ChronoUnit.MINUTES);

        //THEN
        assertThrows(NoSuchOrderException.class, () -> repo.addOrder(new Order("", null, time)));
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertTrue(repo.getOrderById("1").isEmpty());
    }
}
