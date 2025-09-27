package dataAccess;

import java.io.File;
import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();

				  System.out.println("File deleted");
				} else {
				  System.out.println("Operation failed");
				}
		}
		open();
		/*if  (c.isDatabaseInitialized())initializeDB();*/
		
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	/*public void initializeDB(){
		
		db.getTransaction().begin();

		try {

		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=1; year+=1;}  
	    
		   
		    //Create drivers 
			Driver driver1=new Driver("driver1@gmail.com","Aitor Fernandez");
			Driver driver2=new Driver("driver2@gmail.com","Ane Gaztañaga");
			Driver driver3=new Driver("driver3@gmail.com","Test driver");

			
			//Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);

			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);
			
			driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
			driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5);

			driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 3);

			
						
			db.persist(driver1);
			db.persist(driver2);
			db.persist(driver3);

	
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}*/
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
			TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
			List<String> cities = query.getResultList();
			return cities;
		
	}
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList(); 
		return arrivingCities;
		
	}
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, Car car, String driverEmail) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price, car);
			//next instruction can be obviated
			db.persist(driver); 
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

		List<Ride> res = new ArrayList<>();	
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class);   
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
	 	 for (Ride ride:rides){
		   res.add(ride);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",Date.class);   
		
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	return res;
	}
	

public void open(){
	
		
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
	}

	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
	public boolean login(String email, String password, String type) {
		List<User> res = null;
		
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.email=?1 AND u.password=?2 AND u.type=?3",User.class); 
		
		query.setParameter(1, email);
		query.setParameter(2, password);
		query.setParameter(3, type);
	 	res = query.getResultList();
	 	
	 	if(res.size()!=0){
	 		return true;
	 	}else {
	 		return false;
	 	}
	}
	
	public boolean register(String email, String password, String type) {
		
		db.getTransaction().begin();
		
		List<User> res = null;
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u WHERE u.email=?1 AND u.password=?2 AND u.type=?3",User.class); 
		
		
		query.setParameter(1, email);
		query.setParameter(2, password);
		query.setParameter(3, type);
	 	res = query.getResultList();
	 	
	 	if(res.size()==0){
	 		if(type.equals("Driver")) {
	 			Driver d = new Driver(email, password);
	 			db.persist(d);
	 		}else {
	 			Passenger p = new Passenger(email, password);
	 			db.persist(p);
	 		}
	 		db.getTransaction().commit();
	 		return true;
	 	}else {
	 		db.getTransaction().commit();
	 		return false;
	 	}
		
	}
	public boolean withdrawMoney(String email, int amount) {
		db.getTransaction().begin();
		
		Driver driver = null;
		driver = db.find(Driver.class, email);
		
		if(driver!=null) {
			if(driver.getWallet()>=amount) {
				driver.setWallet(driver.getWallet()-amount);
				Mugimenduak m = new Mugimenduak("Dirua atera da", amount, driver, new Date());
				driver.addMugimendua(m);
				db.getTransaction().commit();
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	
	public boolean addMoney(String email, int amount) {
		db.getTransaction().begin();
		
		Passenger passenger = null;
		passenger = db.find(Passenger.class, email);
		
		if(passenger!=null && amount >= 0) {
			Mugimenduak m = new Mugimenduak("Dirua sartu da", amount, passenger, new Date());
			passenger.setWallet(passenger.getWallet()+amount);
			passenger.addMugimendua(m);
			db.getTransaction().commit();
			return true;
		}else {
			return false;
		}
	}
	
	public ArrayList<RideBooked> getBookingsDriver(String email, String password){
		
		ArrayList<RideBooked> ema = new ArrayList<RideBooked>();;
		Driver driver = null;
		driver = db.find(Driver.class, email);
		
		if(driver!=null) {
			for(Ride r : driver.getRides()) {
				for(RideBooked rb : r.getBookings()) {
					if(rb.isAproved() == false){
						ema.add(rb);
					}
				}
			}
		}
		return ema;
	}
	
	public boolean bookRide(Passenger passenger, String driveremail, int seatsBooked, int rideID) {
			db.getTransaction().begin();
	
			Driver driver = null;
			driver = db.find(Driver.class, driveremail);
			
			Passenger pass = null;
			pass = db.find(Passenger.class, passenger.getEmail());
			System.out.println(passenger.getEmail());
			
			Ride ride = null;
			ride = db.find(Ride.class, rideID);
			if (ride.getnPlaces()<seatsBooked || ride.getnPlaces() == 0) {
				return false;
			}
		 	ride.createBook(driver,pass,seatsBooked);		 	
			
			Mugimenduak mu = new Mugimenduak("Erreserba bat egin da",ride.getPrice()*seatsBooked, pass, new Date());
			pass.addMugimendua(mu);
			pass.setWallet(pass.getWallet()-mu.getCant());
			ride.setBetMinimum(ride.getnPlaces()-seatsBooked);
			db.getTransaction().commit();
			return true;
	
	}
	
	public boolean accept(int BookNumber) {
		
		boolean res = false;
		
		db.getTransaction().begin();
		
		RideBooked rbm = null;
		rbm = db.find(RideBooked.class, BookNumber);
		
		Passenger pass = null;
		pass = db.find(Passenger.class, rbm.getPassEmail());		
		
		for(RideBooked rbp : pass.getBookedrides()) {
			if(rbp.getRide().equals(rbm.getRide())) {
				rbp.setAproved(true);
				res = true;
			}
		}
		
		db.persist(pass);
		
		
		db.getTransaction().commit();
		
		return res;
	}
	
	public boolean decline(int BookNumber) {
			
			db.getTransaction().begin();
			
			RideBooked rbm = null;
			rbm = db.find(RideBooked.class, BookNumber);
			
			rbm.getRideBookings().remove(rbm);
			rbm.getPassBookings().remove(rbm);
			db.remove(rbm);
			
			db.getTransaction().commit();
			
			return true;
		}
	
	public Ride getRideByEmail(String email, String from, String to, Date date) {
		Driver driver = db.find(Driver.class, email);
		System.out.println(driver);
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3 AND r.driver=?4", Ride.class);
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		query.setParameter(4, driver);
		System.out.println(query.getSingleResult().toString());
		
		return query.getSingleResult();
	}
	
	
	public List<RideBooked> getBookingsPass(String email, String password){
		
		List<RideBooked> ema = new ArrayList<RideBooked>();
		
		Passenger passenger = null;
		passenger = db.find(Passenger.class, email);
		
		RideBooked rbm = null;
		
		if(passenger!=null) {
			for(RideBooked rb : passenger.getBookedrides()) {
				rbm = db.find(RideBooked.class, rb.getBookNumber());
				ema.add(rbm);
			}
		}
		return ema;
	}
	public List<Mugimenduak> getMugimenduak(String uGmail){
		
		List<Mugimenduak> ema = null;
		User us = null;
		us = db.find(User.class, uGmail);
		
		if(us != null) {
			ema = us.getMugimenduak();
		}
		return ema;
	}
	public boolean bidaiaEginda(int BookNumber, String pGmail, int b) {
		
		db.getTransaction().begin();
		Passenger p = null;
		p = db.find(Passenger.class, pGmail);
		
		RideBooked rb = null;
		rb = db.find(RideBooked.class, BookNumber);
		
		Ride r = null;
		r = db.find(Ride.class, rb.getRideNumber());
		r.setRating(b);
		
		Driver d = null;
		d = db.find(Driver.class, r.getEmail());
		
		if (r.getDate().before(new Date())) {
			r.removeBookRide(BookNumber);
			p.removeBookRide(BookNumber);
			db.persist(r);
			db.persist(p);
			
			Mugimenduak mu = new Mugimenduak("Bidaia arrakastarekin bukatu da",r.getPrice()*rb.getSeatsBooked(), d, new Date());
			p.addMugimendua(mu);
			
			d.setWallet(d.getWallet()+mu.getCant());
			
			db.persist(d);
			db.getTransaction().commit();
			return true;
		}else {
			return false;
		}
		
	}
	public List<Ride> getRidesDriver(String dGmail){
		
		List<Ride> rides = null;
		Driver dr = null;
		dr = db.find(Driver.class, dGmail);
		
		rides = dr.getRides();
		
		return rides;
	}
	public void deleteRide(int RideNumber, String dGmail) {
		System.out.println(RideNumber);
		
		db.getTransaction().begin();
		
		Ride rid = db.find(Ride.class, RideNumber);
		
		Passenger p = null;
		
		Driver dr = null;
		dr = db.find(Driver.class, dGmail);
		db.persist(dr);
		if (rid !=null){
			if (rid.getBookings() != null) {
				List<RideBooked> rb = rid.getBookings();
				for(RideBooked rbm : rb) {
					
					rbm.removeBookRide();
					dr.removeRide(rid.getFrom(), rid.getTo(), rid.getDate());
					p = rbm.getPassenger();
					
					Mugimenduak mu = new Mugimenduak("Bidaia ezabatua izan da",rbm.getSeatsBooked()*rid.getPrice(), dr, new Date());
					Mugimenduak mu2 = new Mugimenduak("Bidaia ezabatua izan da",rbm.getSeatsBooked()*rid.getPrice(), p, new Date());
					dr.addMugimendua(mu);
					p.addMugimendua(mu2);
					
					p.setWallet(p.getWallet()+mu.getCant());
						
				}
			}
			db.remove(rid);
		}
		
		db.getTransaction().commit();
	}
	public float getDriversWallet(String dGmail) {
		
		Driver dr = null;
		dr = db.find(Driver.class, dGmail);
		
		return dr.getWallet();
	}
	public float getPassengersWallet(String pGmail) {
		
		Passenger ps = null;
		ps = db.find(Passenger.class, pGmail);
		System.out.println(ps.toString());
		
		return ps.getWallet();
	}
	public boolean addCar(String license, String brand, String color, int seats,  String dGmail) {
		db.getTransaction().begin();
		Driver dr = null;
		dr = db.find(Driver.class, dGmail);
		
		Car ca = null;
		ca = dr.addCar(license, brand, color,seats, dr);
		db.persist(dr);
		db.getTransaction().commit();
		if(ca!=null) {
			return true;
		}else {
			return false;
		}
	}
	public List<Car> getCars(String dGmail){
		
		Driver dr = null;
		dr = db.find(Driver.class, dGmail);
		
		List<Car> cars = null;
		
		if(dr!=null) {
			cars = dr.getCars();
		}
		return cars;
	}
	
	public void erreklamazioa(String zerg, String pGmail, int BookNumber) {
		
		db.getTransaction().begin();
		
		RideBooked rb = null;
		rb = db.find(RideBooked.class, BookNumber);
		
		Ride ri = null;
		ri = db.find(Ride.class, rb.getRideNumber());
		
		Driver d = null;
		d = db.find(Driver.class, ri.getEmail());
		
		Passenger p = null;
		p = db.find(Passenger.class, pGmail);
		
		if(!zerg.equals("")) {
			d.addErreklamazio(zerg,p,ri,rb);
			
		}
		
		db.persist(d);
		
		db.getTransaction().commit();
	}
	public List<Erreklamazioa> geterreklamazio(String dGmail){
		
		Driver dr = null;
		dr = db.find(Driver.class, dGmail);
		
	List<Erreklamazioa> errek = null;
		
		if(dr!=null) {
			errek = dr.getErreklamazioak();
		}
		return errek;
	}
	public List<Driver> getAllDrivers(){
		
		List<Driver> res = null;
		TypedQuery<Driver> query = db.createQuery("SELECT d FROM Driver d ",Driver.class);
		
		res = query.getResultList();
		
		return res;
	}
	public void ezabatuErreklamazioa(int eID) {
		
		db.getTransaction().begin();
		
		List<Driver> dr = this.getAllDrivers();
		Driver d = null;
		
		for(int i = 0; i<dr.size();i++) {
			
			d = db.find(Driver.class, dr.get(i).getEmail());
			List<Erreklamazioa> er = d.getErreklamazioak();
			
			for(int j = 0;j<er.size();j++) {
				if(er.get(j).getZenb()==eID) {
					er.remove(j);
					
					db.persist(d);
				}
			}
		}
		
		db.getTransaction().commit();
		
	}
	public void baieztatuErreklamazioa(int eID) {
		
		db.getTransaction().begin();
		
		Erreklamazioa err = null;
		err = db.find(Erreklamazioa.class, eID);
		
		Driver d = null;
		d = db.find(Driver.class, err.getDEmail());
		
		Passenger p = null;
		p = db.find(Passenger.class, err.getPEmail());
		
		Ride r = err.getRide();
		RideBooked rb = err.getRb();
		
		
		Mugimenduak mu = new Mugimenduak("Erreklamazioa baieztatua izan da",rb.getSeatsBooked()*r.getPrice(), d, new Date());
		Mugimenduak mu2 = new Mugimenduak("Erreklamazioa baieztatua izan da",rb.getSeatsBooked()*r.getPrice(), p, new Date());
		d.addMugimendua(mu);
		p.addMugimendua(mu2);
		
		p.setWallet(p.getWallet()+(rb.getSeatsBooked()*r.getPrice()));
		
		db.persist(p);
		
		db.getTransaction().commit();
		
		this.ezabatuErreklamazioa(eID);
	}
	
	public boolean deleteUser(String uGmail) {
		boolean res = false;
		db.getTransaction().begin();
		User u = db.find(User.class, uGmail);
		db.getTransaction().commit();
		if (u.getType().equals("Driver")) {
			Driver d = (Driver) u;
			if (d.getRides() != null) {
				for (Ride r : d.getRides()) {
					deleteRide(r.getRideNumber(), d.getEmail());
				}
			}
			for (Car ca : d.getCars()) {
				deleteCar(ca.getlicensePlate());
			}
			for (Mugimenduak mu : d.getMugimenduak()) {
				deleteMugimendua(mu.getMugiZenb());
			}
			db.getTransaction().begin();
			d = db.find(Driver.class, u.getEmail());
			d.getRides().clear();
		    d.getCars().clear();
		    d.getMugimenduak().clear();
			db.remove(d);
			db.getTransaction().commit();
			res = true;
		}else {
			Passenger p = (Passenger) u;
			for (RideBooked rb : p.getBookedrides()) {
				deleteRideBooked(rb.getBookNumber());
			}
			for (Mugimenduak mu : p.getMugimenduak()) {
				deleteMugimendua(mu.getMugiZenb());
			}
			for (Alerta al : p.getAlerts()) {
				deleteAlerta(al.getZenb());
			}
			db.getTransaction().begin();
			p = db.find(Passenger.class, u.getEmail());
			p.getBookedrides().clear();
		    p.getMugimenduak().clear();
		    p.getAlerts().clear();
			db.remove(p);
			db.getTransaction().commit();
			res = true;
		}
		return res;
	}
	
	public void deleteCar(String cPlate) {
		db.getTransaction().begin();
		Car car = db.find(Car.class, cPlate);
		db.remove(car);
		db.getTransaction().commit();
	}
	
	public void deleteMugimendua(int muID) {
		db.getTransaction().begin();
		Mugimenduak mug = db.find(Mugimenduak.class, muID);
		db.remove(mug);
		db.getTransaction().commit();
	}
	
	public void deleteAlerta (int alID) {
		db.getTransaction().begin();
		Alerta ale = db.find(Alerta.class, alID);
		db.remove(ale);
		db.getTransaction().commit();
	}
	
	public void deleteRideBooked (int BookNumber) {
		db.getTransaction().begin();
		RideBooked ridb = db.find(RideBooked.class, BookNumber);
		db.remove(ridb);
		db.getTransaction().commit();
	}
	public void createAlert(String origin, String destination, Date date, String pGmail) {
		
		db.getTransaction().begin();
		
		Passenger p = null;
		p = db.find(Passenger.class, pGmail);
		
		Alerta a = new Alerta(origin,destination,date,p);
		
		p.addAlerts(a);
		
		db.persist(p);
		
		db.getTransaction().commit();
		
	}
	public List<Ride> getAlertRides(String pGmail){
		
		List<Ride> resu = new ArrayList<Ride>();
		Passenger pass = null;
		pass = db.find(Passenger.class, pGmail);
		
		List<Alerta> a = pass.getAlerts();
		
		Date today = new Date();
		
		for(Alerta al : a) {
			TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2",Ride.class);   
			query.setParameter(1, al.getOrigin());
			query.setParameter(2, al.getDestination());
			List<Ride> rides = query.getResultList();
			for(int i = 0; i<rides.size();i++) {
				if(rides.get(i).getDate().getDay()==al.getDate().getDay()) {
					if(rides.get(i).getDate().after(today)) {
						resu.add(rides.get(i));
					}
				}
			}
		}
		return resu;
	}
}
