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
import com.nanicky.medclient.main.task.TaskPresenter;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> implements ItemTouchHelperAdapter {

    private final TaskPresenter taskPresenter;

    private final OnStartDragListener dragStartListener;

    public ItemAdapter(TaskPresenter taskPresenter, OnStartDragListener dragStartListener) {
        this.taskPresenter = taskPresenter;

        this.dragStartListener = dragStartListener;
    }

    @Override
    public void onItemDismiss(final int position) {
        taskPresenter.onItemDissmissed(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        taskPresenter.changeItems(fromPosition, toPosition);
    }

    public void addItem(int position, Item item) {
        taskPresenter.onAddItem(item, position);
    }

    @Override
    public int getItemCount() {
        return taskPresenter.itemList.size();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int position) {

        final Item item = taskPresenter.itemList.get(position);
        itemViewHolder.tvItemName.setText(item.getName());
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

}
