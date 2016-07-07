package com.appdever.foody.HomePage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdever.foody.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by landtanin on 7/4/2016 AD.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private List<DataTest01> newList;
    Context context;

    public RecyclerAdapter(Context context, List<DataTest01> newsList) {
        this.newList = newsList;
        this.context = context;
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_home_row, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        DataTest01 dataTest01 = newList.get(position);
        holder.tvTest01.setText(dataTest01.getTvTest01());
        Glide.with(context).load(dataTest01.getImgTest01()).into(holder.imgTest01);

//        newList.add(Glide.with(context).load("https://cdn3.artstation.com/p/assets/images/images/001/987/707/large/ilya-kuvshinov-cut2.jpg?1455606496").into(holder.imgTest01;)
    }


    @Override
    public int getItemCount() {   return newList.size();}

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvTest01;
        ImageView imgTest01;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvTest01 = (TextView) itemView.findViewById(R.id.tvTest01);
            imgTest01 = (ImageView) itemView.findViewById(R.id.imgTest01);
        }
    }



}

