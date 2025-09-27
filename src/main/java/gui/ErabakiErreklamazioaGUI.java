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

public class ErabakiErreklamazioaGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	
	private JPanel contentPane;
	private JComboBox<Erreklamazioa> comboBoxRequests;
	private DefaultComboBoxModel<Erreklamazioa> bookedRides = new DefaultComboBoxModel<Erreklamazioa>();
	private JTextField textFieldErreklamazioa;
	
	public ErabakiErreklamazioaGUI() {
		
		BLFacade facade = HomeGUI.getBusinessLogic();

		setTitle(ResourceBundle.getBundle("Etiquetas").getString("IkusiErreklamazioGUI.Ikusi"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		

		textFieldErreklamazioa = new JTextField();
		textFieldErreklamazioa.setBounds(10, 56, 414, 126);
		contentPane.add(textFieldErreklamazioa);
		textFieldErreklamazioa.setColumns(10);
		
		comboBoxRequests = new JComboBox<Erreklamazioa>();
		comboBoxRequests.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Erreklamazioa er = (Erreklamazioa) comboBoxRequests.getSelectedItem();
				textFieldErreklamazioa.setText(er.getZergaitia());
			}
		});
		comboBoxRequests.setBounds(10, 23, 414, 22);
		contentPane.add(comboBoxRequests);
		comboBoxRequests.setModel(bookedRides);
		
		JButton btnNewButtonBaieztatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErabakiErreklamazioaGUI.Baiezt")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButtonBaieztatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Erreklamazioa er = (Erreklamazioa) comboBoxRequests.getSelectedItem();
				
				facade.baieztatuErreklamazioa(er.getZenb());
				
			}
		});
		btnNewButtonBaieztatu.setBounds(65, 206, 102, 33);
		contentPane.add(btnNewButtonBaieztatu);
		
		JButton btnNewButtonEzeztatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErabakiErreklamazioaGUI.Ezezt")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButtonEzeztatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Erreklamazioa er = (Erreklamazioa) comboBoxRequests.getSelectedItem();
				
				facade.ezabatuErreklamazioa(er.getZenb());
				
				
			}
		});
		btnNewButtonEzeztatu.setBounds(242, 208, 102, 28);
		contentPane.add(btnNewButtonEzeztatu);
		List<Driver> s = facade.getAllDrivers();
		for(int i = 0; i<s.size(); i++) {
			
			Driver d = s.get(i);
			List<Erreklamazioa> e = d.getErreklamazioak();
			
			for(int j = 0;j<e.size(); j++) {
				if(e.get(j)!=null) {
				bookedRides.addElement(e.get(j));
				}
		}
		}
	}
}

