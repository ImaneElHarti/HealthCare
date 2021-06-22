package com.example.healthcare;

public class DocId {
    public String docid;
    public <T extends DocId> T withId (final String id){
        this.docid= id;
        return  (T)this;
    }
}
