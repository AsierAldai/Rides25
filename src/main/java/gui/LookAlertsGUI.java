package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Alerta;
import domain.Driver;
import domain.Erreklamazioa;
import domain.Passenger;
import domain.Ride;
import domain.RideBooked;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class LookAlertsGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Passenger passenger;
	
	
	private JPanel contentPane;
	private JComboBox<Ride> comboBoxAlerts;
	private DefaultComboBoxModel<Ride> Rides = new DefaultComboBoxModel<Ride>();
	
	public LookAlertsGUI(Passenger p) {
		
		super();

		passenger=p;
		
		BLFacade facade = HomeGUI.getBusinessLogic();

		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("LookAlertsGUI.LookAlerts"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		comboBoxAlerts = new JComboBox<Ride>();
		comboBoxAlerts.setBounds(10, 65, 414, 22);
		contentPane.add(comboBoxAlerts);
		comboBoxAlerts.setModel(Rides);
		
		int i = 0;
		List<Ride> s = facade.getAlertRides(passenger.getEmail());
		while(i<s.size()) {
			if(s.get(i)!=null) {
			Rides.addElement(s.get(i));
			}
			i++;
		}
		
		JLabel lblNewLabelAlerts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LookAlertsGUI.Alerts")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelAlerts.setBounds(37, 26, 351, 14);
		contentPane.add(lblNewLabelAlerts);
		
		
	}
}
