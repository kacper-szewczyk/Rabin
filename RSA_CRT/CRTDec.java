package RSA_CRT;

import java.math.BigInteger;
import java.util.ArrayList;

public class CRTDec 
{
	private Long n;
	private Long e;
	private Long d;
	private ArrayList<Long> p;
	private ArrayList<Long> ds;
	
	public CRTDec(Long n, Long e, Long d, 
			ArrayList<Long> p, ArrayList<Long> ds)  
	{
		this.n = n;
		this.e = e;
		this.d = d;
		this.p = p;
		this.ds = ds;
	}
	
	public Long decryptMessage(Long cipherText)
	{
		/*
		BigInteger message = BigInteger.ONE;
		BigInteger m = new BigInteger(cipherText.toString());
		for(int i=0; i<this.d;i++)
		{
			message = message.multiply(m);
		}
		message = message.mod(new BigInteger(n.toString()));
		return message.longValue();
		*/
		ArrayList<BigInteger> ps = new ArrayList<BigInteger>();
		BigInteger cipher = new BigInteger(cipherText.toString());
		for(int i=0; i<p.size();i++)
		{
			ps.add(new BigInteger(p.get(i).toString()));
		}
		ArrayList<BigInteger> ms = new ArrayList<BigInteger>();
		for(int i=0; i<ps.size();i++)
		{
			ms.add(cipher.pow(ds.get(i).intValue()).mod(ps.get(i)));
		}
		BigInteger qInv = new BigInteger(
				findReverse(p.get(1), p.get(0)).toString());
		//System.out.println("qInV="+qInv);
		
		BigInteger differOfM = ms.get(0).subtract(ms.get(1));
		while(differOfM.compareTo(BigInteger.ZERO)<0)
			differOfM=differOfM.add(ps.get(0));
		BigInteger h = qInv.multiply(
				differOfM).mod(ps.get(0));
		BigInteger m = ms.get(1).add(h.multiply(ps.get(1)));
		return m.longValue();
		/*BigInteger qInv = new BigInteger(
				findReverse(p.get(1), p.get(0)).toString());
		ArrayList<BigInteger> m = new ArrayList<BigInteger>();
		BigInteger message = new BigInteger(cipherText.toString());
		for(int i=0; i<p.size();i++)
		{
			m.add(i,
					(message.pow(ds.get(i).intValue()))
						.mod(new BigInteger(p.get(i).toString())));
			if(m.get(i).compareTo(BigInteger.ZERO)<-1)
				m.set(i, m.get(i).add(new BigInteger(p.get(i).toString())));
		}
		BigInteger h = qInv.multiply(m.get(1).subtract(m.get(0)))
				.mod(new BigInteger(p.get(0).toString()));
		if(h.compareTo(BigInteger.ZERO)<-1)
			h=h.add(new BigInteger(p.get(0).toString()));
		BigInteger result = m.get(1).add(h.multiply(
				new BigInteger(p.get(1).toString())));
		return result.longValue();*/
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

	
}
