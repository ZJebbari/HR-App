package com.jbc.javaproject.AdminstratorClass;

import com.jbc.javaproject.LogicCode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Consult2PageController implements Initializable {
    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView editEmployeePic;

    @FXML
    private JFXButton back;

    @FXML
    private Label employeeFirstName;

    @FXML
    private Label employeeLastName;

    @FXML
    private Label employeeAdress;

    @FXML
    private Label employeePhone;

    @FXML
    private JFXTextField editEmployeePicPath;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        editEmployeePic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(editEmployeePicPath.getScene().getWindow());
                if (file != null) {
                    // Read the contents of the file into a stream
                    try (InputStream input = new FileInputStream(file)) {
                        // Create an image from the stream
                        Image image = new Image(input);
                        editEmployeePicPath.setText(file.toString());
                        // Display the image in the ImageView
                        editEmployeePic.setImage(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPage("Administrator/consultEmployee");
                mainPane.setCenter(view);
            }
        });

    }
    public void setImage(Image imageData){
        editEmployeePic.setImage(imageData);
    }
//    public void setCode (String code){
//        editEmployeeCode.setText(code);
//    }
    public void setFirstName(String firstName){
        employeeFirstName.setText(firstName);
    }
    public void setLastName(String lastName){
        employeeLastName.setText(lastName);
    }
//    public void setHiringDate(LocalDate hiringDate){
//        editEmployeeDate.setValue(hiringDate);
//    }
//    public void setType(String type) { editEmployeeType.setValue(type); }
//    public void setJobTitle(String jobTitle){
//        editEmployeeTitle.setText(jobTitle);
//    }
//    public void setSupervisorCode(String supervisorCode){
//        editEmployeeSupervisor.setText(supervisorCode);
//    }
//    public void  setPassword(String password){
//        editEmployeePassword.setText(password);
//    }
    public void setPhoneNumber(String phoneNumber){
        employeePhone.setText(phoneNumber);
    }
    public void setAddress(String address){
        employeeAdress.setText(address);
    }
}
