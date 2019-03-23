package com.nanicky.medclient.main;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.nanicky.medclient.R;
import com.nanicky.medclient.helper.ItemTouchHelperAdapter;
import com.nanicky.medclient.helper.OnStartDragListener;
import com.nanicky.medclient.main.mvp.MainPresenter;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> implements ItemTouchHelperAdapter {

    MainPresenter mainPresenter;

    private OnStartDragListener dragStartListener;

    public ItemAdapter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;

    }

    @Override
    public void onItemDismiss(final int position) {
        mainPresenter.onItemDissmissed(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        mainPresenter.changeItems(fromPosition, toPosition);
    }

    public void addItem(int position, Item item) {
        mainPresenter.onAddItem(item, position);
    }

    @Override
    public int getItemCount() {
        return mainPresenter.itemList.size();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int position) {

        final Item item = mainPresenter.itemList.get(position);
        itemViewHolder.tvItemName.setText(item.getItemName());
        itemViewHolder.relativeReorder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) ==
                        MotionEvent.ACTION_DOWN) {
                    dragStartListener.onStartDrag(itemViewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grocery_adapter, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    public void setOnItemDragListener(OnStartDragListener dragStartListener) {
        this.dragStartListener = dragStartListener;
    }
}
