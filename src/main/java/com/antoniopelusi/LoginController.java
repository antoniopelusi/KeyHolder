package com.antoniopelusi;

import java.io.File;
import java.io.IOException;

import com.antoniopelusi.popups.EnterNewPassword;
import com.antoniopelusi.popups.EnterPassword;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * @author Antonio Pelusi
 *
 */
public class LoginController
{
    private Scene scene;

    private String encryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + "keyholder";
    private String decryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + ".keyholder";
    
    @FXML
    private CheckMenuItem showPasswords;

    @FXML
    private Label isDatabaseCreated;

    @FXML
    private Button save;

    @FXML
    private Button focus;

    EventHandler<KeyEvent> ctrlq = new EventHandler<KeyEvent>()
    {
        final KeyCombination keyComb = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
        public void handle(KeyEvent ke)
        {
            if (keyComb.match(ke))
            {
                try
                {
                    System.out.println("Closing the app");
                    App.stage.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                ke.consume();
            }
        }
    };

    EventHandler<KeyEvent> ctrlc = new EventHandler<KeyEvent>()
    {
        final KeyCombination keyComb = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        public void handle(KeyEvent ke)
        {
            if (keyComb.match(ke))
            {
                try
                {
                    createDatabase();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                ke.consume();
            }
        }
    };

    EventHandler<KeyEvent> ctrll = new EventHandler<KeyEvent>()
    {
        final KeyCombination keyComb = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
        public void handle(KeyEvent ke)
        {
            if (keyComb.match(ke))
            {
                try
                {
                    loadDatabase();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                ke.consume();
            }
        }
    };

    @FXML void initialize()
    {
        //CTRL+Q shortcut to close the app
        App.stage.addEventFilter(KeyEvent.KEY_PRESSED, ctrlq);

        //CTRL+C shortcut to create a new database
        App.stage.addEventFilter(KeyEvent.KEY_PRESSED, ctrlc);

        //CTRL+L shortcut to load the database
        App.stage.addEventFilter(KeyEvent.KEY_PRESSED, ctrll);
    }

    @FXML
    private void createDatabase() throws IOException
    {
        File f = new File(encryptedDB);
        if(f.exists() && !f.isDirectory())
        {
            //database found
            System.out.println("Database found!");
    
            Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("Database found");
            alert.setContentText("An existing database have been found.\n\nYou can't create another one.");
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("com/antoniopelusi/style.css").toExternalForm());
    
            focus.requestFocus();
            alert.showAndWait();

        }
        else
        {
            //create new empty database
            EnterNewPassword popup = new EnterNewPassword();
            popup.display();
    
            if(EnterNewPassword.isPasswordIn)
            {
                CsvFileWriter.writeCsvFile(decryptedDB, null);
    
                isDatabaseCreated.setText("Database created in\n" + encryptedDB);
            }
        }
    }

    @FXML
    private void loadDatabase() throws IOException
    {
        try
        {
            File f = new File(encryptedDB);
            if(f.exists() && !f.isDirectory())
            { 
                EnterPassword popup = new EnterPassword();
                popup.display();
    
                if(EnterPassword.isPasswordIn)
                {
                    scene = new Scene(App.loadFXML("database"), 640, 480);
                    
                    App.stage.setScene(scene);
                    App.stage.centerOnScreen();
                
                    //remove shortcuts (not ctrlh and ctrlq)
                    App.stage.removeEventFilter(KeyEvent.KEY_PRESSED, ctrlc);
                    App.stage.removeEventFilter(KeyEvent.KEY_PRESSED, ctrll);
                }
            }
            else
            {
                //database not found
                System.out.println("No database found!");
    
                Alert alert = new Alert(AlertType.NONE);
                alert.setTitle("No database found!");
                alert.setContentText("No existing database have been found.\n\nCreate one first!");
                alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                alert.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("com/antoniopelusi/style.css").toExternalForm());
    
                focus.requestFocus();
                alert.showAndWait();
            }
        }
        catch(Exception e)
        {
            isDatabaseCreated.setText("The password is incorrect\nTry again");
        }
    }
}