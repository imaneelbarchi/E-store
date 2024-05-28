package ma.imane.orderservice.services;

import ma.imane.orderservice.entities.Cart;
import ma.imane.orderservice.entities.ProductItem;
import ma.imane.orderservice.model.Product;
import ma.imane.orderservice.repositories.CartRepository;
import ma.imane.orderservice.repositories.ProductItemRepository;
import ma.imane.orderservice.restClient.OrderRestClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {
    private CartRepository cartRepository;
    private ProductItemRepository productItemRepository;
    private OrderRestClient orderRestClient;

    public CartService(CartRepository cartRepository, ProductItemRepository productItemRepository, OrderRestClient orderRestClient) {
        this.cartRepository = cartRepository;
        this.productItemRepository=productItemRepository;
        this.orderRestClient = orderRestClient;
    }

    public Cart getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setId(UUID.randomUUID().toString());
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });
    }

    public List<ProductItem> getAllProductItemsInCart(String userId) {
       Cart cart= getCartByUserId(userId);
        if (cart != null) {
            return cart.getProductItems();
        } else {
            throw new RuntimeException("Cart not found for user: " + userId);
        }
    }

    public ProductItem addItemToCart(String userId, String productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Optional<ProductItem> existingProductItem = cart.getProductItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingProductItem.isPresent()) {
            ProductItem productItem = existingProductItem.get();
            productItem.setQuantity(productItem.getQuantity() + quantity);
            return productItemRepository.save(productItem);
        } else {
            Product product = orderRestClient.findProductById(productId);
            ProductItem productItem = new ProductItem();
            productItem.setProductId(productId);
            productItem.setPrice(product.getPrice());
            productItem.setQuantity(quantity);
            productItem.setCart(cart);
            productItem.setProduct(product);
            return productItemRepository.save(productItem);
        }
    }
    @Transactional
    public void removeItemFromCart(String userId, Long productItemId) {
        Cart cart = getCartByUserId(userId);
        cart.getId();
        cartRepository.deleteProductItemByIdAndCartId(productItemId,cart.getId());
        //cart.getProductItems().removeIf(item -> item.getId().equals(productItemId));
        //cartRepository.save(cart);
    }

    public ProductItem updateItemQuantity(String userId, String productItemId, int quantity) {
        Cart cart = getCartByUserId(userId);
        ProductItem productItem = cart.getProductItems().stream()
                .filter(item -> item.getId().equals(Long.valueOf(productItemId)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ProductItem not found in the cart"));

        productItem.setQuantity(quantity);
        return productItemRepository.save(productItem);
    }

    @Transactional
    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart != null) {
            // Retrieve all product items associated with the cart
            List<ProductItem> productItems = productItemRepository.findByCartId(cart.getId());
            // Detach product items from the cart
            productItems.forEach(pi -> pi.setCart(null));

            // Now, delete the cart
            cartRepository.delete(cart);
        }
    }




}
