package raf.petrovicpleskonjic.rafairlinesuserservice.forms.requests.credit_card;

public class NewCreditCardRequest {
	
	private String number;
	
	private Integer ccv;

	public NewCreditCardRequest(String number, Integer ccv) {
		this.number = number;
		this.ccv = ccv;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getCcv() {
		return ccv;
	}

	public void setCcv(Integer ccv) {
		this.ccv = ccv;
	}
	
	
}
