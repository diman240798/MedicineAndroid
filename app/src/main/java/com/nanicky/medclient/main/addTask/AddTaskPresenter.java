package com.nanicky.medclient.main.addTask;

import com.nanicky.medclient.base.BasePresenter;
import com.nanicky.medclient.main.Item;

public class AddTaskPresenter extends BasePresenter<AddTaskView, AddTaskModel> {

    public Item restoreItem;


    public AddTaskPresenter() {
        this.model = new AddTaskModel(this);
    }

    @Override
    protected void restoreState() {

    }

}
