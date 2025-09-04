import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OrderItem {
    private String productName;
    private int quantity;
    private double price;
    private Category category;
}