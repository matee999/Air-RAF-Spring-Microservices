package raf.petrovicpleskonjic.rafairlinesflightservice.controllers;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import raf.petrovicpleskonjic.rafairlinesflightservice.forms.requests.FindFlightRequest;
import raf.petrovicpleskonjic.rafairlinesflightservice.forms.requests.NewFlightRequest;
import raf.petrovicpleskonjic.rafairlinesflightservice.forms.responses.FlightResponse;
import raf.petrovicpleskonjic.rafairlinesflightservice.messages.FlightDeletedMessage;
import raf.petrovicpleskonjic.rafairlinesflightservice.models.Airplane;
import raf.petrovicpleskonjic.rafairlinesflightservice.models.Flight;
import raf.petrovicpleskonjic.rafairlinesflightservice.models.Passenger;
import raf.petrovicpleskonjic.rafairlinesflightservice.repositories.AirplaneRepository;
import raf.petrovicpleskonjic.rafairlinesflightservice.repositories.FlightRepository;
import raf.petrovicpleskonjic.rafairlinesflightservice.utils.UtilityMethods;
import raf.petrovicpleskonjic.rafairlinesflightservice.forms.responses.UserProfileResponse;

@RestController
@RequestMapping("/flight")
public class FlightController {

	private FlightRepository flightRepo;
	private AirplaneRepository airplaneRepo;

	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	Queue flightDeletedUserQueue;

	@Autowired
	Queue flightDeletedTicketQueue;

	@Autowired
	public FlightController(FlightRepository flightRepo, AirplaneRepository airplaneRepo) {
		this.flightRepo = flightRepo;
		this.airplaneRepo = airplaneRepo;
	}

	@GetMapping("/all")
	public ResponseEntity<List<Flight>> getAllFlights(@RequestHeader(value = "Authorization") String token) {
		try {
			ResponseEntity<Boolean> isAdmin = UtilityMethods.sendGet(Boolean.class,
					UtilityMethods.USER_SERVICE_URL + "admin-verification", token);

			if (!isAdmin.getBody())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			List<Flight> flights = flightRepo.findAll();

			return new ResponseEntity<>(flights, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/available")
	public ResponseEntity<List<Flight>> getAvailableFlights(@RequestParam int page,
			@RequestHeader(value = "Authorization") String token) {
		try {			
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(UtilityMethods.USER_SERVICE_URL + "my-profile");

			ResponseEntity<UserProfileResponse> response = UtilityMethods.sendGet(UserProfileResponse.class,
					builder.toUriString(), token);

			if (response.getBody() == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			List<Flight> flights = flightRepo.getAvailableFlights();
			ListIterator<Flight> iter = flights.listIterator();
			while (iter.hasNext()) {
				Flight flight = iter.next();
				for (Passenger passenger : flight.getPassengers()) {
					if (passenger.getPassengerId() == response.getBody().getUserId())
						iter.remove();
				}
			}

			Integer pageSize = 2;
			Integer fromIndex = (page - 1) * pageSize;

			if (flights.size() <= fromIndex)
				return new ResponseEntity<>(Collections.emptyList(), HttpStatus.ACCEPTED);

			return new ResponseEntity<>(flights.subList(fromIndex, Math.min(fromIndex + pageSize, flights.size())),
					HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/flight-by-id")
	public ResponseEntity<FlightResponse> getFlightById(@RequestParam long flightId) {
		try {
			Optional<Flight> flight = flightRepo.findById(flightId);

			if (!flight.isPresent())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			Boolean isFull = flight.get().getPassengers().size() >= flight.get().getAirplane().getCapacity();

			return new ResponseEntity<>(new FlightResponse(flightId, flight.get().getDistance(),
					flight.get().getPrice(), isFull, flight.get().getStartDestination(),
					flight.get().getEndDestination(), flight.get().getAirplane()), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/search")
	public ResponseEntity<List<Flight>> searchFlights(@RequestBody FindFlightRequest request) {
		try {
			List<Flight> flights = flightRepo.searchFlights(request.getAirplaneId(), request.getStartDestination(),
					request.getEndDestination(), request.getMinDistance(), request.getMaxDistance(),
					request.getMinPrice(), request.getMaxPrice());

			return new ResponseEntity<>(flights, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Flight> addFlight(@RequestBody NewFlightRequest request,
			@RequestHeader(value = "Authorization") String token) {
		try {
			ResponseEntity<Boolean> isAdmin = UtilityMethods.sendGet(Boolean.class,
					UtilityMethods.USER_SERVICE_URL + "admin-verification", token);

			if (!isAdmin.getBody())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			Optional<Airplane> airplane = airplaneRepo.findById(request.getAirplaneId());
			if (!airplane.isPresent())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			Flight flight = new Flight(airplane.get(), request.getStartDestination(), request.getEndDestination(),
					request.getDistance(), request.getPrice(), false);

			flightRepo.saveAndFlush(flight);
			return new ResponseEntity<>(flight, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteFlight(@RequestParam Long flightId,
			@RequestHeader(value = "Authorization") String token) {
		try {
			ResponseEntity<Boolean> isAdmin = UtilityMethods.sendGet(Boolean.class,
					UtilityMethods.USER_SERVICE_URL + "admin-verification", token);

			if (!isAdmin.getBody())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			Optional<Flight> flight = flightRepo.findById(flightId);
			if (!flight.isPresent())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			if (!flight.get().getPassengers().isEmpty()) {
				for (Passenger passenger : flight.get().getPassengers()) {

					String message = new ObjectMapper()
							.writeValueAsString(new FlightDeletedMessage(passenger.getPassengerId(),
									flight.get().getFlightId(), flight.get().getDistance(), flight.get().getPrice()));

					// Send information to User service:
					jmsTemplate.convertAndSend(flightDeletedUserQueue, message);

					// Send information to Ticket service:
					jmsTemplate.convertAndSend(flightDeletedTicketQueue, message);
				}
			}

			flight.get().setCanceled(true);
			flightRepo.save(flight.get());
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
