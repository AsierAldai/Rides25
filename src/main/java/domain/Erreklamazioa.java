package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Erreklamazioa {
	
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int zenb;
	private String zergaitia;
	private Passenger pass;
	private Ride ride;
	private RideBooked rb;
	
	private Driver d;
	
	public Erreklamazioa(int zenb, String zergaitia,Passenger pass, Ride ride, RideBooked rb, Driver driver){
		this.zenb=zenb;
		this.zergaitia=zergaitia;
		this.pass=pass;
		this.ride=ride;
		this.rb=rb;
		this.d=driver;
	}
	public Erreklamazioa(String zergaitia,Passenger pass, Ride ride, RideBooked rb, Driver driver) {
		super()	;
		this.zergaitia=zergaitia;
		this.pass=pass;
		this.ride=ride;
		this.rb=rb;
		this.d=driver;
		}
	
	public int getZenb() {
		return zenb;
	}
	public void setZenb(int zenb) {
		this.zenb = zenb;
	}
	public String getZergaitia() {
		return zergaitia;
	}
	public void setZergaitia(String zergaitia) {
		this.zergaitia = zergaitia;
	}
	public Passenger getPass() {
		return pass;
	}
	public void setPass(Passenger pass) {
		this.pass = pass;
	}
	public Ride getRide() {
		return ride;
	}
	public void setRide(Ride ride) {
		this.ride = ride;
	}
	public Driver getD() {
		return d;
	}
	public void setD(Driver d) {
		this.d = d;
	}
	public RideBooked getRb() {
		return rb;
	}
	public void setRb(RideBooked rb) {
		this.rb = rb;
	}
	
	public String getDEmail() {
		return d.getEmail();
	}
	
	public String getPEmail() {
		return pass.getEmail();
	}
	
	@Override
	public String toString(){
		return zenb+"/"+pass+"/"+ride+"/"+d;
	}
}
