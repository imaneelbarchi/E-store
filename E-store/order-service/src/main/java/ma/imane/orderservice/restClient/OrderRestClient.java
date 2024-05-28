package ma.imane.orderservice.restClient;


import ma.imane.orderservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(url = "http://localhost:8089",name = "product-service")
public interface OrderRestClient {
    @GetMapping("/api/products")
     List<Product> getAllProducts();
    @GetMapping("/api/products/{id}")
     Product findProductById(@PathVariable String id);
}
