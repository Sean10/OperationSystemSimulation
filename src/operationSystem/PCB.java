/*
 * 进程控制块模块
 */

package operationSystem;

public class PCB {

	private String id;
	private String name;
	private Integer runTime;
	private Integer priority;
	private String operation;
	private String file;
	private Integer memuse;


	//构造函数，狗脏进程块
	public PCB(String name, String operation, String file) {
		super();
		int a = (int) (Math.random() * 1000 / 1);
		this.id = "Process-" + a;
		this.name = name;

		this.priority = (int) (Math.random() * 6 / 1);
		if(operation.equals("touch"))
			this.runTime = 30;
		else
			this.runTime = (int) (Math.random() * 25 / 1 + 5);
		this.memuse = (int) (Math.random() * 15 / 1 + 1);
		this.operation = operation;
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public Integer getPriority() {
		return priority;
	}

	public Integer getRunTime() {
		return runTime;
	}
	public Integer getMemUse() {
		return memuse;
	}

	public String getId() {
		return id;
	}

 }

