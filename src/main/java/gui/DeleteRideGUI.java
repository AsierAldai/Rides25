package gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
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

public class DeleteRideGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Driver driver;
	
	
	private JPanel contentPane;
	private JTextArea textAreaShow;
	private JComboBox<Ride> comboBoxRequests;
	private DefaultComboBoxModel<Ride> bookedRides = new DefaultComboBoxModel<Ride>();
	
	public DeleteRideGUI(Driver d) {
		
		super();

		driver=d;
		

		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DeleteRideGUI.Title"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		textAreaShow = new JTextArea();
		textAreaShow.setBounds(10, 129, 414, 31);
		contentPane.add(textAreaShow);
		
		comboBoxRequests = new JComboBox<Ride>();
		comboBoxRequests.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Ride r = (Ride) comboBoxRequests.getSelectedItem();
				textAreaShow.setText(r.toString());
			}
		});
		comboBoxRequests.setBounds(78, 34, 266, 22);
		contentPane.add(comboBoxRequests);
		comboBoxRequests.setModel(bookedRides);
		BLFacade facade = HomeGUI.getBusinessLogic();
		int i = 0;
		List<Ride> s = facade.getRidesDriver(driver.getEmail());
		while(i<s.size()) {
			if(s.get(i).getFrom()!=null) {
			bookedRides.addElement(s.get(i));
			}
			i++;
		}	
		JButton btnNewButtonBukatuta = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteRideGUI.Elim")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButtonBukatuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Ride r = (Ride) comboBoxRequests.getSelectedItem();
				facade.deleteRide(r.getRideNumber(), d.getEmail());
				
				
				
			}
		});
		btnNewButtonBukatuta.setBounds(153, 200, 98, 31);
		contentPane.add(btnNewButtonBukatuta);
		
		
	}
}
