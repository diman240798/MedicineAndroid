package com.nanicky.medclient.main.tasks;

import com.nanicky.medclient.base.BaseView;
import com.nanicky.medclient.main.Item;

public interface TaskView extends BaseView {
    void setItemsCount(int count);
    void onItemDissmissed(int position, Item item);
}
