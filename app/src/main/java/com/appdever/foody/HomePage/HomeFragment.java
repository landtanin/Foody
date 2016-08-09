package com.appdever.foody.HomePage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.appdever.foody.FoodDetailActivity;
import com.appdever.foody.R;
import com.appdever.foody.manager.JSONObtained;
import com.appdever.foody.manager.KeyStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * Created by arisak on 23/6/2559.
 */
public class HomeFragment extends Fragment {
    private static final String LOG_TAG = HomeFragment.class.getSimpleName();

//    private String foodGenres;

    private RecyclerView rv;
    private RecyclerAdapter recyclerAdapter;
    List<HomeListItem> newsList=new ArrayList<>();

    private ImageView homeFoodBanner,homeFoodBanner2;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private Context mContext;
    private ViewFlipper mViewFlipper;
//    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());

    private String resultServer;

    private String homeFoodID, homeFoodTypeID, homeNameFood, homeCookingMethod,
            homeImgFood, homePrepareIngredient, homeFoodDescription;


    public HomeFragment() {
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void homeConnectDatabase() {

        final HttpUrl myurl = HttpUrl.parse(JSONObtained.getAbsoluteUrl("food.php")).newBuilder().build();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {

                    Response response = JSONObtained.getInstance().newCall(JSONObtained.getRequest(myurl)).execute();

                    if (response.isSuccessful()) {

                        resultServer = response.body().string();

//                        Log.d("HOMESERVERCONNECT", resultServer);

                        JSONObject foodJSONStr, ID_food = null;

                        try {
                            foodJSONStr = new JSONObject(resultServer);
                            JSONArray foods = foodJSONStr.getJSONArray("foods");


                            for (int i = 0; i<foods.length(); i++) {

                                ID_food = foods.getJSONObject(i);
                                homeFoodID = ID_food.getString("id_food");
                                homeFoodTypeID = ID_food.getString("id_typefood");
                                homeNameFood = ID_food.getString("name_food");
                                homeCookingMethod = ID_food.getString("cooking_method");
                                homeImgFood = ID_food.getString("img");
                                homePrepareIngredient = ID_food.getString("prepare_ingredient");
                                homeFoodDescription = ID_food.getString("description");

                                // update data to ArrayList in recycler adapter
                                newsList.add(new HomeListItem(homeFoodID, homeFoodTypeID, homeNameFood, homeCookingMethod,
                                        homeImgFood, homePrepareIngredient, homeFoodDescription));

                            }

                        } catch (JSONException e) {
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //-----------------------------------------------------------------------

        mContext = getContext();
        mViewFlipper = (ViewFlipper) rootView.findViewById(R.id.view_flipper);
//        mViewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
//        mViewFlipper.setOutAnimation(getContext(),R.animator.my_duration);

//        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(final View view, final MotionEvent event) {
//                detector.onTouchEvent(event);
//                return true;
//            }
//        });

        //-----------------------------------------------------------------------


        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

//        Toast start
//        int width = size.x;
//        int height = size.y;
//
//        Toast.makeText(getActivity(),"Width =" + width +",Height"+ height,
//                Toast.LENGTH_SHORT).show();
//       Toast end

        StaggeredGridLayoutManager aaa = new StaggeredGridLayoutManager(2,1);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_test01);
        rv.setLayoutManager(aaa);
        recyclerAdapter = new RecyclerAdapter(getActivity(), newsList, new RecyclerAdapter.homeListCarrier() {
            @Override
            public void homeOnClickListener(HomeListItem homeListItem) {


                Intent objIntent = new Intent(getActivity(), FoodDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString(KeyStore.FOODID_DETAIL_SEND_KEY,homeListItem.getHomeFoodId());
                extras.putString(KeyStore.FOODTYOEID_DETAIL_SEND_KEY,homeListItem.getHomeFoodTypeId());
                extras.putString(KeyStore.NAMEFOOD_DETAIL_SEND_KEY,homeListItem.getHomeNameFood());
                extras.putString(KeyStore.FOODMETHOD_DETAIL_SEND_KEY,homeListItem.getHomeCookingMethod());
                extras.putString(KeyStore.FOODIMG_DETAIL_SEND_KEY,homeListItem.getImgTest01());
                extras.putString(KeyStore.FOOD_INGREDIENT_SEND_KEY,homeListItem.getHomePrepareIngredient());
                extras.putString(KeyStore.FOOD_DESCRIPTION_SEND_KEY,homeListItem.getHomeFoodDescribtion());
                objIntent.putExtras(extras);
                startActivity(objIntent);

            }
        });
        rv.setAdapter(recyclerAdapter);
//        rv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rv.setHasFixedSize(true);

//        for (int i=0; i < 10; i++){
//            newsList.add(new HomeListItem(R.drawable.banner_02,"Test".concat(String.valueOf(i))));
//        }

//        recyclerAdapter.notifyDataSetChanged();
//        Log.e("Kasira", String.valueOf(recyclerAdapter.getItemCount()));
        homeConnectDatabase();


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        homeFoodBanner = (ImageView) getView().findViewById(R.id.homeFoodBanner);
        homeFoodBanner2 = (ImageView) getView().findViewById(R.id.homeFoodBanner2);
        homeFoodBanner.setImageResource(R.drawable.bannertest);
        homeFoodBanner2.setImageResource(R.drawable.m2);

    }

}
