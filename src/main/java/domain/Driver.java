package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Driver extends User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float wallet;
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST, orphanRemoval = true)
	private List<Ride> rides = new Vector<Ride>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Car> cars=new Vector<Car>();
	
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Erreklamazioa> erreklamazioak=new Vector<Erreklamazioa>();
	
	public Driver() {
		super();
	}

	public Driver(String email, String password) {
		super(email, password, "Driver");
		this.setEmail(email);
		this.setPassword(password);
		this.setType("Driver");
		this.wallet = 0;
		}
	
	
	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	
	public float getWallet() {
		return wallet;
	}

	public void setWallet(float wallet) {
		this.wallet = wallet;
	}	
	@Override
	public String toString(){
		return super.toString();
	}
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, int nPlaces, float price, Car car)  {
        Ride ride=new Ride(from,to,date,nPlaces,price,car, this);
        rides.add(ride);
        return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(index);
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			found=true;
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}
	public List<Car> getCars() {
		return cars;
	}

	public Car addCar(String license, String brand, String color,int seats, Driver d) {
		Car carsin = new Car (license,brand,color, seats, d);
		cars.add(carsin);
		return carsin;
	}

	public List<Erreklamazioa> getErreklamazioak() {
		return erreklamazioak;
	}
	
	public Erreklamazioa addErreklamazio(String zergaitia,Passenger pass, Ride ride, RideBooked rb) {
		Erreklamazioa errek = new Erreklamazioa (zergaitia,pass,ride,rb, this);
		erreklamazioak.add(errek);
		return errek;
	}
	
}
