/*
 * 用户类
 */


package operationSystem;

import java.util.Hashtable;

public class AllUser {
	private String username;
	private Hashtable<String, MyDir> userlist = new Hashtable<String, MyDir>();
    private static AllUser inst=new AllUser();

    private AllUser(){
		
	}

	//创建单线程实例
	public static synchronized AllUser getInstance(){
		return inst;
	}

	//添加用户
	public void addUser(MyDir a) {
		this.userlist.put(a.getName(), a);
	}

	//判断是否用户已存在
	public boolean whetherExist(String username){
		return userlist.containsKey(username);
	}

	//获取用户名
    public MyDir getUserDir(String username){
    	return this.userlist.get(username);
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Hashtable<String, MyDir> getUserlist() {
		return userlist;
	}

	public void setUserlist(Hashtable<String, MyDir> userlist) {
		this.userlist = userlist;
	}
}
