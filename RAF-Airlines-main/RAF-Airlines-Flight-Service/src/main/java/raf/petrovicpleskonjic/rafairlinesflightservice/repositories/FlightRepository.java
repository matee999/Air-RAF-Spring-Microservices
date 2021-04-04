package raf.petrovicpleskonjic.rafairlinesflightservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import raf.petrovicpleskonjic.rafairlinesflightservice.models.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

	@Query("SELECT f FROM Flight f WHERE f.passengers.size < f.airplane.capacity AND f.canceled is false")
	List<Flight> getAvailableFlights();

	@Query("SELECT f FROM Flight f WHERE f.passengers.size < f.airplane.capacity AND f.canceled is false "
			+ "AND (lower(f.startDestination) LIKE lower(:startDestination) "
			+ "AND lower(f.endDestination) LIKE lower(:endDestination) "
			+ "AND (f.airplane.airplaneId = :airplaneId OR (f.distance BETWEEN :minDistance AND :maxDistance) OR (f.price BETWEEN :minPrice AND :maxPrice)))")
	List<Flight> searchFlights(Long airplaneId, String startDestination, String endDestination, Integer minDistance,
			Integer maxDistance, Float minPrice, Float maxPrice);
}
