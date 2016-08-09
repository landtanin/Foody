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

    private List<HomeListItem> newList;
    Context context;
    homeListCarrier mHomeListCarrier;

    public RecyclerAdapter(Context context, List<HomeListItem> newsList, homeListCarrier mHomeListCarrier) {
        this.newList = newsList;
        this.context = context;
        this.mHomeListCarrier = mHomeListCarrier;
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_home_row, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        final HomeListItem homeListItem = newList.get(position);

        holder.homeNameFood.setText(" "+homeListItem.getHomeNameFood());
        Glide.with(context).load(homeListItem.getImgTest01()).into(holder.homeImgFood);

        holder.homeGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeListCarrier.homeOnClickListener(homeListItem);
            }
        });

//        newList.add(Glide.with(context).load("https://cdn3.artstation.com/p/assets/images/images/001/987/707/large/ilya-kuvshinov-cut2.jpg?1455606496").into(holder.homeImgFood;)
    }


    @Override
    public int getItemCount() {   return newList.size();}

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView homeNameFood;
        ImageView homeImgFood;
        View homeGridView;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            homeGridView = itemView;

            homeNameFood = (TextView) itemView.findViewById(R.id.homeNameFood);
            homeImgFood = (ImageView) itemView.findViewById(R.id.homeImgFood);
        }
    }

    public interface homeListCarrier {

        void homeOnClickListener(HomeListItem homeListItem);

    }


}

