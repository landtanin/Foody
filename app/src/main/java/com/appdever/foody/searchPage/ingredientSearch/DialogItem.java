package com.appdever.foody.searchPage.ingredientSearch;

/**
 * Created by landtanin on 7/22/16 AD.
 */
public class DialogItem {

    private String ingName;
    private boolean ingSelect;
    private String idMat;
    private String idTypeMat;

    public DialogItem(String ingName, boolean ingSelect, String idMat, String idTypeMat) {

        this.ingName = ingName;
        this.ingSelect = ingSelect;
        this.idMat = idMat;
        this.idTypeMat = idTypeMat;

    }

    public String getIngName() {
        return ingName;
    }

    public boolean getIngSelect() {
        return ingSelect;
    }

    public String getIdMat() {
        return idMat;
    }

    public String getIdTypeMat() {
        return idTypeMat;
    }

    public void setIngName(String newIngName) {

        this.ingName = newIngName;
    }

    public void setIngSelect(boolean newIngSelect) {
//        Log.d("SetIng", String.valueOf(newIngSelect));

        this.ingSelect = newIngSelect;

    }

    public void setIdMat(String idMat) {

        this.idMat = idMat;
    }

    public void setIdTypeMat(String idTypeMat) {
        this.idTypeMat = idTypeMat;
    }
}
