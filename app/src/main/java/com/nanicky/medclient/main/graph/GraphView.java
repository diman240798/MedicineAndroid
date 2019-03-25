package com.nanicky.medclient.main.graph;

import com.nanicky.medclient.base.BaseView;
import com.nanicky.medclient.main.Item;

interface GraphView extends BaseView {
    void setItemsCount(int count);
    void onItemDissmissed(int position, Item item);
}
