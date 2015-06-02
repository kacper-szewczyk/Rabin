package RSA_CRT;

import java.math.BigInteger;

public class CRTEnc 
{

	public CRTEnc(Long n, Long e)
	{
		this.n = n;
		this.e = e;
	}
	Long n;
	Long e;
	public Long encryptMessage(Long message)
	{
		BigInteger cipherText = BigInteger.ONE;
		BigInteger m = new BigInteger(message.toString());
		for(int i=0; i<this.e;i++)
		{
			cipherText = cipherText.multiply(m);
		}
		cipherText = cipherText.mod(new BigInteger(n.toString()));
		return cipherText.longValue();
	}
}
