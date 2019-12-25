package com.example.ioun25;

public class EIDOS {
    private String name;
    private int ID;
    private Double timh;

    private String prosu;
    private String kathg;

    private Integer status;
    private Double timhp;

    // Constructor for the Phonebook class
    public EIDOS( Integer ID,String name, Double timh,String prosu,String kathg,Double timhp,Integer status) {
        super();
        this.name = name;
        this.ID = ID;
        this.timh = timh;

        this.prosu = prosu;
        this.kathg = kathg;
        this.status = status;
        this.timhp = timhp;
    }

    // Getter and setter methods for all the fields.
    // Though you would not be using the setters for this example,
    // it might be useful later.
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public Integer getID() {

        return ID;
    }
    public void setID(Integer ID) {
        this.ID = ID;
    }


    public Double getTimhp() {
        return timhp;
    }
    public void setTimhp(Double timhp) {
        this.timhp = timhp;
    }

    public Double getTimh() {
        return timh;
    }
    public void setTimh(Double timh) {
        this.timh = timh;
    }

    public String getProsu() {
        return prosu;
    }
    public void setProsu(String prosu) {
        this.prosu = prosu;
    }



    public String getKathg() {
        return kathg;
    }
    public void setKathg(String kathg) {
        this.kathg = kathg;
    }


    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }






}
