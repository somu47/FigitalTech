package Product.example.Notification.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import Product.example.Notification.entity.OrderDTO;
import Product.example.Notification.entity.ProductDTO;

@Service
public class NotificationService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EmailService emailService;

	@Value("${product.service.url}")
	private  String productServices;
	
	@Value("${orders.service.url}")
	private  String orderService;

	public void sendOrderConfirmationEmail(Long orderId) {
		// Fetch order details
		OrderDTO order = restTemplate.getForObject(orderService + "/" + orderId, OrderDTO.class);

		if (order == null) {
			throw new IllegalArgumentException("Order not found!");
		}

		// Fetch product details
		ProductDTO product = restTemplate.getForObject(productServices + "/" + order.getProductId(),
				ProductDTO.class);

		if (product == null) {
			throw new IllegalArgumentException("Product not found!");
		}

		// Format email body
		String emailBody = formatEmailBody(order, product);
		
		// Send email
		try {
			emailService.sendEmail(order.getEmailId(), "Order Confirmation", emailBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String formatEmailBody(OrderDTO order, ProductDTO product) {
		return """
				<html>
				<body>
				    <h1>Order Confirmation</h1>
				    <p>Thank you for your order!</p>
				    <h2>Order Details:</h2>
				    <ul>
				        <li>Order ID: %s</li>
				        <li>Product Name: %s</li>
				        <li>Quantity: %d</li>
				        <li>Total Price: %.2f</li>
				    </ul>
				    <h2>Product Details:</h2>
				    <ul>
				        <li>Product ID: %s</li>
				        <li>Description: %s</li>
				        <li>Price: %.2f</li>
				        <li>Remaining Stock: %d</li>
				    </ul>
				</body>
				</html>
				""".formatted(order.getId(), product.getName(), order.getQuantity(),
				product.getPrice() * order.getQuantity(), product.getId(), product.getDescription(), product.getPrice(),product.getQuantity());
	}
}
