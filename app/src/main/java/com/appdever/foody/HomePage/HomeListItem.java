package com.appdever.foody.HomePage;

/**
 * Created by landtanin on 7/4/2016 AD.
 */
public class HomeListItem {

    private String homeFoodId;
    private String homeFoodTypeId;
    private String homeNameFood;
    private String homeCookingMethod;
    private String homeImgFood;
    private String homePrepareIngredient;
    private String homeFoodDescribtion;

    public HomeListItem(String homeFoodId, String homeFoodTypeId, String homeNameFood, String homeCookingMethod, String homeImgFood, String homePrepareIngredient, String homeFoodDescribtion) {
        this.homeImgFood = homeImgFood;
        this.homeNameFood = homeNameFood;
        this.homeFoodId = homeFoodId;
        this.homeFoodTypeId = homeFoodTypeId;
        this.homeCookingMethod = homeCookingMethod;
        this.homePrepareIngredient = homePrepareIngredient;
        this.homeFoodDescribtion = homeFoodDescribtion;
    }

    public String getImgTest01() {
        return homeImgFood;
    }

    public String getHomeNameFood() {
        return homeNameFood;
    }

    public String getHomeFoodId() {
        return homeFoodId;
    }

    public String getHomeFoodTypeId() {
        return homeFoodTypeId;
    }

    public String getHomeCookingMethod() {
        return homeCookingMethod;
    }

    public String getHomePrepareIngredient() {
        return homePrepareIngredient;
    }

    public String getHomeFoodDescribtion() {
        return homeFoodDescribtion;
    }

    public void setImgTest01(String homeImgFood) {
        this.homeImgFood = homeImgFood;
    }

    public void setHomeNameFood(String homeNameFood) {
        this.homeNameFood = homeNameFood;
    }

    public void setHomeFoodId(String homeFoodId) {
        this.homeFoodId = homeFoodId;
    }

    public void setHomeFoodTypeId(String homeFoodTypeId) {
        this.homeFoodTypeId = homeFoodTypeId;
    }

    public void setHomeCookingMethod(String homeCookingMethod) {
        this.homeCookingMethod = homeCookingMethod;
    }

    public void setHomePrepareIngredient(String homePrepareIngredient) {
        this.homePrepareIngredient = homePrepareIngredient;
    }

    public void setHomeFoodDescribtion(String homeFoodDescribtion) {
        this.homeFoodDescribtion = homeFoodDescribtion;
    }

}
//
//    public EnterSearchMenu(String FoodID, String FoodTypeID, String nameFood, String CookingMethod, String Img, String PrepareIngredient, String FoodDescription) {
//        this.FoodID = FoodID;
//        this.FoodTypeID = FoodTypeID;
//        this.NameFood = nameFood;
//        this.CookingMethod = CookingMethod;
//        this.Img = Img;
//        this.PrepareIngredient = PrepareIngredient;
//        this.FoodDescription = FoodDescription;
//    }