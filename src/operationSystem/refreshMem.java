/*
 * 内存管理界面刷新
 */


package operationSystem;
import java.util.Date;


public class refreshMem extends Thread {
	
	
	private Memory mem;
	boolean stop = false;
	
	refreshMem(Memory mem)
	{
		this.mem = mem;
	}
	//刷新内存界面
	public void run() 
	{
		while (!stop)
		{
//			System.out.println("refreshTEST");
		    try 
		    {
			    sleep(1000);
		    } 
		    catch (InterruptedException e) 
		    {
			// TODO Auto-generated catch block
			    e.printStackTrace();
		    }
		
		    mem.getMemoryPanel().refresh();
		}
		
	}
}
