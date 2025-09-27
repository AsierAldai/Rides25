package domain;

import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@XmlID
		@Id
		private String email;
		private String password;
		private String type;
		
		@XmlIDREF
		@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		private List<Mugimenduak> mugimenduak = new Vector<Mugimenduak>();
		
		public User() {
			super();
		}
		
		public User(String email, String password, String type) {
			this.email = email;
			this.password = password;
			this.type = type;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		public Mugimenduak addMugimendua(Mugimenduak mugi)  {
	        mugimenduak.add(mugi);
	        return mugi;
		}

		public List<Mugimenduak> getMugimenduak() {
			return mugimenduak;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			User other = (User) obj;
			if (email.equals(other.email)) {
				return true;
			}
				return false;
		}
		@Override
		public String toString() {
			return email;
		}
	}
