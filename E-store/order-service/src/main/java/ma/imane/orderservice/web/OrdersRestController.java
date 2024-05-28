package ma.imane.orderservice.web;


import ma.imane.orderservice.entities.Order;
import ma.imane.orderservice.entities.ProductItem;
import ma.imane.orderservice.repositories.OrderRepository;
import ma.imane.orderservice.restClient.OrderRestClient;
import ma.imane.orderservice.services.CartService;
import ma.imane.orderservice.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrdersRestController {
    private OrderRepository orderRepository;
    private OrderService orderService;
    private OrderRestClient orderRestClient;
    private CartService cartService;

    public OrdersRestController(OrderRepository orderRepository, OrderRestClient orderRestClient,CartService cartService,OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderRestClient = orderRestClient;
        this.cartService=cartService;
        this.orderService=orderService;
    }
    @GetMapping("/orders")
    public List<Order> findAllOrders(){
        List<Order> allOrders = orderRepository.findAll();
        allOrders.forEach(o->{
            o.getProductItems().forEach(pi->{
                pi.setProduct(orderRestClient.findProductById(pi.getProductId()));
            });
        });
        return allOrders;
    }
    @GetMapping("/orders/{id}")
    public Order findOrderById(@PathVariable String id){
        Order order = orderService.getOrderById(id);
        if(order!=null){
            order.getProductItems().forEach(pi->{
                pi.setProduct(orderRestClient.findProductById(pi.getProductId()));
            });
        }

        return order;
    }

    @PostMapping("/orders/create-from-cart/{userId}")
    public Order createOrderFromCart(@PathVariable String userId,@RequestBody String userName) {
        //List<ProductItem> productItems = cartService.getAllProductItemsInCart(userId);
        return orderService.createOrder(userId,userName);
    }

    @PutMapping("/orders/{orderId}")
    public  Order updateOrder(@PathVariable  String orderId, @RequestBody List<ProductItem> productItems){
        return  orderService.updateOrder(orderId,productItems);
    }


}
