package raf.petrovicpleskonjic.rafairlinesticketservice.forms.responses;

public class AirplaneResponse {

	private long airplaneId;
	
	private String name;
	
	private int capacity;
	
	public AirplaneResponse() {}
	
	public AirplaneResponse(long airplaneId, String name, int capacity) {
		this.airplaneId = airplaneId;
		this.name = name;
		this.capacity = capacity;
	}

	public long getAirplaneId() {
		return airplaneId;
	}

	public void setAirplaneId(long airplaneId) {
		this.airplaneId = airplaneId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
