package raf.petrovicpleskonjic.rafairlinesuserservice.forms.requests.user;

public class UpdateRequest {

	private String name;
	private String surname;
	private String email;
	private String passport;
	
	public UpdateRequest(String name, String surname, String email, String passport) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.passport = passport;
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
