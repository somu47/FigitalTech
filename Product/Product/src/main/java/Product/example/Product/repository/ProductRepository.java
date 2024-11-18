package Product.example.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Product.example.Product.Entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}