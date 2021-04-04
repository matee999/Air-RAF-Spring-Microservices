package raf.petrovicpleskonjic.rafairlinesuserservice.messages;

public class FlightAssignedMessage {

	private Long passengerId;
	private Long flightId;
	
	private Integer distance;
	
	public FlightAssignedMessage() {}

	public FlightAssignedMessage(Long passengerId, Long flightId, Integer distance) {
		this.passengerId = passengerId;
		this.flightId = flightId;
		this.distance = distance;
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
}
