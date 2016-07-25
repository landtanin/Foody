package com.appdever.foody.searchPage.ingredientSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appdever.foody.R;

import java.util.List;

/**
 * Created by landtanin on 7/25/16 AD.
 */
public class TotalItemRecyclerAdapter extends RecyclerView.Adapter<TotalItemRecyclerAdapter.RecyclerViewHolder>{

    Context context;
    private List<TotalItem> newList;

    TotalItemRecyclerAdapter(Context context, List<TotalItem> newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_total_resource_row, parent, false);

        return new RecyclerViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        final TotalItem totalItem = newList.get(position);
        holder.totalResourceTxt.setText(totalItem.getTotalFoodName());

    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView totalResourceTxt;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            totalResourceTxt = (TextView) itemView.findViewById(R.id.total_resource_row_txt);
        }
    }
}
