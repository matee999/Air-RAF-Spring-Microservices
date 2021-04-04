package raf.petrovicpleskonjic.rafairlinesflightservice.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Passenger {

	@Id
	private long passengerId;
	
	@ManyToMany(mappedBy = "passengers")
	private List<Flight> flights;
	
	public Passenger() {}

	public Passenger(long passengerId) {
		this.passengerId = passengerId;
	}

	public long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(long passengerId) {
		this.passengerId = passengerId;
	}

	public List<Flight> getFlights() {
		return flights;
	}
}
