package raf.petrovicpleskonjic.rafairlinesflightservice.forms.requests;

public class FindFlightRequest {
	
	private Long airplaneId;
	private String startDestination;
	private String endDestination;
	
	private Integer minDistance;
	private Integer maxDistance;
	
	private Float minPrice;
	private Float maxPrice;
	
	
	public FindFlightRequest(Long airplaneId, String startDestination, String endDestination, Integer minDistance,
			Integer maxDistance, Float minPrice, Float maxPrice) {
		this.airplaneId = airplaneId;
		this.startDestination = startDestination;
		this.endDestination = endDestination;
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
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

	public Integer getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(Integer minDistance) {
		this.minDistance = minDistance;
	}

	public Integer getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(Integer maxDistance) {
		this.maxDistance = maxDistance;
	}

	public Float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Float minPrice) {
		this.minPrice = minPrice;
	}

	public Float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Float maxPrice) {
		this.maxPrice = maxPrice;
	}
}
