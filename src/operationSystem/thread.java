package operationSystem;

public class thread {

	private String processName;
	private String operation;
	private String filename;
	
	public thread(String processName, String operation, String filename) {
		super();
		this.processName = processName;
		this.operation = operation;
		this.filename = filename;
	}
	public String getprocessName() {
		return processName;
	}
	public void setprocessName(String processName) {
		this.processName = processName;
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
