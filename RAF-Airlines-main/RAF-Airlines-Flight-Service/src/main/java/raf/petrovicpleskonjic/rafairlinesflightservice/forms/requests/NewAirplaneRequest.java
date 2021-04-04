package raf.petrovicpleskonjic.rafairlinesflightservice.forms.requests;

public class NewAirplaneRequest {
	
	private String name;
	
	private Integer capacity;
		
	public NewAirplaneRequest(String name, Integer capacity) {
		this.name = name;
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
}
