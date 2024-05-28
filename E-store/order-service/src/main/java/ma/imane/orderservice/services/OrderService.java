package ma.imane.orderservice.services;

import ma.imane.orderservice.entities.Cart;
import ma.imane.orderservice.entities.Order;
import ma.imane.orderservice.entities.ProductItem;
import ma.imane.orderservice.enums.OrderState;
import ma.imane.orderservice.repositories.CartRepository;
import ma.imane.orderservice.repositories.OrderRepository;
import ma.imane.orderservice.restClient.OrderRestClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderRestClient orderRestClient;
    private CartRepository cartRepository;
    private CartService cartService;

    public OrderService(OrderRepository orderRepository, OrderRestClient orderRestClient, CartRepository cartRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderRestClient = orderRestClient;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    /*public Order createOrder(String userId, List<ProductItem> productItems) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setDate(LocalDate.now());
        order.setState(OrderState.PENDING);
        order.setProductItems(productItems);
        productItems.forEach(pi -> pi.setOrder(order));
        return orderRepository.save(order);
    }*/
    @Transactional
    public Order createOrder(String userId,String userName) {
        // Fetch the cart and product items
        Cart cart = cartService.getCartByUserId(userId);
        List<ProductItem> productItems = new ArrayList<>(cart.getProductItems());

        // Create the order
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setUserName(userName); // Save the userName
        order.setDate(LocalDate.now());
        order.setState(OrderState.PENDING);
        order.setProductItems(productItems);

        // Associate ProductItems with the order and detach from the cart
        productItems.forEach(pi -> {
            pi.setOrder(order);
            pi.setCart(null); // Detach from the cart
        });

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Clear the cart after placing the order
        cartService.clearCart(userId);

        return savedOrder;
    }

    public Order updateOrder(String orderId, List<ProductItem> productItems) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setProductItems(productItems);
        productItems.forEach(pi -> pi.setOrder(order));
        return orderRepository.save(order);
    }


    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
