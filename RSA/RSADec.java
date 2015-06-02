package RSA;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class RSADec 
{
	private Long n;
	private Long e;
	private Long d;
	private ArrayList<Long> p;

	public RSADec(Long n, Long e, Long d, ArrayList<Long> p)  
	{
		this.n = n;
		this.e = e;
		this.d = d;
		this.p = p;
	}
	
	public Long decryptMessage(Long cipherText)
	{
		
		BigInteger message = BigInteger.ONE;
		BigInteger m = new BigInteger(cipherText.toString());
		for(int i=0; i<this.d;i++)
		{
			message = message.multiply(m);
		}
		message = message.mod(new BigInteger(n.toString()));
		return message.longValue();
	}
	

}
