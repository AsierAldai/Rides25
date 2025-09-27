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
	public class Passenger extends User implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private float wallet;

		@XmlIDREF
		@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST, orphanRemoval=true)
		private List<RideBooked> bookedrides=new Vector<RideBooked>();
		
		@XmlIDREF
		@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST, orphanRemoval=true)
		private List<Alerta> alerts=new Vector<Alerta>();

		public Passenger() {
			super();
		}

		public Passenger(String email, String password) {
			super(email, password, "Passenger");
			this.setEmail(email);
			this.setPassword(password);
			this.setType("Passenger");
			this.wallet = 0;
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
		public RideBooked addBookRide(RideBooked rb)  {
	        bookedrides.add(rb);
	        return rb;
		}

		public RideBooked removeBookRide(int bookNumber) {
			boolean found=false;
			int index=0;
			RideBooked r=null;
			while (!found && index<=this.bookedrides.size()) {
				r=this.bookedrides.get(index);
				if (r.getBookNumber()==bookNumber) {
					found=true;
				}else {
					index++;
				}
			}
				
			if (found) {
				this.bookedrides.remove(index);
				return r;
			} else {
				return null;
			}
		}
		public List<RideBooked> getBookedrides() {
			return bookedrides;
		}

		public List<Alerta> getAlerts() {
			return alerts;
		}

		public Alerta addAlerts(Alerta a) {
			alerts.add(a);
	        return a;
		}
		
		
		
	}

