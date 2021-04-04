package raf.petrovicpleskonjic.rafairlinesuserservice.forms.responses;

public class UserProfileResponse {
	
	private long userId;

	private Integer miles;
	
	private String name;
	private String surname;
	
	private String email;
	private String passport;
	
	public UserProfileResponse() {}
	
	public UserProfileResponse(long userId, Integer miles, String name, String surname, String email, String passport) {
		this.userId = userId;
		this.miles = miles;
		this.name = name;
		this.surname = surname;
		this.email = email;
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
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	
	
}
