package ma.imane.orderservice.model;
import jakarta.persistence.Lob;
import lombok.*;


@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
public class Product {

    private String id;
    private String name;
    private  String description;
    private double price;
    private int quantity;
    private ProductImage image;
}
