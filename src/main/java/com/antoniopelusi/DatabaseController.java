package com.antoniopelusi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import com.antoniopelusi.popups.AddAccount;
import com.antoniopelusi.popups.EditAccount;
import com.antoniopelusi.popups.RemoveAccount;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * @author Antonio Pelusi
 *
 */
public class DatabaseController
{    
    private Boolean showPw = false;
    
    @FXML
    private ProgressBar progressBar;

    @FXML
    private TableView<Account> table;

    @FXML
    private TableColumn<Account, String> nameColumn;

    @FXML
    private TableColumn<Account, String> emailColumn;

    @FXML
    private TableColumn<Account, String> passwordColumn;

    @FXML
    private Label accountCounter;

    @FXML
    private CheckBox showPasswordCheckbox;

    private String encryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + "keyholder";
    private String decryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + ".keyholder";
    
    //CTRL+A to add a new account
    EventHandler<KeyEvent> ctrla = new EventHandler<KeyEvent>()
    {
        final KeyCombination keyComb = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
        public void handle(KeyEvent ke)
        {
            if (keyComb.match(ke))
            {
                ke.consume();
                try
                {
                    addAccount();
                }
                catch(Exception e)
                {
                    ke.consume();
                    e.printStackTrace();
                }
            }
        }
    };

    //CTRL+E to edit the selected account
    EventHandler<KeyEvent> ctrle = new EventHandler<KeyEvent>()
    {
        final KeyCombination keyComb = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        public void handle(KeyEvent ke)
        {
            if (keyComb.match(ke))
            {
                try
                {
                    editAccount();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                ke.consume();
            }
        }
    };

    //CTRL+R to remove the selected account
    EventHandler<KeyEvent> ctrlr = new EventHandler<KeyEvent>()
    {
        final KeyCombination keyComb = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        public void handle(KeyEvent ke)
        {
            if (keyComb.match(ke))
            {
                try
                {
                    removeAccount();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                ke.consume();
            }
        }
    };  

    //CTRL+P to hide/show passwords
    EventHandler<KeyEvent> ctrlp = new EventHandler<KeyEvent>()
    {
        final KeyCombination keyComb = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
        public void handle(KeyEvent ke)
        {
            if (keyComb.match(ke))
            {
                try
                {
                    showPasswords();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                ke.consume();
            }
        }
    };
    
    //CTRL+H to open the shortcut help
    EventHandler<KeyEvent> ctrlh = new EventHandler<KeyEvent>()
    {
        final KeyCombination keyComb = new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN);
        public void handle(KeyEvent ke)
        {
            if (keyComb.match(ke))
            {
                try
                {
                    shortcuts();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                ke.consume();
            }
        }
    };
        
    @FXML
    private void initialize()
    {   
        //load database
        ObservableList<Account> accounts = CsvFileReader.readCsvFile(encryptedDB);
    
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));        
    
        nameColumn.setReorderable(false);
        emailColumn.setReorderable(false);
        passwordColumn.setReorderable(false);

        passwordColumn.setVisible(false);

        progressBar.setProgress(1);

        table.setPlaceholder(new Label("No accounts here!"));
        table.setItems(accounts);
        nameColumn.setSortable(false);

        accountCounter.setText("Accounts saved: " + accounts.size());
    
        System.out.println("Database loaded");

        App.stage.addEventFilter(KeyEvent.KEY_PRESSED, ctrla);
        App.stage.addEventFilter(KeyEvent.KEY_PRESSED, ctrle);
        App.stage.addEventFilter(KeyEvent.KEY_PRESSED, ctrlr);
        App.stage.addEventFilter(KeyEvent.KEY_PRESSED, ctrlp);
        App.stage.addEventFilter(KeyEvent.KEY_PRESSED, ctrlh);
    }

    @FXML
    private void showPasswords() throws IOException
    {
        if(showPw)
        {
            //hide password
            passwordColumn.setVisible(false);
            showPasswordCheckbox.setSelected(false);
            showPw = false;
            System.out.println("passwords status set to hidden");
        }
        else
        {
            //show password
            passwordColumn.setVisible(true);
            showPasswordCheckbox.setSelected(true);
            showPw = true;
            System.out.println("Passwords status set to visible");
        }
    }

    private void save()
    {
        nameColumn.setSortable(true);
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(nameColumn);
        table.sort();
        nameColumn.setSortable(false);
        ObservableList<Account> accounts = table.getItems();
        
        CsvFileWriter.writeCsvFile(decryptedDB, accounts);
        for(int i = 1; i<=accounts.size(); i++)
        {
            progressBar.setProgress(i);
        }

        accountCounter.setText("Accounts saved: " + accounts.size());
    }

    @FXML
    private void addAccount() throws MalformedURLException, URISyntaxException
    {
        AddAccount popup = new AddAccount();
        popup.display();

        if (AddAccount.account != null)
        {
            ObservableList<Account> accounts = table.getItems();
            accounts.add(AddAccount.account);

            System.out.println("Added account:\n" + AddAccount.account);

            AddAccount.account = null;

            save();
        }
    }

    @FXML
    private void editAccount() throws MalformedURLException
    {
        int row = table.getSelectionModel().getSelectedIndex();

        if(row != -1)
        {
            Account account = table.getSelectionModel().getSelectedItem();

            EditAccount popup = new EditAccount(account);
            popup.display();
            
            if(EditAccount.editRow)
            {
                table.getItems().remove(row);

                ObservableList<Account> accounts = table.getItems();
                accounts.add(EditAccount.account);

                EditAccount.editRow = false;

                System.out.println("Edit account in row " + (row+1));

                save();
            }
        }
    }

    @FXML
    private void removeAccount() throws MalformedURLException
    {
        int row = table.getSelectionModel().getSelectedIndex();

        if(row != -1)
        {
            RemoveAccount popup = new RemoveAccount();
            popup.display();

            if(RemoveAccount.removeRow)
            {
                table.getItems().remove(row);

                RemoveAccount.removeRow = false;

                System.out.println("Removed account in row " + (row+1));
                
                save();
            }
        }
    }

    @FXML
    private void about() throws MalformedURLException
    {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("About");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Label text = new Label("KeyHolder is developed by Antonio Pelusi.\nFind more here:\n");
        Hyperlink link = new Hyperlink("http://www.antoniopelusi.com");

        VBox vb = new VBox(text, link);

        alert.getDialogPane().setContent(vb);
        alert.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("com/antoniopelusi/style.css").toExternalForm());
        alert.show();
    }

    @FXML
    private void locateDatabase() throws MalformedURLException
    {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("Locate Database");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        
        alert.setContentText("The location of the database is:\n" + encryptedDB);

        alert.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("com/antoniopelusi/style.css").toExternalForm());
        alert.show();
    }

    @FXML void shortcuts() throws MalformedURLException
    {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("Shortcuts");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        
        alert.setContentText(
            "Shortcuts available in the login page:\n"
            + "    CTRL + C: Create a new database\n"
            + "    CTRL + L: Load database\n"
            + "    CTRL + Q: Quit\n\n"
            + "Shortcut available in the database page:\n"
            + "    CTRL + A: Add a new account\n"
            + "    CTRL + E: Edit the selected account\n"
            + "    CTRL + R: Remove the selected account\n"
            + "    CTRL + P: Hide/show passwords\n"
            + "    CTRL + H: Show shortcuts help\n"
            + "    CTRL + Q: Quit\n"
            );

        alert.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("com/antoniopelusi/style.css").toExternalForm());
        alert.show();
    }
}
