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

public class WithdrawMoneyGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Driver driver;
	
	
	private JPanel contentPane;
	private JTextField textFieldCantidad;
	private JButton btnNewButtonSartu;
	private JTextArea textAreaSaldoActual;
	private JLabel lblNewLabelActualMoney;
	private JTextArea textAreaResultado;
	
	public WithdrawMoneyGUI(Driver d) {
		
		super();

		driver=d;
		
		
		
		
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.Withdraw"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		BLFacade facade = HomeGUI.getBusinessLogic();
		
		JLabel lblNewLabelCantidad = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.Cuantity")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelCantidad.setBounds(18, 75, 230, 17);
		contentPane.add(lblNewLabelCantidad);
		
		textFieldCantidad = new JTextField();
		textFieldCantidad.setBounds(278, 73, 86, 20);
		contentPane.add(textFieldCantidad);
		textFieldCantidad.setColumns(10);
		
		btnNewButtonSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.Retire")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButtonSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				Integer am = Integer.parseInt(textFieldCantidad.getText());
				if(facade.withdrawMoney(driver.getEmail(),am)) {
					textAreaResultado.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.CR"));
				}else {
					textAreaResultado.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.ERROR"));
				}
			}
		});
		btnNewButtonSartu.setBounds(135, 123, 145, 35);
		contentPane.add(btnNewButtonSartu);
		
		textAreaSaldoActual = new JTextArea();
		textAreaSaldoActual.setEditable(false);
		String num = Float.toString(facade.getDriversWallet(driver.getEmail()));
		textAreaSaldoActual.setText(num);
		textAreaSaldoActual.setBounds(278, 17, 86, 22);
		contentPane.add(textAreaSaldoActual);
		
		lblNewLabelActualMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.Actual")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelActualMoney.setBounds(18, 21, 230, 17);
		contentPane.add(lblNewLabelActualMoney);
		
		textAreaResultado = new JTextArea();
		textAreaResultado.setBounds(78, 190, 266, 60);
		contentPane.add(textAreaResultado);
		
	}
}
