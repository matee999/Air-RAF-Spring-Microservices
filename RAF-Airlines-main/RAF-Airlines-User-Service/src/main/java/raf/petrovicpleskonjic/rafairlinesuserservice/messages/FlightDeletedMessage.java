package raf.petrovicpleskonjic.rafairlinesuserservice.messages;

public class FlightDeletedMessage {

	private Long passengerId;
	private Long flightId;
	
	private Integer distance;
	
	private Float price;
	
	public FlightDeletedMessage() {}

	public FlightDeletedMessage(Long passengerId, Long flightId, Integer distance, Float price) {
		this.passengerId = passengerId;
		this.flightId = flightId;
		this.distance = distance;
		this.price = price;
	}

	public Long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
