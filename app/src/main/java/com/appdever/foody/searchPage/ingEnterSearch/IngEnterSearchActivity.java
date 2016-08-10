package com.appdever.foody.searchPage.ingEnterSearch;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.appdever.foody.FoodDetailActivity;
import com.appdever.foody.R;
import com.appdever.foody.databinding.ActivityIngEnterSearchBinding;
import com.appdever.foody.manager.JSONObtained;
import com.appdever.foody.manager.KeyStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Response;

public class IngEnterSearchActivity extends AppCompatActivity {

    ActivityIngEnterSearchBinding binding;

    //    private RecyclerView rv;
    private IngEnterSearchRecyclerAdapter mRecyclerAdapter;
    List<IngEnterSearchMenu> newsList = new ArrayList<>();

    private String resultServer;
    private String strFoodID = "0";
    private String strFoodTypeID = "0";
    private String strNameFood;
    private String strCookingMethod = "Unknown method";
    private String strImg = "Unknown image";
    private String strError = "Unknown Status!";
    private String strPrepareIngredient = "No ingredients were found";
    private String strFoodDescription = "No description";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ing_enter_search);

        // -----------------Spinner-------------------------------------
//        binding.ingEnterSearchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.hard_code_menus, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.ingEnterSearchSpinner.setAdapter(adapter);
        //---------------Spinner-end------------------------------------

        StaggeredGridLayoutManager ingEnterSearchGrid = new StaggeredGridLayoutManager(1, 1);
        binding.rvIngEnterSearch.setLayoutManager(ingEnterSearchGrid);
        mRecyclerAdapter = new IngEnterSearchRecyclerAdapter(this, newsList, new IngEnterSearchRecyclerAdapter.ingDataCarrier() {
            @Override
            public void ingMySetOnClickListener(IngEnterSearchMenu ingEnterSearchMenu) {

                Intent objIntent = new Intent(IngEnterSearchActivity.this, FoodDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString(KeyStore.FOODID_DETAIL_SEND_KEY, ingEnterSearchMenu.getFoodID());
                extras.putString(KeyStore.FOODTYOEID_DETAIL_SEND_KEY, ingEnterSearchMenu.getFoodTypeID());
                extras.putString(KeyStore.NAMEFOOD_DETAIL_SEND_KEY, ingEnterSearchMenu.getNameFood());
                extras.putString(KeyStore.FOODMETHOD_DETAIL_SEND_KEY, ingEnterSearchMenu.getCookingMethod());
                extras.putString(KeyStore.FOODIMG_DETAIL_SEND_KEY, ingEnterSearchMenu.getImg());
                extras.putString(KeyStore.FOOD_INGREDIENT_SEND_KEY, ingEnterSearchMenu.getPrepareIngredient());
                extras.putString(KeyStore.FOOD_DESCRIPTION_SEND_KEY, ingEnterSearchMenu.getFoodDescription());
                objIntent.putExtras(extras);
                startActivity(objIntent);

            }
        });

        binding.rvIngEnterSearch.setAdapter(mRecyclerAdapter);
        binding.rvIngEnterSearch.setHasFixedSize(true);

        connectDatabase();

//        binding.dataNotFoundTxt.setVisibility(View.VISIBLE);
//        Log.w("SIZE", String.valueOf(connectDatabase()));

    }


    private void connectDatabase() {


        List<String> getArrayList = getIntent().getStringArrayListExtra(KeyStore.ING_MAT_ID_SEND_KEY);

        // postRequest technique
        final FormBody.Builder formBody = new FormBody.Builder();
        for (int i = 0; i < getArrayList.size(); i++) {
            formBody.add("material[" + i + "]", getArrayList.get(i));
        }

        final HttpUrl myurl = HttpUrl.parse(JSONObtained.getAbsoluteUrl("food_filter.php")).newBuilder()
                .build();

        //  Log.d("ARRAYID",String.valueOf(myurl.query()));


        new AsyncTask<String, Integer, Boolean>() {

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);

                //TODO: progress update

            }



            @Override
            protected Boolean doInBackground(String... s) {
                boolean checkVoid = false;
                try {

                    Response response = JSONObtained.getInstance()
                            .newCall(JSONObtained.postRequest(myurl, formBody.build())).execute();

//                    Log.w("API", String.valueOf(myurl));

                    if (response.isSuccessful()) {

                        resultServer = response.body().string();

                        Log.d("xxxxxx", resultServer);

                        JSONObject foodJSONStr, ID_food = null;

                        try {
                            foodJSONStr = new JSONObject(resultServer);
                            JSONArray foods = foodJSONStr.getJSONArray("foods");
                            if (foods.length() > 0) {

                                for (int i = 0; i < foods.length(); i++) {

                                    ID_food = foods.getJSONObject(i);
                                    strFoodID = ID_food.getString("id_food");
                                    strFoodTypeID = ID_food.getString("id_typefood");
                                    strNameFood = ID_food.getString("name_food");
                                    strCookingMethod = ID_food.getString("cooking_method");
                                    strImg = ID_food.getString("img");
                                    strPrepareIngredient = ID_food.getString("prepare_ingredient");
                                    strFoodDescription = ID_food.getString("description");

                                    // update data to ArrayList in recycler adapter
                                    newsList.add(new IngEnterSearchMenu(strFoodID, strFoodTypeID, strNameFood,
                                            strCookingMethod, strImg, strPrepareIngredient, strFoodDescription));

                                    publishProgress(i);
                                    //TODO: progress update sender

                                }

                            } else {

                                checkVoid = true;

                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("failed", response.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }



                return checkVoid;
            }



            @Override
            protected void onPostExecute(Boolean s) {
                if (s){
                    alertNoData();
                }
                mRecyclerAdapter.notifyDataSetChanged();

                super.onPostExecute(s);
            }

        }.execute();

    }

    private void alertNoData() {



        TextView dataNotFound = (TextView) findViewById(R.id.dataNotFoundTxt);
        dataNotFound.setVisibility(View.VISIBLE);

    }

    private String[] convertToNormalArray(ArrayList<String> arrayList) {

        String[] newFoodIdArray = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {

            newFoodIdArray[i] = arrayList.get(i);

        }

        return newFoodIdArray;
    }
}
