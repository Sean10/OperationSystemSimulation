
package operationSystem;

import java.util.HashMap;
import java.util.Map;

public class MemoryPartition implements Comparable<MemoryPartition> {
	
	
	
	static int counter = 0;//内存使用块数
	private String name;
	private int id;//内存占用的序号
	private long start;//内存占用的初始块
	private long capacity;//内存的可使用容量
	private String status;
	private String content;//文件内容
	
	
	/*public String getStatus() {
		return status;
	}*/

	public void setStatus(String status) {
		this.status = status;
	}

	//构造函数
	public MemoryPartition () { 
		this.id = ++counter;
	}

	//构造函数
	public MemoryPartition(long start, long capacity, String status, String area) {
		this.id = ++counter;
		this.start = start;
		this.capacity = capacity;
		this.status = status;
		this.content = null;
		this.name = null;
	}

	//设置内存占用名字
	public  void setName(String name){
		this.name = name;
	}

	//获取内存占用名字
	public String getName(){
		return name;
	}

	//清除内存占用名字
	public void clearName(){
		this.name = null;
	}

	//获取内存使用块数
	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		MemoryPartition.counter = counter;
	}
	
	//获取内存使用序号
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void addId(){
		this.id ++;
	}

	//获取初始块
	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	//获取容量
	public long getCapacity() {
		return capacity;
	}

	//设置容量
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	//设置内容
	public void setContent(String content){
		this.content = content;
	}

	//获取内存内容
	public String getContent(){
		return content;
	}


	
	public int compareTo(MemoryPartition mp) {
		return (int)(start - mp.start);
	}
	
	@Override
	public int hashCode() {
		return (int)start;
	}
	
	@Override
	public boolean equals(Object object) {
		return object != null && object instanceof MemoryPartition
				&& ((MemoryPartition)object).id == id;
	}
	
	public String [] toStringArray () {
		return new String [] {
				String.valueOf(id),
				String.valueOf(start),
				String.valueOf(capacity),
				status,
				name
		};
	}

	public MemoryPartition[] allocate(long size) {
		//
		if (this.capacity == size) {
			return new MemoryPartition [] {this, null};
		}
		MemoryPartition [] mps = new MemoryPartition [2];
		mps[1] = new MemoryPartition();
		mps[1].start = this.start + size;
		mps[1].capacity = this.capacity - size;
		this.capacity = size;
		mps[0] = this;		
		//System.out.println("in allocate counter is " + this.getCounter());
		return mps;
	}
}
