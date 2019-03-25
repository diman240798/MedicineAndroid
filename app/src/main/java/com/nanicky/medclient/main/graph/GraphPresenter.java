package com.nanicky.medclient.main.graph;

import com.nanicky.medclient.base.BasePresenter;
import com.nanicky.medclient.helper.OnStartDragListener;
import com.nanicky.medclient.main.Item;
import com.nanicky.medclient.main.ItemAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphPresenter extends BasePresenter<GraphView, GraphModel> {


    public GraphPresenter() {
        this.model = new GraphModel(this);
    }

    @Override
    protected void restoreState() {

    }

}
