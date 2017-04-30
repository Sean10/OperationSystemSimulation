package operationSystem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Interrupt extends Thread {
	
	boolean stop = false;
	private processUI frm;
	private Integer intrTime = 0;
	private String name;
	
	private static final SimpleDateFormat sf = new SimpleDateFormat(
			"HH:mm:ss ");
	
	public Interrupt(processUI frm, Integer it, String name) {
		this.frm = frm;
		this.intrTime = it;
		this.name = name;
	}
	
	public void run() {
		while (!stop) {
			try {
				sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DefaultTableModel rdft = (DefaultTableModel) getFrm().getRunTable().getModel();
			DefaultTableModel bdft = (DefaultTableModel) getFrm().getBlockTable().getModel();
			DefaultTableModel pdft = (DefaultTableModel) getFrm().getPreparedTable().getModel();
				//System.out.println(sf.format(new Date())

			//
			Vector v = rdft.getDataVector();
			int vsize = v.size();
			if(vsize > 0)
			{
				//Iterator it = v.iterator();
				int p = 0;
				while (p < vsize) 
				{
					Vector tmp = (Vector)v.elementAt(p);
					String pname = (String)(tmp.elementAt(1));
					if(pname.equals(name))
					{
						Object[] temp = {tmp.elementAt(0), tmp.elementAt(1),
								tmp.elementAt(2), tmp.elementAt(3), tmp.elementAt(4) };
						bdft.addRow(temp);
						rdft.removeRow(p);
						getFrm().getRunInfo().append(
								new Character((char) 13) + sf.format(new Date())
										+ "Run         " + tmp.elementAt(0)
										+ "," + tmp.elementAt(1) + " is interrupt\n");
						try {
							sleep(intrTime*1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						p = 0;
						v = bdft.getDataVector();
						boolean introk = false;
						while(!introk)
						{
							tmp = (Vector)v.elementAt(p);
							pname = (String)(tmp.elementAt(1));
							if(pname.equals(name))
							{
								pdft.addRow(temp);
								bdft.removeRow(p);
								getFrm().getRunInfo().append(
										new Character((char) 13) + sf.format(new Date())
												+ "Interrupt " + tmp.elementAt(0)
												+ "," + tmp.elementAt(1) + " is finished\n");
							}
							else
								p++;
						}

						//调用中断、中断结束程序执行完毕，结束循环条件设置？
						p = vsize+1;
					}
					else
						p++;
				}
			}
//			else
//				stop = true;
//			if(stop == true)
//			{
//				System.out.println("¸ÃÖÐ¶ÏÁË");
//				getFrm().getRunInfo().append(
//						new Character((char) 13) + sf.format(new Date())
//								+ "ÖÐ¶Ï-" + name + "·¢Éú"
//								+ ", " + "Ê±¼äÎª"+intrTime+"s\n");
//			}
		}
	}


	public processUI getFrm() {
		return frm.getFrame();
	}
	
//	private int Timer = 0;
//	private int AClock =1000000000;
//
//	public int Timer()
//	{
//		while(true)
//		{
//			Timer++;
//			AClock--;
//			if(AClock==0) break;
//			for(int i=0;i<1000000000;i++);
//		}
//		return Timer;
//		
//	}

}
