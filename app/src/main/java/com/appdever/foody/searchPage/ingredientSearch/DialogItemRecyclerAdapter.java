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
 * Created by landtanin on 7/22/16 AD.
 */
public class DialogItemRecyclerAdapter extends RecyclerView.Adapter<DialogItemRecyclerAdapter.RecyclerViewHolder> {

    Context context;
    private List<DialogItem> newList;

    DialogItemRecyclerAdapter(Context context, List<DialogItem> newList) {
        this.context = context;
        this.newList = newList;
    }

    @Override

    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = Layoutflater.from(parent.getContext())
//                .inflate(R.layout.recycler_widget, parent, false);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_other_resource_row, parent, false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        final DialogItem dialogItem = newList.get(position);

        holder.otherResourceTxt.setText(dialogItem.getIngName());

    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView otherResourceTxt;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            otherResourceTxt = (TextView) itemView.findViewById(R.id.other_resource_row_txt);
        }
    }
}
