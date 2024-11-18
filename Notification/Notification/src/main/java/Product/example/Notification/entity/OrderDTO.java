package Product.example.Notification.entity;



public class OrderDTO {
    private Long id;
    private Long productId;
    private int quantity;
    private String emailId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Override
	public String toString() {
		return "OrderDTO [id=" + id + ", productId=" + productId + ", quantity=" + quantity + ", emailId=" + emailId
				+ "]";
	}
	

    
}

