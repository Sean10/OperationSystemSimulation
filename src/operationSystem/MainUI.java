package operationSystem;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.*;

public class MainUI extends JFrame implements ActionListener{

	/**
	 * @param args
	 */
	//设置两个按钮指向进程和文件
	private JButton process = new JButton("PROCESS");
//	private JButton memory = new JButton("MEMORY");
	private JButton file = new JButton("FILE");
	//private JDialog dialog = new JDialog(this,true);


	public MainUI()
	{
		setTitle("Main UI");
		JPanel J=(JPanel)this.getContentPane();
		//初始化主UI容器,获取内容面板
		getContentPane();
		setSize(555, 355);
		setLocation(350, 150);
	    this.setLayout(null);
	    J.setOpaque(false);
		
	    // 设置背景
	    ImageIcon background = new ImageIcon("main.png");  
	 	// 设置背景实例
	 	JLabel label = new JLabel(background);  
	 	// 设置背景坐标、长宽
	 	label.setBounds(0, 0, this.getWidth(), this.getHeight()); 
	 	// 设置多层面板，添加背景板
	 	this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));  
	 	
	 	process.setBounds(80, 135, 100, 30);
	 	add(process);
//	 	memory.setBounds(80, 170, 100, 30);
//	 	add(memory);
	 	file.setBounds(80, 195, 100, 30);
	 	add(file);

	 	//设置监听
	 	process.addActionListener(this);
//		memory.addActionListener(this);
		file.addActionListener(this);
	 	
	 	this.setVisible(true);
	    
	    
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
            MainUI main= new MainUI();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == process)
	    {
			processUI mf = new processUI();
			Memory mem = new Memory();
	    }
	   
	    if(e.getSource() == file)
	    {
	      fileUI f = new fileUI();
		}

	}

}
