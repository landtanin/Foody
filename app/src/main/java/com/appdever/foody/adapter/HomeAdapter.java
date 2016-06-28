//package com.appdever.foody.adapter;
//
//import android.app.Activity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//
//import com.appdever.foody.HomeMenuItem;
//import com.appdever.foody.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
///**
// * Created by landtanin on 6/21/2016 AD.
// */
//public class HomeAdapter extends ArrayAdapter<HomeMenuItem> {
//
//
//    public HomeAdapter(Activity context, List<HomeMenuItem> menuItems) {
//        super(context, 0, menuItems);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        HomeMenuItem menuItem = getItem(position);
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.home_menu_icon, parent, false);
//        }
//
//        ImageView homeMenuImage = (ImageView) convertView.findViewById(R.id.homeMenuImage);
//
//        Picasso.with(getContext()).load(String.valueOf(menuItem.image)).into(homeMenuImage);
//
//        return convertView;
//    }
//
//    @Override
//    public HomeMenuItem getItem(int position) {
//        return super.getItem(position);
//    }
//}
