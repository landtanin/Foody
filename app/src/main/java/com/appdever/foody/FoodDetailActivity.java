package com.appdever.foody;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FoodDetailActivity extends AppCompatActivity {

    private String strFoodID, strFoodTypeID, strNameFood, strCookingMethod, strImg,strFoodIngredient, strFoodDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);



    }
}
