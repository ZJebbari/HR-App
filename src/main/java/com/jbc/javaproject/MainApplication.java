package com.jbc.javaproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("signUpAdmin.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}