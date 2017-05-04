package operationSystem;

import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;


public class TimeSlice extends Thread {
	volatile boolean stop = false;
	private static final int slice = 1;
	private static final SimpleDateFormat sf = new SimpleDateFormat(
			"HH:mm:ss ");
	private processUI frm;
	private int strategy;
	private Show showing;
	private MemoryModel memorymodel;
	
	
	char c = new Character((char) Character.LINE_SEPARATOR).charValue();
	public TimeSlice(processUI frm, int strategy, Show showing, MemoryModel memorymodel) {
		this.frm = frm;
		this.strategy = strategy;
		this.showing = showing;
		this.memorymodel = memorymodel;
	}
	public void run() {
		while (!stop) {
			DefaultTableModel rundft = (DefaultTableModel) getFrm().getRunTable().getModel();
			Vector v = rundft.getDataVector();
			Iterator it1 = v.iterator();

			while (it1.hasNext()) {
				Vector tmp1 = (Vector) it1.next();
//				tmp1.setElementAt("Running", 4);
				//System.out.println(sf.format(new Date())
						//+ "低级调度--就绪队列中优先权高的进程" + tmp1.elementAt(0) + ","
						//+ tmp1.elementAt(1) + "进入cpu");
//				getFrm().getRunInfo().append(
//						new Character((char) 13) + sf.format(new Date())
//								+ "Ready " + tmp1.elementAt(0)
//								+ "," + tmp1.elementAt(1) + " enter cpu\n");
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Object temp_v = v.elementAt(0) ;


			getFrm().repaint();
//			it1 = v.iterator();
			int vsize = v.size();
			for(int p = vsize-1; p >= 0; p--)
			{
				Vector tmp = (Vector)v.elementAt(p);
				//time越小，优先级越高
				int time = (int)tmp.elementAt(3);
				//这里可以设置非优先级最高的暂停减小
				//if(it1.equals(temp_v)) {
					time = ((Integer) tmp.elementAt(3)).intValue() - slice;
				//}
				tmp.setElementAt(new Integer(time), 3);

				if(time <= 0)
				{
					getFrm().getRunInfo().append(
							new Character((char) 13) + sf.format(new Date())
									+ "" + tmp.elementAt(0) + ","
									+ tmp.elementAt(1) + " termined\n");
					Object[] temp = {tmp.elementAt(0), tmp.elementAt(1),
							tmp.elementAt(2), "Normal", "Term" };
					DefaultTableModel terdft = (DefaultTableModel) getFrm().getTerTable().getModel();
					terdft.addRow(temp);
					rundft.removeRow(p);
					String processName = (String)tmp.elementAt(1);
					
					showing.threadEnd(processName);
					//发送线程结束信号
					System.out.println("time to use delete");
					memorymodel.deleteProcess(processName);
				}
			}
////			DefaultTableModel dft = (DefaultTableModel) getFrm().getRunTable().getModel();
////			Vector v = dft.getDataVector();
//			strategy = frm.getStrategy();
//			if(strategy == 1)
//				v = v;
//			else if(strategy == 2)
//			    v = Sort.sortPriority(v);
//			else if(strategy == 3)
//				v = Sort.sortTime(v);
//			else if(strategy == 4)
//				v = Sort.sortRR(v);
//			
////			System.out.println("!!!"+strategy);
//			
////			Iterator it1 = v.iterator();
//			if (it1.hasNext()) {
//				Vector tmp1 = (Vector) it1.next();
//				tmp1.setElementAt("Running", 4);
//				//System.out.println(sf.format(new Date())
//						//+ "低级调度--就绪队列中优先权高的进程" + tmp1.elementAt(0) + ","
//						//+ tmp1.elementAt(1) + "进入cpu");
//				getFrm().getRunInfo().append(
//						new Character((char) 13) + sf.format(new Date())
//								+ "Ready " + tmp1.elementAt(0)
//								+ "," + tmp1.elementAt(1) + " enter cpu\n");
//				while (it1.hasNext()) {
//					((Vector) it1.next()).setElementAt("Ready", 4);
//					try {
//						this.sleep(1);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//			try {
//				sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			// v = BubbleSort.sort(v);
//			getFrm().repaint();
//			if (getFrm().getRunTable().getRowCount() > 0) {
//				rundft = (DefaultTableModel) getFrm().getRunTable().getModel();
//				v = rundft.getDataVector();
//				Iterator it = v.iterator();
//				Vector tmp;
//				int priority;
//				int time;
//				if (it.hasNext()) {
//					tmp = (Vector) it.next();
//					priority = ((Integer) tmp.elementAt(2)).intValue();
////					if (priority > -5 && strategy != 1) {
////						priority--;
////					}
//					time = ((Integer) tmp.elementAt(3)).intValue() - slice;
//					p = time;
////					tmp.setElementAt(new Integer(priority), 2);
//					tmp.setElementAt(new Integer(time), 3);
//					
//				}
//				if (p <= 0) {
//					System.out.println(sf.format(new Date()) + ""
//							+ rundft.getValueAt(0, 0) + "," + rundft.getValueAt(0, 1)
//							+ "termined");
//					getFrm().getRunInfo().append(
//							new Character((char) 13) + sf.format(new Date())
//									+ "" + rundft.getValueAt(0, 0) + ","
//									+ rundft.getValueAt(0, 1) + "termined\n");
//					// System.out.println(dft.getRowCount()+"****");
//					rundft.removeRow(0);
//					// System.out.println(dft.getRowCount()+"****");
//					if (rundft.getRowCount() > 0) {
//						rundft.setValueAt("Running", 0, 4);
//						System.out.println(sf.format(new Date())
//								+ "Ready " + rundft.getValueAt(0, 0)
//								+ "," + rundft.getValueAt(0, 1) + "into cpu");
//						getFrm().getRunInfo().append(
//								new Character((char) 13)
//										+ sf.format(new Date())
//										+ "Ready "
//										+ rundft.getValueAt(0, 0) + ","
//										+ rundft.getValueAt(0, 1) + " enter cpu\n");
//					}
//				}
//			}
		}
	}

	public processUI getFrm() {
		return frm.getFrame();
	}
	
//	public void setStrategy(int s)
//	{
//		this.strategy = s;
//		System.out.println("@@@"+strategy);
//	}
}

