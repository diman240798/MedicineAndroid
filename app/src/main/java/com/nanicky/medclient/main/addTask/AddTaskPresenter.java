package com.nanicky.medclient.main.addTask;

import com.nanicky.medclient.base.BasePresenter;

public class AddTaskPresenter extends BasePresenter<AddTaskView, AddTaskModel> {

    public AddTaskPresenter() {
        this.model = new AddTaskModel(this);
    }

    @Override
    protected void restoreState() {

    }

}
