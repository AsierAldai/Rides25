package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Passenger;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;


import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtLogin;
	private  ButtonGroup buttonGroup = new ButtonGroup();
	private JPasswordField pwdPassword;
	private JButton btnSistemanSartu;
	private JTextArea textArea;
	private JRadioButton rdbtnDriver;
	private JRadioButton rdbtnTraveler;

	

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		
		
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Login"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(233, 23, 134, 28);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel lblErabiltzaileIzenaSartu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Email"));
		lblErabiltzaileIzenaSartu.setBounds(50, 29, 165, 16);
		contentPane.add(lblErabiltzaileIzenaSartu);
		
		JLabel lblPasahitzaSartu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Pass"));
		lblPasahitzaSartu.setBounds(50, 72, 145, 16);
		contentPane.add(lblPasahitzaSartu);
		
		JLabel lblErabiltzaileMotaAukeratu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.DRorTR"));
		lblErabiltzaileMotaAukeratu.setBounds(30, 118, 165, 16);
		contentPane.add(lblErabiltzaileMotaAukeratu);
		
		btnSistemanSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Enter"));
		btnSistemanSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = HomeGUI.getBusinessLogic();
				if(rdbtnDriver.isSelected()) {
					if(facade.login(txtLogin.getText(), pwdPassword.getText(), "Driver")) {
						textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Loged"));
						Driver d = new Driver(txtLogin.getText(), pwdPassword.getText());
						JFrame a = new DriverMainGUI(d);
						a.setVisible(true);
					}else {
						textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.NotLoged"));
					}
				}else if(rdbtnTraveler.isSelected()) {
					if(facade.login(txtLogin.getText(), pwdPassword.getText(), "Passenger")) {
						textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Loged"));
						Passenger p = new Passenger(txtLogin.getText(), pwdPassword.getText());
						JFrame a = new PassengerMainGUI(p);
						a.setVisible(true);
					}else {
						textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.NotLoged"));
					}
					
				}
				
				
				
			}
		});
		btnSistemanSartu.setBounds(178, 159, 117, 29);
		contentPane.add(btnSistemanSartu);
		
		textArea = new JTextArea();
		textArea.setBounds(94, 199, 290, 55);
		contentPane.add(textArea);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("");
		pwdPassword.setBounds(233, 66, 134, 28);
		contentPane.add(pwdPassword);
		
		rdbtnDriver = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Driver"));
		buttonGroup.add(rdbtnDriver);
		rdbtnDriver.setBounds(207, 114, 88, 23);
		contentPane.add(rdbtnDriver);
		
		rdbtnTraveler = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Traveler"));
		buttonGroup.add(rdbtnTraveler);
		rdbtnTraveler.setBounds(299, 114, 85, 23);
		contentPane.add(rdbtnTraveler);
	}
}
