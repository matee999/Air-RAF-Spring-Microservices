package raf.petrovicpleskonjic.rafairlinesuserservice.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	private Integer miles;
	
	private String name;
	private String surname;
		
	@JsonIgnore
	private String password;
	
	@Column(unique = true)
	private String email;
	
	@Column(unique = true)
	private String passport;
	
	@ManyToOne
	private Tier tier;
	
	@OneToMany(mappedBy = "owner")
	private List<CreditCard> creditCards;
	
	public User() {}
	
	public User(Integer miles, String name, String surname, String email, String password, String passport) {
		this.miles = miles;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.passport = passport;
	}

	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public Integer getMiles() {
		return miles;
	}
	
	public void setMiles(Integer miles) {
		this.miles = miles;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassport() {
		return passport;
	}
	
	public void setPassport(String passport) {
		this.passport = passport;
	}
	
	public Tier getTier() {
		return tier;
	}
	
	public void setTier(Tier tier) {
		this.tier = tier;
	}
	
}
