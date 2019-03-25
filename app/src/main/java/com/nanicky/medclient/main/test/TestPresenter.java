package com.nanicky.medclient.main.test;

import com.nanicky.medclient.base.BasePresenter;
import com.nanicky.medclient.main.task.TaskView;

public class TestPresenter extends BasePresenter<TestView, TestModel> {

    public TestPresenter() {
        this.model = new TestModel(this);
    }

    @Override
    protected void restoreState() {

    }
}
