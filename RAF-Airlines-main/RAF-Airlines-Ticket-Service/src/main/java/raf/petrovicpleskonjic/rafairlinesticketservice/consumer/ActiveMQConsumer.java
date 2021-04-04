package raf.petrovicpleskonjic.rafairlinesticketservice.consumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import raf.petrovicpleskonjic.rafairlinesticketservice.messages.FlightDeletedMessage;
import raf.petrovicpleskonjic.rafairlinesticketservice.models.Ticket;
import raf.petrovicpleskonjic.rafairlinesticketservice.repositories.TicketRepository;

@Component
public class ActiveMQConsumer {
	
	private TicketRepository ticketRepo;
	
	@Autowired
	public ActiveMQConsumer(TicketRepository ticketRepo) {
		this.ticketRepo = ticketRepo;
	}

	@JmsListener(destination = "flight-deleted.ticket.queue")
	public void consume(String jsonMessage) {
		try {
			FlightDeletedMessage message = new ObjectMapper().readValue(jsonMessage, FlightDeletedMessage.class);
			
			List<Ticket> tickets = ticketRepo.getTicketsForFlight(message.getFlightId());
			
			for (Ticket ticket : tickets) {
				ticket.setCanceled(true);
				ticketRepo.save(ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
