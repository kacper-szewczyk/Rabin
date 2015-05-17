package Rabin_scheme;

public class RabinEncryption 
{
	public RabinEncryption(Long n)
	{
		this.n = n;
	}
	Long n;
	public Long encryptMessage(Long message)
	{
		Long cipherText;
		cipherText = (long) (Math.pow(message, 2) % this.n);
		return cipherText;
	}
}
