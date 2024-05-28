package ma.imane.orderservice.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.imane.orderservice.enums.OrderState;

import java.time.LocalDate;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "ORDERS")
public class Order {
    @Id
    private String id;
    private String userId;
    private String userName;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private OrderState state;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductItem> productItems;
}
