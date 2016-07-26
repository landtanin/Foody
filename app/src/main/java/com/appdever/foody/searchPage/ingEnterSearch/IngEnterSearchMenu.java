package com.appdever.foody.searchPage.ingEnterSearch;

/**
 * Created by landtanin on 7/26/16 AD.
 */
public class IngEnterSearchMenu {

    private String FoodID = "0";
    private String FoodTypeID = "0";
    private String NameFood;
    private String CookingMethod = "Unknown method";
    private String Img = "Unknown image";
    private String PrepareIngredient = "No ingredients were found";
    private String FoodDescription = "No description";

    public IngEnterSearchMenu(String FoodID, String FoodTypeID, String nameFood, String CookingMethod, String Img, String PrepareIngredient, String FoodDescription) {
        this.FoodID = FoodID;
        this.FoodTypeID = FoodTypeID;
        this.NameFood = nameFood;
        this.CookingMethod = CookingMethod;
        this.Img = Img;
        this.PrepareIngredient = PrepareIngredient;
        this.FoodDescription = FoodDescription;
    }

    public String getFoodID() {
        return FoodID;
    }

    public String getFoodTypeID() {
        return FoodTypeID;
    }

    public String getNameFood() {
        return NameFood;
    }

    public String getCookingMethod() {
        return CookingMethod;
    }

    public String getImg() {
        return Img;
    }

    public String getPrepareIngredient() {
        return PrepareIngredient;
    }

    public String getFoodDescription() {
        return FoodDescription;
    }

    //----------set------------

    public void setFoodID(String newFoodID) {
        this.FoodID = newFoodID;
    }

    public void setFoodTypeID(String newFoodTypeID) {
        this.NameFood = newFoodTypeID;
    }

    public void setNameFood(String newNameFood) {
        this.NameFood = newNameFood;
    }

    public void setCookingMethod(String newCookingMethod) {
        this.NameFood = newCookingMethod;
    }

    public void setImg(String newImg) {
        this.Img = newImg;
    }

    public void setPrepareIngredient(String newPrepareIngredient) {
        this.NameFood = newPrepareIngredient;
    }

    public void setFoodDescription(String newFoodDescription) {
        this.NameFood = newFoodDescription;
    }


}
