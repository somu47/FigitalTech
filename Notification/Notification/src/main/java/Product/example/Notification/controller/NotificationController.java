package Product.example.Notification.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Product.example.Notification.Service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send-order-confirmation/{orderId}")
    public ResponseEntity<String> sendOrderConfirmationEmail(@PathVariable Long orderId) {
        try {
            notificationService.sendOrderConfirmationEmail(orderId);
            return ResponseEntity.ok("Order confirmation email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
        }
    }

}
