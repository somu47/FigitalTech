package Product.example.Order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import Product.example.Order.entity.Order;
import Product.example.Order.service.OrderService;
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Order> placeOrder(
            @RequestParam Long productId,
            @RequestParam int quantity,
            @RequestBody String emailId) {
    	 String cleanEmail =extractEmail(emailId);
        return ResponseEntity.ok(orderService.placeOrder(productId, quantity,cleanEmail));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
    private String extractEmail(String emailId) {
        try {
            // Parse the incoming JSON string to extract the actual email
            JSONObject jsonObject = new JSONObject(emailId);
            String email = jsonObject.getString("emailId");
            return email;
        } catch (Exception e) {
            // Handle any errors that may occur during parsing
            throw new IllegalArgumentException("Invalid email format: " + emailId, e);
        }
    }
}
