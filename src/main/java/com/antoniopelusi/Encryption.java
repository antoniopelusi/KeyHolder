package com.antoniopelusi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Antonio Pelusi
 *
 */
public class Encryption
{
    private static String encryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + "keyholder";
    private static String decryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + ".keyholder";

    private static File encryptedFile = new File(encryptedDB);
    public static File decryptedFile = new File(decryptedDB);

    public static String initialKey;
    private static byte[] key;

    public static void encrypt() //case: createDatabase() and save()
    {        
        //read decryptedFile (created by writeCsvFile()) and store it in decryptedContent
        String decryptedContent = "";
        
        try
        {
            decryptedContent = new String(Files.readAllBytes(Paths.get(decryptedFile.getAbsolutePath())));
        }
        catch (IOException e)
        {
            
        }

        //encrypt decryptedContent and store it in encryptedContent
        String encryptedContent = Encryption.cipherEncrypt(decryptedContent, initialKey);

        //write encryptedContent in encryptedFile
        try(FileOutputStream fos = new FileOutputStream(encryptedFile); BufferedOutputStream bos = new BufferedOutputStream(fos))
        {
            byte[] bytes = encryptedContent.getBytes();

            bos.write(bytes);
            bos.close();
            fos.close();
        }
        catch (IOException e)
        {

        }

        //delete decryptedFile
        decryptedFile.delete();
    }

    public static String decrypt() throws IOException //case: loadDatabase().initialize()
    {
        //create temporary decryptedFile
        decryptedFile.createNewFile();

        //read encryptedFile (created by readCsvFile()) and store it in encryptedContent
        String encryptedContent = "";
 
        try 
        {
            encryptedContent = new String(Files.readAllBytes(Paths.get(encryptedFile.getAbsolutePath())));
        }
        catch (IOException e)
        {

        }

        //decrypt encryptedContent and store it in decryptedContent
        String decryptedContent = cipherDecrypt(encryptedContent, initialKey);

        //write decryptedContent in decryptedFile
        try(FileOutputStream fos = new FileOutputStream(decryptedFile); BufferedOutputStream bos = new BufferedOutputStream(fos))
        {
            byte[] bytes = decryptedContent.getBytes();

            bos.write(bytes);
            bos.close();
            fos.close();
        }
        catch (IOException e)
        {

        }

        
        //return decryptedFile path
        return decryptedFile.getAbsolutePath();
    }
    
    public static SecretKeySpec setKey(final String myKey)
    {
        MessageDigest sha = null;
        try
        {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            
            return new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {

        }
        return null;
    }

      public static String cipherEncrypt(final String strToEncrypt, final String initialKey)
      {
        try
        {            
            SecretKeySpec secretKey = setKey(initialKey);
            
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
 
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {

        }
        return null;
    }
    
    public static String cipherDecrypt(final String strToDecrypt, final String initialKey)
    {
        try
        {
            SecretKeySpec secretKey = setKey(initialKey);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            
        }
        return null;
    }
}
