package raf.petrovicpleskonjic.rafairlinesuserservice.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CreditCard {

	@Id
	private String number;
	
	private Integer ccv;
	
	@ManyToOne
	@JsonIgnore
	private User owner;
	
	public CreditCard() {}

	public CreditCard(String number, Integer ccv, User owner) {
		this.number = number;
		this.ccv = ccv;
		this.owner = owner;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getCcv() {
		return ccv;
	}

	public void setCcv(Integer ccv) {
		this.ccv = ccv;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
