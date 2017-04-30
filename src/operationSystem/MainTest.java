package operationSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

import operationSystem.AllUser;
import operationSystem.MyDir;
import operationSystem.MyDisk;
import operationSystem.MyDiskBlock;
import operationSystem.MyFile;

public class MainTest {
	    MainTest(String username) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		MyDir nowdir = new MyDir(username);//当前目录
		MyDir dirsave = null;//用以临时保存目录
		MyFile filesave = null;//用以临时保存文件
		while (true) {
			System.out.print(username+"/");
			Stack<String> brid=new Stack<String>();
		
			MyDir k = new MyDir();
			k = nowdir;
			while (nowdir.getFatherDir() != null) // 循环更新父目录磁盘块(添加)和大小，直至根目�?
			{

				brid.push(nowdir.getName()+"/");
				nowdir = nowdir.getFatherDir();
			}
			nowdir = k;
			while(!brid.empty())
            System.out.print(brid.pop());
			
			String s = br.readLine().trim();

			

			if (s.equals("ls")) // --->目录显示
				nowdir.ls();
			else if (s.startsWith("cd") && !s.equals("cdup")) { // --->跳转到指定目�?
				String real = s.substring(3, s.length()-1);//读取文件名，要除去首尾两个中括号
				MyDir a = nowdir.cd(real);
				if (a != null) {//更改当前目录，并记录下父目录
					MyDir b = nowdir;
					nowdir = a;
					nowdir.setFatherDir(b);
				} else
					System.out.println("您输入的目录名有误，请重新输�?");

			} else if (s.equals("cdup")) { // --->跳转到上层目�?
				if (nowdir.cdReturn() != null)
					nowdir = nowdir.cdReturn();//如果有父目录，则更改当前目录为其父目�?
				else
					System.out.println("已经�?到根目录");//如果没有父目录，则已经是根目�?

			}else if (s.startsWith("vim") && s.length() > 4) { // --->创建文件
				String real = s.substring(4, s.length()-1);//读取文件名，要除去首尾两个中括号
				nowdir.addFile(new MyFile(real, 0, 0));//调用addFile

			} else if (s.startsWith("rmfile") && s.length() > 7) { // --->删除文件
				String real = s.substring(7, s.length()-1);//读取文件名，要除去首尾两个中括号
				MyFile a = nowdir.getFile(real);//看是否有重名的文�?
				if (a != null) {
					ArrayList<MyDiskBlock> blocklist = a.getBlocklist();//得到该文件的属�?�：�?用的磁盘�?
					ArrayList<Integer> thenw = new ArrayList<Integer>();
					for (MyDiskBlock one : blocklist) {
						MyDisk.getInstance().deleteUsed(one.getId());
						thenw.add(one.getId());//记录下来�?用的磁盘块的ID
					}
					nowdir.deleteFile(real);//在该目录下删除该文件

					MyDir save2 = new MyDir();
					save2 = nowdir;
					while (nowdir.getFatherDir() != null) // 循环更新父目录磁盘块（删除）和大小，直至根目�?
					{

						nowdir.removeold(thenw);//调用removeold删除磁盘ID
						nowdir.updateSize();//更新大小
						nowdir = nowdir.getFatherDir();
					}
					nowdir = save2;

					System.out.println("Succeed to delete.");
				} else
					System.out.println("Sorry, the file doesn't exist.");
			} else if (s.startsWith("renfile") && s.length() > 8) { // --->文件重命�?
				String real = s.substring(8, s.length()-1);//读取文件名，要除去首尾两个中括号
				MyFile a = nowdir.getFile(real);
				if (a != null) {
					System.out.println("Please input new file name");
					String newname = br.readLine().trim();
					if (nowdir.canPasteFile(new MyFile(newname))) { // 判断是否存在同名文件，如果返回�?�为true则可以重命名
						if (!newname.equals("")) {
							nowdir.deleteFile(real);
							MyFile thnew = a;
							thnew.setName(newname);
							nowdir.addFile(thnew);
							System.out.println("Succeed to rename the file.");
						}
					} else
						System.out.println("Sorry, the file has existed.");

				} else
					System.out.println("Sorry, the file doesn't exist.");
			} else if (s.equals("open")) { // --->文件编辑
				System.out.println("Please input the file name.");
				String filename = br.readLine();
				MyFile a = nowdir.getFile(filename);
				if (a != null) {
					System.out.println("Please edit");
					ArrayList<MyDiskBlock> list = a.getBlocklist();
					ArrayList<Integer> oldused = new ArrayList<Integer>(); // 用来保存原本文件的磁块号
					StringBuffer all = new StringBuffer();//用来保存原本文件的内�?
					
					for (MyDiskBlock one : list) {
						oldused.add(one.getId());//保存编辑前文件使用的磁盘块号
						all.append(one.getContent());//保存编辑前文件的内容
						MyDisk.getInstance().deleteUsed(one.getId());//删除该磁盘块
					}

					MyDir save1 = new MyDir();
					save1 = nowdir;
					//先把磁盘块记录都删了
					while (nowdir.getFatherDir() != null) // 循环更新父目录磁盘块，直至根目录
					{

						nowdir.removeold(oldused);
						nowdir.updateSize();
						nowdir = nowdir.getFatherDir();
					}
					nowdir = save1;

					System.out.println(all);
					StringBuffer realcontent = new StringBuffer();
					realcontent.append(all);//先保存下来原来的文件内容
					realcontent.append(br.readLine());//新增新的内容
					int point = 0;
					ArrayList<MyDiskBlock> newFileBlocklist = new ArrayList<MyDiskBlock>();
					ArrayList<Integer> thenw = new ArrayList<Integer>();
					int blocksize = MyDiskBlock.getSize();//获取磁盘块大�?
					int sizecount = 0;
					if (realcontent.length() > blocksize) {//如果内容长度超出�?块磁盘的大小
						for (int i = 0; i < realcontent.length() - blocksize; i = i
								+ blocksize) {
							StringBuffer op = new StringBuffer(
									realcontent.substring(i, i + blocksize));//获取磁盘块大小的内容存在op
							MyDiskBlock newblock = new MyDiskBlock(op);//新建磁盘块并储存op
							MyDisk.getInstance().addUsed(newblock);//记录磁盘块，自动设置磁盘块ID
							thenw.add(newblock.getId());//记录磁盘块ID
							newFileBlocklist.add(newblock);
							point = i;
							sizecount++;
						}
						//剩下的不足以�?块的内容
						StringBuffer rest = new StringBuffer(
								realcontent.substring(point + blocksize,
										realcontent.length()));
						sizecount++;
						MyDiskBlock ano = new MyDiskBlock(rest);
						MyDisk.getInstance().addUsed(ano);
						thenw.add(ano.getId());
						newFileBlocklist.add(ano);
					} else {//如果没有超出
						StringBuffer shortone = new StringBuffer(
								realcontent.substring(0, realcontent.length()));
						MyDiskBlock one = new MyDiskBlock(shortone);
						sizecount++;
						MyDisk.getInstance().addUsed(one);
						thenw.add(one.getId());
						newFileBlocklist.add(one);
					}
					a.setOldsize(a.getNewsize()); // 将原本的文件大小置为上一次文件大�?

					int newsize = sizecount * blocksize;
					a.setNewsize(newsize); // 设置文件的最新大�?
					
					

					MyDir save2 = new MyDir();
					save2 = nowdir;
					while (nowdir.getFatherDir() != null) // 循环更新父目录磁盘块(添加)和大小，直至根目�?
					{

						nowdir.addnew(thenw);
						nowdir.updateSize();
						nowdir = nowdir.getFatherDir();
					}
					nowdir = save2;

					a.setBlocklist(newFileBlocklist);
					System.out.println("Succeed to edit");
				} else
					System.out.println("Sorry, something wrong happened when editing.");

			} else if (s.startsWith("type") && s.length() > 5) { // 显示文件内容
				String filename = s.substring(5, s.length()-1);//读取文件名，要除去首尾两个中括号
				MyFile a = nowdir.getFile(filename);
				if (a != null) {
					ArrayList<MyDiskBlock> list = a.getBlocklist();

					StringBuffer all = new StringBuffer();
					for (MyDiskBlock one : list) {
						all.append(one.getContent());
					}

					System.out.println(all);
				} else
					System.out.println("文件名称输入有误");

			} else if (s.startsWith("cpfile") && s.length() > 7) { // --->文件复制
				String real = s.substring(7, s.length()-1);//读取文件名，要除去首尾两个中括号
				MyFile a = nowdir.getFile(real);
				if (a != null) {
					filesave=new MyFile();
					filesave = a;
					System.out.println("复制文件到剪切板成功");
				} else
					System.out.println("对不起，文件名输入有�?");
			} else if (s.equals("ptfile")) { // --->文件粘贴
				if (filesave != null) {
					ArrayList<MyDiskBlock> fileblocklist = filesave
							.getBlocklist();
					if (nowdir.canPasteFile(filesave)) { // 判断目录下是否有同名文件
						StringBuffer realcontent = new StringBuffer();
						for (MyDiskBlock one : fileblocklist) {
							realcontent.append(one.getContent()); // 先将文件中的StringBuffer全部拷贝出来
						}

						int point = 0;
						ArrayList<MyDiskBlock> newFileBlocklist = new ArrayList<MyDiskBlock>(); // 创建新的磁盘块列表用来存放这个StringBuffer
						int blocksize = MyDiskBlock.getSize();
						int sizecount = 0;
						ArrayList<Integer> thenw = new ArrayList<Integer>();
						if (realcontent.length() > blocksize) {
							for (int i = 0; i < realcontent.length()
									- blocksize; i = i + blocksize) {
								StringBuffer op = new StringBuffer(
										realcontent.substring(i, i + blocksize));
								MyDiskBlock newblock = new MyDiskBlock(op);//新建磁盘块并储存op
								MyDisk.getInstance().addUsed(newblock); //记录磁盘块，自动设置磁盘块ID
								thenw.add(newblock.getId());
								newFileBlocklist.add(newblock);
								point = i;
								sizecount++;
							}
							StringBuffer rest = new StringBuffer(
									realcontent.substring(point + blocksize,
											realcontent.length()));
							sizecount++;
							MyDiskBlock ano = new MyDiskBlock(rest);
							MyDisk.getInstance().addUsed(ano); 
							thenw.add(ano.getId());
							newFileBlocklist.add(ano); 
						} else {
							StringBuffer shortone = new StringBuffer(
									realcontent.substring(0,
											realcontent.length()));
							MyDiskBlock one = new MyDiskBlock(shortone);
							sizecount++;
							MyDisk.getInstance().addUsed(one);
							thenw.add(one.getId());
							newFileBlocklist.add(one);
						}

						MyDir save2 = new MyDir();
						save2 = nowdir;
						while (nowdir.getFatherDir() != null) // 循环更新父目录磁盘块(添加)和大小，直至根目�?
						{

							nowdir.addnew(thenw);
							nowdir.updateSize();
							nowdir = nowdir.getFatherDir();
						}
						nowdir = save2;

						MyFile anoth = new MyFile(filesave.getName()); // 新建�?个文件用来保存复制板里的信息,不要直接用filesave,之前在此处出�?,debug用时2小时,就是因为没new�?个新的MyFile
						anoth.setBlocklist(newFileBlocklist);
						anoth.setNewsize(filesave.getNewsize());
						anoth.setOldsize(filesave.getOldsize());

						nowdir.addFile(anoth);
					} else
						System.out.println("该目录下已经有同名文件，不能粘贴");
				}

				else
					System.out.println("剪切板中没有文件");
			}

			else if (s.startsWith("rendir") && s.length() > 7) { // --->目录重命�?
				String real = s.substring(7, s.length()-1);
				MyDir old = nowdir.getDir(real);
				if (old != null) {
					System.out.println("请输入新的目录名");
					String newname = br.readLine().trim();
					if (nowdir.canPasteDir(new MyDir(newname))) { // 确认该目录下无同名目�?
						if (newname != "") {
							nowdir.deleteDir(real);
							MyDir thnew = old;
							thnew.setName(newname);
							nowdir.addDir(thnew);
							System.out.println("目录重命名成�?!!!");
						} else {
							System.out.println("文件名未输入");
						}
					} else
						System.out.println("已经存在同名目录");

				} else
					System.out.println("对不起，不存在该目录");

			} else if (s.startsWith("rmdir") && s.length() > 6) { // 删除目录
				String real = s.substring(6, s.length()-1);
				MyDir old = nowdir.getDir(real);
				if (old != null) {
					ArrayList<Integer> all = new ArrayList<Integer>();
					all = old.getUsedblock();
					for (Integer a : all) {
						MyDisk.getInstance().deleteUsed(a);
					}

					nowdir.deleteDir(real);

					MyDir save2 = new MyDir();
					save2 = nowdir;
					while (nowdir.getFatherDir() != null) // 循环更新父目录磁盘块(删除)和大小，直至根目�?
					{

						nowdir.removeold(all);
						nowdir.updateSize();
						nowdir = nowdir.getFatherDir();
					}
					nowdir = save2;
				} else
					System.out.println("对不起，不存在该目录");
			}

			else if (s.startsWith("mkdir") && s.length() > 6) { // 创建目录
				String dirname = s.substring(6, s.length()-1);
				MyDir dir = new MyDir(dirname, 0, 0);
				nowdir.addDir(dir);

			} else if (s.startsWith("cpdir") && s.length() > 6) { // 复制目录
				String real = s.substring(6, s.length()-1);
				MyDir a = nowdir.getDir(real);
				if (a != null) {
					dirsave=new MyDir();
					dirsave = (MyDir) MyDir.cloneObject(a);
					System.out.println("目录成功复制到剪切板");
				} else
					System.out.println("对不起，不存在该目录");
				
			} else if (s.equals("ptdir")) { // 粘贴目录
				if (dirsave != null) {
					MyDir instance = new MyDir();
					instance = (MyDir) MyDir.cloneObject(dirsave);
					if (nowdir.canPasteDir(instance)) { // �?查无同名目录，才能添加；
						instance.setFatherDir(nowdir);
						ArrayList<Integer> oldused = instance.getUsedblock();

						ArrayList<Integer> thenew = new ArrayList<Integer>();
						ArrayList<MyDiskBlock> bridge = new ArrayList<MyDiskBlock>();
						for (int i = 0; i < oldused.size(); i++) {
							MyDiskBlock a = new MyDiskBlock();
							MyDisk.getInstance().addUsed(a);
							thenew.add(a.getId());

						}
						instance.setUsedblock(thenew);
						nowdir.addDir(instance);

						MyDir save2 = new MyDir();
						save2 = nowdir;
						while (nowdir.getFatherDir() != null) // 循环更新父目录磁盘块(添加)和大小，直至根目�?
						{

							nowdir.addnew(thenew);
							nowdir.updateSize();
							nowdir = nowdir.getFatherDir();
						}
						nowdir = save2;
					} else
						System.out.println("目录下已经有同名目录，无法粘�?");

				} else
					System.out.println("剪切板中没有目录");
				
			} else if (s.equals("mem")) { // 显示磁盘使用情况
				MyDisk.getInstance().showUsed();
				
			} else if (s.equals("help")) { // 显示帮助
				System.out
						.println("-------------------------Below is the show and jump command------------------------------");
				System.out
						.println("ls   show file and direction   cd [dirname]:   jump to the specified direction               cdup	jump to up direction        ");
				System.out
						.println("---------------------------Below is the direction operation--------------------------------");
				System.out
						.println("mkdir [dirname]:Create direction       ptdir:paste direction       cpdir [dirname]:copy direction ");
				System.out
						.println("rendir [dirname]:rename direction           rmdir [dirname]:delete direction");
				System.out
						.println("---------------------------Below is the file operation--------------------------------");
				System.out
						.println("vim [filename]:create new file       ptfile:paste file       cpfile [filename]:copy file ");
				System.out
						.println("renfile [filename]:rename file           rmfile [filename]:delete file       type [filename]:show file");
				System.out.println("open :open file");
				System.out
						.println("---------------------------Below is the disk operation--------------------------------");
				System.out.println("mem  :show disk used status");
				System.out
						.println("---------------------------Below is the system operation----------------------------");
				System.out.println("exit");

				System.out
						.println("-----------------------------------------------------------------------------");
			} 
			else if (s.equals("exit")) {
				System.out.println("You have exit the system.");
				System.exit(0);
			} 
			else if (s.equals("cls")) {//清屏效果
				for (int i = 0; i <= 30; i++) {
					System.out.println();
				}
			} 
			else if(s.equals("logout")){
				MyDir thisone = new MyDir();
				thisone = nowdir;
				while (nowdir.getFatherDir() != null) // 循环更新父目录磁盘块(添加)和大小，直至根目�?
				{

					brid.push(nowdir.getName()+"/");
					nowdir = nowdir.getFatherDir();
				}
               AllUser.getInstance().addUser(thisone);
               System.out.println("You have logout，and you could choose：①exit   ②login 'other user'  ③add new user");
               String in=br.readLine().trim();
               if(in.equals("exit"))  System.exit(0);
               else if (in.startsWith("login") && in.length() > 6) { //登陆原有的账�?
   				String anouser = in.substring(6, in.length());
   				if(AllUser.getInstance().whetherExist(anouser)){
   					nowdir=AllUser.getInstance().getUserDir(anouser);
   	   				System.out.println("Welcome " + anouser
   	  						+ "   you could create new direction and new files, if you need help，please input help to look it up");
   	   				username=anouser;
   				}else System.out.println("The user doesn't exist.");
   					
               }
               else if (in.startsWith("add") && in.length() > 4) { //创建新账号并登录
      				String newuser = in.substring(4, in.length());
      				if(!AllUser.getInstance().whetherExist(newuser)){
      					nowdir= new MyDir(newuser);
          				System.out.println("Welcome " + newuser
          						+ "   you could create new direction and new files, if you need help，please input help to look it up");
          					AllUser.getInstance().addUser(nowdir);
          					username=newuser;
      				}
      				else System.out.println("The user has existed");
      					
                  }
               else {
            	   System.out.println("The command cannot be found");
               }
            	   
			}
			else
				System.out.println("The command cannot be found,please retry.");
			
		}

	}

}
