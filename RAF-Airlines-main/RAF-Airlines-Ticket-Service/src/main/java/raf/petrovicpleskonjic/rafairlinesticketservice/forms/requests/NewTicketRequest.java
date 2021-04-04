package raf.petrovicpleskonjic.rafairlinesticketservice.forms.requests;

public class NewTicketRequest {

	private String creditCardNumber;
	
	private long flightId;
	
	public NewTicketRequest() {}
	
	public NewTicketRequest(String creditCardNumber, long flightId) {
		this.creditCardNumber = creditCardNumber;
		this.flightId = flightId;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public long getFlightId() {
		return flightId;
	}
	public void setFlightId(long flightId) {
		this.flightId = flightId;
	}
}
