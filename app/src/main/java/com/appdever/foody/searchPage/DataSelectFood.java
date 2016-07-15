package com.appdever.foody.searchPage;

/**
 * Created by arisak on 7/7/2559.
 */
public class DataSelectFood {
    private int imgTitle, idFoodType;
    private String tvTitle;


    public DataSelectFood(int imgTitle, String tvTitle, int idFoodType) {
        this.imgTitle = imgTitle;
        this.tvTitle = tvTitle;
        this.idFoodType = idFoodType;
    }

    public int getImgTitle() {
        return imgTitle;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public int getIdFoodType() {
        return idFoodType;
    }
}
