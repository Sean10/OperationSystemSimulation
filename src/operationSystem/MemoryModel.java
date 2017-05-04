package operationSystem;


import java.util.*;
//import osprac.vo.*;

import javax.swing.JOptionPane;


public class MemoryModel {
	
	private static final int MAX = 128;//512;
	private static final int K = 5;	//1024;
	
	

	private static MemoryModel instance;
	public MemoryModel () { }
	public static MemoryModel getInstance() {
		if (instance == null)
			instance = new MemoryModel();
		return instance;
	}
	
	
	private List<MemoryPartition> free = new ArrayList<MemoryPartition> ();
	private List<MemoryPartition> used = new ArrayList<MemoryPartition> ();

	//在构造时运行
	{
		free.add(new MemoryPartition(0, MAX * K, "free", "free"));
		System.out.println("test memoryPartition");
	}
	
	
	
	Map processList = new HashMap();
	Map fileList = new HashMap();
	
	public boolean addProcess(String processName, int pSize){
		
		for (int i = 0; i < free.size(); i++) {
			if (free.get(i).getCapacity() >= pSize) {
				int pId;
				System.out.println("counter is " + free.get(i).getCounter());
				pId = this.getInstance().allocation(pSize);
				for(MemoryPartition e : this.getInstance().used){
					if(e.getId() == pId){
						e.setName(processName);
						break;
					}
				}
				processList.put(processName, pId);
				return true;
			}
		}
		return false;
	}
	
	public void deleteProcess(String processName){
		int pId = (Integer)processList.get(processName);
		
		System.out.println("deleteProcess used size :"+ this.getInstance().used.size());
		this.deallocate(pId);
		System.out.println("deleteProcess used size :"+ this.getInstance().used.size());
		
		processList.remove(processName);
	}
	
	public boolean addFile(String fName, String fContent){
		int fSize = fContent.length();
		
		for (int i = 0; i < free.size(); i++) {
			MemoryPartition mp = free.get(i);
			if (mp.getCapacity() >= fSize) {
				int fId;
				fId = this.getInstance().allocation(fSize);
				fileList.put(fName, fId);
				for(MemoryPartition e : this.getInstance().used){
					if(e.getId() == fId){
						e.setContent(fContent);
						e.setName(fName);
						break;
					}
				}
				return true;
			}
		}
		return false;
		
	}
	
	//touch更新文件内容
	public boolean requesttouch(String fName, String fContent){
		int fSize = fContent.length();

		System.out.println("new file size is " + fSize);
		this.deleteFile(fName);

		for (int i = 0; i < free.size(); i++) {
			MemoryPartition mp = free.get(i);
			if (mp.getCapacity() >= fSize) {
				int fId;
				fId = this.getInstance().allocation(fSize);
				fileList.put(fName, fId);
				for(MemoryPartition e : this.getInstance().used){
					if(e.getId() == fId){
						e.setContent(fContent);
						e.setName(fName);
						break;
					}
				}
				return true;
			}
		}
		return false;
		
	}
	
	//删除文件
	public void deleteFile(String processName){
		int fId = (Integer)fileList.get(processName);
		System.out.println("hey!delete!!!!!!!!!!!!!!!!!" + processName + " id is " + fId);
		this.getInstance().deallocate(fId);
		fileList.remove(processName);
	}

	//show文件
	public String fileShow(String fName){
		int fId = (Integer)fileList.get(fName);
		for(MemoryPartition e : this.getInstance().used){
			System.out.println(e.getName() + "   " + "fID: " + fId + " eId: " + e.getId() + " " + e.getContent());
			if(e.getId() == fId){
				System.out.println(e.getContent());
				return e.getContent();
			}
		}
		return null;
	}
	
	
	//内存分配空间
	public int allocation(int size) {
		for (int i = 0; i < free.size(); i++) {
			//获取空闲内存中的索引i对应的内存分区结构数据
			MemoryPartition mp = free.get(i);
//			System.out.println("in allocation counter is " + free.get(i).getCounter());
			if (mp.getCapacity() >= size) {
				//移除空闲内存中的索引mp
				free.remove(mp);
				//在mp地址头分配size空间的内存
				MemoryPartition [] mps = mp.allocate(size);
				//对mps[0]位置设置数据为已使用
				mps[0].setStatus("used");
				//将对应地址添加到已使用实例
				used.add(mps[0]);
				System.out.println("Allocation used size :"+used.size());
				for(MemoryPartition e : this.getInstance().used){
					System.out.println("new used id is : " + e.getId());
				}

				//判断剩余空间是否还有容量
				if (mps[1] != null){
					mps[1].setStatus("free");
					this.getInstance().free.add(mps[1]);
				}
				return mps[0].getId();
			}
		}
		JOptionPane.showOptionDialog(null, "内存不足", "警告", JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE, null,null, "OK");
		throw new RuntimeException("Not Enough Memory Exception");
	}

	//内存释放
	public void deallocate(int mid) {
		MemoryPartition mp = null;
		
		System.out.println("Enter deallocate");
		for (MemoryPartition p: this.getInstance().used) { 
			if (p.getId() == mid) {
				mp = p;
				break;
			}
		}
		if (mp != null) {
			mp.setStatus("free");
			this.getInstance().free.add(mp);
			this.getInstance().used.remove(mp);
			emerge();
		}
	}

	/*
	public void free_all(){
		free.removeAll(free);
		used.removeAll(used);
		MemoryPartition mp = new MemoryPartition(1, 640, "free", "In");
		free.add(mp);
		}
		*/
	
	//合并内存空间
	private void emerge() {
		Collections.sort(this.getInstance().free);
		for (int i = this.getInstance().free.size() - 2; i >= 0; --i) {
			MemoryPartition previous = this.getInstance().free.get(i);
			previous.clearName();
			MemoryPartition next = this.getInstance().free.get(i + 1);
			next.clearName();
			if (previous.getStart() + previous.getCapacity() == next.getStart()) {
				previous.setCapacity(previous.getCapacity() + next.getCapacity());
				this.getInstance().free.remove(next);
			}
		}
	}
	
	public List<MemoryPartition> getFree() {
		return free;
	}
	
	public List<MemoryPartition> getUsed() {
		return used;
	}

	//获取内存空间大小
	public long getSize(){
		int total=0;
		for(MemoryPartition mp: free)
			total+=mp.getCapacity();
		for(MemoryPartition mp: used)
			total+=mp.getCapacity();
		return total;
	}
	
}
