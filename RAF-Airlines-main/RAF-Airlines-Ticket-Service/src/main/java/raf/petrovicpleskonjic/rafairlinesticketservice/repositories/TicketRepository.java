package raf.petrovicpleskonjic.rafairlinesticketservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import raf.petrovicpleskonjic.rafairlinesticketservice.models.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	@Query("SELECT t FROM Ticket t WHERE t.passenger.passengerId = :passengerId ORDER BY t.dayBought")
	List<Ticket> getTicketsForPassenger(long passengerId);
	
	@Query("SELECT t FROM Ticket t WHERE t.flight.flightId = :flightId")
	List<Ticket> getTicketsForFlight(long flightId);
}
