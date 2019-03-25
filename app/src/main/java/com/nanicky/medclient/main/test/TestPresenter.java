package com.nanicky.medclient.main.test;

import com.nanicky.medclient.base.BasePresenter;
import com.nanicky.medclient.helper.OnStartDragListener;
import com.nanicky.medclient.main.Item;
import com.nanicky.medclient.main.ItemAdapter;
import com.nanicky.medclient.main.tasks.TaskView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TestPresenter extends BasePresenter<TaskView, TestModel> {

    public TestPresenter() {
        this.model = new TestModel(this);
    }

    @Override
    protected void restoreState() {

    }
}
