package raf.petrovicpleskonjic.rafairlinesticketservice.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Flight {

	@Id
	private long flightId;
	
	@OneToMany(mappedBy = "flight")
	private List<Ticket> tickets;
	
	public Flight() {}

	public Flight(long flightId) {
		this.flightId = flightId;
	}

	public long getFlightId() {
		return flightId;
	}

	public void setFlightId(long flightId) {
		this.flightId = flightId;
	}
}
