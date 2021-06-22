package com.example.healthcare;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Rdv implements Serializable {

    private Long année;
    private Long jour;
    private String medcin;
    private Long moi;
    private String patientid;
    private String motif;
    private String etat;
    private String name;

    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    // contructor


    public Rdv() {

    }

    public Rdv(Long année, Long jour, String medcin, Long moi, String patientid, String motif, String etat,String name) {
        this.année = année;
        this.jour = jour;
        this.medcin = medcin;
        this.moi = moi;
        this.patientid = patientid;
        this.motif=motif;
        this.etat = etat;
        this.name = name;


}


    // getter & setters


    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Long getAnnée() {
        return année;
    }

    public void setAnnée(Long année) {
        this.année = année;
    }

    public Long getJour() {
        return jour;
    }

    public void setJour(Long jour) {
        this.jour = jour;
    }

    public String getMedcin() {
        return medcin;
    }

    public void setMedcin(String medcin) {
        this.medcin = medcin;
    }

    public Long getMoi() {
        return moi;
    }

    public void setMoi(Long moi) {
        this.moi = moi;
    }

}
