package operationSystem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel; //DefaultTableModel使用�?个vector来存储单元格值的对象
                                            //该vector由多个vector组成

public class Schedule extends Thread {
	
	/*就像大家更熟悉的const一样，volatile是一个类型修饰符（type specifier）它是被设计用来修饰被不同线程访问和修改的变量
	 * 如果不加入volatile，基本上会导致这样的结果：要么无法编写多线程程序，要么编译器失去大量优化的机会
	 */
	volatile boolean stop = false;
	private processUI frm;
	private static final int daoshu = 3; //道数
	private static final SimpleDateFormat sf = new SimpleDateFormat(
			"HH:mm:ss ");
	private int strategy = 1;
	private Show showing;
	private MemoryModel memorymodel;
	
	public Schedule(processUI frm, int strategy, Show showing, MemoryModel memorymodel) {
		this.frm = frm;
		this.strategy = strategy;
		this.showing = showing;
		this.memorymodel = memorymodel;
	}
	
	public void run() {
		while (true) {
//			System.out.println("ScheduleTEST");
			try {
				sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (getFrm().getPreparedTable().getRowCount() > 0 && 
			    getFrm().getRunTable().getRowCount() < 3) 
			{
				DefaultTableModel dft = (DefaultTableModel) getFrm()
						.getPreparedTable().getModel();
				DefaultTableModel rundft = (DefaultTableModel) getFrm()
						.getRunTable().getModel();
				Vector v = dft.getDataVector();
				strategy = frm.getStrategy();
				if(strategy == 1)
					;
				else if(strategy == 2)
				    v = Sort.sortPriority(v);
				else if(strategy == 3)
					v = Sort.sortTime(v);
				else if(strategy == 4)
					v = Sort.sortRR(v);
				if(v.size()>0)
				{
					Integer memuse = (Integer)(((Vector)v.elementAt(0)).elementAt(4));
					String pname = (String)(((Vector)v.elementAt(0)).elementAt(1));
					
					boolean MemOK = memorymodel.addProcess(pname, memuse);
					/*这里是给管占明发进程名，占用内存大小

					new �?个包含IsMemOK函数的对�?
					boolean MemOK = IsMemOK(name, memuse); name是String类型  memuse是Integer*/
					
//					boolean MemOK = true;
//					System.out.println(MemOK+"!!!"+pname+" "+memuse);
					if(MemOK)
					{
//						String pname = (String)((Vector)v.elementAt(0)).elementAt(1);
						
						showing.threadStart(pname);
						/*这里是管占明那边的内存满足了，进程就�?始运行，我这边告诉卓思进程名，卓思那边要查找之前存的这个进程要干啥，
						 * 就像早上说的如果是显示文件，那你就要立即显示出来，其他的话就不用管了，等我进程运行完的时候给你指令你再操�?
		
						
						new �?个包含ProcessStart函数的对�?
						ProcessStart(pname); pname是String类型*/
						
						if(v.size() > 0)
						{
							Vector tmp = (Vector)v.elementAt(0);
							Object[] temp = { ((Vector)v.elementAt(0)).elementAt(0), ((Vector)v.elementAt(0)).elementAt(1),
									((Vector)v.elementAt(0)).elementAt(2), ((Vector)v.elementAt(0)).elementAt(3), ((Vector)v.elementAt(0)).elementAt(4) };
							rundft.addRow(temp);
							dft.removeRow(0);
							
							
//							Object[] temp = { dft.getValueAt(0, 0), dft.getValueAt(0, 1),
//									dft.getValueAt(0, 2), dft.getValueAt(0, 3), "Ready" };
//							dft.removeRow(0);
//							DefaultTableModel rdft = (DefaultTableModel) getFrm()
//									.getRunTable().getModel();
							//System.out.println(sf.format(new Date())
								//	+ "高级调度--后备队列中优先权�?高的进程" + temp[0].toString() + ","
								//	+ temp[1].toString() + "进入就绪队列");
							getFrm().getRunInfo().append(
									new Character((char) 13) + sf.format(new Date())
											+ "Ready     " + tmp.elementAt(0)
											+ "," + tmp.elementAt(1) + " change to Run\n"
											);
//							rdft.addRow(temp);
						}
						
					}
				}
			
//				Vector v = rdft.getDataVector();
//				strategy = frm.getStrategy();
//				if(strategy == 1)
//					v = v;
//				else if(strategy == 2)
//				    v = Sort.sortPriority(v);
//				else if(strategy == 3)
//					v = Sort.sortTime(v);
//				else if(strategy == 4)
//					v = Sort.sortRR(v);
//				
//				Iterator it = v.iterator();
//				if (it.hasNext()) {
//					Vector tmp = (Vector) it.next();
//					tmp.setElementAt("Running", 4);
//					//System.out.println(sf.format(new Date())
//							//+ "低级调度--就绪队列中优先权�?高的进程" + tmp.elementAt(0) + ","	+ tmp.elementAt(1) + "进入cpu");
//					getFrm().getRunInfo().append(
//							new Character((char) 13) + sf.format(new Date())
//									+ "Ready " + tmp.elementAt(0)
//									+ "," + tmp.elementAt(1) + " enter cpu\n");
//					while (it.hasNext()) {
//						((Vector) it.next()).setElementAt("Ready", 4);
//					}
//				}
			}
		}
	}

	public processUI getFrm() {
		return frm.getFrame();
	}
	
//	public void setStrategy(int s)
//	{
//		this.strategy = s;
//	}
}

