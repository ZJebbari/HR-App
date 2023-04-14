package com.jbc.javaproject.AdminstratorClass;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static com.jbc.javaproject.LogicCode.*;

public class DeleteEmployeeController implements Initializable {

    @FXML
    private JFXTextField textFieldSearchCode;

    @FXML
    private JFXComboBox<String> typeFilter;
    @FXML
    private TableView<Employee> employeTable;

    @FXML
    private TableColumn<Employee,String> firstName;

    @FXML
    private TableColumn<Employee,String> lastName;

    @FXML
    private TableColumn<Employee,String> colCode;

    @FXML
    private TableColumn<Employee,String> colType;

    @FXML
    private TableColumn<Employee,String> superVisorCode;

    @FXML
    private TableColumn<Employee,String> select;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton deletebtn;
    @FXML
    private JFXButton upload;
    @FXML
    private JFXCheckBox selectAll;
    ObservableList<Employee> listE;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeFilter.setItems(FXCollections.observableArrayList("Administrator","Employee"));
        // upload employees data after pressing upload button
        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                uploadEmployeeData();
            }
        });
        // delete employee from dataBase after selecting and pressing delete btn
        deletebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Employee emp:listE
                ) {
                    if (emp.getCheckBox().isSelected()){
                        deleteEmployee(emp.getCode());
                    }
                }
                uploadEmployeeData();

            }
        });

        // select all project
        selectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(selectAll.isSelected()){
                    for (Employee emp:listE
                    ) {
                        emp.getCheckBox().setSelected(true);
                    }
                }else if (!selectAll.isSelected()) {
                    for (Employee emp:listE
                    ) {
                        emp.getCheckBox().setSelected(false);
                    }
                }
            }

        });

        // get employee by code
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                uploadEmployeeData(textFieldSearchCode.getText());
                textFieldSearchCode.setText("");
            }
        });

        // get employee by type
        typeFilter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String value = typeFilter.getValue();
                uploadEmployeeDataType(value);
            }

        });

    }
    public  void uploadEmployeeDataType(String type){
        setCellsValuesEmployees();
        listE = employeeDataByTypeDelete(type);
        employeTable.setItems(listE);
    }

    // get data from database by code of employee
    public  void uploadEmployeeData(String code){
        setCellsValuesEmployees();
        listE = employeeDataDelete(code);
        employeTable.setItems(listE);
    }
    public  void uploadEmployeeData(){
        setCellsValuesEmployees();
        listE = employeeDataForDelete();
        employeTable.setItems(listE);
    }
    public void setCellsValuesEmployees(){
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        superVisorCode.setCellValueFactory(new PropertyValueFactory<>("supervisorCode"));
        select.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
    }
}
