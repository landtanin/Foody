package com.appdever.foody;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appdever.foody.searchPage.enterSearch.EnterSearchMenu;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {

    String setfoodID;
    String setFoodTypeID;
    String setNameFood;
    String setCookingMethod ;
    String setImg ;
    String setPrepareIngredient ;
    String setFoodDescription;
    ImageView imgFood;
    TextView nameFood;
    TextView foodDes;
    TextView foodIngredient;
    TextView foodCooking;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);




        Bundle foodDetail = getIntent().getExtras();
        setfoodID = foodDetail.getString( KeyStore.FOODID_DETAIL_SEND_KEY ) ;
        setFoodTypeID = foodDetail.getString(KeyStore.FOODTYOEID_DETAIL_SEND_KEY ) ;
        setNameFood = foodDetail.getString( KeyStore.NAMEFOOD_DETAIL_SEND_KEY );
        setCookingMethod = foodDetail.getString(KeyStore.FOODMETHOD_DETAIL_SEND_KEY ) ;
        setImg = foodDetail.getString( KeyStore.FOODIMG_DETAIL_SEND_KEY ) ;
        setPrepareIngredient  = foodDetail.getString(KeyStore.FOOD_INGREDIENT_SEND_KEY ) ;
        setFoodDescription = foodDetail.getString(KeyStore.FOOD_DESCRIPTION_SEND_KEY ) ;



        nameFood = (TextView) findViewById(R.id.nameFood);
        nameFood.setText(setNameFood);
        imgFood = (ImageView)  findViewById(R.id.imgFood);
        Glide.with(getApplicationContext()).load(setImg).centerCrop().diskCacheStrategy
                (DiskCacheStrategy.ALL).placeholder(R.drawable.home).crossFade().into(imgFood);

        foodDes = (TextView) findViewById(R.id.foodDescription) ;
        foodDes.setText(setFoodDescription);
        foodDes.setMovementMethod(new ScrollingMovementMethod());

        foodIngredient =(TextView) findViewById(R.id.prepareIngredient) ;
        foodIngredient.setText(setPrepareIngredient);
        foodIngredient.setMovementMethod(new ScrollingMovementMethod());

        foodCooking =(TextView) findViewById(R.id.cookingMethod) ;
        foodCooking.setText(setCookingMethod);
        foodCooking.setMovementMethod(new ScrollingMovementMethod());










    }
}
