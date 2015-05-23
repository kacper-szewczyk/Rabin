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
		Long message=randomMessage.nextLong()%gen.n;
		message=Math.abs(message);
		Long cipherText = enc.encryptMessage(message);	
		System.out.println("message: "+message);
		System.out.println("CipherText: "+cipherText);
		dec.decryptMessage(cipherText);
		
	}

}
