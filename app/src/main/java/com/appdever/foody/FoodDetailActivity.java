package com.appdever.foody;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdever.foody.manager.KeyStore;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class FoodDetailActivity extends AppCompatActivity {

    private String setfoodID;
    private String setFoodTypeID;
    private String setNameFood;
    private String setCookingMethod ;
    private String setImg ;
    private String setPrepareIngredient ;
    private String setFoodDescription;
    private ImageView imgFood;
    private TextView nameFood;
    private TextView foodDes;
    private TextView foodIngredient;
    private TextView foodCooking;

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
        Glide.with(getApplicationContext()).load(setImg).diskCacheStrategy
                (DiskCacheStrategy.ALL).placeholder(R.drawable.home).crossFade().into(imgFood);

        foodDes = (TextView) findViewById(R.id.foodDescription);
        foodDes.setText(setFoodDescription);

//        foodDes.setMovementMethod(new ScrollingMovementMethod());

        foodIngredient = (TextView)findViewById(R.id.prepareIngredient) ;
        String strUl=setPrepareIngredient.replace("<ul>","").trim();
        String strLi=strUl.replaceAll("<li>","");
        String closeLi=strLi.replaceAll("</li>","");
        String closeUl=closeLi.replace("</ul>","");
        foodIngredient.setText(closeUl);
//        foodIngredient.setMovementMethod(new ScrollingMovementMethod());

        foodCooking =(TextView) findViewById(R.id.cookingMethod);
        String strCookbr = setCookingMethod.replaceAll("<br>", "\n");
        foodCooking.setText(strCookbr);
//        foodCooking.setMovementMethod(new ScrollingMovementMethod());

    }
}
