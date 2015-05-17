package Rabin_scheme;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;


public class RabinDecryption 
{
	public RabinDecryption(Long p, Long q) 
	{
		this.p = p;
		this.q = q;
		this.n = p*q;
	}
	Long p;
	Long q;
	Long n;
	public Long decryptMessage(Long cipherText)
	{
		Long mp = (long) (Math.pow(cipherText, (p+3)/4)%p);
		Long mq = (long) (Math.pow(cipherText, (q+3)/4)%q);
		Long a,b;
		Random randomNumber = new Random();
		Long number = randomNumber.nextLong()%1000;
		number = Math.abs(number);
		a = number;
		b = findB(a);
		Long x = (a*p*mp + b*q*mq) % n;
		Long y = (a*p*mp - b*q*mq) % n;
		System.out.println("m1: "+x);
		System.out.println("m2: "+y);
		System.out.println("m3: "+(n-x));
		System.out.println("m4: "+(n-y));
		return x;
	}
	
	public Long findB(Long a)
	{
		BigInteger a2,b2;
		BigInteger p2,q2;
		a2 = new BigInteger(a.toString());
		b2 = BigInteger.ONE;
		p2 = new BigInteger(p.toString());
		q2 = new BigInteger(q.toString());
		BigInteger result = null;
		ArrayList<RabinThread> threads = new ArrayList<RabinThread>();
		ArrayList<Boolean> flags = new ArrayList<Boolean>();
		for(int i=0;i<4;i++)
		{
			flags.add(false);
		}
		threads.add( new RabinThread
				(result, a2, p2, q2, b2, flags,0));
		threads.add( new RabinThread
				(result, a2, p2, q2, 
				b2.add(BigInteger.ONE), flags,1));
		threads.add( new RabinThread(result, a2, p2, q2, 
				b2.add(BigInteger.ONE)
				.add(BigInteger.ONE), flags,2));
		threads.add( new RabinThread(result, a2, p2, q2, 
				b2.add(BigInteger.ONE)
				.add(BigInteger.ONE).add(BigInteger.ONE), flags,3));
		
		for (RabinThread rabinThread : threads) 
		{
			rabinThread.start();
		}
		while(!flags.get(0)
			&& !flags.get(1)
			&& !flags.get(2)
			&& !flags.get(3))
		{	
			try {
				Thread.sleep(200);
				//System.out.println("spie ");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result.longValue();
	}
}
