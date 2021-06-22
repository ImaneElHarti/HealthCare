package com.example.healthcare;

import java.io.Serializable;

public class doctor implements Serializable {
    public String email;
    public String fullName;
    public String phone;
    public String speciality;
    public String password;
    public  doctor(){

    }
    public doctor(String email, String fullName, String phone, String speciality, String password) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.speciality = speciality;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getPassword() {
        return password;
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

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "doctor{" +
                "Email='" + email + '\'' +
                ", FullName='" + fullName + '\'' +
                ", Phone='" + phone + '\'' +
                ", Speciality='" + speciality + '\'' +
                '}';
    }
}
