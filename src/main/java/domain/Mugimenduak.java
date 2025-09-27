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
public class Mugimenduak {
	
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private int mugiZenb;
	private String egindakoa;
	private float cant;
	private Date date;
	
	private User u;
	
	public Mugimenduak(int mugiZenb, String egindakoak, float cant, User u, Date date) {
		this.mugiZenb=mugiZenb;
		this.egindakoa=egindakoak;
		this.cant=cant;
		this.u=u;
		this.date=date;
	}
	public Mugimenduak(String egindakoak, float cant, User u, Date date) {
		super()	;
		this.egindakoa=egindakoak;
		this.cant=cant;
		this.u=u;
		this.date=date;
		}

	public int getMugiZenb() {
		return mugiZenb;
	}
	public void setMugiZenb(int mugiZenb) {
		this.mugiZenb = mugiZenb;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getU() {
		return u;
	}
	public void setU(User u) {
		this.u = u;
	}
	public void setEgindakoa(String egindakoa) {
		this.egindakoa = egindakoa;
	}
	public void setCant(float cant) {
		this.cant = cant;
	}
	public String getEgindakoa() {
		return egindakoa;
	}

	public float getCant() {
		return cant;
	}
	@Override
	public String toString(){
		return egindakoa+"/"+cant+"/"+date;
	}
}
