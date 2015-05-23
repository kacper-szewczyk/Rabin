package Rabin_scheme;

import java.util.Random;

public class RabinGenerator 
{
	public RabinGenerator() 
	{
		generateKey();
	}
	Long p;
	Long q;
	Long n;
	private void generateKey()
	{
		/*findQ();
		findP();
		*/
		p = 82633l; // as an example 
		q = 101161l; // p=q=1(mod4)
		p = 5l; // as an example 
		q = 37l; // p=q=1(mod4)
		n=p*q;
	}
	private void findQ() 
	{
		Random randomNumber = new Random();
		Long number=(long) 4;
		while(number%4==1)
		{
			while(!checkIfPrime(number))
			{
				number = randomNumber.nextLong();
				number = Math.abs(number);
			}
		}
		q=number;
		
	}
	private boolean checkIfPrime(Long number) 
	{
		Long sqrt=(long) Math.sqrt(number);
		for(int i=2;i<=sqrt;i++)
		{
			if(number%i==0)
				return false;
		}
		return true;
	}
	private void findP() 
	{
		Random randomNumber = new Random();
		Long number=(long) 4;
		while(number%4==1)
		{
			while(!checkIfPrime(number))
			{
				number = randomNumber.nextLong();
				number = Math.abs(number);
			}
		}
		p=number;
	}
	public Long getPublicKey() 
	{
		return n;
	}
}
