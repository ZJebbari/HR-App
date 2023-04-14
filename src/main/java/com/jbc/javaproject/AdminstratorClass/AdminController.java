package com.jbc.javaproject.AdminstratorClass;

import com.jbc.javaproject.LogicCode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminController implements Initializable {
    @FXML
    private ImageView ImageAdmin;
    //here
    @FXML
    private AnchorPane adminMainAnchor;
    @FXML
    private BorderPane mainPane;
    @FXML
    private FontAwesomeIcon btnClose;
    @FXML
    private ImageView minimizeBtn;
    @FXML
    private JFXButton addEmployee;

    @FXML
    private JFXButton editEmployee;

    @FXML
    private JFXButton deleteEmployee;

    @FXML
    private JFXButton consultEmployee;
    @FXML
    private JFXButton logout;
    @FXML
    private Label labelWelcome = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // when logout button pressed it get you Back to the main menu which is the login menu
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LogicCode.changeScene(actionEvent,"login.fxml",null,null);
            }
        });
        addEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPage("Administrator/addEmployee");
                mainPane.setCenter(view);
            }
        });
        editEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPage("Administrator/editEmployee");
                mainPane.setCenter(view);
            }
        });
        deleteEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPage("Administrator/deleteEmployee");
                mainPane.setCenter(view);
            }
        });
        consultEmployee.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FxmlLoader object = new FxmlLoader();
                Pane view = object.getPage("Administrator/consultEmployee");
                mainPane.setCenter(view);
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
            Stage stage = (Stage) adminMainAnchor.getScene().getWindow();
            stage.setIconified(true);
        }
    }
    // add name to welcome
    public void setName(String name){
        labelWelcome.setText("Welcome Back "+name);
    }
    // add image in the welcome page
    public void setImage(byte[] imageData){
        InputStream in = new ByteArrayInputStream(imageData);
        Image image = new Image(in);
        ImageAdmin.setImage(image);
    }
}
