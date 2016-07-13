package com.appdever.foody.searchPage;

/**
 * Created by arisak on 7/7/2559.
 */
public class DataSelectFood {
    private int imgTitle, idFood;
    private String tvTitle;


    public DataSelectFood(int imgTitle, String tvTitle, int idFood) {
        this.imgTitle = imgTitle;
        this.tvTitle = tvTitle;
        this.idFood = idFood;
    }

    public int getImgTitle() {
        return imgTitle;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public int getIdFood() {
        return idFood;
    }
}
