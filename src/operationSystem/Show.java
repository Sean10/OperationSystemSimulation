/*
*
* 线程、文件部分管理操作
*
*/

package operationSystem;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Show {

	private MyDir nowdir = new MyDir("show");
	//存储线程表的动态数组
	private ArrayList<thread> threadCame = new ArrayList<thread>();
	private MemoryModel mm = new MemoryModel();
	private JFrame frameT = new JFrame();
	private JPanel ContentPanel = new JPanel();
	private JPanel panel = new JPanel();
	JTextArea jta = new JTextArea();


	//初始化(测试）
	public void init(){
		//String content1 = "hi! i am file1. i am a test file!";
		//boolean result = nowdir.requestDiskBlock(mm, "file1",content1);
		//nowdir.addFile(new MyFile("file1", 0, 0));//调用addFile
		//String content2 = "hi! i am file2. i am a test file, too! how are you? it is cold in Beijing.";
		//result = nowdir.requestDiskBlock(mm, "file2",content2);
		//nowdir.addFile(new MyFile("file2", 0, 0));//调用addFile
	}

	//线程状态判断函数
	public boolean threadComing(String processName, String operation, String filename){
		String ls = "ls";
		String dir = "admin";
		String vim = "vim";
		if(operation.equals(ls)){
			//ls操作配置
			if(filename.equals(dir)){
				thread t = new thread(processName,operation,filename);
				threadCame.add(t);
				return true;
			}
			else
				return false;
		}
		else if(operation.equals(vim)){
			//新建文件线程操作
			MyFile f = nowdir.getFile(filename);
			if(f != null)
				return false;
			else{
				thread t = new thread(processName,operation,filename);
				threadCame.add(t);
				return true;
			}
		}
		else{
			//对其他(rm,cat,touch)操作新建线程
			MyFile f = nowdir.getFile(filename);
			if(f == null)
				return false;
			else{
				thread t = new thread(processName,operation,filename);
				threadCame.add(t);
				return true;
			}
		}
	}

	//启动线程
	public boolean threadStart(String processName){
		Iterator it = threadCame.iterator();
		//启动所有的线程
		while(it.hasNext()){
			thread temp = (thread) it.next();
			if(temp.getprocessName().equals(processName)){
				String op = temp.getOperation();
				String fn = temp.getFilename();
				switch(op){
				case "cat":
					//查看文件
					view(mm, fn);
					return true;
				case "ls":
					//打开一个ls窗口展示根目录文件
					showAll();
					return true;
					//打开一个窗口显示内容
				case "touch":
					touch(mm,fn);
					return true;
				default:
					return true;
				}
			}
		}
		return false;
	}

	//接收结束线程信号，结束操作
	public boolean threadEnd(String processName){
		Iterator it = threadCame.iterator();
		while(it.hasNext()){
			thread temp = (thread) it.next();
			if(temp.getprocessName().equals(processName)){
				String op = temp.getOperation();
				String fn = temp.getFilename();
				switch(op){
				case "vim":
					create(mm,fn);
					return true;
				case "rm":
					delete(mm,fn);
					return true;
				case "cat":
					//关掉弹出窗口
					closeView();
					return true;
				case "ls":
					//关掉弹出窗口
					showEnd();
					return true;
				case "touch":
					//
					touchEnd(fn);
					return true;
				}
			}
		}
		return false;
	}

	//创建文件
	public void create(MemoryModel mm, String filename){
		MyFile a = nowdir.getFile(filename);
		if(a==null){
			nowdir.addFile(new MyFile(filename, 0, 0));
			String content = filename;
			boolean result = nowdir.requestDiskBlock(mm, filename,content);
			if(result == true)
				JOptionPane.showMessageDialog(null, "a file is created!", "succeed", JOptionPane.PLAIN_MESSAGE); 
			else
				JOptionPane.showMessageDialog(null, "oops! same file exsits!", "alert", JOptionPane.ERROR_MESSAGE); 
		}
		//System.out.println(nowdir.);
	}

	//删除文件
	public void delete(MemoryModel mm, String filename){
		//看是否有重名的文件
		MyFile a = nowdir.getFile(filename);
		if(a != null){
			//在该目录下删除该文件
			nowdir.deleteFile(filename);
			//if(nowdir.requestDelete(filename) == true)
			nowdir.requestDelete(mm, filename);
			JOptionPane.showMessageDialog(null, "Deletion completed!", "succeed", JOptionPane.PLAIN_MESSAGE); 
		} 
		else
			JOptionPane.showMessageDialog(null, "Sorry! The file doesn't exit!", "alert", JOptionPane.ERROR_MESSAGE); 
	}

	//查看文件
	public void view(MemoryModel mm,String filename){
		MyFile a = nowdir.getFile(filename);
		if(a!=null){
			//调用mydir内的获取内容函数
			String output = nowdir.requestView(mm, filename);
			frameT.setTitle(filename);
			frameT.getContentPane().add(ContentPanel);
			//JTextArea jta = new JTextArea(output,10,15);
			jta.setText(output);
			jta.setLineWrap(true);
			ContentPanel.add(panel);
			panel.add(jta);
			frameT.setSize(320, 160);
			frameT.setVisible(true);
			frameT.setLocation(50, 50);
			frameT.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			panel.setBackground(new Color(255, 250, 205));
		}
		else
			JOptionPane.showMessageDialog(null, "Sorry! The file doesn't exit!", "alert", JOptionPane.ERROR_MESSAGE); 
	}

	//关闭查看
	public void closeView(){
		frameT.setVisible(false);
	}

	//查看所有的
	public void showAll(){
		nowdir.lsNew();
	}

	//关闭ls窗口
	public void showEnd(){
		nowdir.closeLs();
	}

	//显示文件内容
	public void touch(MemoryModel mm,String filename){
		MyFile a = nowdir.getFile(filename);
		if(a!=null){
			String output = nowdir.requestView(mm, filename);
			frameT.setTitle(filename);
			frameT.getContentPane().add(ContentPanel);
			jta.setText(output);
			jta.setLineWrap(true);
			ContentPanel.add(panel);
			panel.add(jta);
			frameT.setSize(320, 160);
			frameT.setVisible(true);
			frameT.setLocation(50, 50);
			frameT.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			panel.setBackground(new Color(255, 250, 205));
		}
		else
			JOptionPane.showMessageDialog(null, "Sorry! The file doesn't exit!", "alert", JOptionPane.ERROR_MESSAGE); 
	}

	//更新文件修改时间
	public void touchEnd(String filename){
		String m = jta.getText();
		boolean result;
		//调用memorymodel中的更新文件修改时间函数
		result = mm.requesttouch(filename,m);
		if(result == false)
			JOptionPane.showMessageDialog(null, " Sorry! Failed! ", "alert", JOptionPane.ERROR_MESSAGE); 
		else
			JOptionPane.showMessageDialog(null, "touch completed!", "succeed", JOptionPane.PLAIN_MESSAGE);
		frameT.setVisible(false);
	}
}
