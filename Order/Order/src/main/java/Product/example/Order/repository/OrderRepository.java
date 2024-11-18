package Product.example.Order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Product.example.Order.entity.Order;

 
public interface OrderRepository extends JpaRepository<Order, Long> {

}
