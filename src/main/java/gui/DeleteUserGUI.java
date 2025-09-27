package gui;

import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Passenger;
import domain.User;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteUserGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	
	private JPanel contentPane;
	private JButton btnNewButtonDelete;
	private JTextArea textAreaResultado;
	private JLabel lblNewLabelZihurtatzea;
	
	public DeleteUserGUI(User  u) {
		
		super();

		user=u;
		
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DeleteUserGUI.Delete"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		BLFacade facade = HomeGUI.getBusinessLogic();
		
		textAreaResultado = new JTextArea();
		textAreaResultado.setBounds(78, 190, 266, 60);
		contentPane.add(textAreaResultado);
		
		btnNewButtonDelete = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteUserGUI.Delete2")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButtonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!facade.deleteUser(u.getEmail())) {
					textAreaResultado.setText(ResourceBundle.getBundle("Etiquetas").getString("DeleteUserGUI.Error"));
				}else {
					System.exit(0);
				}				
			}
		});
		btnNewButtonDelete.setBounds(135, 123, 145, 35);
		contentPane.add(btnNewButtonDelete);
		
		lblNewLabelZihurtatzea = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DeleteUserGUI.Zihurtatzea")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelZihurtatzea.setBounds(36, 30, 349, 60);
		contentPane.add(lblNewLabelZihurtatzea);
		
	}
}
