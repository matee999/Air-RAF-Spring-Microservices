package raf.petrovicpleskonjic.rafairlinesticketservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import raf.petrovicpleskonjic.rafairlinesticketservice.models.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
	
	@Query("SELECT f FROM Flight f WHERE f.flightId = "
			+ "(SELECT ticket.flight.flightId FROM Ticket ticket WHERE ticket.passenger.passengerId = :passengerId)")
	List<Flight> getFlightsForPassenger(long passengerId);
}
