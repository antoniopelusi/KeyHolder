package com.antoniopelusi;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 * @author Antonio Pelusi
 */
public class App extends Application
{
    public static Scene scene;
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException
    {
        stage.setTitle("KeyHolder");
        App.stage = stage;
        scene = new Scene(loadFXML("login"), 400, 200);
        
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException
    {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args)
    {
        launch();
    }
}