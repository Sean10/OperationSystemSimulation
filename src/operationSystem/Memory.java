package operationSystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Memory {
	CardLayout layout = new CardLayout();
	JPanel ContentPanel = new JPanel(layout);
	JButton logoutButton;
	JFrame frame = new JFrame("Memory Management");
	
	private MemoryPanel memPanel;
	
	public Memory () {
		
		memPanel = new MemoryPanel(this);
		refreshMem thread1 = new refreshMem(this);
		thread1.start();
		JPanel panel = new JPanel(new BorderLayout(7, 7));
		panel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		panel.add(createContentPanel(), BorderLayout.CENTER);
		frame.getContentPane().add(panel);
		frame.setLocationRelativeTo(null);
		frame.setSize(640, 480);
		frame.setVisible(true);
		frame.setLocation(780, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
	}
	
	ActionListener al = new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			layout.show(ContentPanel, ae.getActionCommand());
		}
	};
	
	
	JPanel createContentPanel() {
		ContentPanel.add(memPanel.getPanel(), "Memory");
		return ContentPanel;
	}
	

	public void close() {
		frame.dispose();
	}

	public MemoryPanel getMemoryPanel()
	{
		return memPanel;
	}
}
