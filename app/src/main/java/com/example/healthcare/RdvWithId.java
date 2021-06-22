package com.example.healthcare;

public class RdvWithId {
    Rdv r;
    String id;

    public RdvWithId(Rdv r, String id) {
        this.r = r;
        this.id = id;
    }

    public Rdv getR() {
        return r;
    }

    public void setR(Rdv r) {
        this.r = r;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
