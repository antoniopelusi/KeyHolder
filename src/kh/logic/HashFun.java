package kh.logic;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashFun
{
	static byte[] Hash(String pw) throws NoSuchAlgorithmException, InvalidKeySpecException
	{

		byte[] salt = new byte[16];
		for(int i=0; i<16; i++)
		{
			salt[i] = 0;			
		}
		
		KeySpec spec = new PBEKeySpec(pw.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		
		byte[] hash = factory.generateSecret(spec).getEncoded();
		
		return hash;
	}
	
	static String encoder(byte[] hash)
	{
		Base64.Encoder enc = Base64.getEncoder();
		String enchash = enc.encodeToString(hash);
		
		System.out.println("\t\t" + enchash);
		return enchash;
	}
}
