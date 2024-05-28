package ma.imane.orderservice.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.imane.orderservice.model.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Cart {
    @Id
    private String id;
    private String userId;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductItem> productItems= new ArrayList<>();;

    public Cart(String userId) {
        this.userId=userId;
    }
}
