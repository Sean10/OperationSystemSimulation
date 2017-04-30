package operationSystem;

public class Timerinterupt {
	private int Timer = 0;
	private int AClock =1000000000;

	public int Timer()
	{
		while(true)
		{
			Timer++;
			AClock--;
			if(AClock==0) break;
			for(int i=0;i<1000000000;i++);
		}
		return Timer;
		
	}

}
