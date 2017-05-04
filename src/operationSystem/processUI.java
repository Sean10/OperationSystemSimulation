/*
 * 进程管理UI
 */

package operationSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class processUI extends JFrame implements ActionListener {
	private static processUI frm;
	private static final long serialVersionUID = 1L;
	
	private JPanel jContentPane = null;
	private JPanel InfoPanel = null;
	private JPanel buttonPane;
	private JPanel readyPanel = null;
	private JScrollPane prparedScrollPane = null;
	private JPanel Intrpane = null;
	
	private JLabel jLabel = new JLabel();
	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
	JTextField nameText = new JTextField();
	String[] priority = { "0", "1", "2", "3", "4", "5" };
	JComboBox priorityText = new JComboBox(priority);
	JTextField runTimeText = new JTextField();
	private JButton confirm = null;
	private JButton cancel = null;
	
	JDialog intrDialog = new JDialog();

	
	private JTable preparedTable = null;
	
	/*JTable 用来显示和编辑常规二维单元表*/
	
	private DefaultTableModel defaultTableModel = null; 
	
	/*DefaultTableModel是 TableModel 的一个实现，它使用一个 Vector 来存储单元格的对象，
	 * 通过DefaultTableModel可以对表格进行数据的增删改*/
	
	private JPanel blockPanel = null;
	private JPanel runPanel = null;
	private JPanel terPanel = null;
	private JScrollPane blockScrollPane = null;
	private JScrollPane terScrollPane = null;
	private JTable blockTable = null;
	private JTable terTable = null;
	private DefaultTableModel defaultTableModel1 = null; 
	private DefaultTableModel defaultTableModelt = null; 
	private JScrollPane runScrollPane = null;
	private JTable runTable = null;
	private DefaultTableModel defaultTableModel2 = null;	
	private JTextArea runInfo;
	private JPanel infoPane;
	private JScrollPane infoScroll;	
	
	private Show showing = new Show();
	private MemoryModel memorymodel = new MemoryModel();
	
	
	/*标记策略类型*/
	public static int strategy = 0;
	
	/*生成运行信息的滚动*/
	public JScrollPane getInfoScroll(){
		if(infoScroll == null){
			infoScroll = new JScrollPane(getRunInfo());
			//infoScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		}
		return infoScroll;
	}
	
	public JPanel getInfoPane(){
		if(infoPane == null){
			infoPane = new JPanel();			
			infoPane.setBorder(
				BorderFactory.createTitledBorder(null, "DISPATCH INFO", /*createTitledBorder建立一个标题边界*/
						TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION));
			infoPane.add(getInfoScroll());
			infoPane.setBackground(new Color(173, 216, 230));
		}
		return infoPane;
	}
	
	public JTextArea getRunInfo() {
		if(runInfo == null){
			runInfo = new JTextArea(24,25);
		}
		return runInfo;
	}

	//构造函数，初始化Ui
	public processUI() {
		super();
		initialize();
		this.frm = this;
		this.setVisible(true);
	}
	
	private void initialize() {
		showing.init();
		this.setBounds(350,150,750,500);
		this.add(getJContentPane(), BorderLayout.WEST);
		this.add(getInfopanel(), BorderLayout.CENTER);
		this.add(getButtonPane(), BorderLayout.EAST);
		this.setTitle("PROCESS MANAGEMENT");
		this.setLocation(20, 250);
	}
	
	public JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(4, 1, 0, 0));
			jContentPane.add(getReadyPanel());
			jContentPane.add(getRunPanel());
			jContentPane.add(getBlockPanel());
			jContentPane.add(getTerPanel());
//			jContentPane.add(getInfoPane());
		}
		return jContentPane;
	}

	public JPanel getInfopanel()
	{
		if(InfoPanel == null)
		{
			InfoPanel = new JPanel();
			InfoPanel.setLayout(new GridLayout(1, 1, 0, 0));
			InfoPanel.add(getInfoPane());
		}
		return InfoPanel;
	}
	
	public JPanel getReadyPanel() {
		if (readyPanel == null) {
			readyPanel = new JPanel();
			readyPanel.setLayout(new BorderLayout());
			readyPanel.setBorder(
					BorderFactory.createTitledBorder(
					null, "READY",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			readyPanel.add(getPrparedScrollPane(), BorderLayout.CENTER);
			readyPanel.setBackground(Color.pink);
		}
		return readyPanel;
	}
	
	public JScrollPane getPrparedScrollPane() {
		if (prparedScrollPane == null) {
			prparedScrollPane = new JScrollPane(getPreparedTable());
			// prparedScrollPane.add(getPreparedTable());
		}
		return prparedScrollPane;
	}
	

	//获取表
	public synchronized JTable getPreparedTable() {
		if (preparedTable == null) {
			preparedTable = new JTable();
			preparedTable.setShowGrid(true); /*设置显示网格*/
			
			/*Dimension类只是用来封装单个对象中组件的宽度和高度，与组件的某个属性关�?*/
			Dimension d = new Dimension(300, 440);
			preparedTable.setModel(getDefaultTableModelr());
			preparedTable.setPreferredScrollableViewportSize(d);
			preparedTable.getColumnModel().getColumn(0).setPreferredWidth(140);
			preparedTable.getTableHeader().setReorderingAllowed(true);
		}
		return preparedTable;
	}

	public DefaultTableModel getDefaultTableModel() {
		if (defaultTableModel == null) {
			Object[] heads = { "PID", "processName", "PRI", "TIME", "MEM" };
			defaultTableModel = new DefaultTableModel(heads, 0);

		}
		return defaultTableModel;
	}
	
	public DefaultTableModel getDefaultTableModelr() {
		if (defaultTableModel == null) {
			Object[] heads = { "PID", "processName", "PRI", "TIME", "MEM" };
			defaultTableModel = new DefaultTableModel(heads, 0);

		}
		return defaultTableModel;
	}
	
	public JPanel getBlockPanel() {
		if (blockPanel == null) {
			blockPanel = new JPanel();
			blockPanel.setLayout(new BorderLayout());
			blockPanel.setBorder(BorderFactory.createTitledBorder(
					null, "BLOCKED",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION));
			blockPanel.add(getBlockTable(), BorderLayout.CENTER);
			blockPanel.add(getBlockScrollPane(), BorderLayout.CENTER);
			blockPanel.setBackground(new Color(245, 222, 179));
		}
		return blockPanel;
	}
	
	public JPanel getRunPanel() {
		if (runPanel == null) {
			runPanel = new JPanel();
			runPanel.setLayout(new BorderLayout());
			runPanel.setBorder(BorderFactory.createTitledBorder(null, "RUNNING",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION));
			runPanel.add(getRunScrollPane(), BorderLayout.CENTER);
			runPanel.setBackground(new Color(216, 191, 216));
		}
		return runPanel;
	}
	
	public JPanel getTerPanel() {
		if (terPanel == null) {
			terPanel = new JPanel();
			terPanel.setLayout(new BorderLayout());
			terPanel.setBorder(BorderFactory.createTitledBorder(null, "TERMINATED",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION));
			terPanel.add(getTerTable(), BorderLayout.CENTER);
			terPanel.add(getTerScrollPane(), BorderLayout.CENTER);
			terPanel.setBackground(new Color(255, 252, 153));
		}
		return terPanel;
	}
	
	private JButton b1 = new JButton("START");
	private JButton b2 = new JButton("BLOCK");
	private JButton b3 = new JButton("WAKEUP");
	private JButton b4 = new JButton("NEW");
	private JButton b5 = new JButton("INTR");
	private JRadioButton FCFS = new JRadioButton("FCFS"); //1
	private JRadioButton Pri = new JRadioButton("PRIORITY"); //1
	private JRadioButton SJF = new JRadioButton("SJF");   //3
//	private JRadioButton RR = new JRadioButton("RR");     //4
	
	public JPanel getButtonPane() {
		if (buttonPane == null) {
			buttonPane = new JPanel();
			
			b4.addActionListener(this);
			b1.addActionListener(this);
			b2.addActionListener(this);
			b3.addActionListener(this);
			b5.addActionListener(this);
			FCFS.addActionListener(this);
			SJF.addActionListener(this);
//			RR.addActionListener(this);
			Pri.addActionListener(this);
			
			buttonPane.setLayout(new GridLayout(12, 1));
			buttonPane.add(new JLabel(""));
			buttonPane.add(FCFS);
			buttonPane.add(Pri);
			buttonPane.add(SJF);
//			buttonPane.add(RR);
			buttonPane.add(new JLabel(""));
			buttonPane.add(b4);
			buttonPane.add(b1);
			buttonPane.add(b2);		
			buttonPane.add(b3);
			buttonPane.add(b5);
			buttonPane.add(new JLabel(""));
			
			buttonPane.setBackground(new Color(152, 251, 152));
			
			ButtonGroup group = new ButtonGroup();// 创建按钮组
	        group.add(FCFS);
	        group.add(Pri);
	        group.add(SJF);
//	        group.add(RR);
		}
		return this.buttonPane;
	}

	public JScrollPane getBlockScrollPane() {
		if (blockScrollPane == null) {
			blockScrollPane = new JScrollPane(getBlockTable());
		}
		return blockScrollPane;
	}
	
	public JScrollPane getTerScrollPane() {
		if (terScrollPane == null) {
			terScrollPane = new JScrollPane(getTerTable());
		}
		return terScrollPane;
	}
	
	public synchronized JTable getBlockTable() {
		if (blockTable == null) {
			blockTable = new JTable();
			Dimension d = new Dimension(300, 440);
			blockTable.setPreferredScrollableViewportSize(d);
			blockTable.setModel(getDefaultTableModel2());
			blockTable.getColumnModel().getColumn(0).setPreferredWidth(140);
			blockTable.getTableHeader().setReorderingAllowed(false);
		}
		return blockTable;
	}
	
	public synchronized JTable getTerTable() {
		if (terTable == null) {
			terTable = new JTable();
			Dimension d = new Dimension(300, 440);
			terTable.setPreferredScrollableViewportSize(d);
			terTable.setModel(getDefaultTableModelt());
			terTable.getColumnModel().getColumn(0).setPreferredWidth(140);
			terTable.getTableHeader().setReorderingAllowed(false);
		}
		return terTable;
	}
	
	public DefaultTableModel getDefaultTableModel1() {
		if (defaultTableModel1 == null) {
			Object[] heads = { "PID", "processName", "PRI", "TIME", "MEM" };
			defaultTableModel1 = new DefaultTableModel(heads, 0);
		}
		return defaultTableModel1;
	}
	
	public DefaultTableModel getDefaultTableModelt() {
		if (defaultTableModelt == null) {
			Object[] heads = { "PID", "processName", "PRI", "STATUS", "STATE" };
			defaultTableModelt = new DefaultTableModel(heads, 0);
		}
		return defaultTableModelt;
	}
	
	public JScrollPane getRunScrollPane() {
		if (runScrollPane == null) {
			runScrollPane = new JScrollPane(getRunTable());
		}
		return runScrollPane;
	}
	
	public synchronized JTable getRunTable() {
		if (runTable == null) {
			runTable = new JTable();
			Dimension d = new Dimension(300, 440);
			runTable.setPreferredScrollableViewportSize(d);
			runTable.setModel(getDefaultTableModel1());
			runTable.getColumnModel().getColumn(0).setPreferredWidth(140);
			runTable.getTableHeader().setReorderingAllowed(false);
		}
		return runTable;
	}
	
	public DefaultTableModel getDefaultTableModel2() {
		if (defaultTableModel2 == null) {
			Object[] heads = { "PID", "processName", "PRI", "TIME", "MEM" };
			defaultTableModel2 = new DefaultTableModel(heads, 0);
		}
		return defaultTableModel2;
	}
	
	int s;
	//进程管理操作
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		Schedule thread1 = new Schedule(getFrame(), strategy, showing, memorymodel);
		TimeSlice thread2 = new TimeSlice(getFrame(),strategy, showing, memorymodel);
		//创建进程
		if (e.getSource() == b4) {
			Addprocessor d = new Addprocessor(this, "create new process", false, strategy, showing);
			d.show();
		}
		//block进程
		else if (e.getSource() == b2) {
			int index = getFrame().getRunTable().getSelectedRow();
			if (index != -1) {
				DefaultTableModel rdft = (DefaultTableModel) getFrame().getRunTable().getModel();
				DefaultTableModel bdft = (DefaultTableModel) getFrame().getBlockTable().getModel();
				Object[] temp = { rdft.getValueAt(index, 0),
						          rdft.getValueAt(index, 1), rdft.getValueAt(index, 2),
						          rdft.getValueAt(index, 3), rdft.getValueAt(index, 4)};
				rdft.removeRow(index);
				bdft.addRow(temp);
			} else {
				JOptionPane.showMessageDialog(null, "Please choose a process in running table.",
						"Warning", JOptionPane.YES_OPTION);
			}
			//恢复进程到准备区
		} else if (e.getSource() == b3) {
			int index = getFrame().getBlockTable().getSelectedRow();
			if (index != -1) {
				DefaultTableModel pdft = (DefaultTableModel) getFrame().getPreparedTable().getModel();
				DefaultTableModel bdft = (DefaultTableModel) getFrame().getBlockTable().getModel();
				Object[] temp = { bdft.getValueAt(index, 0),
						          bdft.getValueAt(index, 1), bdft.getValueAt(index, 2),
						          bdft.getValueAt(index, 3), bdft.getValueAt(index, 4) };
				bdft.removeRow(index);
				pdft.addRow(temp);
			} else {
				JOptionPane.showMessageDialog(null, "Please choose a process in blocked table", "",
						JOptionPane.YES_OPTION);
			}
		}
		//启动进程
		else if (e.getSource() == b1) {
			thread1.start();
			thread2.start();
		}
		//中断进程
		else if(e.getSource() == b5) {
			intrDialog.setSize(330, 230);
			intrDialog.setBounds(660,320,330,230);
			intrDialog.setTitle("Interrupt");
			intrDialog.setContentPane(getIntrPane());
			
			nameText.setBounds(new java.awt.Rectangle(165, 31, 125, 21));
//			priorityText.setBounds(new java.awt.Rectangle(165, 69, 107, 20));
			runTimeText.setBounds(new java.awt.Rectangle(166, 105, 123, 23));
			
			intrDialog.setVisible(true);
//			System.out.println("中断允许");
//			thread1.stop = true;
//			thread2.stop = true;
//			try {
//				thread1.sleep(3000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			try {
//				thread2.sleep(300);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			Interupt sum= new Interupt();
//			s = sum.Timer();
//			thread1.stop = false;
//			thread2.stop = false;
//			System.out.println("中断处理结束"); 
//			System.out.println("中断期间进行加法计算，计算结果为"+s); 
			}
		else if(e.getSource() == FCFS)
		{
			strategy = 1;
//			thread1.setStrategy(strategy);
//			thread2.setStrategy(strategy);
		}
		else if(e.getSource() == Pri)
		{
			strategy = 2;
//			System.out.println("~~~"+strategy);
//			thread1.setStrategy(strategy);
//			thread2.setStrategy(strategy);
		}
		else if(e.getSource() == SJF)
		{
			strategy = 3;
//			thread1.setStrategy(strategy);
//			thread2.setStrategy(strategy);
		}
//		else if(e.getSource() == RR)
//		{
//			strategy = 4;
////			thread1.setStrategy(strategy);
////			thread2.setStrategy(strategy);
//		}
		//确认，开始执行
		else if (e.getSource() == confirm) {
			String name = getNameText().getText();
			//Integer prior = Integer.valueOf(getPriorityText().getSelectedItem().toString());
			Integer runTime = Integer.valueOf(getRunTimeText().getText());
			Interrupt thread3 = new Interrupt(getFrame(), runTime, name);
			thread3.start();
			intrDialog.setVisible(false);
		}
	}
	public static synchronized processUI getFrame() {
		if (frm == null) {
			frm = new processUI();
		}
		return frm;
	}
	
	public int getStrategy()
	{
		return strategy;
	}
	//public static void main(String[] args){
		//MainFrame mf = new MainFrame();
	//}
	private JPanel getIntrPane() {
		if (Intrpane == null) {
			jLabel2.setBounds(new java.awt.Rectangle(48, 106, 75, 22));
			jLabel2.setText("ITIME");
//			jLabel1.setBounds(new java.awt.Rectangle(48, 69, 75, 22));
//			jLabel1.setText("PROI");
			jLabel.setBounds(new java.awt.Rectangle(48, 30, 75, 22));
			jLabel.setText("processName");
			Intrpane = new JPanel();
			Intrpane.setLayout(null);
			Intrpane.add(jLabel, null);
			//Intrpane.add(jLabel1, null);
			Intrpane.add(jLabel2, null);
			Intrpane.add(getNameText(), null);
			//Intrpane.add(getPriorityText(), null);
			Intrpane.add(getRunTimeText(), null);
			Intrpane.add(getConfirm(), null);
			Intrpane.add(getcancel(), null);
			Intrpane.setBackground(new Color(255, 250, 205));

		}
		nameText.setText("");
		runTimeText.setText("");
		
		return Intrpane;
	}
	
	private JTextField getNameText() {
		//if (nameText == null) {
			//nameText = new JTextField();
			//nameText.setBounds(new java.awt.Rectangle(165, 31, 125, 21));
		//}
		return nameText;
	}
	
	/*JComboBox下拉列表*/
	private JComboBox getPriorityText() {
			
		return priorityText;
	}
	
	private JTextField getRunTimeText() {

		return runTimeText;
	}
	
	private JButton getConfirm() {
		if (confirm == null) {
			confirm = new JButton();
			confirm.setBounds(new java.awt.Rectangle(83, 143, 80, 24));
			confirm.setText("OK");
			confirm.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){

				}
			});
		}
		return confirm;
	}
	
	private JButton getcancel() {
		if (cancel == null) {
			cancel = new JButton();
			cancel.setBounds(new java.awt.Rectangle(170, 143, 80, 24));
			cancel.setText("CANCEL");
			cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//Intrpane.setVisible(false);

				}
			});
		}
		return cancel;
	}
}
