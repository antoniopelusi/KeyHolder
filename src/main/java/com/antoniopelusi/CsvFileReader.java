package com.antoniopelusi;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat.Builder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Antonio Pelusi
 *
 */
public class CsvFileReader
{   
    //CSV file header
    private static final String [] FILE_HEADER_MAPPING = {"name", "email", "password"};
     
    //Account attributes
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
     
    private static String decryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + ".keyholder";
    public static File decryptedFile = new File(decryptedDB);

    public static ObservableList<Account> readCsvFile(String encryptedDB)
    {
        FileReader fileReader = null;
         
        CSVParser csvFileParser = null;
        
        //Create the CSVFormat object with the header mapping and set the header
        Builder builder = Builder.create().setHeader(FILE_HEADER_MAPPING);
        CSVFormat csvFileFormat = builder.build();
                
        //Create a new list of account to be filled by CSV file data 
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        
        try
        {
            //decrypt database
            String decryptedDB = Encryption.decrypt();

            //initialize FileReader object
            fileReader = new FileReader(decryptedDB);
             
            //initialize CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
             
            //Get a list of CSV file records
            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
             
            //Read the CSV file records starting from the second record to skip the header
            for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord record = csvRecords.get(i);
                //Create a new account object and fill his data
                Account account = new Account(record.get(NAME), record.get(EMAIL), record.get(PASSWORD));
                accounts.add(account);
            }

            // delete decryptedDB
            decryptedFile.delete();
        } 
        catch(Exception e)
        {
            System.err.println("Error in CsvFileReader!");
        }
        finally
        {
            try
            {
                fileReader.close();
                csvFileParser.close();
            }
            catch(IOException e)
            {
                System.err.println("Error while closing fileReader/csvFileParser!");
            }
        }
        return accounts;
    }
}