package com.jbc.javaproject.AdminstratorClass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Getter
@Setter
public class Employee {
    private String firstName;
    private String lastName;
    private String code;
    private String password;
    private String type;
    private ImageView imageView;
    private Date hiringDate;
    private String jobTitle;
    private String supervisorCode;
    private String phoneNumber;
    private String address;
    private CheckBox checkBox;

    public Employee(String firstName, String lastName, String code, String password, String type, ResultSet rs, Date hiringDate, String jobTitle, String supervisorCode, String phoneNumber, String address) throws SQLException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.code = code;
        this.password = password;
        this.type = type;
        InputStream in = rs.getBinaryStream("image");
        Image img = new Image(in);
        this.imageView = new ImageView(img);
        imageView.setFitHeight(100);
        imageView.setFitWidth(200);
        this.hiringDate = hiringDate;
        this.jobTitle = jobTitle;
        this.supervisorCode = supervisorCode;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    public ImageView getImageView(){
        return imageView;
    }
    public Image getImage(){
        return imageView.getImage();
    }
    public Employee(String firstName, String lastName, String code, String type, String supervisorCode){
        this.firstName = firstName;
        this.lastName = lastName;
        this.code = code;
        this.type = type;
        this.supervisorCode = supervisorCode;
        this.checkBox = new CheckBox();

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public void setImageView(ImageView imageView) {
//        this.imageView = imageView;
//    }

    public Date getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSupervisorCode() {
        return supervisorCode;
    }

    public void setSupervisorCode(String supervisorCode) {
        this.supervisorCode = supervisorCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

}
