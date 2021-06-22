package com.example.healthcare;

public class PatientWithId {

    String id;
    patient p;

    public PatientWithId() {
    }

    public PatientWithId(String id, patient p) {
        this.id = id;
        this.p = p;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public patient getP() {
        return p;
    }

    public void setP(patient p) {
        this.p = p;
    }
}
