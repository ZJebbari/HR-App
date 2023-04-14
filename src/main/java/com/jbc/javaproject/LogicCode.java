package com.jbc.javaproject;

import com.jbc.javaproject.AdminstratorClass.AdminController;
import com.jbc.javaproject.AdminstratorClass.Edit2pageController;
import com.jbc.javaproject.AdminstratorClass.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.jbc.javaproject.AdminstratorClass.BDConnection.connectDB;


public class LogicCode {
    // change scene for the entire application
    public static void changeScene(ActionEvent event, String fxmlFile, String code,byte[] imageData) {
        Parent root = null;
        if (code != null) {
            try {
                FXMLLoader loader = new FXMLLoader(LogicCode.class.getResource(fxmlFile));
                root = loader.load();
                AdminController adminController = loader.getController();
                adminController.setName(code);
                adminController.setImage(imageData);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(LogicCode.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

    // sign up for the admin to create account
    public static void signUpEmployee(ActionEvent event, String first_Name, String last_Name, String code, String password,
                                      String type, String filePath, LocalDate hiring_date, String job_title, String supervisor_code,
                                      String phone_number, String address,boolean adminPage) {


        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        Connection c = connectDB();

        try {
           psCheckUserExists = c.prepareStatement("select * from employees where code =?");
            psCheckUserExists.setString(1, code);
            resultSet = psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst()) {
                System.out.println("Admin Already Exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You Can't use this UserName");
                alert.show();
            } else {
                psInsert = c.prepareStatement("insert into employees values (?,?,?,?,?,?,?,?,?,?,?)");
                InputStream imageFile = new FileInputStream(filePath);
                psInsert.setString(1, first_Name);
                psInsert.setString(2, last_Name);
                psInsert.setString(3, code);
                psInsert.setString(4, password);
                psInsert.setString(5,type);
                psInsert.setBinaryStream(6,imageFile);
                psInsert.setDate(7,Date.valueOf(hiring_date));
                psInsert.setString(8,job_title);
                psInsert.setString(9,supervisor_code);
                psInsert.setString(10,phone_number);
                psInsert.setString(11,address);

                psInsert.executeUpdate();
                byte[] byteArray = null;
                try (FileInputStream inputStream = new FileInputStream(filePath)) {
                    byteArray = new byte[(int) new File(filePath).length()];
                    inputStream.read(byteArray);
                } catch (IOException e) {
                    // Handle exception
                    e.printStackTrace();
                }
                if (adminPage){
                    changeScene(event, "Administrator/admin.fxml", first_Name+" "+last_Name,byteArray);
                }
                else if (!adminPage){
                    System.out.println("Employee got added");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Your request was Successful");
                    alert.show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // login for the entire application
    public static void loginAdmin(ActionEvent event,String code,String password,String type){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection c = connectDB();
        try {
            preparedStatement = c.prepareStatement("select code,password,first_name,last_name,typ,image from employees where code =? and typ =?");
            preparedStatement.setString(1,code);
            preparedStatement.setString(2,type);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                System.out.println("User Not Found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Wrong Code Or Password");
                alert.show();
            }else {
               while(resultSet.next()){
                   String retrievePassword = resultSet.getString("password");
                   String retrieveFirstName = resultSet.getString("first_name");
                   String retrievelasttName = resultSet.getString("last_name");
                   String retrieveTitle = resultSet.getString("typ");
                   byte[] retrieveImage = resultSet.getBytes("image");
                   if (retrievePassword.equals(password.trim()) && retrieveTitle.equals("Administrator")){
                      changeScene(event,"Administrator/admin.fxml",retrieveFirstName+" "+retrievelasttName,retrieveImage);
                   }else{
                       System.out.println("Password Did not match!");
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setContentText("Wrong Code or Password");
                       alert.show();
                   }
               }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Get information from dataBase and store it in observable List

    public static ObservableList<Employee> employeeData(){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection c = connectDB();
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try{
           preparedStatement = c.prepareStatement("select * from employees");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                System.out.println("No Users found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No Users In The DataBase!");
                alert.show();
            }
            else{
                while (resultSet.next()){
                    list.add(new Employee(resultSet.getString("first_name"),resultSet.getString("last_name"),
                            resultSet.getString("code"),resultSet.getString("password"),resultSet.getString("typ"),
                            resultSet.getStatement().getResultSet(),resultSet.getDate("hiring_date") ,resultSet.getString("job_title"),
                            resultSet.getString("supervisor_code"),resultSet.getString("phone_number"),resultSet.getString("address")));
                }
            }
//            resultSet.getStatement().getResultSet()
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    // get employe by code
    public static ObservableList<Employee> employeeData(String code){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection c = connectDB();
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try{
            preparedStatement = c.prepareStatement("select * from employees where code =?");
            preparedStatement.setString(1,code);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                System.out.println("No Users found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No User In The DataBase with code "+code);
                alert.show();
            }
            else{
                while (resultSet.next()){
                    list.add(new Employee(resultSet.getString("first_name"),resultSet.getString("last_name"),
                            resultSet.getString("code"),resultSet.getString("password"),resultSet.getString("typ"),
                            resultSet.getStatement().getResultSet(),resultSet.getDate("hiring_date") ,resultSet.getString("job_title"),
                            resultSet.getString("supervisor_code"),resultSet.getString("phone_number"),resultSet.getString("address")));
                }
            }
//            resultSet.getStatement().getResultSet()
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    // get employe by code
    public static ObservableList<Employee> employeeDataByType(String type){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection c = connectDB();
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try{
            preparedStatement = c.prepareStatement("select * from employees where typ =?");
            preparedStatement.setString(1,type);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                System.out.println("No Users found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No User In The DataBase with type "+type);
                alert.show();
            }
            else{
                while (resultSet.next()){
                    list.add(new Employee(resultSet.getString("first_name"),resultSet.getString("last_name"),
                            resultSet.getString("code"),resultSet.getString("password"),resultSet.getString("typ"),
                            resultSet.getStatement().getResultSet(),resultSet.getDate("hiring_date") ,resultSet.getString("job_title"),
                            resultSet.getString("supervisor_code"),resultSet.getString("phone_number"),resultSet.getString("address")));
                }
            }
//            resultSet.getStatement().getResultSet()
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    // update employee
    public static void updateEmployee(String firstName, String lastName, String code,String password,String type
                                      ,String filePath,LocalDate date, String jobTitle, String superVisorCode,
                                      String phoneNumber, String address) throws FileNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection c = connectDB();
        InputStream imageFile = new FileInputStream(filePath);
        try{
             preparedStatement = c.prepareStatement("update employees set first_name=?,last_name=?,code=?,typ=?,password=?,image=?,hiring_date=?,job_title=?,supervisor_code=?,phone_number=?,address=?  where code=?");
            preparedStatement.setString(1,firstName);
             preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,code);
            preparedStatement.setString(4,type);
            preparedStatement.setString(5,password);
            preparedStatement.setBinaryStream(6,imageFile);
            preparedStatement.setDate(7,Date.valueOf(date));
            preparedStatement.setString(8,jobTitle);
            preparedStatement.setString(9,superVisorCode);
            preparedStatement.setString(10,phoneNumber);
            preparedStatement.setString(11,address);
            preparedStatement.setString(12,code);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Get information from dataBase and store it in observable List

    public static ObservableList<Employee> employeeDataForDelete(){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection c = connectDB();
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try{
            preparedStatement = c.prepareStatement("select * from employees");
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                System.out.println("No Users found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No Users In The DataBase!");
                alert.show();
            }
            else{
                while (resultSet.next()){
                    list.add(new Employee(resultSet.getString("first_name"),resultSet.getString("last_name"),
                            resultSet.getString("code"),resultSet.getString("typ"), resultSet.getString("supervisor_code")));
                }
            }
//            resultSet.getStatement().getResultSet()
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    // delete employee from database using code
    public static void deleteEmployee(String code){
        PreparedStatement preparedStatement = null;
        Integer resultSet = null;
        Connection c = connectDB();
        try{
            preparedStatement = c.prepareStatement("delete from Employees where code=?");
            preparedStatement.setString(1,code);
            resultSet =  preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if ( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ObservableList<Employee> employeeDataDelete(String code){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection c = connectDB();
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try{
            preparedStatement = c.prepareStatement("select * from employees where code =?");
            preparedStatement.setString(1,code);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                System.out.println("No Users found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No User In The DataBase with code "+code);
                alert.show();
            }
            else{
                while (resultSet.next()){
                    list.add(new Employee(resultSet.getString("first_name"),resultSet.getString("last_name"),
                            resultSet.getString("code"),resultSet.getString("typ"), resultSet.getString("supervisor_code")));
                }
            }
//            resultSet.getStatement().getResultSet()
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    // get employe by code
    public static ObservableList<Employee> employeeDataByTypeDelete(String type){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection c = connectDB();
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try{
            preparedStatement = c.prepareStatement("select * from employees where typ =?");
            preparedStatement.setString(1,type);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                System.out.println("No Users found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No User In The DataBase with type "+type);
                alert.show();
            }
            else{
                while (resultSet.next()){
                    list.add(new Employee(resultSet.getString("first_name"),resultSet.getString("last_name"),
                            resultSet.getString("code"),resultSet.getString("typ"), resultSet.getString("supervisor_code")));
                }
            }
//            resultSet.getStatement().getResultSet()
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void changeScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = null;

                FXMLLoader loader = new FXMLLoader(LogicCode.class.getResource(fxmlFile));
                root = loader.load();

        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }

}





































