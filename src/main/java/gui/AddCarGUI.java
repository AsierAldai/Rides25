package gui;

import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Passenger;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddCarGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Driver driver;
	
	
	private JPanel contentPane;
	private JTextField textFieldBrand;
	private JButton btnNewButtonSartu;
	private JLabel lblNewLabelLicensePlate;
	private JTextArea textAreaResultado;
	private JTextField textFieldLicensePlate;
	private JTextField textFieldSeats;
	private JTextField textFieldColor;
	
	public AddCarGUI(Driver d) {
		
		super();

		driver=d;
		
		
		
		
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Title"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		
		
		JLabel lblNewLabelBrand = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Brand")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelBrand.setBounds(10, 49, 225, 17);
		contentPane.add(lblNewLabelBrand);
		
		textFieldBrand = new JTextField();
		textFieldBrand.setBounds(245, 47, 86, 20);
		contentPane.add(textFieldBrand);
		textFieldBrand.setColumns(10);
		
		btnNewButtonSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DepositMoneyGUI.Insert")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButtonSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BLFacade facade = HomeGUI.getBusinessLogic();
				String license = textFieldLicensePlate.getText();
				String brand = textFieldBrand.getText();
				String color = textFieldColor.getText();
				int seats = Integer.parseInt(textFieldSeats.getText());
				
				if(facade.addCar(license, brand, color,seats, d.getEmail())) {
					textAreaResultado.setText(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Correct"));
				}else {
					textAreaResultado.setText(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Error"));
			}
		}
		});
		btnNewButtonSartu.setBounds(136, 169, 145, 35);
		contentPane.add(btnNewButtonSartu);
		
		lblNewLabelLicensePlate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.LicensePlate")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelLicensePlate.setBounds(10, 21, 225, 17);
		contentPane.add(lblNewLabelLicensePlate);
		
		textAreaResultado = new JTextArea();
		textAreaResultado.setBounds(78, 215, 266, 35);
		contentPane.add(textAreaResultado);
		
		textFieldLicensePlate = new JTextField();
		textFieldLicensePlate.setBounds(245, 19, 86, 20);
		contentPane.add(textFieldLicensePlate);
		textFieldLicensePlate.setColumns(10);
		
		JLabel lblNewLabelSeats = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Seats")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelSeats.setBounds(10, 81, 225, 14);
		contentPane.add(lblNewLabelSeats);
		
		textFieldSeats = new JTextField();
		textFieldSeats.setBounds(245, 78, 86, 20);
		contentPane.add(textFieldSeats);
		textFieldSeats.setColumns(10);
		
		JLabel lblNewLabelcolor = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Color")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelcolor.setBounds(10, 112, 225, 14);
		contentPane.add(lblNewLabelcolor);
		
		textFieldColor = new JTextField(); 
		textFieldColor.setBounds(245, 109, 86, 20);
		contentPane.add(textFieldColor);
		textFieldColor.setColumns(10);
		
	}
}