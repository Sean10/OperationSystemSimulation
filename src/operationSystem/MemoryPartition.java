
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

	public MemoryPartition () { 
		this.id = ++counter;
	}

	public MemoryPartition(long start, long capacity, String status, String area) {
		this.id = ++counter;
		this.start = start;
		this.capacity = capacity;
		this.status = status;
		this.content = null;
		this.name = null;
	}

	public  void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void clearName(){
		this.name = null;
	}
	
	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		MemoryPartition.counter = counter;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void addId(){
		this.id ++;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getCapacity() {
		return capacity;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
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
