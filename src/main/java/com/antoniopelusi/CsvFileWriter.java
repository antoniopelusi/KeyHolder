package com.antoniopelusi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat.Builder;
 
/**
 * @author Antonio Pelusi
 *
 */
public class CsvFileWriter
{
    //Delimiter used in CSV file
    private static final String NEW_LINE_SEPARATOR = "\n";
     
    //CSV file header
    private static final Object [] FILE_HEADER = {"name", "email", "password"};

    public static void writeCsvFile(String decryptedDB, List<Account> accounts)
    {
        FileWriter fileWriter = null;
        
        CSVPrinter csvFilePrinter = null;
            
        //Create the CSVFormat object with "\n" as a record delimiter
        //deprecated: CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        Builder builder = Builder.create().setRecordSeparator(NEW_LINE_SEPARATOR);
        CSVFormat csvFileFormat = builder.build();

        try
        {
            //initialize FileWriter object
            fileWriter = new FileWriter(decryptedDB);
                
            //initialize CSVPrinter object 
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
                
            //Create CSV file header
            csvFilePrinter.printRecord(FILE_HEADER);
            
            //Write a new account object list to the CSV file
            if(accounts!=null)
            {
                for(Account account : accounts)
                {
                    List<String> accountDataRecord = new ArrayList<String>();
                    accountDataRecord.add(account.getName());
                    accountDataRecord.add(account.getEmail());
                    accountDataRecord.add(account.getPassword());
    
                    csvFilePrinter.printRecord(accountDataRecord);
                }
            }
        }
        catch(Exception e)
        {
            System.err.println("Error in CsvFileWriter!");
        }
        finally
        {
            try
            {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
                
                Encryption.encrypt();

                System.out.println("CSV file was created successfully!");
            }
            catch(IOException e)
            {
                System.err.println("Error while flushing/closing fileWriter/csvPrinter!");
            }
        }
    }    
}
