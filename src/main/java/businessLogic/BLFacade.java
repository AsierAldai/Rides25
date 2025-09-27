package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Ride;
import domain.RideBooked;
import domain.User;
import domain.Car;
import domain.Driver;
import domain.Erreklamazioa;
import domain.Mugimenduak;
import domain.Passenger;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	@WebMethod public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, Car car, String driverEmail) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	@WebMethod public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	@WebMethod public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	//@WebMethod public void initializeBD();

	@WebMethod public boolean register(String email, String password, String type);
	
	@WebMethod public boolean login(String email, String password, String type);
	
	@WebMethod public boolean addMoney(String email, int amount);
	
	@WebMethod public boolean withdrawMoney(String email, int amount);
	
	@WebMethod public ArrayList<RideBooked> getBookingsDriver(String email,String password);
	
	@WebMethod public boolean bookRide(Passenger passenger, String driveremail, int seatsBooked, int rideID);
	//@WebMethod public boolean bookRide(Passenger passenger, String driveremail, int seatsBooked, String from, String to,  Date date, int nPlaces, float price);
	
	@WebMethod public boolean accept(int BookNumber);
	
	@WebMethod public boolean decline(int BookNumber);
	
	@WebMethod public List<RideBooked> getBookingsPass(String email,String password);
	
	@WebMethod public List<Mugimenduak> getMugimenduak(String uGmail);
	
	 @WebMethod public boolean bidaiaEginda(int BookNumber, String pGmail, int b);
	 
	 @WebMethod public List<Ride> getRidesDriver(String dGmail);
	
	 @WebMethod public void deleteRide(int RideNumber, String dGmail);
	 
	 @WebMethod public float getDriversWallet(String dGmail);
	 
	 @WebMethod public float getPassengersWallet(String pGmail);
	 
	 @WebMethod public boolean addCar(String license, String brand, String color, int seats,  String dGmail);
	 
	 @WebMethod public List<Car> getCars(String dGmail);
	 
	 @WebMethod public Ride getRideByEmail(String email, String from, String to, Date date);
	 
	 @WebMethod public void erreklamazioa(String zerg, String pGmail, int BookNumber);
	 
	 @WebMethod public List<Erreklamazioa> geterreklamazio(String dGmail);
	 
	 @WebMethod public List<Driver> getAllDrivers();
	 
	 @WebMethod public void ezabatuErreklamazioa(int eID);
	 
	 @WebMethod public void baieztatuErreklamazioa(int eID);
	 
	 @WebMethod public boolean deleteUser(String uGmail);
	 
	 @WebMethod public void createAlert(String origin, String destination, Date date, String pGmail);
	 
	 @WebMethod public List<Ride> getAlertRides(String pGmail);
}
