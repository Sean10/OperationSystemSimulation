package operationSystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class fileUI implements ActionListener {

	CardLayout Card = new CardLayout();
	JPanel ContentPanel = new JPanel(Card);
	JButton LoginButton = new JButton("log in");
	JButton ShowButton = new JButton("show");
	JFrame frame = new JFrame("FILE MANAGEMENT");
	JTextField login = new JTextField("");
	JLabel username = new JLabel("input your username:");
	
	
	public fileUI(){
		JPanel panel1 = new JPanel(new BorderLayout(7, 7));
		panel1.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		panel1.setBackground(new Color(255, 250, 205));
		JPanel panelSouth = new JPanel(new FlowLayout());
		panelSouth.setBackground(new Color(255, 250, 205));
		frame.getContentPane().add(ContentPanel);
		ContentPanel.add(panel1);
		frame.setLocationRelativeTo(null);
		panel1.add(username,BorderLayout.NORTH);
		panel1.add(login,BorderLayout.CENTER);
		panelSouth.add(LoginButton);
		panelSouth.add(ShowButton);
		panel1.add(panelSouth,BorderLayout.SOUTH);
		LoginButton.addActionListener(this);
		ShowButton.addActionListener(this);
		
		frame.setSize(320, 160);
		frame.setVisible(true);
		frame.setLocation(500, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void close() {
		frame.dispose();
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == LoginButton){
			String username;
			username = login.getText();
			if(username.equals("")){
				JOptionPane.showMessageDialog(null, "Wrong Username! Please input again!", "alert", JOptionPane.ERROR_MESSAGE); 
			}
			else{
				JOptionPane.showMessageDialog(null, "Welcome! The commands are defined internally. Type `help' to see this list.", "welcome", JOptionPane.PLAIN_MESSAGE);
				close();
				try {
					//这里可以把命令行改成图形界面操作
					MainTest file = new MainTest(username);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		//以后应该不是写在这里
		else if(e.getSource() == ShowButton){
			Show showing = new Show();
			showing.init();
		}
		
	}
	
	
}
