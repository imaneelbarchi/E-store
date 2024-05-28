package ma.imane.productservice.web;

import ma.imane.productservice.dtos.ProductRequestDTO;
import ma.imane.productservice.dtos.ProductResponseDTO;
import ma.imane.productservice.entities.Product;
import ma.imane.productservice.entities.ProductImage;
import ma.imane.productservice.mapper.ProductMapper;
import ma.imane.productservice.repositories.ProductRepository;
import ma.imane.productservice.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ProductRestController {
    private ProductRepository productRepository;
    private ProductService productService;
    private ProductMapper productMapper;

    public ProductRestController(ProductRepository productRepository, ProductService productService, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.productMapper = productMapper;
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable String id){
        return productRepository.findById(id).orElse(null);
    }

    @GetMapping("/products")
    public Page<Product> allProducts(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/products")
    public ProductResponseDTO save(@ModelAttribute ProductRequestDTO productRequestDTO,
                                   @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
     return productService.addProduct(productRequestDTO,imageFile);
    }
    @CrossOrigin(origins = "*")
    @PutMapping("/products/{id}")
    public ProductResponseDTO updateProduct(@ModelAttribute ProductRequestDTO productRequest,
                                            @RequestParam("imageFile") MultipartFile imageFile,@PathVariable String id) throws IOException {
        return productService.updateProduct(id,productRequest,imageFile);
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable String id){
        productRepository.deleteById(id);
    }

    @GetMapping("/search")
    public Page<Product> searchProduct(@RequestParam(name = "keyword",defaultValue = "") String keyword,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByNameContains(keyword, pageable);
    }

    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }




}
