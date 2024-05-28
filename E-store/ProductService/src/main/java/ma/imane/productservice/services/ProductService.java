package ma.imane.productservice.services;


import ma.imane.productservice.dtos.ProductRequestDTO;
import ma.imane.productservice.dtos.ProductResponseDTO;
import ma.imane.productservice.entities.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {


    //ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO);


    ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO, MultipartFile imageFile) throws IOException;

    ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO,
                                     MultipartFile imageFile) throws IOException;
}
