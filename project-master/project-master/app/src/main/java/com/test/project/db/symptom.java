package com.test.project.db;

public class symptom {
    String name;
    int d_id;

    public symptom(String name, int d_id){
        this.name = name;
        this.d_id = d_id;
    }

    public void setName(String name){this.name = name;}

    public void setD_id(int d_id){this.d_id = d_id;}

    public String getName(){return name;}

    public int getD_id(){return d_id;}
}
