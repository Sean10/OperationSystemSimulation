package operationSystem;

import java.util.Iterator;
import java.util.Vector;

public class Sort {
	public static Vector sortPriority(Vector v) {
		Vector temp;
		for (int i = 0; i < v.size(); i++) {
			// 每轮要比较的次数
			for (int j = 0; j < v.size() - i - 1; j++) {
				Integer first = (Integer) ((Vector) v.elementAt(j))
						.elementAt(2);
				Integer second = (Integer) ((Vector) v.elementAt(j + 1))
						.elementAt(2);
				if (first.intValue() < second.intValue()) {
					temp = (Vector) v.elementAt(j);
					v.setElementAt(v.elementAt(j + 1), j);
					v.setElementAt(temp, j + 1);
				}
			}
		}
		return v;
	}
	
	public static Vector sortTime(Vector v) {
		Vector temp;
		for (int i = 0; i < v.size(); i++) {
			// 每轮�?要比较的次数
			for (int j = 0; j < v.size() - i - 1; j++) {
				Integer first = (Integer) ((Vector) v.elementAt(j))
						.elementAt(3);
				Integer second = (Integer) ((Vector) v.elementAt(j + 1))
						.elementAt(3);
				if (first.intValue() > second.intValue()) {
					temp = (Vector) v.elementAt(j);
					v.setElementAt(v.elementAt(j + 1), j);
					v.setElementAt(temp, j + 1);
				}
			}
		}
		return v;
	}
	
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
		System.out.println("排序�?:= " + sort.printArray(intArray));
		intArray = sort.bubbleSort(intArray);
		// Arrays.sort(intArray);
		System.out.println("排序�?:= " + sort.printArray(intArray));
	}*/

	public int[] bubbleSort(int[] obj) {
		int temp;
		// �?要比较的轮数
		for (int i = 0; i < obj.length; i++) {
			// 每轮�?要比较的次数
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

