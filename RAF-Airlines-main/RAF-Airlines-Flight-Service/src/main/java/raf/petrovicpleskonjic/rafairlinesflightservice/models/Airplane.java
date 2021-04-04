package raf.petrovicpleskonjic.rafairlinesflightservice.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Airplane {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long airplaneId;
	
	private String name;
	
	private Integer capacity;
	
	@OneToMany(mappedBy = "airplane", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Flight> flights;
	
	public Airplane() {}

	public Airplane(String name, Integer capacity) {
		this.name = name;
		this.capacity = capacity;
	}
	
	public List<Flight> getFlights() {
		return flights;
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

	public long getAirplaneId() {
		return airplaneId;
	}
}
