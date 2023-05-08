package com.antoniopelusi.popups;

import java.net.MalformedURLException;

import com.antoniopelusi.Account;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Antonio Pelusi
 *
 */
public class EditAccount
{
    public static boolean editRow = false;

    public static Account account;

    public EditAccount(Account account)
    {
        EditAccount.account = account;
    }

    public void display() throws MalformedURLException
    {
        Stage popupwindow=new Stage();
              
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Edit account");
        
        Label nameLabel = new Label("Edit the name:");
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        TextField nameField = new TextField(account.getName());
        nameField.setMaxWidth(250);
        Label emailLabel = new Label("Edit the email:");
        emailLabel.setTextAlignment(TextAlignment.CENTER);
        TextField emailField = new TextField(account.getEmail());
        emailField.setMaxWidth(250);
        Label passwordLabel = new Label("Insert the password:");
        passwordLabel.setTextAlignment(TextAlignment.CENTER);
        TextField passwordField = new TextField(account.getPassword());
        passwordField.setMaxWidth(250);

        Region region = new Region();

        Button button = new Button("Edit account");

        button.setOnAction(e ->
        {   
            if(!nameField.getText().isBlank() && !emailField.getText().isBlank() && !passwordField.getText().isBlank())
            {
                account = new Account(nameField.getText(), emailField.getText(), passwordField.getText());    
                editRow = true;
                popupwindow.close();
            }
        });

        button.setDefaultButton(true);
             
        VBox layout= new VBox(7);
             
        layout.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField, region, button);
              
        layout.setAlignment(Pos.CENTER);
              
        Scene scene = new Scene(layout, 300, 250);
        
        scene.getStylesheets().add(getClass().getClassLoader().getResource("com/antoniopelusi/style.css").toExternalForm());
              
        popupwindow.setScene(scene);
              
        popupwindow.showAndWait();    
    }
}