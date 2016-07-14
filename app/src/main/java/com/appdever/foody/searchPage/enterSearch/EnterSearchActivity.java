package com.appdever.foody.searchPage.enterSearch;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appdever.foody.KeyStore;
import com.appdever.foody.R;
import com.appdever.foody.databinding.ActivityEnterSearchBinding;
import com.appdever.foody.manager.JSONObtained;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Response;

public class EnterSearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ActivityEnterSearchBinding binding;

    private RecyclerView rv;
    private EnterSearchRecyclerAdapter recyclerAdapter;
    List<EnterSearchMenu> newsList = new ArrayList<>();

    private String resultServer;
    private static String strFoodID = "0";
    private String strFoodTypeID = "0";
    private String strNameFood;
    private String strCookingMethod = "Unknown method";
    private String strImg = "Unknown image";
    private String strError = "Unknown Status!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_enter_search);

        binding.enterSearchSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hard_code_menus, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.enterSearchSpinner.setAdapter(adapter);

//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);

//        int width = size.x;
//        int height = size.y;
//
//        Toast.makeText(EnterSearchActivity.this,"Width =" + width +",Height"+ height,
//                Toast.LENGTH_SHORT).show();

        int namefac = 0;
        StaggeredGridLayoutManager aaa = new StaggeredGridLayoutManager(1,1);
        rv = (RecyclerView) findViewById(R.id.rv_enter_search);
        rv.setLayoutManager(aaa);
        recyclerAdapter = new EnterSearchRecyclerAdapter(EnterSearchActivity.this, newsList, new EnterSearchRecyclerAdapter.abc() {
            @Override
            public void mySetOnClickListener(int listNumber) {
                Log.d("ABC", newsList.get(listNumber).getMyMenu());
            }
        });
        rv.setAdapter(recyclerAdapter);

        rv.setHasFixedSize(true);

        connectDatabase();

//        listActionController();

    }

//    private void listActionController() {
//
//        if (EnterSearchRecyclerAdapter.ListNumber) {
//        }
//
////        rv.setOnTouchListener(new View.OnTouchListener() {
////            @Override
////            public boolean onTouch(View v, MotionEvent event) {
////                binding.rvEnterSearch.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Log.d("CHECKKK", );
////                    }
////                });
////            }
////        });
//    }

//    public void IntentToDetailActivity() {
//
//        Intent objIntent = new Intent(this, FoodDetailActivity.class);
//        Bundle extras = new Bundle();
//        extras.putString(KeyStore.FOODID_DETAIL_SEND_KEY,strFoodID);
//        extras.putString(KeyStore.FOODTYOEID_DETAIL_SEND_KEY,strFoodTypeID);
//        extras.putString(KeyStore.NAMEFOOD_DETAIL_SEND_KEY,strNameFood);
//        extras.putString(KeyStore.FOODMETHOD_DETAIL_SEND_KEY,strCookingMethod);
//        extras.putString(KeyStore.FOODIMG_DETAIL_SEND_KEY,strImg);
//        objIntent.putExtras(extras);
//        startActivity(objIntent);
//
//    }

    private void connectDatabase() {

//        Request request = new Request.Builder().url(JSONObtained.getAbsoluteUrl("food.php" + "?type=ย่าง")).build();
// optional

//        Bundle bundle=getIntent().getExtras();
//        int key=bundle.getInt(KeyStore.SELECT_FOOD_SEND_KEY);

        int getSendKey = getIntent().getExtras().getInt(KeyStore.SELECT_FOOD_SEND_KEY);

        final HttpUrl myurl = HttpUrl.parse(JSONObtained.getAbsoluteUrl("food.php")).newBuilder().addQueryParameter("id_typefood", String.valueOf(getSendKey)).build();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {

                    Response response = JSONObtained.getInstance().newCall(JSONObtained.getRequest(myurl)).execute();

                    if (response.isSuccessful()) {

                        resultServer = response.body().string();

                        Log.d("xxxxxx", resultServer);

                        JSONObject foodJSONStr, ID_food = null;

                        try {
                            foodJSONStr = new JSONObject(resultServer);
                            JSONArray foods = foodJSONStr.getJSONArray("foods");


                            for (int i = 0; i<foods.length(); i++) {

                                ID_food = foods.getJSONObject(i);
                                strFoodID = ID_food.getString("id_food");
                                strFoodTypeID = ID_food.getString("id_typefood");
                                strNameFood = ID_food.getString("name_food");
                                strCookingMethod = ID_food.getString("cooking_method");
                                strImg = ID_food.getString("img");

                                // update data to ArrayList in recycler adapter
                                newsList.add(new EnterSearchMenu(strNameFood,strImg));

                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                recyclerAdapter.notifyDataSetChanged();

                super.onPostExecute(s);
            }

        }.execute();

//        JSONObtained.getInstance().newCall(JSONObtained.getRequest(myurl)).enqueue(new Callback() {

//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("eeeeee", e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                resultServer = response.body().string();
//
//                Log.d("xxxxxx", resultServer);
//
//                JSONObject foodJSONStr, ID_food = null;
//
//                try {
//                    foodJSONStr = new JSONObject(resultServer);
//                    JSONArray foods = foodJSONStr.getJSONArray("foods");
//
//
//                    for (int i = 0; i<foods.length(); i++) {
//
//                        ID_food = foods.getJSONObject(i);
//                        strFoodID = ID_food.getString("id_food");
//                        strFoodTypeID = ID_food.getString("id_typefood");
//                        strNameFood = ID_food.getString("name_food");
//                        strCookingMethod = ID_food.getString("cooking_method");
//                        strImg = ID_food.getString("img");
//
//                        newsList.add(new EnterSearchMenu(strNameFood));
//
//                    }
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            recyclerAdapter.notifyDataSetChanged();
//                        }
//                    });
//
//
//
//                    Log.d("JSONObjectArray", String.valueOf(ID_food));
//
////            strFoodID = c.getString("id_food");
////            strFoodName = c.getString("name_food");
////            /*strFoodPic = c.getString("FoodPic");*/
////            strError = c.getString("Error");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) parent.getChildAt(0)).setTextSize(25);

        String item = parent.getItemAtPosition(position).toString();

        Toast.makeText(EnterSearchActivity.this,item, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
//
//    @Override
//    public void recyclerViewListClicked(View v, int position) {
//
//    }
}

