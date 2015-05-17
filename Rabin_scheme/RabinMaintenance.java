package Rabin_scheme;

import java.util.Random;

public class RabinMaintenance 
{
	
	public void test()
	{
		RabinGenerator gen=new RabinGenerator();
		RabinEncryption enc=new RabinEncryption(gen.getPublicKey());
		RabinDecryption dec=new RabinDecryption(gen.p,gen.q);
		Random randomMessage=new Random();
		Long message=randomMessage.nextLong()%10000000;
		message=Math.abs(message);
		Long cipherText = enc.encryptMessage(message);	
		Long received=dec.decryptMessage(cipherText);
		System.out.println("message: "+message);
		System.out.println("CipherText: "+cipherText);
		System.out.println("ReceivedText: "+received);
	}

}
