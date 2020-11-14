package kh.logic;
/**
 * Avoid messy throws in Crypt function
 * @author antoniopelusi
 *
 */
public class CryptoException extends Exception
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * print the CryptoException
	 * @param message is a String containing a encrypt/decrypt error message
	 * @param throwable contain the Throwable IOException
	 */
    public CryptoException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}