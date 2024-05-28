package ma.imane.orderservice.repositories;


import ma.imane.orderservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
    void deleteProductItemById(Long id);
    @Query("SELECT pi FROM ProductItem pi WHERE pi.cart.id = :cartId")
    List<ProductItem> findByCartId(@Param("cartId") String cartId);
}
