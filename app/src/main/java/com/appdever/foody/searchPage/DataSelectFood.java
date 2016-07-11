package com.appdever.foody.searchPage;

/**
 * Created by arisak on 7/7/2559.
 */
public class DataSelectFood {
    private int imgTitle;
    private String tvTitle,tvSubTitle;


    public DataSelectFood(int imgTitle, String tvTitle, String tvSubTitle) {
        this.imgTitle = imgTitle;
        this.tvTitle = tvTitle;
        this.tvSubTitle = tvSubTitle;
    }

    public int getImgTitle() {
        return imgTitle;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public String getTvSubTitle() {
        return tvSubTitle;
    }
}
