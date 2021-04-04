package raf.petrovicpleskonjic.rafairlinesticketservice.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Passenger {
	
	@Id
	private long passengerId;
	
	@OneToMany(mappedBy = "passenger")
	private List<Ticket> tickets;
	
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
}
