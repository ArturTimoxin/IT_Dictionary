package com.timoxin.it_dictionary.model;

import java.io.Serializable;

public class WordCard implements Serializable {
    private int id_word;
    private String name_word;
    private String desc_word;
    private String imgURL_word = null;
    private boolean isItMyWordList = false;

    public WordCard(int id, String name, String desc, String imgURL){
        this.id_word = id;
        this.name_word = name;
        this.desc_word = desc;
        this.imgURL_word = imgURL;
    }

    public WordCard(int id, String name, String desc, boolean isItMyWordList){
        this.id_word = id;
        this.name_word = name;
        this.desc_word = desc;
        this.isItMyWordList = isItMyWordList;
    }

    public int getID_word() {
        return id_word;
    }

    public String getName_word() {
        return name_word;
    }

    public String getDesc_word() {
        return desc_word;
    }

    public String getImgURL_word() {
        return imgURL_word;
    }

    public boolean getIsItMyWordList() { return isItMyWordList; }
}
