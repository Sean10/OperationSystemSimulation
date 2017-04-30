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
	private ArrayList<thread> threadCame = new ArrayList<thread>();
	private MemoryModel mm = new MemoryModel();
	private JFrame frameT = new JFrame();
	private JPanel ContentPanel = new JPanel();
	private JPanel panel = new JPanel();
	JTextArea jta = new JTextArea();
	
	public void init(){
		String content1 = "hi! i am file1. i am a test file!";
		boolean result = nowdir.requestDiskBlock(mm, "file1",content1);
		nowdir.addFile(new MyFile("file1", 0, 0));//调用addFile
		String content2 = "hi! i am file2. i am a test file, too! how are you? it is cold in Beijing.";
		result = nowdir.requestDiskBlock(mm, "file2",content2);
		nowdir.addFile(new MyFile("file2", 0, 0));//调用addFile
		
		
	}
	
	public boolean threadComing(String pName, String operation, String filename){
		String ls = "ls";
		String dir = "admin";
		String vim = "vim";
		if(operation.equals(ls)){
			if(filename.equals(dir)){
				thread t = new thread(pName,operation,filename);
				threadCame.add(t);
				return true;
			}
			else
				return false;
		}
		else if(operation.equals(vim)){
			MyFile f = nowdir.getFile(filename);
			if(f != null)
				return false;
			else{
				thread t = new thread(pName,operation,filename);
				threadCame.add(t);
				return true;
			}
		}
		else{
			MyFile f = nowdir.getFile(filename);
			if(f == null)
				return false;
			else{
				thread t = new thread(pName,operation,filename);
				threadCame.add(t);
				return true;
			}
		}
	}
	
	public boolean threadStart(String pName){
		Iterator it = threadCame.iterator();
		while(it.hasNext()){
			thread temp = (thread) it.next();
			if(temp.getpName().equals(pName)){
				String op = temp.getOperation();
				String fn = temp.getFilename();
				switch(op){
				case "type":
					view(mm, fn);
					return true;
				case "ls":
					showAll();
					return true;
				case "modify":
					modify(mm,fn);
					return true;
				default:
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean threadEnd(String pName){
		Iterator it = threadCame.iterator();
		while(it.hasNext()){
			thread temp = (thread) it.next();
			if(temp.getpName().equals(pName)){
				String op = temp.getOperation();
				String fn = temp.getFilename();
				switch(op){
				case "vim":
					create(mm,fn);
					return true;
				case "rmfile":
					delete(mm,fn);
					return true;
				case "type":
					//关掉弹出
					closeView();
					return true;
				case "ls":
					//关掉弹出�?
					showEnd();
					return true;
				case "modify":
					//
					modifyEnd(fn);
					return true;
				}
			}
		}
		return false;
	}
	
	public void create(MemoryModel mm, String filename){
		MyFile a = nowdir.getFile(filename);
		if(a==null){
			nowdir.addFile(new MyFile(filename, 0, 0));//调用addFile
			String content = filename;
			boolean result = nowdir.requestDiskBlock(mm, filename,content);
			if(result == true)
				JOptionPane.showMessageDialog(null, "a file is created!", "succeed", JOptionPane.PLAIN_MESSAGE); 
			else
				JOptionPane.showMessageDialog(null, "oops! same file exsits!", "alert", JOptionPane.ERROR_MESSAGE); 
		}
		//System.out.println(nowdir.);
	}
	
	public void delete(MemoryModel mm, String filename){
		MyFile a = nowdir.getFile(filename);//看是否有重名的文�?
		if(a != null){
			nowdir.deleteFile(filename);//在该目录下删除该文件
			//if(nowdir.requestDelete(filename) == true)
			nowdir.requestDelete(mm, filename);
			JOptionPane.showMessageDialog(null, "Deletion completed!", "succeed", JOptionPane.PLAIN_MESSAGE); 
		} 
		else
			JOptionPane.showMessageDialog(null, "Sorry! The file doesn't exit!", "alert", JOptionPane.ERROR_MESSAGE); 
	}
	
	public void view(MemoryModel mm,String filename){
		MyFile a = nowdir.getFile(filename);
		if(a!=null){
			String output = nowdir.requestView(mm, filename);
			//JFrame frame = new JFrame(filename);
			frameT.setTitle(filename);
			//JPanel ContentPanel = new JPanel();
			//JPanel panel = new JPanel();
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
	
	public void closeView(){
		frameT.setVisible(false);
	}
	
	public void showAll(){
		nowdir.lsNew();
		//nowdir.ls();
	}
	
	public void showEnd(){
		nowdir.closeLs();
	}
	
	public void modify(MemoryModel mm,String filename){
		MyFile a = nowdir.getFile(filename);
		if(a!=null){
			String output = nowdir.requestView(mm, filename);
			frameT.setTitle(filename);
			//JPanel ContentPanel = new JPanel();
			//JPanel panel = new JPanel();
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
			
			//modify
			
		}
		else
			JOptionPane.showMessageDialog(null, "Sorry! The file doesn't exit!", "alert", JOptionPane.ERROR_MESSAGE); 
	}
	
	public void modifyEnd(String filename){
		String m = jta.getText();
		boolean result;
		result = mm.requestModify(filename,m);
		if(result == false)
			JOptionPane.showMessageDialog(null, " Sorry! Failed! ", "alert", JOptionPane.ERROR_MESSAGE); 
		else
			JOptionPane.showMessageDialog(null, "Modify completed!", "succeed", JOptionPane.PLAIN_MESSAGE); 
		frameT.setVisible(false);
	}
}
