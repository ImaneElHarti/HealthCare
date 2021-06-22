package com.example.healthcare;

import java.io.Serializable;

public class patient implements Serializable {

    public String birthDay;
    public String email;
    public String fullName;
    public String password;
    public String phone;
    public  patient(){

    }
    public patient(String birthtDay, String email, String fullName,  String password, String phone) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.birthDay = birthtDay;
        this.password = password;
    }
    public String getBirthDay() {return birthDay;}
    public String getEmail() {
        return email;
    }
    public String getFullName() {
        return fullName;
    }
    public String getPassword() {
        return password;
    }
    public String getPhone() {
        return phone;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "client{" +
                "Email='" + email + '\'' +
                ", FullName='" + fullName + '\'' +
                ", Phone='" + phone + '\'' +
                ", birthDay='" + birthDay + '\'' +
                '}';
    }
}
