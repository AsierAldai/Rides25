package domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class RideBooked implements Serializable {

	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int bookNumber;
	private int seatsBooked;
	private boolean aproved;
	
	@ManyToOne
	private Ride ride;
	
	@ManyToOne
	private Passenger passenger;
	
	public RideBooked(int bookNumber, int seatsBooked, Ride ride, Passenger passenger) {
		super();
		this.bookNumber=bookNumber;
		this.seatsBooked=seatsBooked;
		this.ride=ride;
		this.passenger=passenger;
		this.aproved=false;
	}

	

	public RideBooked(int seatsBooked, Ride ride, Passenger passenger) {
		super();
		this.seatsBooked=seatsBooked;
		this.ride=ride;
		this.passenger=passenger;
		this.aproved=false;
	}

	public int getBookNumber() {
		return bookNumber;
	}

	public void setBookNumber(int bookNumber) {
		this.bookNumber = bookNumber;
	}

	public int getSeatsBooked() {
		return seatsBooked;
	}

	public void setSeatsBooked(int seatsBooked) {
		this.seatsBooked = seatsBooked;
	}

	public boolean isAproved() {
		return aproved;
	}

	public void setAproved(boolean aproved) {
		this.aproved = aproved;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	
	@Override
	public String toString(){
		return ride+"/"+"/"+seatsBooked+"/"+aproved+"/"+"/"+passenger;  
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		RideBooked other = (RideBooked) obj;
		if (this.bookNumber==(other.bookNumber) && this.passenger.equals(other.passenger) && this.ride.equals(other.ride)) {
			return true;
		}
		return false;
	}
	public void removeBookRide() {
		System.out.println(bookNumber);
		System.out.println(passenger);

		passenger.removeBookRide(bookNumber);
		ride.removeBookRide(bookNumber);
	}
	
	public int getRideNumber() {
		return ride.getRideNumber();
	}
	
	public float getRidePrice() {
		System.out.println(ride);
		return ride.getPrice();
	}
	
	public String getPassEmail() {
		return passenger.getEmail();
	}
	
	public List<RideBooked> getPassBookings(){
		return passenger.getBookedrides();
	}
	
	public List<RideBooked> getRideBookings(){
		return ride.getBookings();
	}

	
}
