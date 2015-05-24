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
		Long qrP = findQR(p, cipherText);
		Long qrQ = findQR(q, cipherText);
		Long mp = (long) (Math.pow(cipherText, qrP))%p;
		Long mq = (long) (Math.pow(cipherText, qrQ))%q;
		//System.out.println(p + " " + q + " " + n);
		//System.out.println(mp + " " + mq);
		
		Long yp = findGCDx(new BigInteger(p.toString()),
				new BigInteger(q.toString())).longValue();
		Long yq = findGCDy(new BigInteger(p.toString()),
				new BigInteger(q.toString())).longValue();
		Long x = (long) ((yp*p*mq + yq*q*mp) % n);
		Long y = (long) ((yp*p*mq - yq*q*mp) % n);
		x = Math.abs(x);
		y = Math.abs(y);
		System.out.println("m1: "+x);
		System.out.println("m2: "+y);
		System.out.println("m3: "+(n-x));
		System.out.println("m4: "+(n-y));
		return x;
	}
	
	private Long findQR(Long a, Long cipherText) 
	{
		if(a%4 == 3)
		{
			return (a+1)/4;
		}
		Long q = a-1;
		Long s = 0l;
		BigInteger q2 = new BigInteger(q.toString());
		BigInteger number2 = BigInteger.ONE.add(BigInteger.ONE);
		while(true)
		{
			if(q2.gcd(number2).equals(number2))
			{
				q2=q2.divide(number2);
				s++;
			}
			else
			{
				if(s==1)
				{
					return (a+1)/4;
				}
				q = q2.longValue();
				break;
			}
		}
		Long z = findNonQR(a);
		Long c = (long) Math.pow(z,q) % a;
		Long r = (long) Math.pow(cipherText, (q+1)/2) % a;
		Long t = (long) Math.pow(cipherText, q) % a;
		Long m = s;
		Long index = 0l;
		Long b;
		if(t%a != 1)
		{
			for(Long i=1l; i<m; i++)
			{
				if(Math.pow(t,Math.pow(2, i))%a == 1)
				{
					index = i;
					i = m;
				}
			}
			b = (long) Math.abs(Math.pow(c,Math.pow(2, m-index-1))%a);
			r = Math.abs((r*b)%a);
			t = Math.abs((t*b*b)%a);
			c = Math.abs((b*b)%a);
			m = index;
			return p-r;
		}
		else
			return r;
	}

	private Long findNonQR(Long a) 
	{
		ArrayList<Boolean> rests = new ArrayList<>();
		int resultOfPowering = 0;
		for(int i=0;i<a;i++)
		{
			rests.add(false);
		}
		for(int i=0;i<a;i++)
		{
			resultOfPowering = (int) (Math.pow(i, 2) % a);
			rests.set(resultOfPowering, true);
		}
		Random randomNumber = new Random();
		Long nonQR = randomNumber.nextLong()%100;
		nonQR = Math.abs(nonQR);
		while(!rests.get((int) (nonQR%a)))
		{
			nonQR++;
		}
		return nonQR;
	}

	private BigInteger findGCDy(BigInteger a, BigInteger b) 
	{
		BigInteger s = BigInteger.ZERO;
		BigInteger oldS = BigInteger.ONE;
		BigInteger r = b;
		BigInteger oldR = a;
		BigInteger quotient = BigInteger.ZERO;
		BigInteger help = BigInteger.ZERO;
		
		while(!(r.equals(BigInteger.ZERO)))
		{
			quotient = oldR.divide(r);
			help = r;
			r = oldR.subtract(quotient.multiply(help));
			oldR = help;
			help = s;
			s = oldS.subtract(quotient.multiply(help));
			oldS = help;
		}
		return s;
	}

	private BigInteger findGCDx(BigInteger a, BigInteger b) 
	{
		BigInteger t = BigInteger.ONE;
		BigInteger oldT = BigInteger.ZERO;
		BigInteger r = b;
		BigInteger oldR = a;
		BigInteger quotient = BigInteger.ZERO;
		BigInteger help = BigInteger.ZERO;
		while(!(r.equals(BigInteger.ZERO)))
		{
			quotient = oldR.divide(r);
			help = r;
			r = oldR.subtract(quotient.multiply(help));
			oldR = help;
			help = t;
			t = oldT.subtract(quotient.multiply(help));
			oldT = help;
		}
		return t;
	}
}
