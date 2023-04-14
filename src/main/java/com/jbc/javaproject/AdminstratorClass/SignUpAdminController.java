package com.jbc.javaproject.AdminstratorClass;

import com.jbc.javaproject.LogicCode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpAdminController implements Initializable {


    @FXML
    private JFXTextField jobTitleSignUp;
    @FXML
    private JFXTextField superVisorCodeSignUp;

    @FXML
    private JFXTextField phoneNumberSignUp;

    @FXML
    private PasswordField secondPasswordSignUp;

    @FXML
    private JFXTextField addressSignUp;
    @FXML
    private DatePicker dateSignUp;
    @FXML
    private ImageView uploadButtonImage;
    @FXML
    private JFXTextField imageFileSignUp;

    //here
    @FXML
    private JFXComboBox<String> titlesSignUp;
    @FXML
    private JFXTextField codeSignUp;
    @FXML
    private PasswordField firstPasswordSignUp;
    @FXML
    private JFXTextField firstNameSignUp;
    @FXML
    private JFXTextField lastNameSignUp;
    @FXML
    private JFXButton signUpBtnSignUP;
    @FXML
    private JFXButton backToLoginBtnSignUp;


        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            // setting up titles
            titlesSignUp.setItems(FXCollections.observableArrayList("Administrator"));
            // get the image file path and show in the image view
            uploadButtonImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    FileChooser fileChooser = new FileChooser();
                    File file = fileChooser.showOpenDialog(imageFileSignUp.getScene().getWindow());
                    if (file != null) {
                        // Read the contents of the file into a stream
                        try (InputStream input = new FileInputStream(file)) {
                            // Create an image from the stream
                            Image image = new Image(input);
                            imageFileSignUp.setText(file.toString());
                            // Display the image in the ImageView
                            uploadButtonImage.setImage(image);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //after filling up the information if pressed on the SignUp button it will confirm the registration
            signUpBtnSignUP.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (!codeSignUp.getText().trim().isEmpty() && !firstPasswordSignUp.getText().trim().isEmpty() &&
                    !firstNameSignUp.getText().trim().isEmpty() && !lastNameSignUp.getText().trim().isEmpty()){
                        LogicCode.signUpEmployee(actionEvent,firstNameSignUp.getText(),lastNameSignUp.getText(),codeSignUp.getText(),
                                firstPasswordSignUp.getText(),titlesSignUp.getValue(),imageFileSignUp.getText(),dateSignUp.getValue(),jobTitleSignUp.getText(),
                                superVisorCodeSignUp.getText(),phoneNumberSignUp.getText(),addressSignUp.getText(),true);
                    }else {
                        System.out.println("please fill in all Information");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please fill up all information");
                        alert.show();
                    }
                }
            });
            // get you back the main menu or login menu
            backToLoginBtnSignUp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    LogicCode.changeScene(actionEvent,"login.fxml",null,null);
                }
            });

    }
}




















