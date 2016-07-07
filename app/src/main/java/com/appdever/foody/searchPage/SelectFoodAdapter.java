package com.appdever.foody.searchPage;

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
 * Created by arisak on 7/7/2559.
 */
public class SelectFoodAdapter extends RecyclerView.Adapter<SelectFoodAdapter.SelectFoodViewHolder> {

    private List<DataSelectFood> selectFoodList;
    Context context;

    public static class SelectFoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvSubTitle;
        ImageView imgTitle;


        SelectFoodViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSubTitle = (TextView) itemView.findViewById(R.id.tvSubTitle);
            imgTitle = (ImageView) itemView.findViewById(R.id.imgTitle);
        }

    }

    public SelectFoodAdapter(Context context, List<DataSelectFood> SelectFoodList) {
        this.selectFoodList = SelectFoodList;
        this.context = context;
    }

    @Override
    public SelectFoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_select_food_row, parent, false);


        return new SelectFoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectFoodViewHolder holder, int position) {


      DataSelectFood dataSelectFood = selectFoodList.get(position);
        holder.tvTitle.setText(dataSelectFood.getTvTitle());
        holder.tvSubTitle.setText(dataSelectFood.getTvSubTitle());
        Glide.with(context).load(dataSelectFood.getImgTitle()).placeholder(R.drawable.banner_02).error(R.drawable.banner_app).into(holder.imgTitle);


    }



    @Override
    public int getItemCount() {return selectFoodList.size();}
}

