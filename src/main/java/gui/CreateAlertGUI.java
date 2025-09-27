package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Car;
import domain.Driver;
import domain.Passenger;
import domain.Ride;
import domain.RideBooked;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateAlertGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	
	private Passenger pass;
	private JTextField fieldOrigin=new JTextField();
	private JTextField fieldDestination=new JTextField();
	
	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo")); 
	private JLabel jLabRideDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private DefaultComboBoxModel<Car> bookedRides = new DefaultComboBoxModel<Car>();

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JLabel jLabelMsg = new JLabel();
	
	private List<Date> datesWithEventsCurrentMonth;
	private final JTextField textFieldMessage = new JTextField();


	public CreateAlertGUI(Passenger passenger) {
		
		
		

		pass=passenger;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateAlert"));

		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));

		jCalendar.setBounds(new Rectangle(300, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jLabelMsg.setBounds(new Rectangle(275, 214, 305, 20));
		jLabelMsg.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelOrigin, null);
		

		

		this.getContentPane().add(jCalendar, null);

		
		
		
		BLFacade facade = HomeGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getThisMonthDatesWithRides("a","b",jCalendar.getDate());		
		
		jLabRideDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabRideDate.setBounds(298, 16, 140, 25);
		getContentPane().add(jLabRideDate);
		
		jLabelDestination.setBounds(10, 109, 61, 16);
		getContentPane().add(jLabelDestination);
		
		
		fieldOrigin.setBounds(100, 53, 130, 26);
		getContentPane().add(fieldOrigin);
		fieldOrigin.setColumns(10);
		
		
		fieldDestination.setBounds(100, 104, 123, 26);
		getContentPane().add(fieldDestination);
		fieldDestination.setColumns(10);
		
		textFieldMessage.setEditable(false);
		
		JButton btnNewCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateAlertGUI.Create")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String origin = fieldOrigin.getText();
				String destination = fieldDestination.getText();
				Date date = jCalendar.getDate();
				
				facade.createAlert(origin, destination, date, pass.getEmail());
				
				textFieldMessage.setText("Eginda");
			}
		});
		btnNewCreate.setBounds(187, 245, 117, 36);
		getContentPane().add(btnNewCreate);
		
		textFieldMessage.setBounds(106, 214, 159, 20);
		textFieldMessage.setColumns(10);
		getContentPane().add(textFieldMessage);
	
		
		
		 //Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//			
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);						
	
					}
					jCalendar.setCalendar(calendarAct);
					int offset = jCalendar.getCalendar().get(Calendar.DAY_OF_WEEK);
					
						if (Locale.getDefault().equals(new Locale("es")))
							offset += 4;
						else
							offset += 5;
				Component o = (Component) jCalendar.getDayChooser().getDayPanel().getComponent(jCalendar.getCalendar().get(Calendar.DAY_OF_MONTH) + offset);
				}}});
		
	}	 
	
}