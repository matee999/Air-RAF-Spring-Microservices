package raf.petrovicpleskonjic.rafairlinesuserservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tierId;
	
	private String name;
	
	private Float salePercentage;
	
	private int threshold;
	
	public Tier() {}

	public Tier(String name, int threshold, Float salePercentage) {
		this.name = name;
		this.threshold = threshold;
		this.salePercentage = salePercentage;
	}

	public long getTierId() {
		return tierId;
	}

	public void setTierId(long tierId) {
		this.tierId = tierId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	public Float getSalePercentage() {
		return salePercentage;
	}
	
	public void setSalePercentage(Float salePercentage) {
		this.salePercentage = salePercentage;
	}
}
