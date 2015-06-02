package RSA_CRT;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class CRTGen 
{
	public CRTGen(ArrayList<Long> p) 
	{
		this.p = p;
		this.n = 1l;
		this.k = p.size();
		this.fi = 1l;
		
		for(int i=0;i<k;i++)
		{
			this.n *= this.p.get(i);
			this.fi *= (this.p.get(i)-1);
		}
		Random random = new Random();
		findE();
		this.d = findReverse(this.d,this.p.get(0));
	}
	
	public CRTGen() 
	{
		
		/*this.p = new ArrayList<Long>();
		this.p.add(61l);
		this.p.add(53l);
		this.n = 1l;
		this.k = p.size();
		this.fi = 1l;
		
		for(int i=0;i<k;i++)
		{
			this.n *= this.p.get(i);
			this.fi *= (this.p.get(i)-1);
		}
		findE();
		this.d = findReverseD();*/
		
		this.p = new ArrayList<Long>();
		this.ds = new ArrayList<Long>();
		this.p.add(137l);
		this.p.add(131l);
		this.n = p.get(0)*p.get(1);
		this.fi = 3120l;
		this.e = 3l;
		this.d = 11787l;
		this.ds.add(d % (p.get(0)-1));
		this.ds.add(d % (p.get(1)-1));
	}
	
	private void findE() 
	{
		Random random = new Random();
		this.e = random.nextLong()%this.fi;
		BigInteger N = new BigInteger(this.n.toString());
		BigInteger bigE = new BigInteger(this.e.toString());
		while(!N.gcd(bigE).equals(BigInteger.ONE))
		{
			bigE=bigE.add(BigInteger.ONE);
			if(bigE.compareTo(new BigInteger(fi.toString()))>=0)
				bigE = BigInteger.ONE.add(BigInteger.ONE);
		}
		this.e = bigE.longValue();
	}
	
	private ArrayList<Long> p;
	public Long n;
	private int k;
	public Long e;
	public Long fi;
	private Long d;
	private ArrayList<Long> ds;
	
	public Long getPublicKeyN() 
	{
		return n;
	}
	
	public Long getPublicKeyE() 
	{
		return e;
	}
	
	public Long getFi() 
	{
		return fi;
	}
	
	public ArrayList<Long>  getPrivateKeyP() 
	{
		return p;
	}
	public ArrayList<Long>  getPrivateKeyDs() 
	{
		return ds;
	}
	
	public Long findReverse(Long a, Long b)
	{
		Long u = 1l;
		Long x = 0l;
		Long w = a;
		Long z = b;
		Long buffer;
		Long q2;
		while(w!=0l)
		{
			if(w<z)
			{
				buffer=u;
				u=x;
				x=buffer;
				buffer=w;
				w=z;
				z=buffer;
			}
			q2=w/z;
			u=u-q2*x;
			w=w-q2*z;
		}
		if(z!=1l)
		{
			//System.out.println("Couldn'y find d^(-1)");
			return null;
		}
		if(x<0l)
		{
			x=x+b;
		}
		return x;
	}
	
	public Long getD() 
	{
		return d;
	}
}
