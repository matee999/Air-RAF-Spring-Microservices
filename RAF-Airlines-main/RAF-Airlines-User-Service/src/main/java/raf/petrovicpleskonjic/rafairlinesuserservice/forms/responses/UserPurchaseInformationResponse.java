package raf.petrovicpleskonjic.rafairlinesuserservice.forms.responses;

public class UserPurchaseInformationResponse {

	private long userId;
	
	private Float salePercentage;

	private String creditCardNumber;
	
	public UserPurchaseInformationResponse() {}

	public UserPurchaseInformationResponse(long userId, Float salePercentage, String creditCardNumber) {
		this.userId = userId;
		this.salePercentage = salePercentage;
		this.creditCardNumber = creditCardNumber;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Float getSalePercentage() {
		return salePercentage;
	}

	public void setSalePercentage(Float salePercentage) {
		this.salePercentage = salePercentage;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
}
