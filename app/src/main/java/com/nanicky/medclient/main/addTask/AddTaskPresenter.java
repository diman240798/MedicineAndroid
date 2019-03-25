package com.nanicky.medclient.main.addTask;

import com.nanicky.medclient.base.BasePresenter;
import com.nanicky.medclient.helper.OnStartDragListener;
import com.nanicky.medclient.main.Item;
import com.nanicky.medclient.main.ItemAdapter;
import com.nanicky.medclient.main.tasks.TaskView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AddTaskPresenter extends BasePresenter<AddTaskView, AddTaskModel> {

    public Item restoreItem;


    public AddTaskPresenter() {
        this.model = new AddTaskModel(this);
    }

    @Override
    protected void restoreState() {

    }

}
