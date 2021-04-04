package raf.petrovicpleskonjic.rafairlinesticketservice.forms.responses;

import java.util.Date;

public class TicketResponse {

	private long ticketId;
		
	private FlightResponse flight;
	
	private Date dayBought;
	
	private boolean canceled;
	
	public TicketResponse() {}

	public TicketResponse(long ticketId, FlightResponse flight, Date dayBought, boolean canceled) {
		this.ticketId = ticketId;
		this.flight = flight;
		this.dayBought = dayBought;
		this.canceled = canceled;
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public FlightResponse getFlight() {
		return flight;
	}

	public void setFlight(FlightResponse flight) {
		this.flight = flight;
	}

	public Date getDayBought() {
		return dayBought;
	}

	public void setDayBought(Date dayBought) {
		this.dayBought = dayBought;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
}
