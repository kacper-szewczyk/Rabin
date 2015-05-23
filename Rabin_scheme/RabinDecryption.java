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
		while(true)
		{
			if(q%2==0)
			{
				q/=2;
				s++;
			}
			else
			{
				break;
			}
		}
		Long z = findNonQR();
		Long c = (long) Math.pow(z,q) % a;
		Long r = (long) Math.pow(cipherText, (q+1)/2) % a;
		Long t = (long) Math.pow(cipherText, q) % a;
		Long m = s;
		Long index = 0l;
		Long b;
		while(true)
		{
			if(t%a == 1)
			{
				break;
			}
			for(Long i=1l; i<m; i++)
			{
				if(Math.pow(t,Math.pow(2, i))%a == 1)
				{
					index = i;
					i = m;
				}
			}
			
			b = (long) (Math.pow(c,Math.pow(2, m-index-1))%a);
			r = (r*b)%a;
			t = (t*b*b)%a;
			c = (b*b)%a;
			m = index;
			System.out.println(" b " + b + " r " + r + " t " + t + " c " + c);
		}
		return p-r;
	}

	private Long findNonQR() 
	{
		Random randomNumber = new Random();
		Long nonQR = randomNumber.nextLong()%100;
		nonQR = Math.abs(nonQR);
		while(nonQR%4!=3 && nonQR%4!=2)
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
