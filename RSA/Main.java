package RSA;

import java.util.Random;


public class Main 
{
	public static void main(String[] args) 
	{
		
		RSAGen gen=new RSAGen();
		while(gen.getD()==null)
			gen = new RSAGen();

		RSAEnc enc=new RSAEnc(gen.getPublicKeyN(),
				gen.getPublicKeyE());
		RSADec dec=new RSADec(gen.getPublicKeyN(),
				gen.getPublicKeyE(),gen.getD(),gen.getPrivateKeyP());
		Random randomMessage=new Random();
		Long message=randomMessage.nextLong()%gen.getPublicKeyN();
		message=Math.abs(message);
		Long cipherText = enc.encryptMessage(message);	
		System.out.println("message: "+message);
		System.out.println("CipherText: "+cipherText);
		Long receivedText = dec.decryptMessageStandard(cipherText);
		System.out.println("ReceivedText: "+receivedText);
	}

}
