package ma.imane.productservice.dtos;

import jakarta.persistence.Lob;
import lombok.*;
import ma.imane.productservice.entities.ProductImage;
import ma.imane.productservice.enums.ProductType;

@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ProductResponseDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private ProductImage image;
}
