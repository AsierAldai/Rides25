package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Passenger;
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

public class AcceptDeclineGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Driver driver;
	
	
	private JPanel contentPane;
	private JTextArea textAreaResultado;
	private JTextArea textAreaEmaitza;
	private JButton btnNewButtonAccept;
	private JButton btnNewButtonDecline;
	private JComboBox<RideBooked> comboBoxRequests;
	private DefaultComboBoxModel<RideBooked> bookedRides = new DefaultComboBoxModel<RideBooked>();
	
	public AcceptDeclineGUI(Driver d) {
		
		super();

		driver=d;
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("AcceptDeclineGUI.AcceptDecline"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		textAreaResultado = new JTextArea();
		textAreaResultado.setBounds(10, 103, 414, 32);
		contentPane.add(textAreaResultado);
		
		JTextArea textAreaEmaitza = new JTextArea();
		textAreaEmaitza.setBounds(78, 218, 274, 32);
		contentPane.add(textAreaEmaitza);
		
		comboBoxRequests = new JComboBox<RideBooked>();
		comboBoxRequests.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				RideBooked r = (RideBooked) comboBoxRequests.getSelectedItem();
				textAreaResultado.setText(r.toString());
			}
		});
		comboBoxRequests.setBounds(78, 34, 266, 22);
		contentPane.add(comboBoxRequests);
		comboBoxRequests.setModel(bookedRides);
		BLFacade facade = HomeGUI.getBusinessLogic();
		int i = 0;
		ArrayList<RideBooked> s = facade.getBookingsDriver(driver.getEmail(), driver.getPassword());
		while(i<s.size()) {
			if(s.get(i).getRide()!=null) {
			bookedRides.addElement(s.get(i));
			}
			i++;
		}
		
		btnNewButtonAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AcceptDeclineGUI.Accept")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButtonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RideBooked rb = (RideBooked) comboBoxRequests.getSelectedItem();
				BLFacade facade = HomeGUI.getBusinessLogic();
				if(facade.accept(rb.getBookNumber())) {
					textAreaEmaitza.setText("Has been completed correctly");
				}else {
					textAreaEmaitza.setText("Couldnt be done");
				}
			}
		});
		btnNewButtonAccept.setBounds(65, 163, 118, 32);
		contentPane.add(btnNewButtonAccept);
		
		btnNewButtonDecline = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AcceptDeclineGUI.Decline")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButtonDecline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RideBooked rb = (RideBooked) comboBoxRequests.getSelectedItem();
				BLFacade facade = HomeGUI.getBusinessLogic();
				if(facade.decline(rb.getBookNumber())) {
					textAreaEmaitza.setText("Has been completed correctly");
				}else {
					textAreaEmaitza.setText("Couldnt be done");
				}
			}
		});
		btnNewButtonDecline.setBounds(238, 163, 118, 32);
		contentPane.add(btnNewButtonDecline);
		
	}
}
