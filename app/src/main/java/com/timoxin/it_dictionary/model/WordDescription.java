package com.timoxin.it_dictionary.model;

public class WordDescription {
    private int id_word;
    private String name_word;
    private String desc_word;
    private String imgURL_word;

    public WordDescription(int id, String name, String desc, String imgURL){
        this.id_word = id;
        this.name_word = name;
        this.desc_word = desc;
        this.imgURL_word = imgURL;
    }

    public int getId_word() {
        return id_word;
    }

    public void setId_word(int id_word) {
        this.id_word = id_word;
    }

    public String getName_word() {
        return name_word;
    }

    public void setName_word(String name_word) {
        this.name_word = name_word;
    }

    public String getDesc_word() {
        return desc_word;
    }

    public void setDesc_word(String desc_word) {
        this.desc_word = desc_word;
    }

    public String getImgURL_word() {
        return imgURL_word;
    }

    public void setImgURL_word(String imgURL_word) {
        this.imgURL_word = imgURL_word;
    }
}
