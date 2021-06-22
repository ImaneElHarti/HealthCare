package com.example.healthcare;

import java.util.ArrayList;

public interface RecycleViewClick {
    void onItemClick(int position, ArrayList<doctor> Med);
    void onLongItemClick(int position);
    void onItemClickP(int position, ArrayList<PatientWithId> patients);
    void onItemClickV(int position, ArrayList<RdvWithId> visites,String motif);
    void onItemClickC(int position,ArrayList<RdvWithId> rdv);
    void onItemClickA(int position,ArrayList<RdvWithId> rdv);




}
