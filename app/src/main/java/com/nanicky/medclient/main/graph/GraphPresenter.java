package com.nanicky.medclient.main.graph;

import com.nanicky.medclient.base.BasePresenter;

public class GraphPresenter extends BasePresenter<GraphView, GraphModel> {


    public GraphPresenter() {
        this.model = new GraphModel(this);
    }

    @Override
    protected void restoreState() {

    }

}
