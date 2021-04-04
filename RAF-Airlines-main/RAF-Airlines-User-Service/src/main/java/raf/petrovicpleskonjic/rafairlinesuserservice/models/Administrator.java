package raf.petrovicpleskonjic.rafairlinesuserservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Administrator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long administratorId;

	private String username;
	
	@JsonIgnore
	private String password;
	
	public Administrator() {}
	
	public Administrator(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public long getAdministratorId() {
		return administratorId;
	}
	
	public void setAdministratorId(long administratorId) {
		this.administratorId = administratorId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
