package com.jbc.javaproject.AdminstratorClass;

import com.jbc.javaproject.LogicCode;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    @FXML
    private AnchorPane signUpParent;
    @FXML
    private JFXTextField addEmployeeTitle;
    @FXML
    private DatePicker addEmployeeDate;
    @FXML
    private PasswordField addEmployeePassword;
    @FXML
    private JFXTextField addEmployeeCode;
    @FXML
    private JFXComboBox<String> addEmployeeType;
    @FXML
    private JFXTextField addEmployeeSupervisor;

    @FXML
    private JFXTextField addEmployeeLastName;

    @FXML
    private JFXTextField addEmployeeFirstName;

    @FXML
    private JFXTextField addEmployeePhone;
    @FXML
    private JFXTextField addEmployeeAdress;
    @FXML
    private JFXButton addEmployeeBtn;
    @FXML
    private ImageView addEmployeePic;
    @FXML
    private JFXTextField addEmployeePicPath;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Option titles HardCoded
        addEmployeeType.setItems(FXCollections.observableArrayList("Administrator","Employee"));
        // addEmployee Picture
        addEmployeePic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(addEmployeePicPath.getScene().getWindow());
                if (file != null) {
                    // Read the contents of the file into a stream
                    try (InputStream input = new FileInputStream(file)) {
                        // Create an image from the stream
                        Image image = new Image(input);
                        addEmployeePicPath.setText(file.toString());
                        // Display the image in the ImageView
                        addEmployeePic.setImage(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // add Employee "President","Regular Employee","Manager
        addEmployeeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!addEmployeeFirstName.getText().isEmpty() && !addEmployeeLastName.getText().isEmpty() && !addEmployeePassword.getText().isEmpty() &&
                !addEmployeeCode.getText().isEmpty() && !addEmployeeAdress.getText().isEmpty()){
                        LogicCode.signUpEmployee(actionEvent,addEmployeeFirstName.getText(),addEmployeeLastName.getText(),
                                addEmployeeCode.getText(),addEmployeePassword.getText(),addEmployeeType.getValue(),addEmployeePicPath.getText(),addEmployeeDate.getValue(),
                                addEmployeeTitle.getText(),addEmployeeSupervisor.getText(),addEmployeePhone.getText(),addEmployeeAdress.getText(),false);


                }else {
                    System.out.println("please fill in all Information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill up all information");
                    alert.show();
                }
                restFields();
            }
        });
    }
    public void restFields(){
        addEmployeeFirstName.setText("");
        addEmployeeLastName.setText("");
        addEmployeeDate.setValue(null);
        addEmployeeType.setValue(null);
        addEmployeeTitle.setText("");
        addEmployeeSupervisor.setText("");
        addEmployeeCode.setText("");
        addEmployeePassword.setText("");
        addEmployeePhone.setText("");
        addEmployeeAdress.setText("");
        addEmployeePicPath.setText("");
        addEmployeePic.setImage(null);
    }
}
