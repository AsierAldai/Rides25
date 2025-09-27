package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Car {

	@XmlID
	@Id 
	private String licensePlate;
	private String brand;
	private String color;
	private int seats;
	
	private Driver driver;
	
	public Car(String licensePlate, String brand, String color, int seats, Driver d) {
		this.licensePlate=licensePlate;
		this.brand=brand;
		this.color=color;
		this.seats=seats;
		this.driver = d;
	}

	public String getlicensePlate() {
		return licensePlate;
	}

	public void setlicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String toString() {
		return licensePlate+"/"+brand+"/"+seats;
	}
	
}
