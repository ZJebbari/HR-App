package com.jbc.javaproject.AdminstratorClass;

import com.jbc.javaproject.LogicCode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane loginMainAnchor;
    @FXML
    private FontAwesomeIcon btnClose;
    @FXML
    private ImageView minimizeBtn;
    @FXML
    private JFXRadioButton rb_President_SignUp;

    @FXML
    private JFXRadioButton rb_Manager_SignUp;

    @FXML
    private JFXRadioButton rb_HR_SignUp;

    @FXML
    private JFXRadioButton rb_Employee_SignUp;
    @FXML
    private JFXTextField loginCode;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private JFXTextField typeLogin;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton LoginSignUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // toggle between the 2 titles we have Employee and H.R.Manager
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_HR_SignUp.setToggleGroup(toggleGroup);
        rb_HR_SignUp.setSelected(true);

        // login
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                LogicCode.loginAdmin(actionEvent, loginCode.getText(),loginPassword.getText(),toggleName);
            }
        });
        // signUp
        LoginSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LogicCode.changeScene(actionEvent,"Administrator/signUpAdmin.fxml",null,null);
            }
        });
    }
    // Customize close Menu
    @FXML
    void handleClose(MouseEvent event) {
        if (event.getSource() == btnClose){
            System.exit(0);
        }
    }
    // Customize Minimize Menu
    @FXML
    void handleMinimize(MouseEvent event) {
        if (event.getSource() == minimizeBtn ){
            Stage stage = (Stage) loginMainAnchor.getScene().getWindow();
            stage.setIconified(true);
        }
    }
}