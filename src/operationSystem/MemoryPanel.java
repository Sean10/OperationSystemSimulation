/*
 *  内存界面
 */


package operationSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import java.util.*;
import java.util.List;


public class MemoryPanel implements ActionListener{
	Memory mem;
	BackgroundPanel panel = new BackgroundPanel((new ImageIcon("graphics\\images.jpg")).getImage());
	//JPanel panel = new JPanel(new BorderLayout());
	JButton refresh = new JButton("refresh"), 
			allocate = new JButton("allocate"), 
			free = new JButton("free"),
	        free_all = new JButton("free_all");
	DefaultTableModel dtm = new DefaultTableModel();
	JTable table = new JTable(dtm);
	JLabel status  = new JLabel("All memory will be shown here.");
	static String [] headers = {"Memory Block ID", "Initializer Blocks", "Memory Block Size", "State", "Process/File Name"};
	public MemoryPanel(Memory memory) {
		mem = memory;
		//panel.add(createTopPanel(), BorderLayout.PAGE_START);
		panel.add(createCenterPanel(), BorderLayout.CENTER);
		//panel.add(createBottomPanel(), BorderLayout.PAGE_END);
		
		refresh();
	}
	
	JPanel createTopPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
		panel.add(refresh);
		panel.add(allocate);
		panel.add(free);
		panel.add(free_all);
		refresh.addActionListener(this);
		allocate.addActionListener(this);
		free.addActionListener(this);
		free_all.addActionListener(this);

		return panel;
	}	

	//创建中心面板
	JScrollPane createCenterPanel() {
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		return scrollPane;
	}
	
	/*
	JPanel createBottomPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.add(status);
		long msize = MemoryModel.getInstance().getSize();
		//status.setText(String.valueOf(msize));
		return panel;
	}
	*/
	//刷新内存界面
	public void refresh() {
		List<MemoryPartition> free = MemoryModel.getInstance().getFree();
		List<MemoryPartition> used = MemoryModel.getInstance().getUsed();
		List<MemoryPartition> all = new ArrayList<MemoryPartition>();
		Collections.sort(used);
		all.addAll(used);   
		Collections.sort(free);
		all.addAll(free);
		Collections.sort(all);

		showData(all);
		panel.repaint();
	}
	
	//show内存使用
	public void showData(List<MemoryPartition> all) {
		String [] [] data = new String [all.size()] [];

		for (int i = 0; i < data.length; i++){
			data[i] = all.get(i).toStringArray();
		}
		dtm.setDataVector(data, headers);
		//System.out.println(data);
	}
	
	
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/*if(e.getSource() == refresh)
		{
			refresh();	
		}
		if(e.getSource() == allocate)
		{
			String s = JOptionPane.showInputDialog("Input the size to allocate:");
			if (s == null || s.equals("") || s.equals("0")) return ;
			try {
				int value = Integer.parseInt(s);
				int SSS = MemoryModel.getInstance().allocation(value);
				System.out.println(SSS);
				refresh();
			} catch (NumberFormatException nfe) {
				return;
			}catch (RuntimeException ek){
				return;
			}	
		}
		if(e.getSource() == free)
		{
			int index = table.getSelectedRow();//Returns the index of the first selected row, -1 if no row is selected
			
			if (index < 0) return ;
			int mid = Integer.parseInt(dtm.getValueAt(index, 0).toString());
			MemoryModel.getInstance().deallocate(mid);
			
			refresh();
		}
		if(e.getSource() == free_all)
		{
			MemoryModel.getInstance().free_all();
			refresh();
		}
		*/
}
	
	//设置背景界面类
class BackgroundPanel extends JPanel{
	Image im;
	public BackgroundPanel(Image im)
	{
		this.im = im;
		//this.setOpaque(true);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(im, 0, 0, this.getWidth(), this.getHeight(), this);
		
	}

}


	
}
