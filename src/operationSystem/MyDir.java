package operationSystem;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MyDir implements Serializable{
	private static final long serialVersionUID = 1L;

	// 目录名
	private String name;

	// 父目录
	private MyDir fatherDir;
	// 该目录下目录列表
	private Hashtable<String, MyDir> dirlist = new Hashtable<String, MyDir>();
	// 该目录下文件列表
	private Hashtable<String, MyFile> filelist = new Hashtable<String, MyFile>();
	// 该目录大小
	private int oldsize;
	private int newsize;
	// 记录该目录所占用的磁盘块序号
	private ArrayList<Integer> usedblock = new ArrayList<Integer>();
	
	
	private JFrame frame = new JFrame();
	JPanel ContentPanel = new JPanel();
	JPanel panel = new JPanel();
	JTextArea jta = new JTextArea();

	public ArrayList<Integer> getUsedblock() {
		return usedblock;
	}

	//克隆对象
	 public static Object cloneObject(Object obj){
	     Object objx=null;
		 try{
	    	    ByteArrayOutputStream  byteOut = new ByteArrayOutputStream();  
		        ObjectOutputStream out = new ObjectOutputStream(byteOut);  
		        out.writeObject(obj);         
		        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
		        ObjectInputStream in =new ObjectInputStream(byteIn);  
		        objx=in.readObject();
		        
	     }catch (Exception e) {
			// TODO: handle exception
	    	 System.out.println("Failed to copy the dir. Please copy again.");
		}
		 return objx;
		
	}
/**
 * 判断能否粘贴该目录
 * @param a
 * @return
 */
    public boolean canPasteDir(MyDir a){
    	return !dirlist.containsKey(a.getName());
    }
    /**
     * 判断能否粘贴该文件
     * @param a
     * @return
     */
    public boolean canPasteFile(MyFile a){//如果含该文件，则返回true的非，即false；如果不含，则返回false
    	return !filelist.containsKey(a.getName());
    }
    
    
	public void setUsedblock(ArrayList<Integer> usedblock) {
		this.usedblock = usedblock;
	}
	
	public void removeold(ArrayList<Integer> all){//删除磁盘记录
		for(Integer a:all){
			if (usedblock.contains(a))
			usedblock.remove(a);
		}
	}
	
	//在目录下增加磁盘块号
	public void addnew(ArrayList<Integer> all){
		for(Integer a:all){
			usedblock.add(a);
		}
	}

	// 更新目录的大小
	public void updateSize(){
		this.newsize=MyDiskBlock.getSize()*usedblock.size();//得到每块磁盘块的大小和所用的磁盘块数量，乘积为大小
	}

	public int getOldsize() {
		return oldsize;
	}

	public void setOldsize(int oldsize) {
		this.oldsize = oldsize;
	}

	public int getNewsize() {
		return newsize;
	}

	public void setNewsize(int newsize) {
		this.newsize = newsize;
	}

	public MyDir() {

	}

	public MyDir(String name) {
		this.name = name;
	}

	public MyDir(String name, int old, int tnew) {
		this(name);
		this.newsize = tnew;
		this.oldsize = old;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MyDir getFatherDir() {
		return fatherDir;
	}

	public void setFatherDir(MyDir fatherDir) {
		this.fatherDir = fatherDir;

	}


	 //显示目录下所有文件和目录
	public void ls() {
		int count = 0;
		Iterator<String> a = filelist.keySet().iterator();
		while (a.hasNext()) {
			MyFile inst = filelist.get(a.next());
			System.out.print(inst.getName() + "(file)---size:" + inst.getNewsize()
					+ "     ");
			count++;
		}
		Iterator<String> b = dirlist.keySet().iterator();
		while (b.hasNext()) {
			MyDir inst = dirlist.get(b.next());
			System.out.print(inst.getName() + "(dir)---size:" + inst.getNewsize()
					+ "     ");
			count++;
		}
		if (count == 0)
			System.out.println("sorry, there isn't file here");
		else
			System.out.println();
	}

	 // 跳转cd
	public MyDir cd(String name) {

		return dirlist.get(name);
	}

	public MyDir cdReturn() {
		return fatherDir;
	}

	/**
	 * 根据目录名得到该目录
	 * 
	 * @param oldname
	 *            目录名，为String类型
	 * 
	 */
	public MyDir getDir(String oldname) {
		return dirlist.get(oldname);
	}

	/**
	 * 添加目录
	 * 
	 * @param a
	 *            创建个MyDir的实例
	 */
	public void addDir(MyDir a) {
		if(dirlist.containsKey(a.getName()))
		     System.out.println("Sorry! There exists the direction with the same name.");
		else dirlist.put(a.getName(), a);
	}

	/**
	 * 删除目录
	 * 
	 * @param dirname
	 *            String
	 */
	public void deleteDir(String dirname) {
		dirlist.remove(dirname);
	}

	/**
	 * 根据文件名得到该文件
	 * 
	 * @param filename
	 *            文件名，为String类型
	 * 
	 */
	public MyFile getFile(String filename) {
		return filelist.get(filename);
	}

	/**
	 * 增加一个文件
	 * 
	 * @param filename
	 *            文件名，为String类型
	 * 
	 */
	public void addFile(MyFile a) {
		if(filelist.containsKey(a.getName()))
				System.out.println("Sorry! There exists the file with the same name.");
		else filelist.put(a.getName(), a);
	}

	/**
	 * 删除文件
	 * 
	 * @param filename
	 *            String
	 */
	public void deleteFile(String filename) {
		filelist.remove(filename);
	}
	
	
	//new
	public boolean requestDiskBlock(MemoryModel mm, String filename, String content){
		//new an instance
		//MemoryModel mm = new MemoryModel();
		//call a function and get the result(boolean)
		boolean result = mm.addFile(filename, content);
		return result;
		}
	//delete
	public void requestDelete(MemoryModel mm, String filename){
		//new an instance of you
		//MemoryModel mm = new MemoryModel();
		//call a function and get the result(boolean)
		mm.deleteFile(filename);
		//return result;
	}
	//view
	public String requestView(MemoryModel mm, String filename){
		//new an instance of you
		//MemoryModel mm = new MemoryModel();
		//call a function and get the result(string)
		String content = mm.fileShow(filename);
		System.out.println(content + "-------dosi");
		return content;
	}
	//ls
	public void lsNew() {
		int count = 0;
		Iterator<String> a = filelist.keySet().iterator();
		String file = "";
		while (a.hasNext()) {
			MyFile inst = filelist.get(a.next());
			file += "文件:"+ inst.getName() + "\n";
			//System.out.print(inst.getName());
			count++;
		}
		if (count == 0)
			JOptionPane.showMessageDialog(null, "Sorry! No file under this dir!", "alert", JOptionPane.ERROR_MESSAGE);
			//System.out.println("对不起，当前目录下并无文�?");
		else{
			//System.out.println(file);
			frame.setTitle("dosi");
			//JPanel ContentPanel = new JPanel();
			//JPanel panel = new JPanel();
			//frame.getContentPane().add(ContentPanel);
			//JTextArea jta = new JTextArea(file);
			jta.setText("");
			jta.setText(file);
			jta.setLineWrap(true);
			//ContentPanel.add(panel);
			panel.add(jta);
			frame.add(panel);
			frame.setSize(200, 300);
			frame.setVisible(true);
			frame.setLocation(50, 50);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			panel.setBackground(new Color(255, 250, 205));
		}
	}
	
	public void closeLs(){
		frame.setVisible(false);
	}
}
