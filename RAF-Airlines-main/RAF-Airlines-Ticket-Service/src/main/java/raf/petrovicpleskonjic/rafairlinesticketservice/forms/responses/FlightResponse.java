package raf.petrovicpleskonjic.rafairlinesticketservice.forms.responses;

public class FlightResponse {

	private long flightId;
	
	private Integer distance;
	
	private String startDestination;
	private String endDestination;
	
	private Float price;
	
	private Boolean full;
	
	private AirplaneResponse airplane;
	
	public FlightResponse() {}
	
	public FlightResponse(long flightId, Integer distance, Float price, Boolean full, String startDestination, String endDestination, AirplaneResponse airplane) {
		this(flightId, distance, price, full);
		
		this.startDestination = startDestination;
		this.endDestination = endDestination;
		this.airplane = airplane;
	}

	public FlightResponse(long flightId, Integer distance, Float price, Boolean full) {
		this.flightId = flightId;
		this.distance = distance;
		this.price = price;
		this.full = full;
	}

	public long getFlightId() {
		return flightId;
	}

	public void setFlightId(long flightId) {
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
	
	public Boolean isFull() {
		return full;
	}
	
	public void setFull(Boolean full) {
		this.full = full;
	}
	
	public AirplaneResponse getAirplane() {
		return airplane;
	}
	
	public void setAirplane(AirplaneResponse airplane) {
		this.airplane = airplane;
	}
	
	public String getStartDestination() {
		return startDestination;
	}
	
	public void setStartDestination(String startDestination) {
		this.startDestination = startDestination;
	}
	
	public String getEndDestination() {
		return endDestination;
	}
	
	public void setEndDestination(String endDestination) {
		this.endDestination = endDestination;
	}
}
