package com.appdever.foody.searchPage.enterSearch;

/**
 * Created by landtanin on 7/11/2016 AD.
 */
public class EnterSearchMenu {

    private String myMenu;
    private String menuImage;

    public EnterSearchMenu(String myMenu, String menuImage) {
        this.myMenu = myMenu;
        this.menuImage = menuImage;
    }

    public String getMyMenu() {
        return myMenu;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMyMenu(String newMenu) {
        this.myMenu = newMenu;
    }

    public void setMenuImage(String newMenuImage) {
        this.menuImage = newMenuImage;
    }

}


