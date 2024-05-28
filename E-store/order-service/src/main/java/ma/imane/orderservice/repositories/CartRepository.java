package ma.imane.orderservice.repositories;

import ma.imane.orderservice.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {
   Optional<Cart>  findByUserId(String userId);
   @Modifying
   @Query("DELETE FROM ProductItem pi WHERE pi.id = :productItemId AND pi.cart.id = :cartId")
   void deleteProductItemByIdAndCartId(@Param("productItemId") Long productItemId, @Param("cartId") String cartId);

}
