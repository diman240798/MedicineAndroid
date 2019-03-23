package com.nanicky.medclient.main.mvp;

import com.nanicky.medclient.base.BaseView;
import com.nanicky.medclient.main.Item;

public interface MainView extends BaseView {
    void setItemsCount(int count);
    void onItemDissmissed(int position, Item item);
}
