package ma.imane.orderservice.web;

import ma.imane.orderservice.entities.Cart;
import ma.imane.orderservice.entities.ProductItem;
import ma.imane.orderservice.model.Product;
import ma.imane.orderservice.restClient.OrderRestClient;
import ma.imane.orderservice.services.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin("*")
public class CartRestController {
    private CartService cartService;
    private OrderRestClient orderRestClient;

    public CartRestController(CartService cartService,OrderRestClient orderRestClient) {
        this.cartService = cartService;
        this.orderRestClient=orderRestClient;
    }

    @GetMapping("/{userId}")
    public Cart getCartByUserId(@PathVariable String userId) {
        Cart cart=cartService.getCartByUserId(userId);
        // Fetch and set product details for each product item
        cart.getProductItems().forEach(productItem -> {
            Product product = orderRestClient.findProductById(productItem.getProductId());
            productItem.setProduct(product);  // Set the fetched product details
        });
        return cart ;
    }

    @GetMapping("/{userId}/items")
    public List<ProductItem> getAllProductItemsInCart(@PathVariable String userId) {
        return cartService.getAllProductItemsInCart(userId);
    }

    @PostMapping("/{userId}/items")
    public ProductItem addItemToCart(@PathVariable String userId,
                                     @RequestParam String productId,
                                     @RequestParam int quantity) {
        return cartService.addItemToCart(userId, productId, quantity);
    }

    @DeleteMapping("/{userId}/items/{productItemId}")
    public void removeItemFromCart(@PathVariable String userId, @PathVariable Long productItemId) {
        cartService.removeItemFromCart(userId, productItemId);
    }

    @PutMapping("/{userId}/items/{productItemId}")
    public ProductItem updateItemQuantity(@PathVariable String userId,
                                          @PathVariable String productItemId,
                                          @RequestParam int quantity) {
        return cartService.updateItemQuantity(userId, productItemId, quantity);
    }
}
