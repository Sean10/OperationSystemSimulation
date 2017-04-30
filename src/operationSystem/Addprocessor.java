package operationSystem;

import java.awt.Color;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Addprocessor extends JDialog implements ActionListener {
	private JPanel jContentPane = null;
	private JLabel jLabel = new JLabel();
	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
	private JTextField nameText = new JTextField();
	String[] priority = { "ls", "vim", "rmfile", "type", "modify" };
	private JComboBox opertionText = new JComboBox(priority);
	private JTextField fileNameText = new JTextField();;
	private JButton confirm = null;
	private JButton cancel = null;
	private Show showing;
	
	private int strategy;


	public Addprocessor(JFrame owner, String title, boolean modal, int strategy, Show showing) {
		super(owner, title, modal);
		this.strategy = strategy;
		this.showing = showing;
		initialize();
	}
	
	private void initialize() {
		this.setSize(330, 230);
		this.setBounds(660,320,330,230);
		this.setTitle("AddProcessor");
		this.setContentPane(getJContentPane());
		nameText.setBounds(new java.awt.Rectangle(165, 31, 125, 21));
		opertionText.setBounds(new java.awt.Rectangle(165, 69, 107, 20));
		fileNameText.setBounds(new java.awt.Rectangle(166, 105, 123, 23));
		
	}
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2.setBounds(new java.awt.Rectangle(48, 106, 75, 22));
			jLabel2.setText("FNAME");
			jLabel1.setBounds(new java.awt.Rectangle(48, 69, 75, 22));
			jLabel1.setText("OP");
			jLabel.setBounds(new java.awt.Rectangle(48, 30, 75, 22));
			jLabel.setText("PNAME");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getNameText(), null);
			jContentPane.add(getopertionText(), null);
			jContentPane.add(getfileNameText(), null);
			jContentPane.add(getConfirm(), null);
			jContentPane.add(getcancel(), null);
			jContentPane.setBackground(new Color(255, 250, 205));
		}
		return jContentPane;
	}
	
	private JTextField getNameText() {
		//if (nameText == null) {
			//nameText = new JTextField();
			//nameText.setBounds(new java.awt.Rectangle(165, 31, 125, 21));
		//}
		return nameText;
	}
	
	/*JComboBox下拉列表*/
	private JComboBox getopertionText() {
			
		return opertionText;
	}
	
	private JTextField getfileNameText() {

		return fileNameText;
	}
	
	private JButton getConfirm() {
		if (confirm == null) {
			confirm = new JButton();
			confirm.setBounds(new java.awt.Rectangle(83, 143, 80, 24));
			confirm.setText("OK");
			confirm.addActionListener(this);
		}
		return confirm;
	}
	
	private JButton getcancel() {
		if (cancel == null) {
			cancel = new JButton();
			cancel.setBounds(new java.awt.Rectangle(170, 143, 80, 24));
			cancel.setText("CANCEL");
			cancel.addActionListener(this);
		}
		return cancel;
	}
	
	
//	public boolean isIntFormat(String string) {
//		boolean flag = true;
//		char[] c = string.toCharArray();
//		for (int i = 0; i < c.length; i++) {
//			if (!Character.isDigit(c[i])) {
//				flag = false;
//				JOptionPane.showMessageDialog(null, "进程占用内存的时间需要为大于0的整�?", "警告",
//						JOptionPane.YES_OPTION);
//				break;
//			}
//		}
//		return flag;
//	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirm) {
			String name = this.getNameText().getText();
			String file = this.getfileNameText().getText();
			String operation = this.getopertionText().getSelectedItem().toString();
			if (name.equals("")) {
				JOptionPane.showMessageDialog(null, "Please input the name of process", "Warning",
						JOptionPane.YES_OPTION);
				this.getNameText().requestFocus();
			} else if (name.toCharArray().length > 20) {
				JOptionPane.showMessageDialog(null, "Please don't except 20 bytes", "Warning",
						JOptionPane.YES_OPTION);
			} else if (file.equals("")) {
				JOptionPane.showMessageDialog(null, "Please input the file name you operated.", "Warning",
						JOptionPane.YES_OPTION);
			}else {
//				Integer priority = Integer.valueOf(this.getopertionText()
//						.getSelectedItem().toString());
//				Integer runTime = Integer.valueOf(this.getfileNameText()
//						.getText());
//				int priority = (int) (Math.random() * 6 / 1);
//				int runTime = (int) (Math.random() * 25 / 1);
//				int memUse = (int) (Math.random() * 15 / 1);
				
				
				/*这里是给卓�?�发操作和文件名还有进程号，卓�?�需要存下这些东西，
				 * 之后我这边时间到了再给卓思发�?个进程名就可以了
				 * 
				new �?个包含FileExist函数的对�?
				boolean fexist = FileExist(operation, file，name );  都是String类型*/
				
				//boolean fexist = true; //只是为了测试�?
				boolean fexist = showing.threadComing(name, operation, file);
				if(fexist == true)
				{
					PCB p = new PCB(name, operation, file);
					Object[] temp = { p.getId(), p.getName(), p.getPriority(),
							p.getRunTime(), p.getMemUse() };
					processUI frm = (processUI) this.getOwner();
					DefaultTableModel dtm = (DefaultTableModel) frm
							.getPreparedTable().getModel();
					dtm.addRow(temp);
//					Vector v = dtm.getDataVector();
//					if(strategy == 1)
//						v = v;
//					else 
//						v = BubbleSort.sort(v);
					this.setVisible(false);
					this.dispose();
				}
				else
				{
					Object[] temp = {"", name, "", "Wrong", "Term"};
					processUI frm = (processUI) this.getOwner();
					DefaultTableModel dtm = (DefaultTableModel) frm
							.getTerTable().getModel();
					dtm.addRow(temp);
					this.setVisible(false);
					this.dispose();
				}
		} 
//				else {
//				this.getfileNameText().requestFocus();
//				this.getfileNameText().selectAll();
//			}
		} /*else if (e.getActionCommand().equals("CANCEL")) {
			this.setVisible(false);
			this.dispose();
		} */else if (e.getSource() == cancel){
			this.setVisible(false);
			this.dispose();
		}
		//关闭并删除中断窗口，似乎不是这里
	}
} 

