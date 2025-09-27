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
public class Alerta {
	
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int zenb;
	
	private String origin;
	private String destination;
	private Date date;
	
	private Passenger p;
	
	public Alerta(int zenb, String origin, String destination, Date date, Passenger p) {
		this.zenb=zenb;
		this.origin=origin;
		this.destination=destination;
		this.date=date;
		this.p=p;
	}
	public Alerta(String origin, String destination, Date date, Passenger p) {
		super()	;
		this.origin=origin;
		this.destination=destination;
		this.date=date;
		this.p=p;
		}
	public int getZenb() {
		return zenb;
	}
	public void setZenb(int zenb) {
		this.zenb = zenb;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Passenger getP() {
		return p;
	}
	public void setP(Passenger p) {
		this.p = p;
	}

	
}
