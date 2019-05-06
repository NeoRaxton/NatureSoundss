package com.example.naturesounds;

public class SoundsModel{
    private int id,imgRes;
    private String title;
    public SoundsModel(int id,int imgRes, String title)
    {
    this.id = id;
    this.imgRes = imgRes;
    this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
