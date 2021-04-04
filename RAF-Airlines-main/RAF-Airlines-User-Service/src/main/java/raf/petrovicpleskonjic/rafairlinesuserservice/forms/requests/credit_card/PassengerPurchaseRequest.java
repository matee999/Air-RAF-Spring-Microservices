package raf.petrovicpleskonjic.rafairlinesuserservice.forms.requests.credit_card;

public class PassengerPurchaseRequest {

	private Long userId;
	private String creditCardNumber;
	
	public PassengerPurchaseRequest() {}
	
	public PassengerPurchaseRequest(Long userId, String creditCardNumber) {
		this.userId = userId;
		this.creditCardNumber = creditCardNumber;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
}
