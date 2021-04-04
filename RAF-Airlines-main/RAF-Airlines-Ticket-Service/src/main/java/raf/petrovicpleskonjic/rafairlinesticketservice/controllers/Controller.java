package raf.petrovicpleskonjic.rafairlinesticketservice.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import raf.petrovicpleskonjic.rafairlinesticketservice.forms.requests.NewTicketRequest;
import raf.petrovicpleskonjic.rafairlinesticketservice.forms.requests.PassengerPurchaseRequest;
import raf.petrovicpleskonjic.rafairlinesticketservice.forms.responses.FlightResponse;
import raf.petrovicpleskonjic.rafairlinesticketservice.forms.responses.TicketResponse;
import raf.petrovicpleskonjic.rafairlinesticketservice.forms.responses.UserProfileResponse;
import raf.petrovicpleskonjic.rafairlinesticketservice.forms.responses.UserPurchaseInformationResponse;
import raf.petrovicpleskonjic.rafairlinesticketservice.messages.FlightAssignedMessage;
import raf.petrovicpleskonjic.rafairlinesticketservice.models.Flight;
import raf.petrovicpleskonjic.rafairlinesticketservice.models.Passenger;
import raf.petrovicpleskonjic.rafairlinesticketservice.models.Ticket;
import raf.petrovicpleskonjic.rafairlinesticketservice.repositories.FlightRepository;
import raf.petrovicpleskonjic.rafairlinesticketservice.repositories.PassengerRepository;
import raf.petrovicpleskonjic.rafairlinesticketservice.repositories.TicketRepository;
import raf.petrovicpleskonjic.rafairlinesticketservice.utils.UtilityMethods;

@RestController
@RequestMapping("")
public class Controller {

	private PassengerRepository passengerRepo;
	private TicketRepository ticketRepo;
	private FlightRepository flightRepo;
	
	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	Queue flightAssignedUserQueue;

	@Autowired
	Queue flightAssignedFlightQueue;

	@Autowired
	public Controller(PassengerRepository passengerRepo, TicketRepository ticketRepo, FlightRepository flightRepo) {
		this.passengerRepo = passengerRepo;
		this.ticketRepo = ticketRepo;
		this.flightRepo = flightRepo;
	}

	@GetMapping("/get-my-flights")
	public ResponseEntity<List<Flight>> getMyFlights(@RequestHeader(value = "Authorization") String token) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(UtilityMethods.USER_SERVICE_URL + "my-profile");

			ResponseEntity<UserProfileResponse> response = UtilityMethods.sendGet(UserProfileResponse.class,
					builder.toUriString(), token);

			if (response.getBody() == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			List<Flight> flights = flightRepo.getFlightsForPassenger(response.getBody().getUserId());

			return new ResponseEntity<>(flights, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/get-my-tickets")
	public ResponseEntity<List<TicketResponse>> getMyTickets(@RequestHeader(value = "Authorization") String token) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(UtilityMethods.USER_SERVICE_URL + "my-profile");

			ResponseEntity<UserProfileResponse> response = UtilityMethods.sendGet(UserProfileResponse.class,
					builder.toUriString(), token);

			if (response.getBody() == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			List<Ticket> tickets = ticketRepo.getTicketsForPassenger(response.getBody().getUserId());
			List<TicketResponse> ticketsResponse = new ArrayList<TicketResponse>();
			
			for (Ticket t : tickets) {
				UriComponentsBuilder flightRequestBuilder = UriComponentsBuilder
						.fromHttpUrl(UtilityMethods.FLIGHT_SERVICE_URL + "flight/flight-by-id")
						.queryParam("flightId", t.getFlight().getFlightId());
				
				ResponseEntity<FlightResponse> flightResponse = UtilityMethods.sendGet(FlightResponse.class,
						flightRequestBuilder.toUriString(), token);
				
				ticketsResponse.add(new TicketResponse(t.getTicketId(), flightResponse.getBody(), t.getDayBought(), t.isCanceled()));
			}
			
			Collections.sort(ticketsResponse, new Comparator<TicketResponse>() {

				@Override
				public int compare(TicketResponse t1, TicketResponse t2) {
					return t1.getDayBought().compareTo(t2.getDayBought());
				}
				
			});

			return new ResponseEntity<>(ticketsResponse, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/buy-ticket")
	public ResponseEntity<TicketResponse> buyTicket(@RequestBody NewTicketRequest request,
			@RequestHeader(value = "Authorization") String token) {
		try {
			UriComponentsBuilder profileRequestBuilder = UriComponentsBuilder
					.fromHttpUrl(UtilityMethods.USER_SERVICE_URL + "my-profile");

			ResponseEntity<UserProfileResponse> response = UtilityMethods.sendGet(UserProfileResponse.class,
					profileRequestBuilder.toUriString(), token);

			if (response.getBody() == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			Passenger passenger = null;
			if (passengerRepo.existsById(response.getBody().getUserId()))
				passenger = passengerRepo.findById(response.getBody().getUserId()).get();
			else
				passenger = passengerRepo.save(new Passenger(response.getBody().getUserId()));
			
			List<Ticket> ticketsForPassenger = ticketRepo.getTicketsForPassenger(passenger.getPassengerId());
			for (Ticket t : ticketsForPassenger) {
				if (t.getFlight().getFlightId() == request.getFlightId())
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			UriComponentsBuilder flightRequestBuilder = UriComponentsBuilder
					.fromHttpUrl(UtilityMethods.FLIGHT_SERVICE_URL + "flight/flight-by-id")
					.queryParam("flightId", request.getFlightId());

			ResponseEntity<FlightResponse> flightResponse = UtilityMethods.sendGet(FlightResponse.class,
					flightRequestBuilder.toUriString(), token);

			if (flightResponse.getBody() == null || flightResponse.getBody().isFull())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			PassengerPurchaseRequest purchaseRequest = new PassengerPurchaseRequest(passenger.getPassengerId(),
					request.getCreditCardNumber());

			ResponseEntity<UserPurchaseInformationResponse> purchaseResponse = UtilityMethods.sendPost(
					UserPurchaseInformationResponse.class,
					UtilityMethods.USER_SERVICE_URL + "/credit-card/get-purchase-information", token, purchaseRequest);

			if (purchaseResponse.getBody() == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			Float totalAmountToPay = flightResponse.getBody().getPrice()
					- (purchaseResponse.getBody().getSalePercentage() / 100 * flightResponse.getBody().getPrice());

			System.out
					.println("Requesting " + totalAmountToPay + " funds for passenger " + response.getBody().getName());

			Flight flight = flightRepo.save(new Flight(flightResponse.getBody().getFlightId()));
			Ticket ticket = ticketRepo.save(new Ticket(passenger, flight));

			String message = new ObjectMapper().writeValueAsString(new FlightAssignedMessage(passenger.getPassengerId(),
					request.getFlightId(), flightResponse.getBody().getDistance()));

			jmsTemplate.convertAndSend(flightAssignedFlightQueue, message);
			jmsTemplate.convertAndSend(flightAssignedUserQueue, message);
			
			return new ResponseEntity<>(new TicketResponse(ticket.getTicketId(), flightResponse.getBody(), ticket.getDayBought(), false), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
