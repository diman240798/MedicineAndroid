package com.nanicky.medclient.main.mvp;

import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.nanicky.medclient.R;
import com.nanicky.medclient.base.BasePresenter;
import com.nanicky.medclient.helper.OnStartDragListener;
import com.nanicky.medclient.main.Item;
import com.nanicky.medclient.main.ItemAdapter;
import com.nanicky.medclient.main.Typefaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainPresenter extends BasePresenter<MainView, MainModel> {

    public final ItemAdapter itemAdapter;
    public List<Item> itemList = new ArrayList<>();

    public MainPresenter() {
        this.model = new MainModel(this);
        itemAdapter = new ItemAdapter(this);
        loadItems();
    }


    private void loadItems() {
        //Initial items
        for(int i=10;i>0;i--)
        {
            Item item = new Item();
            item.setItemName("item"+i);
            itemList.add(item);
        }
    }

    @Override
    protected void restoreState() {

    }

    public ItemAdapter getItemAdapter() {
        return itemAdapter;
    }

    public int getItemsSize() {
        return itemList.size();
    }

    public void setOnStartDragListener(OnStartDragListener dragListener) {
        itemAdapter.setOnItemDragListener(dragListener);
    }

    public void onItemDissmissed(int position) {
        final Item item =new Item();
        item.setItemName(itemList.get(position).getItemName());

        itemAdapter.notifyItemRemoved(position);
        itemList.remove(position);
        itemAdapter.notifyItemRangeChanged(0, itemList.size());

        view.onItemDissmissed(position, item);
    }

    public void onAddItem(Item item, int position) {
        itemList.add(position, item);
        itemAdapter.notifyItemInserted(position);
        view.setItemsCount(itemList.size());
    }

    public void changeItems(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }

        itemAdapter.notifyItemMoved(fromPosition, toPosition);

    }
}