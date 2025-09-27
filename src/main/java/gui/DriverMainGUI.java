package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Driver;
import domain.User;
import businessLogic.BLFacade;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class DriverMainGUI extends JFrame {
	
    private Driver driver;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JPanel jOptionPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonWithdrawMoney = null;
	private JButton jButtonAccepDecline = null;
	private JButton jButtonMovements = null;
	private JButton jButtonDeleteRide = null;
	private JButton jButtonAddCar = null;
	private JButton jButtonDeleteUser = null;
	private JButton jButtonLookErr= null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	/**
	 * This is the default constructor
	 */
	public DriverMainGUI(Driver d) {
		super();

		driver=d;
		
		// this.setSize(271, 295);
		this.setSize(495, 290);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateRideGUI(driver);
				a.setVisible(true);
			}
		});
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI();

				a.setVisible(true);
			}
		});
		
		jButtonWithdrawMoney = new JButton();
		jButtonWithdrawMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Withdraw"));
		jButtonWithdrawMoney.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new WithdrawMoneyGUI(driver);
				a.setVisible(true);
			}
		});
		
		jButtonAccepDecline = new JButton();
		jButtonAccepDecline.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AcceptDecline"));
		jButtonAccepDecline.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new AcceptDeclineGUI(driver);
				a.setVisible(true);
			}
		});
		
		jButtonMovements = new JButton();
		jButtonMovements.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Movements"));
		jButtonMovements.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Driver d = new Driver(driver.getEmail(),driver.getPassword());
				JFrame a = new LookMovementsGUI(d);
				a.setVisible(true);
			}
		});
		jButtonDeleteRide = new JButton();
		jButtonDeleteRide.setText(ResourceBundle.getBundle("Etiquetas").getString("DeleteRideGUI.Title"));
		jButtonDeleteRide.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new DeleteRideGUI(driver);
				a.setVisible(true);
			}
		});
		
		jButtonAddCar = new JButton();
		jButtonAddCar.setText(ResourceBundle.getBundle("Etiquetas").getString("AddCarGUI.Title"));
		jButtonAddCar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new AddCarGUI(driver);
				a.setVisible(true);
			}
		});
		jButtonDeleteUser = new JButton();
		jButtonDeleteUser.setText(ResourceBundle.getBundle("Etiquetas").getString("DeleteUserGUI.Delete"));
		jButtonDeleteUser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new DeleteUserGUI(driver);
				a.setVisible(true);
			}
		});
		
		jButtonLookErr = new JButton();
		jButtonLookErr.setText(ResourceBundle.getBundle("Etiquetas").getString("IkusiErreklamazioGUI.Ikusi"));
		jButtonLookErr.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new IkusiErreklamazioaGUI(driver);
				a.setVisible(true);
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new BorderLayout());
		jContentPane.add(jLabelSelectOption, BorderLayout.NORTH);
		
		jOptionPane = new JPanel();
		jOptionPane.setLayout(new GridLayout(9, 1, 0, 0));
		
		jOptionPane.add(jButtonCreateQuery);
		jOptionPane.add(jButtonQueryQueries);
		jOptionPane.add(jButtonWithdrawMoney);
		jOptionPane.add(jButtonAccepDecline);
		jOptionPane.add(jButtonMovements);
		jOptionPane.add(jButtonDeleteRide);
		jOptionPane.add(jButtonAddCar);
		jOptionPane.add(jButtonLookErr);
		jOptionPane.add(jButtonDeleteUser);
		jContentPane.add(jOptionPane, BorderLayout.CENTER);
		
		setContentPane(jContentPane);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverMainGUI.Title") + " - driver :"+driver.getPassword());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - driver :"+driver.getPassword());
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"

