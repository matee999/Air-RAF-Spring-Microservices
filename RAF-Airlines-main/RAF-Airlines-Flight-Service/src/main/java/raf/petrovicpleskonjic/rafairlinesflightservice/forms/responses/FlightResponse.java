package raf.petrovicpleskonjic.rafairlinesflightservice.forms.responses;

import raf.petrovicpleskonjic.rafairlinesflightservice.models.Airplane;

public class FlightResponse {

	private long flightId;

	private Integer distance;

	private String startDestination;
	private String endDestination;

	private Float price;

	private Boolean full;

	private Airplane airplane;

	public FlightResponse() {
	}

	public FlightResponse(long flightId, Integer distance, Float price, Boolean full, String startDestination,
			String endDestination, Airplane airplane) {
		this.flightId = flightId;
		this.distance = distance;
		this.price = price;
		this.full = full;
		this.startDestination = startDestination;
		this.endDestination = endDestination;
		this.airplane = airplane;
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

	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
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
