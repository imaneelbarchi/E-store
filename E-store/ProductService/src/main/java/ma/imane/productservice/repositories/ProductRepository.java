package ma.imane.productservice.repositories;

import ma.imane.productservice.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {

    Page<Product> findAll(Pageable pageable);
    Page<Product> findByNameContains(String keyword,Pageable pageable);
}
