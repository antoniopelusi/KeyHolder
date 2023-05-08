package com.antoniopelusi.popups;

import java.net.MalformedURLException;

import com.antoniopelusi.Encryption;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Antonio Pelusi
 *
 */
public class EnterNewPassword
{
    public static boolean isPasswordIn = false;

    public boolean display() throws MalformedURLException
    {
        Stage popupwindow=new Stage();
              
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Enter new password");
        
        Label label = new Label("Enter the master password\nfor database encryption:");
        label.setTextAlignment(TextAlignment.CENTER);
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(250);

        Region region = new Region();

        Button button = new Button("Confirm");

        button.setOnAction(e ->
        {   
            if(!passwordField.getText().isBlank())
            {
                Encryption.initialKey = passwordField.getText();
                System.out.println("Master password: " + passwordField.getText());

                isPasswordIn = true;
                popupwindow.close();  
            }
        });

        button.setDefaultButton(true);
             
        VBox layout= new VBox(7);
             
        layout.getChildren().addAll(label, passwordField, region, button);
              
        layout.setAlignment(Pos.CENTER);
              
        Scene scene = new Scene(layout, 300, 150);
        
        scene.getStylesheets().add(getClass().getClassLoader().getResource("com/antoniopelusi/style.css").toExternalForm());
              
        popupwindow.setScene(scene);
              
        popupwindow.showAndWait();

        return isPasswordIn; 
    }
}