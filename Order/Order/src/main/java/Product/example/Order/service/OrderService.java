 package Product.example.Order.service;

 
import java.time.LocalDate;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Product.example.Order.entity.Order;
import Product.example.Order.entity.ProductDTO;
import Product.example.Order.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${product.notification.url}")
    private String notificationServiceUrl;
    
    public Order placeOrder(Long productId, int quantity,String  EmailId) {
        // Fetch product details
        String productUrl = productServiceUrl + "/" + productId;
        ProductDTO product = restTemplate.getForObject(productUrl, ProductDTO.class);

        if (product == null || product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Product not available or insufficient stock");
        }

        // Deduct stock
        String updateStockUrl = productServiceUrl + "/" + productId + "/update-stock?quantity=" + quantity;
        restTemplate.put(updateStockUrl, null);

        // Save order
        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setStatus("CONFIRMED");
        order.setOrderDate(LocalDate.now());
        order.setEmailId(EmailId);

         orderRepository.save(order);
         String notificationUrl = notificationServiceUrl + "/send-order-confirmation/" + order.getId();
        restTemplate.postForObject(notificationUrl, null, String.class);  // Sending POST request to Notification Service
 
        return order;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
