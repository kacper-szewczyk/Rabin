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
		Long np = findQR(p);
		Long qrP = findR(p,np);
		Long qrP2 = p-qrP;
		Long nq = findQR(q);
		Long qrQ = findR(q,nq);
		Long qrQ2 = q-qrQ;
		Long mp = (long) (Math.pow(cipherText, qrP))%p;
		Long mq = (long) (Math.pow(cipherText, qrQ))%q;
		Long mp2 = (long) (Math.pow(cipherText, qrP2))%p;
		Long mq2 = (long) (Math.pow(cipherText, qrQ2))%q;
		//System.out.println("mp=" + mp + " mq=" + mq);
		Long yp = findGCDx(new BigInteger(p.toString()),
				new BigInteger(q.toString())).longValue();
		Long yq = findGCDy(new BigInteger(p.toString()),
				new BigInteger(q.toString())).longValue();
		//System.out.println("yp=" + yp + " yq=" + yq);
		Long result = yp*p+yq*q;
		//System.out.println(result);
		Long x = (long) ((yp*p*mq + yq*q*mp) % n);
		Long y = (long) ((yp*p*mq - yq*q*mp) % n);
		x = Math.abs(x);
		y = Math.abs(y);
		Long x2 = (long) ((yp*p*mq2 + yq*q*mp2) % n);
		Long y2 = (long) ((yp*p*mq2 - yq*q*mp2) % n);
		x2 = Math.abs(x2);
		y2 = Math.abs(y2);
		Long x3 = (long) ((yp*p*mq + yq*q*mp2) % n);
		Long y3 = (long) ((yp*p*mq - yq*q*mp2) % n);
		x3 = Math.abs(x3);
		y3 = Math.abs(y3);
		Long x4 = (long) ((yp*p*mq2 + yq*q*mp) % n);
		Long y4 = (long) ((yp*p*mq2 - yq*q*mp) % n);
		x4 = Math.abs(x4);
		y4 = Math.abs(y4);
		System.out.println("m1: "+x);
		System.out.println("m2: "+y);
		System.out.println("m3: "+(n-x));
		System.out.println("m4: "+(n-y));
		/*System.out.println("m12: "+x2);
		System.out.println("m22: "+y2);
		System.out.println("m32: "+(n-x2));
		System.out.println("m42: "+(n-y2));
		System.out.println("m13: "+x3);
		System.out.println("m23: "+y3);
		System.out.println("m33: "+(n-x3));
		System.out.println("m43: "+(n-y3));
		System.out.println("m14: "+x4);
		System.out.println("m24: "+y4);
		System.out.println("m34: "+(n-x4));
		System.out.println("m44: "+(n-y4));*/
		return x;
	}
	
	private Long findQR(Long a) 
	{
		Long rest = 1l;
		ArrayList<Long> rests = new ArrayList<>();
		for(Long i = 1l; i < a;i++)
		{
			rest = (long) (Math.pow(i, 2)%a);
			if(rest<0) rest = a - rest;
			rests.add(rest);
		}
		return rests.get(new Random().nextInt((int) (a-1)));
	}

	private Long findR(Long a, Long na) 
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
		Long r = (long) Math.pow(na, (q+1)/2) % a;
		Long t = (long) Math.pow(na, q) % a;
		Long m = s;
		Long index = 0l;
		Long b;
		System.out.println("z="+z+" r="+r+" t="+t+" c="+c + " m="+m);
		while(true)
		{
			if(t%a != 1)
			{
				for(Long i=1l; i<m; i++)
				{
					if(Math.pow(t,Math.pow(2, i))%a == 1)
					{
						index = i;
						i = m;
					}
					if(Math.pow(t,Math.pow(2, i))%a == 1-a)
					{
						index = i;
						i = m;
					}
				}
				if(m == 0) return r;
				b = (long) Math.pow(c,Math.pow(2, m-index-1))%a;
				r = (r*b)%a;
				t = (t*b*b)%a;
				c = (b*b)%a;
				m = index;
				System.out.println("b="+b+" r="+r+" t="+t+" c="+c+ " m="+m);
			}
			else
			{
				return r;
			}
				
		}
	}

	private Long findNonQR(Long a) 
	{
		Long result = 1l;
		for(Long i = 1l; i < a;i++)
		{
			if(Math.pow(i, (a-1)/2)%a == -1)
			{
				result=i;
				break;
			}
			if(Math.pow(i, (a-1)/2)%a == a-1)
			{
				result=i;
				break;
			}
		}
		return result;
	}

	private BigInteger findGCDx(BigInteger a, BigInteger b) 
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
		return oldS;
	}

	private BigInteger findGCDy(BigInteger a, BigInteger b) 
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
		return oldT;
	}
}
