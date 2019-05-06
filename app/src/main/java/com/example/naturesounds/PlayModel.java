package com.example.naturesounds;

import android.media.MediaPlayer;

public class PlayModel {
    private int idp,imgResp;
    private String titlep;

    public PlayModel(int id, int imgRes, String title)
    {
        this.idp = id;
        this.imgResp = imgRes;
        this.titlep = title;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public int getImgResp() {
        return imgResp;
    }

    public void setImgResp(int imgResp) {
        this.imgResp = imgResp;
    }

    public String getTitlep() {
        return titlep;
    }

    public void setTitlep(String titlep) {
        this.titlep = titlep;
    }
}
