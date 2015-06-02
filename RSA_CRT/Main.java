package RSA_CRT;

import java.math.BigInteger;
import java.util.Random;

public class Main 
{

	public static void main(String[] args)
	{
		CRTGen gen=new CRTGen();
		while(gen.getD()==null)
			gen = new CRTGen();

		CRTEnc enc=new CRTEnc(gen.getPublicKeyN(),
				gen.getPublicKeyE());
		CRTDec dec=new CRTDec(gen.getPublicKeyN(),
				gen.getPublicKeyE(),gen.getD(),
				gen.getPrivateKeyP(),gen.getPrivateKeyDs());
		Random randomMessage=new Random();
		Long message=randomMessage.nextLong()%gen.getPublicKeyN();
		message=Math.abs(message);
		Long cipherText = enc.encryptMessage(message);
		System.out.println("message: "+message);
		System.out.println("CipherText: "+cipherText);
		Long receivedText = dec.decryptMessage(cipherText);
		System.out.println("ReceivedText: "+receivedText);

	}

}
