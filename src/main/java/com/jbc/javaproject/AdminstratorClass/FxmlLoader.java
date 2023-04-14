package com.jbc.javaproject.AdminstratorClass;

import com.jbc.javaproject.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmlLoader {
    private Pane view;

    public Pane getPage(String fileName){
        try{
            URL fileUrl = MainApplication.class.getResource(fileName+".fxml");
            if (fileUrl == null){
                throw new java.io.FileNotFoundException("FXML file not found");
            }
            view = new FXMLLoader().load(fileUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
