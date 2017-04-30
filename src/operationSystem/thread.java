package operationSystem;

public class thread {

	private String pName;
	private String operation;
	private String filename;
	
	public thread(String pName, String operation, String filename) {
		super();
		this.pName = pName;
		this.operation = operation;
		this.filename = filename;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
