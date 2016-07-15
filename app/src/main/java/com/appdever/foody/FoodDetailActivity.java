package com.appdever.foody;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appdever.foody.databinding.ActivityFoodDetailBinding;
import com.bumptech.glide.Glide;

public class FoodDetailActivity extends AppCompatActivity {

    private String strFoodID, strFoodTypeID, strNameFood, strCookingMethod, strImg,strFoodIngredient, strFoodDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFoodDetailBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_food_detail);

        Bundle extras = getIntent().getExtras();
        strFoodID = extras.getString(KeyStore.FOODID_DETAIL_SEND_KEY);
        strFoodTypeID = extras.getString(KeyStore.FOODTYOEID_DETAIL_SEND_KEY);
        strNameFood = extras.getString(KeyStore.NAMEFOOD_DETAIL_SEND_KEY);
        strCookingMethod = extras.getString(KeyStore.FOODMETHOD_DETAIL_SEND_KEY);
        strImg = extras.getString(KeyStore.FOODIMG_DETAIL_SEND_KEY);
        strFoodIngredient = extras.getString(KeyStore.FOOD_INGREDIENT_SEND_KEY);
        strFoodDescription = extras.getString(KeyStore.FOOD_DESCRIPTION_SEND_KEY);

        Glide.with(this).load(strImg).into(binding.detailImg);
        binding.detailFoodNameTxt.setText(strNameFood);
        binding.materialDetailText.setText(strFoodIngredient);
        binding.methodDetailText.setText(strCookingMethod);

    }
}
