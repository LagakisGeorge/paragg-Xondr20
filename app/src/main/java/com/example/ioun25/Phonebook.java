package com.example.ioun25;

public class Phonebook {
    private String name;
    private String posothta;
    private String timh;

    private String prosu;
    private String sxolia;

    private Integer status;
    private Integer pointer;

    // Constructor for the Phonebook class
    public Phonebook(String name, String posothta, String timh,String prosu,String sxolia,Integer status,Integer pointer) {
        super();
        this.name = name;
        this.posothta = posothta;
        this.timh = timh;

        this.prosu = prosu;
        this.sxolia = sxolia;
        this.status = status;
        this.pointer = pointer;
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


    public String getPosothta() {
        return posothta;
    }
    public void setPosothta(String posothta) {
        this.posothta = posothta;
    }


    public String getTimh() {
        return timh;
    }
    public void setTimh(String timh) {
        this.timh = timh;
    }


    public String getProsu() {
        return prosu;
    }
    public void setProsu(String prosu) {
        this.prosu = prosu;
    }



    public String getSxolia() {
        return sxolia;
    }
    public void setSxolia(String sxolia) {
        this.sxolia = sxolia;
    }


    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }


    public int getPointer() {
        return pointer;
    }
    public void setStatus(Integer pointer) {
        this.pointer = pointer;
    }



}
