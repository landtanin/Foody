package com.appdever.foody.searchPage.ingredientSearch;

/**
 * Created by landtanin on 7/25/16 AD.
 */
public class TotalItem {

    private String totalFoodName;
    private String idMat;
    private String idTypeMat;

    public TotalItem(String totalFoodName, String idMat, String idTypeMat) {

        this.totalFoodName = totalFoodName;
        this.idMat = idMat;
        this.idTypeMat = idTypeMat;

    }

    public String getTotalFoodName() {
        return totalFoodName;
    }

    public String getIdMat() {
        return idMat;
    }

    public String getIdTypeMat() {
        return idTypeMat;
    }

    public void setTotalFoodName(String totalFoodName) {
        this.totalFoodName = totalFoodName;
    }

    public void setIdMat(String idMat) {

        this.idMat = idMat;
    }

    public void setIdTypeMat(String idTypeMat) {
        this.idTypeMat = idTypeMat;
    }
}
