package com.jbc.javaproject.AdminstratorClass;

import com.jbc.javaproject.LogicCode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static com.jbc.javaproject.LogicCode.updateEmployee;

public class Edit2pageController implements Initializable {


    @FXML
    private BorderPane mainPane;
    @FXML
    private JFXButton back;
    @FXML
    private JFXTextField editEmployeeCode;

    @FXML
    private JFXTextField editEmployeeLastName;

    @FXML
    private JFXTextField editEmployeeSupervisor;

    @FXML
    private JFXTextField editEmployeeFirstName;

    @FXML
    private JFXTextField editEmployeePhone;

    @FXML
    private JFXButton editEmployeeBtn;

    @FXML
    private JFXPasswordField editEmployeePassword;

    @FXML
    private ImageView editEmployeePic;

    @FXML
    private JFXTextField editEmployeeAdress;

    @FXML
    private JFXTextField editEmployeePicPath;

    @FXML
    private JFXComboBox<String> editEmployeeType = new JFXComboBox<>();

    @FXML
    private DatePicker editEmployeeDate;

    @FXML
    private JFXTextField editEmployeeTitle;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editEmployeeType.setItems(FXCollections.observableArrayList("Administrator","Employee"));

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
//        editEmployeeType.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                editEmployeeType.setItems(FXCollections.observableArrayList("H.R.Manager","Employee"));
//            }
//        });
        editEmployeeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    LogicCode.updateEmployee(editEmployeeFirstName.getText(),editEmployeeLastName.getText(),editEmployeeCode.getText(),
                            editEmployeePassword.getText(),editEmployeeType.getValue(),editEmployeePicPath.getText(),editEmployeeDate.getValue(),
                            editEmployeeTitle.getText(),editEmployeeSupervisor.getText(),editEmployeePhone.getText(),editEmployeeAdress.getText());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Employee Updated");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Employee Information upDated");
                alert.show();
                restFields();
            }
        });
//        editEmployeeType.getValue(),

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPage("Administrator/editEmployee");
                mainPane.setCenter(view);
            }
        });

    }
    public void setImage(Image imageData){
        editEmployeePic.setImage(imageData);
    }
    public void setCode (String code){
        editEmployeeCode.setText(code);
    }
    public void setFirstName(String firstName){
        editEmployeeFirstName.setText(firstName);
    }
    public void setLastName(String lastName){
        editEmployeeLastName.setText(lastName);
    }
    public void setHiringDate(LocalDate hiringDate){
        editEmployeeDate.setValue(hiringDate);
    }
    public void setType(String type) { editEmployeeType.setValue(type); }
    public void setJobTitle(String jobTitle){
        editEmployeeTitle.setText(jobTitle);
    }
    public void setSupervisorCode(String supervisorCode){
        editEmployeeSupervisor.setText(supervisorCode);
    }
    public void  setPassword(String password){
        editEmployeePassword.setText(password);
    }
    public void setPhoneNumber(String phoneNumber){
        editEmployeePhone.setText(phoneNumber);
    }
    public void setAddress(String address){
        editEmployeeAdress.setText(address);
    }

    public void restFields(){
        editEmployeePic.setImage(null);
        editEmployeeCode.setText("");
        editEmployeeFirstName.setText("");
        editEmployeeLastName.setText("");
        editEmployeeDate.setValue(null);
        editEmployeeType.setValue(null);
        editEmployeeTitle.setText("");
        editEmployeeSupervisor.setText("");
        editEmployeePassword.setText("");
        editEmployeePhone.setText("");
        editEmployeeAdress.setText("");
    }

}
