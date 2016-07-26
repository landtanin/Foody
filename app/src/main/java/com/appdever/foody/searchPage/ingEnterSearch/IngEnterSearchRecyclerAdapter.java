package com.appdever.foody.searchPage.ingEnterSearch;

import android.app.Activity;
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
 * Created by landtanin on 7/26/16 AD.
 */
public class IngEnterSearchRecyclerAdapter extends RecyclerView.Adapter<IngEnterSearchRecyclerAdapter.RecyclerViewHolder> {

    private List<IngEnterSearchMenu> newList;
    Context context;
    ingDataCarrier mIngDataCarrier;

    public IngEnterSearchRecyclerAdapter(Activity context, List<IngEnterSearchMenu> newList, ingDataCarrier mIngDataCarrier) {

        this.context = context;
        this.newList = newList;
        this.mIngDataCarrier = mIngDataCarrier;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_enter_search_row, parent, false);
        return new RecyclerViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        final IngEnterSearchMenu ingEnterSearchMenu = newList.get(position);

        holder.ingMyMenu.setText(ingEnterSearchMenu.getNameFood());
        Glide.with(context).load(ingEnterSearchMenu.getImg()).placeholder(R.drawable.home).into(holder.ingMenuImage);
        // TODO: change placeholder picture

        holder.ingEnterSearchListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIngDataCarrier.ingMySetOnClickListener(ingEnterSearchMenu);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView ingMyMenu;
        ImageView ingMenuImage;
        View ingEnterSearchListView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            ingEnterSearchListView = itemView;

            ingMyMenu = (TextView) itemView.findViewById(R.id.enter_search_row_txt);
            ingMenuImage = (ImageView) itemView.findViewById(R.id.enter_search_row_img);

        }
    }

    public interface ingDataCarrier {

        void ingMySetOnClickListener(IngEnterSearchMenu ingEnterSearchMenu);

    }

}
