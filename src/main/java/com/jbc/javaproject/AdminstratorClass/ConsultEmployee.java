package com.jbc.javaproject.AdminstratorClass;

import com.jbc.javaproject.LogicCode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static com.jbc.javaproject.LogicCode.employeeData;
import static com.jbc.javaproject.LogicCode.employeeDataByType;

public class ConsultEmployee implements Initializable {
    @FXML
    private BorderPane mainPane;
    @FXML
    private TableColumn<Employee,String> password;

    @FXML
    private TableColumn<Employee,String> address;

    @FXML
    private TableColumn<Employee,String> phoneNumber;

    @FXML
    private TableColumn<Employee, Date> date;
    @FXML
    private TableColumn<Employee, ImageView> image;

    @FXML
    private TableColumn<Employee,String> jobTitle;

    @FXML
    private TableColumn<Employee,String> superVisorCode;
    @FXML
    private TableView<Employee> employeTable;
    @FXML
    private TableColumn<Employee,String> colCode;

    @FXML
    private TableColumn<Employee,String> colType;

    @FXML
    private TableColumn<Employee,String> firstName;

    @FXML
    private TableColumn<Employee,String> lastName;
    @FXML
    private JFXTextField textFieldSearchCode;
    @FXML
    private Button consultbtnSearch;
    @FXML
    private Button uploadBtn;
    @FXML
    private JFXComboBox<String> consultTypeFilter= new JFXComboBox<>();
    ObservableList<Employee> listE;
    Parent root = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Option titles HardCoded
        consultTypeFilter.setItems(FXCollections.observableArrayList("Administrator","Employee"));

        uploadBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                uploadEmployeeData();
            }
        });

        // get employee by code
        consultbtnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                uploadEmployeeData(textFieldSearchCode.getText());
                textFieldSearchCode.setText("");
            }
        });

        // get employee by type
        consultTypeFilter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String value = consultTypeFilter.getValue();
                uploadEmployeeDataType(value);
            }
        });

        employeTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String code = employeTable.getSelectionModel().getSelectedItem().getCode();
                listE = employeeData(code);
                for (Employee emp:listE
                ) {
                    if (code.equals(emp.getCode())){
                        FXMLLoader loader = new FXMLLoader(LogicCode.class.getResource("Administrator/consult2page.fxml"));
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Consult2PageController consult2pageController = loader.getController();
                        consult2pageController.setFirstName(emp.getFirstName());
                        consult2pageController.setLastName(emp.getLastName());
//                        consult2pageController.setType(emp.getType().trim());
//                        consult2pageController.setJobTitle(emp.getJobTitle());
//                        consult2pageController.setSupervisorCode(emp.getSupervisorCode());
//                        consult2pageController.setPassword(emp.getPassword());
                        consult2pageController.setPhoneNumber(emp.getPhoneNumber());
                        consult2pageController.setAddress(emp.getAddress());
//                        consult2pageController.setCode(emp.getCode());
                        consult2pageController.setImage(emp.getImageView().getImage());
//                        consult2pageController.setHiringDate(emp.getHiringDate().toLocalDate());
                        mainPane.setCenter(root);

                    }
                }

            }
        });

    }

    // get data from database by type of employees
    public  void uploadEmployeeDataType(String type){
        setCellsValuesEmployees();
        listE = employeeDataByType(type);
        employeTable.setItems(listE);
    }

    // get data from database by code of employee
    public  void uploadEmployeeData(String code){
        setCellsValuesEmployees();
        listE = employeeData(code);
        employeTable.setItems(listE);
    }
    // get data from database and fill up the interface Employees with it
    public  void uploadEmployeeData(){
        setCellsValuesEmployees();
        listE = employeeData();
        employeTable.setItems(listE);
    }

    // we set the column types from the class Employee
    public void setCellsValuesEmployees(){
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        jobTitle.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        superVisorCode.setCellValueFactory(new PropertyValueFactory<>("supervisorCode"));
        image.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        date.setCellValueFactory(new PropertyValueFactory<>("hiringDate"));

    }
}
