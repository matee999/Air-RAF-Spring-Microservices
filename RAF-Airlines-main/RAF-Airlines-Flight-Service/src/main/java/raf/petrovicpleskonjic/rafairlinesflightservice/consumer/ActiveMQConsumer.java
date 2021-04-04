package raf.petrovicpleskonjic.rafairlinesflightservice.consumer;

import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import raf.petrovicpleskonjic.rafairlinesflightservice.messages.FlightAssignedMessage;
import raf.petrovicpleskonjic.rafairlinesflightservice.models.Flight;
import raf.petrovicpleskonjic.rafairlinesflightservice.models.Passenger;
import raf.petrovicpleskonjic.rafairlinesflightservice.repositories.FlightRepository;
import raf.petrovicpleskonjic.rafairlinesflightservice.repositories.PassengerRepository;

@Component
public class ActiveMQConsumer {
	
	private PassengerRepository passengerRepo;
	private FlightRepository flightRepo;
	
	@Autowired
	public ActiveMQConsumer(PassengerRepository passengerRepo, FlightRepository flightRepo) {
		this.passengerRepo = passengerRepo;
		this.flightRepo = flightRepo;
	}

	@JmsListener(destination = "flight-assigned.flight.queue")
	public void consume(String jsonMessage) {
		try {
			FlightAssignedMessage message = new ObjectMapper().readValue(jsonMessage, FlightAssignedMessage.class);
			
			Optional<Flight> flight = flightRepo.findById(message.getFlightId());
			if (!flight.isPresent())
				return;
			
			Passenger passenger = null;
			if (passengerRepo.existsById(message.getPassengerId()))
				passenger = passengerRepo.findById(message.getPassengerId()).get();
			else
				passenger = passengerRepo.save(new Passenger(message.getPassengerId()));
			
			Hibernate.initialize(flight.get().getPassengers());
			flight.get().getPassengers().add(passenger);
			flightRepo.save(flight.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
