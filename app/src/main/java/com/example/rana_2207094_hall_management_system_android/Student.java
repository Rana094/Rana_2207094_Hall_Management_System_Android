//package com.example.rana_2207094_hall_management_system_android; // <--- MAKE SURE THIS MATCHES YOUR ANDROID APP PACKAGE
//
//import java.io.Serializable;
//
//public class Student implements Serializable {
//
//    private int roll;
//    private String name;
//    private byte[] image;
//    private String email;
//    private String address;
//    private String dept;
//    private String cgpa;
//    private String birthdate;
//    private String password;
//
//    // Required empty constructor for Firebase
//    public Student() {
//    }
//
//    // Constructor for partial data
//    public Student(int roll, String name, byte[] image, String dept) {
//        this.roll = roll;
//        this.name = name;
//        this.image = image;
//        this.dept = dept;
//    }
//
//    // Full Constructor
//    public Student(int roll, String name, String email, String address,
//                   String dept, String cgpa, String birthdate, byte[] image, String password) {
//        this.roll = roll;
//        this.name = name;
//        this.email = email;
//        this.address = address;
//        this.dept = dept;
//        this.cgpa = cgpa;
//        this.birthdate = birthdate;
//        this.image = image;
//        this.password = password;
//    }
//
//    // Getters and Setters
//    public int getRoll() { return roll; }
//    public void setRoll(int roll) { this.roll = roll; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public byte[] getImage() { return image; }
//    public void setImage(byte[] image) { this.image = image; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getAddress() { return address; }
//    public void setAddress(String address) { this.address = address; }
//
//    public String getDept() { return dept; }
//    public void setDept(String dept) { this.dept = dept; }
//
//    public String getCgpa() { return cgpa; }
//    public void setCgpa(String cgpa) { this.cgpa = cgpa; }
//
//    public String getBirthdate() { return birthdate; }
//    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
//
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//}


package com.example.rana_2207094_hall_management_system_android;

import java.io.Serializable;

public class Student implements Serializable {

    private int roll;
    private String name;
    private String image; // <--- CHANGED: This is now a String (URL), not byte[]
    private String email;
    private String address;
    private String dept;
    private String cgpa;
    private String birthdate;
    private String password;

    // Required empty constructor for Firebase
    public Student() {
    }

    // Full Constructor
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
    // Inside Student.java
    private String status = "false";
    private String removeStatus = "false";

    // Add Getters and Setters for these two!
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemoveStatus() { return removeStatus; }
    public void setRemoveStatus(String removeStatus) { this.removeStatus = removeStatus; }

    // Getters and Setters
    public String getImage() { return image; }        // <--- CHANGED RETURN TYPE
    public void setImage(String image) { this.image = image; } // <--- CHANGED PARAM TYPE

    // ... (Keep the other getters and setters for roll, name, etc. exactly the same) ...
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