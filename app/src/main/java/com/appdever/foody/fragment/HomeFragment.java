package com.appdever.foody.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.appdever.foody.R;
import com.appdever.foody.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arisak on 23/6/2559.
 */
public class HomeFragment extends Fragment {
    private static final String LOG_TAG = HomeFragment.class.getSimpleName();
    private ListView lv_who_see;
    private String pageName = "";

//    private String foodGenres;

    private RecyclerView rv;
    private RecyclerAdapter recyclerAdapter;
    List<DataTest01> newsList=new ArrayList<>();

    private ImageView homeFoodBanner;

    public HomeFragment() {
    }

    public Fragment setPageName(String pageName){
        this.pageName = pageName;
        return this;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Toast.makeText(getActivity(),"Width =" + width +",Height"+ height,
                Toast.LENGTH_SHORT).show();

        int namefac = 0;
        StaggeredGridLayoutManager aaa = new StaggeredGridLayoutManager(2,1);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_test01);
        rv.setLayoutManager(aaa);
        recyclerAdapter = new RecyclerAdapter(getActivity(), newsList);
        rv.setAdapter(recyclerAdapter);
//        rv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rv.setHasFixedSize(true);

        for (int i=0; i < 10; i++){
            newsList.add(new DataTest01(R.drawable.banner_02,"Test".concat(String.valueOf(i))));
        }

        recyclerAdapter.notifyDataSetChanged();
        Log.e("Kasira", String.valueOf(recyclerAdapter.getItemCount()));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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

        homeFoodBanner.setImageResource(R.drawable.bannertest);

    }
}
