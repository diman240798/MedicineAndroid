package com.nanicky.medclient.main.test;

import com.nanicky.medclient.base.BaseView;
import com.nanicky.medclient.main.Item;

interface TestView extends BaseView {
    void setItemsCount(int count);
    void onItemDissmissed(int position, Item item);
}
