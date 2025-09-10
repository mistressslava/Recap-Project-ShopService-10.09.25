import lombok.Builder;
import lombok.With;

import java.time.Instant;
import java.util.List;

@With
@Builder
public record Order(
        String id,
        List<Product> products,
        OrderStatus orderStatus,
        Instant orderDate
) {
    public Order(String id, List<Product> products, Instant orderDate) {
        this(id, products, OrderStatus.PROCESSING, orderDate);
    }
}
