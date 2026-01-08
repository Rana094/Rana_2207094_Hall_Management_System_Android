

package com.example.rana_2207094_hall_management_system_android;

import java.io.Serializable;

public class Student implements Serializable {

    private int roll;
    private String name;
    private String image;
    private String email;
    private String address;
    private String dept;
    private String cgpa;
    private String birthdate;
    private String password;

    private String status ;
    private String removeStatus ;

    public Student() {
    }

    public Student(int roll, String name, String email, String address,
                   String dept, String cgpa, String birthdate, String image, String password) { // <--- CHANGED TYPE HERE
        this.roll = roll;
        this.name = name;
        this.email = email;
        this.address = address;
        this.dept = dept;
        this.cgpa = cgpa;
        this.birthdate = birthdate;
        this.image = image;
        this.password = password;
    }


    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemoveStatus() { return removeStatus; }
    public void setRemoveStatus(String removeStatus) { this.removeStatus = removeStatus; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public int getRoll() { return roll; }
    public void setRoll(int roll) { this.roll = roll; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDept() { return dept; }
    public void setDept(String dept) { this.dept = dept; }
    public String getCgpa() { return cgpa; }
    public void setCgpa(String cgpa) { this.cgpa = cgpa; }
    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}