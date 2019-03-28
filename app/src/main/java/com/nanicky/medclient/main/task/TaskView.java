package com.nanicky.medclient.main.task;

import com.nanicky.medclient.base.BaseView;
import com.nanicky.medclient.main.Item;

public interface TaskView extends BaseView {
    void setItemsCount(int count);
    void onItemDissmissed(int position, Item item);

    void notifyItemDissmissed(int position);

    void notifyItemInserted(int position);

    void notifyItemMoved(int fromPosition, int toPosition);
}
