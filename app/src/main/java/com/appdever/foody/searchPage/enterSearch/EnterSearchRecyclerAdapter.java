package com.appdever.foody.searchPage.enterSearch;

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
 * Created by landtanin on 7/11/2016 AD.
 */
public class EnterSearchRecyclerAdapter extends RecyclerView.Adapter<EnterSearchRecyclerAdapter.RecyclerViewHolder> {

    private List<EnterSearchMenu> newList;
    Context context;

    public EnterSearchRecyclerAdapter(Context context, List<EnterSearchMenu> newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_enter_search_row, parent, false);
        return new RecyclerViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        EnterSearchMenu enterSearchMenu = newList.get(position);
        holder.myMenu.setText(enterSearchMenu.getMyMenu());
        Glide.with(context).load(enterSearchMenu.getMenuImage()).placeholder(R.drawable.home).into(holder.menuImage);

    }

    @Override
    public int getItemCount() {
        return newList.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView myMenu;
        ImageView menuImage;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            myMenu = (TextView) itemView.findViewById(R.id.enter_search_row_txt);
            menuImage = (ImageView) itemView.findViewById(R.id.enter_search_row_img);
        }
    }
}
