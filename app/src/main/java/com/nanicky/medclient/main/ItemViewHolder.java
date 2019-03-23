package com.nanicky.medclient.main;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nanicky.medclient.R;
import com.nanicky.medclient.helper.ItemTouchHelperViewHolder;

public class ItemViewHolder extends RecyclerView.ViewHolder implements
        ItemTouchHelperViewHolder, View.OnClickListener {

    protected RelativeLayout container;
    protected TextView tvItemName;
    protected ImageView ivReorder;
    protected RelativeLayout relativeReorder;


    public ItemViewHolder(final View v) {
        super(v);
        container = (RelativeLayout) v.findViewById(R.id.container);
        tvItemName = (TextView) v.findViewById(R.id.tvItemName);
        ivReorder = (ImageView) v.findViewById(R.id.ivReorder);
        relativeReorder = (RelativeLayout) v.findViewById(R.id.relativeReorder);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onItemSelected(Context context) {
        container.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        tvItemName.setTextColor(ContextCompat.getColor(context, R.color.white));
        ivReorder.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onItemClear(Context context) {
        container.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        ivReorder.setColorFilter(ContextCompat.getColor(context, R.color.textlight), PorterDuff.Mode.SRC_IN);
        tvItemName.setTextColor(ContextCompat.getColor(context, R.color.textlight));
    }

}