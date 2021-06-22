package com.example.healthcare;

public class Message {
    private String patient;
    private String medcin;
    private String message;
    private  String etat;

    public Message() {
    }

    public Message(String patient, String medcin, String message,String etat) {
        this.patient = patient;
        this.medcin = medcin;
        this.message = message;
        this.etat = etat;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getMedcin() {
        return medcin;
    }

    public void setMedcin(String medcin) {
        this.medcin = medcin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
