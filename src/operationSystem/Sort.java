package operationSystem;

import java.util.Iterator;
import java.util.Vector;

public class Sort {
	//按优先级排序
	public static Vector sortPriority(Vector v) {
		Vector temp;
		for (int i = 0; i < v.size(); i++) {
			// 每轮要比较的次数
			for (int j = 0; j < v.size() - i - 1; j++) {
				//获取对应的优先级
				Integer first = Integer.parseInt(((Vector) v.elementAt(j))
						.elementAt(2).toString());
				Integer second = Integer.parseInt(((Vector) v.elementAt(j + 1))
						.elementAt(2).toString());
				if (first.intValue() < second.intValue()) {
					temp = (Vector) v.elementAt(j);
					v.setElementAt(v.elementAt(j + 1), j);
					v.setElementAt(temp, j + 1);
				}
			}
		}
		return v;
	}

	//按照时间排序
	public static Vector sortTime(Vector v) {
		Vector temp;
		for (int i = 0; i < v.size(); i++) {
			// 每轮要比较的次数
			for (int j = 0; j < v.size() - i - 1; j++) {
				Integer first = Integer.parseInt (((Vector) v.elementAt(j))
						.elementAt(3).toString());
				Integer second = Integer.parseInt (((Vector) v.elementAt(j + 1))
						.elementAt(3).toString());
				if (first.intValue() > second.intValue()) {
					temp = (Vector) v.elementAt(j);
					v.setElementAt(v.elementAt(j + 1), j);
					v.setElementAt(temp, j + 1);
				}
			}
		}
		return v;
	}

	//时间片轮转
	public static Vector sortRR(Vector v) {
		Vector temp;
		if(v.size() > 0)
		{
			temp = (Vector) v.elementAt(0);
			for(int i = 0; i <= v.size()-2; i++)
				v.setElementAt(v.elementAt(i+1), i);
		
			v.setElementAt(temp, v.size()-1);
		}
		
		return v;
	}
	/*public static void main(String[] args) {
		BubbleSort sort = new BubbleSort();
		int[] intArray = { 12, 23, 2, 56, 11, 44 };
		System.out.println("排序:= " + sort.printArray(intArray));
		intArray = sort.bubbleSort(intArray);
		// Arrays.sort(intArray);
		System.out.println("排序:= " + sort.printArray(intArray));
	}*/

	public int[] bubbleSort(int[] obj) {
		int temp;
		// 要比较的轮数
		for (int i = 0; i < obj.length; i++) {
			// 每轮要比较的次数
			for (int j = 0; j < obj.length - i - 1; j++) {
				if (obj[j] > obj[j + 1]) {
					temp = obj[j];
					obj[j] = obj[j + 1];
					obj[j + 1] = temp;
				}
			}
		}
		return obj;
	}

	public String printArray(int[] obj) {
		String result = "";
		for (int i = 0; i < obj.length; i++) {
			result += obj[i] + " ";
		}
		return result;
	}
}

