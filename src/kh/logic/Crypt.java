package kh.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
/**
 * Contain the Crypt logic
 * @author antoniopelusi
 *
 */
public class Crypt
{
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
 
    /**
     * encrypt() call CryptFun() passing the following parameters:
     * @param key is the String used to encrypt the input file with the AES algorithm
     * @param inputFile is the file to encrypt
     * @param outputFile is the output encrypted file
     * @return the encrypted file
     * @throws CryptoException
     */
    public static File encrypt(String key, File inputFile, File outputFile) throws Exception
    {
        return CryptFun(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }
 
    /**
     * decrypt() call CryptFun() passing the following parameters:
     * @param key is the String used to decrypt the input file with the AES algorithm
     * @param inputFile is the file to decrypt
     * @param outputFile is the output decrypted file
     * @return the decrypted file
     * @throws CryptoException
     */
    public static File decrypt(String key, File inputFile, File outputFile) throws Exception
    {
        return CryptFun(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }
    
    /**
     * The CryptFun() function implement the Cipher algorithm to encrypt/decrypt a file.
     * This function can be called only by encrypt() and decrypt() function.
     * @param cipherMode change CryptFun() functioning between ENCRYPT_MODE and DECRYPT_MODE
     * @param key is the String used by the AES algorithm to encrypt/decrypt a file
     * @param inputFile is the input file (encrypted if Chiper is in ENCRYPT_MODE and vice versa)
     * @param outputFile is the output file
     * @return the encrypted/decrypted file
     * @throws CryptoException
     */
    private static File CryptFun(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException
    {
        try
        {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
             
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
             
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
             
            inputStream.close();
            outputStream.close();
            
            return outputFile;
        }
        
        /**
         * This catch function include all the error that might show up
         * This function serves to avoid messy throws
         */
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException ex)
        {
        	throw new CryptoException("Error encrypting/decrypting file", ex.getCause());
        }
    }
}