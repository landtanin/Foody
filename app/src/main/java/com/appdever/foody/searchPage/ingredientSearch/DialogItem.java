package com.appdever.foody.searchPage.ingredientSearch;

import android.util.Log;

/**
 * Created by landtanin on 7/22/16 AD.
 */
public class DialogItem {

    private String ingName;
    private boolean ingSelect;

    public DialogItem(String ingName, boolean ingSelect) {

        this.ingName = ingName;
        this.ingSelect = ingSelect;

    }

    public String getIngName() {
        return ingName;
    }

    public boolean getIngSelect() {
        return ingSelect;
    }

    public void setIngName(String newIngName) {

        this.ingName = newIngName;
    }

    public void setIngSelect(boolean newIngSelect) {
        Log.d("SetIng", String.valueOf(newIngSelect));

        this.ingSelect = newIngSelect;

    }


}
