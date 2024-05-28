package ma.imane.productservice.services;


import ma.imane.productservice.dtos.ProductRequestDTO;
import ma.imane.productservice.dtos.ProductResponseDTO;
import ma.imane.productservice.entities.Product;
import ma.imane.productservice.entities.ProductImage;
import ma.imane.productservice.mapper.ProductMapper;
import ma.imane.productservice.repositories.ProductImageRepository;
import ma.imane.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
@Service @Transactional
public class ProductServiceImpl implements ProductService {
    private static final String UPLOAD_DIR = "/ProductService/images"; // Change this to your desired upload directory
    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private ProductImageRepository productImageRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO, MultipartFile imageFile) throws IOException {
        Product product=productMapper.toProduct(productRequestDTO);
        product.setId(UUID.randomUUID().toString());


        //create product image entity
        ProductImage productImage=new ProductImage();
        productImage.setName(imageFile.getOriginalFilename());
        productImage.setData(imageFile.getBytes());
        Product saveProduct = productRepository.save(product);

          productImage.setProduct(saveProduct);

          //Save product image entity
          productImageRepository.save(productImage);


        ProductResponseDTO productResponseDTO = productMapper.fromProduct(saveProduct);
        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO,
                                            MultipartFile imageFile) throws IOException {

        Product product=productRepository.findById(id).get();

        //update the product details
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setQuantity(productRequestDTO.getQuantity());


            // Create a new ProductImage entity
            ProductImage productImage = product.getImage();
            productImage.setName(imageFile.getOriginalFilename());
            productImage.setData(imageFile.getBytes());
             //update the product image
            //product.setImage(productImage);


        Product saveProduct = productRepository.save(product);
        productImage.setProduct(product);
        productImageRepository.save(productImage);
        ProductResponseDTO productResponseDTO = productMapper.fromProduct(saveProduct);

        return productResponseDTO;
    }



}
