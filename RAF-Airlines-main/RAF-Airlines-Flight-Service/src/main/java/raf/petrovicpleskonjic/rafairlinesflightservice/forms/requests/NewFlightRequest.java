package raf.petrovicpleskonjic.rafairlinesflightservice.forms.requests;

public class NewFlightRequest {
	
	private Long airplaneId;
		
	private String startDestination;
	private String endDestination;
	
	private Integer distance;
	
	private Float price;

	public NewFlightRequest(Long airplaneId, String startDestination, String endDestination, Integer distance,
			Float price) {
		this.airplaneId = airplaneId;
		this.startDestination = startDestination;
		this.endDestination = endDestination;
		this.distance = distance;
		this.price = price;
	}

	public Long getAirplaneId() {
		return airplaneId;
	}

	public void setAirplaneId(Long airplaneId) {
		this.airplaneId = airplaneId;
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
