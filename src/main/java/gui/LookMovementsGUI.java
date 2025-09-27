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
import domain.Mugimenduak;
import domain.Passenger;
import domain.RideBooked;
import domain.User;

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

public class LookMovementsGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	
	private JPanel contentPane;
	private JTextArea textAreaResultado;
	private JComboBox<Mugimenduak> comboBoxRequests;
	private DefaultComboBoxModel<Mugimenduak> bookedRides = new DefaultComboBoxModel<Mugimenduak>();
	
	public LookMovementsGUI(User u) {
		
		super();

		user=u;
		
		
		
		
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Movements"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		textAreaResultado = new JTextArea();
		textAreaResultado.setBounds(49, 96, 338, 32);
		contentPane.add(textAreaResultado);
		
		comboBoxRequests = new JComboBox<Mugimenduak>();
		comboBoxRequests.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Mugimenduak r = (Mugimenduak) comboBoxRequests.getSelectedItem();
				textAreaResultado.setText(r.toString());
			}
		});
		comboBoxRequests.setBounds(78, 34, 266, 22);
		contentPane.add(comboBoxRequests);
		comboBoxRequests.setModel(bookedRides);
		BLFacade facade = HomeGUI.getBusinessLogic();
		int i = 0;
		List<Mugimenduak> s = facade.getMugimenduak(user.getEmail());
		while(i<s.size()) {
			bookedRides.addElement(s.get(i));
			i++;
		}
		
	}
}
