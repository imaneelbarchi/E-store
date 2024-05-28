package ma.imane.productservice.mapper;


import ma.imane.productservice.dtos.ProductRequestDTO;
import ma.imane.productservice.dtos.ProductResponseDTO;
import ma.imane.productservice.entities.Product;
import ma.imane.productservice.entities.ProductImage;
import ma.imane.productservice.repositories.ProductImageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProductMapper {


    public ProductResponseDTO fromProduct(Product product){
        ProductResponseDTO productResponseDTO=new ProductResponseDTO();
        BeanUtils.copyProperties(product,productResponseDTO);
       // productResponseDTO.setImage(product.getImage());
        return productResponseDTO;
    }
    public Product toProduct(ProductRequestDTO productRequestDTO) throws IOException {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setQuantity(productRequestDTO.getQuantity());
       // product.setImage(productRequestDTO.getImage());
        return product;
    }
}
