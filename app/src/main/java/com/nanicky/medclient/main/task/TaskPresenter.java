package com.nanicky.medclient.main.task;

import com.nanicky.medclient.base.BasePresenter;
import com.nanicky.medclient.main.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskPresenter extends BasePresenter<TaskView, TaskModel> {

    public List<Task> itemList = new ArrayList<>();


    public TaskPresenter() {
        this.model = new TaskModel(this);
        loadItems();
    }


    private void loadItems() {
        //Initial items
        for(int i=100;i>0;i--)
        {
            Task item = new Task("Feed Cat " + i, "desc", 20);
            itemList.add(item);
        }
    }

    @Override
    protected void restoreState() {

    }

    public int getItemsSize() {
        return itemList.size();
    }



    public void onItemDissmissed(int position) {
        final Task item = itemList.get(position);
        itemList.remove(position);
        view.notifyItemDissmissed(position);
        view.onItemDissmissed(position, item);
    }

    public void onAddItem(Task item, int position) {
        itemList.add(position, item);
        view.notifyItemInserted(position);
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
        view.notifyItemMoved(fromPosition, toPosition);
    }
}
