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
        selectFoodAdapter = new SelectFoodAdapter(getContext(), selectFoodList);
        rv.setAdapter(selectFoodAdapter);
//        rv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rv.setHasFixedSize(true);

        DataSelectFood selectFood = new DataSelectFood(R.drawable.bannertest,"1","1");
        DataSelectFood selectFood1 = new DataSelectFood(R.drawable.banner_02,"2","2");
        DataSelectFood selectFood3 = new DataSelectFood(R.drawable.bannertest,"3","3");
        selectFoodList.add(selectFood);
        selectFoodList.add(selectFood1);
        selectFoodList.add(selectFood3);


        selectFoodAdapter.notifyDataSetChanged();
        Log.e("Kasira", String.valueOf(selectFoodAdapter.getItemCount()));


        return rootview;
    }


}
