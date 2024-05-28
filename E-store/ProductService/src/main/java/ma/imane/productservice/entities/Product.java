package ma.imane.productservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor @NoArgsConstructor @Builder @Setter @Getter @ToString
public class Product {
    @Id
    private String id;
    private String name;
    private  String description;
    private double price;
    private int quantity;
    @JsonManagedReference
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductImage image;

}
