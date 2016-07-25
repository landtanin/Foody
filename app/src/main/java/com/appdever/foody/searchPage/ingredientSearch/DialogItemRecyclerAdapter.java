package com.appdever.foody.searchPage.ingredientSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdever.foody.R;

import java.util.List;

/**
 * Created by landtanin on 7/22/16 AD.
 */
public class DialogItemRecyclerAdapter extends RecyclerView.Adapter<DialogItemRecyclerAdapter.RecyclerViewHolder> {

    Context context;
    private List<DialogItem> newList;
    dialogListCarrier mDialogListCarrier;

    DialogItemRecyclerAdapter(Context context, List<DialogItem> newList, dialogListCarrier mDialogListCarrier) {
        this.context = context;
        this.newList = newList;
        this.mDialogListCarrier = mDialogListCarrier;
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
    public void onBindViewHolder(final RecyclerViewHolder holder,final int position) {

        final DialogItem dialogItem = newList.get(position);

        holder.otherResourceTxt.setText(dialogItem.getIngName());
//        Glide.with(context).load(R.drawable.select).into(holder.checkImageImg);
        holder.dialogCheckBox.setChecked(dialogItem.getIngSelect());


        holder.dialogListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkChange(dialogItem);

                holder.dialogCheckBox.setChecked(!holder.dialogCheckBox.isChecked());
//                holder.dialogCheckBox.isChecked();
            }

        });

    }

    public void checkChange(DialogItem dialogItem){
        dialogItem.setIngSelect(!dialogItem.getIngSelect());
        mDialogListCarrier.dialogOnClickListener(dialogItem);

    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView otherResourceTxt;
        View dialogListView;
        public CheckBox dialogCheckBox;
        LinearLayout llContent;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            dialogListView = itemView;
            llContent = (LinearLayout) itemView.findViewById(R.id.llContent);
            dialogCheckBox = (CheckBox) itemView.findViewById(R.id.dialogCheckBox);
            otherResourceTxt = (TextView) itemView.findViewById(R.id.other_resource_row_txt);
        }
    }

    public interface dialogListCarrier {

        void dialogOnClickListener(DialogItem dialogItem);

    }

}
