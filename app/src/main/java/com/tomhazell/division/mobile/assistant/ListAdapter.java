package com.tomhazell.division.mobile.assistant;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tomhazell.division.battleassistant.R;

/**
 * Created by Tom Hazell on 18/03/2016.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ListItem[] mDataset;
    private int layout;
    private OnRecyclerClick listener;
    private Context context;

    public interface OnRecyclerClick {
        void onItemClick(ListItem item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Title;
        public TextView Discrip;
        public ImageView Image;

        public RelativeLayout layout;
        public ViewHolder(View v) {
            super(v);
            Title = (TextView) v.findViewById(R.id.ListTitle);
            Discrip = (TextView) v.findViewById(R.id.listDiscrip);
            Image = (ImageView) v.findViewById(R.id.listImage);
            layout = (RelativeLayout) v.findViewById(R.id.layoutRelClick);
        }

        public void bind(final ListItem item, final OnRecyclerClick listener) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public ListAdapter(ListItem[] myDataset, int Layout, Context context, OnRecyclerClick listner) {
        mDataset = myDataset;
        layout = Layout;
        this.listener = listner;
        this.context = context;
    }

    // Create new view
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get element from your dataset at this position
        // replace the contents of the view with that element

        ListItem item = mDataset[position];
        holder.Title.setText(item.Title);
        holder.Discrip.setText(item.Discription);
        holder.Image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), item.Icon));
        holder.itemView.setTag(item);
        holder.bind(mDataset[position], listener);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}