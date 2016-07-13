package com.appdever.foody.searchPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.appdever.foody.R;

import java.util.ArrayList;
import java.util.List;


public class selectFoodFragment extends Fragment {
    private RecyclerView rv;
    private com.appdever.foody.searchPage.SelectFoodAdapter selectFoodAdapter;
    List<DataSelectFood> selectFoodList =new ArrayList<>();

    public selectFoodFragment() {
        // Required empty public constructor
    }


    public static selectFoodFragment newInstance() {
        selectFoodFragment fragment = new selectFoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_select_food, container, false);

        LinearLayoutManager aaa = new LinearLayoutManager(getContext());
        rv = (RecyclerView) rootview.findViewById(R.id.rv_test01);
        rv.setLayoutManager(aaa);
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(10);
        selectFoodAdapter = new SelectFoodAdapter(getContext(), selectFoodList);
        rv.setAdapter(selectFoodAdapter);
//        rv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rv.setHasFixedSize(true);

        DataSelectFood selectBoil = new DataSelectFood(R.drawable.type_boil,"ต้ม",1);
        DataSelectFood selectStir = new DataSelectFood(R.drawable.type_stir,"ผัด",2);
        DataSelectFood selectCurry = new DataSelectFood(R.drawable.type_curry,"แกง",3);
        DataSelectFood selectFry = new DataSelectFood(R.drawable.type_fry,"ทอด",4);
        DataSelectFood selectSteam = new DataSelectFood(R.drawable.type_steam,"นึ่ง",5);
        DataSelectFood selectGrill = new DataSelectFood(R.drawable.type_grill,"ย่าง,",6);
        DataSelectFood selectSalad = new DataSelectFood(R.drawable.type_salad,"สลัด-ยำ",7);

        selectFoodList.add(selectBoil);
        selectFoodList.add(selectStir);
        selectFoodList.add(selectCurry);
        selectFoodList.add(selectFry);
        selectFoodList.add(selectSteam);
        selectFoodList.add(selectGrill);
        selectFoodList.add(selectSalad);

        selectFoodAdapter.notifyDataSetChanged();
        Log.e("Kasira", String.valueOf(selectFoodAdapter.getItemCount()));

        return rootview;
    }


}
