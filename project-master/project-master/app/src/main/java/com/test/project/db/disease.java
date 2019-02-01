package com.test.project.db;

public class disease {
    int id;
    String name;

    public disease(){}
    public disease(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }
}
