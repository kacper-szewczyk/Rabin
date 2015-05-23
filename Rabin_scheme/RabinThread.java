package Rabin_scheme;

import java.math.BigInteger;
import java.util.ArrayList;

public class RabinThread extends Thread
{
	private BigInteger b;
	private BigInteger a;
	private BigInteger p;
	private BigInteger q;
	private BigInteger n;
	private ArrayList<Boolean> flag;
	private int threadID;
	public BigInteger result;
	public RabinThread(BigInteger result,BigInteger a, 
			BigInteger p, BigInteger q,
			BigInteger b, ArrayList<Boolean> flag, int threadID) 
	{
		this.result=result;
		this.b = b;
		this.a=a;
		this.p=p;
		this.q=q;
		this.n=this.p.multiply(this.q);
		this.flag=flag;
		this.threadID=threadID;
	}
	
	@Override
	public void run() 
	{
		BigInteger result2 = a.multiply(p).add(b.multiply(q))
				.mod(n);
		while(!(result2.equals(BigInteger.ONE)) 
				&& !flag.get(0)
				&& !flag.get(1)
				&& !flag.get(2)
				&& !flag.get(3))
		{
			b = b.add(BigInteger.ONE.add
					(BigInteger.ONE.add
					(BigInteger.ONE.add
					(BigInteger.ONE))));
			
			result2 = a.multiply(p)
					.add(b.multiply(q));
			System.out.println(b);
			
		}
		flag.set(threadID, true);
		result = b;
	}
}
