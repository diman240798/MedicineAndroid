package com.nanicky.medclient.main.mvp;

import com.nanicky.medclient.base.BasePresenter;
import com.nanicky.medclient.main.addTask.AddTaskPresenter;
import com.nanicky.medclient.main.graph.GraphPresenter;
import com.nanicky.medclient.main.task.TaskPresenter;
import com.nanicky.medclient.main.test.TestPresenter;

public class MainPresenter extends BasePresenter<MainView, MainModel> {
    private final TaskPresenter taskPresenter;
    private final GraphPresenter graphPresenter;
    private final AddTaskPresenter addAaskPresenter;
    private final TestPresenter testPresenter;
    public int currentFragmentNumber = 1;


    public MainPresenter(TaskPresenter taskPresenter, GraphPresenter graphPresenter, AddTaskPresenter addAaskPresenter, TestPresenter testPresenter) {
        this.taskPresenter = taskPresenter;
        this.graphPresenter = graphPresenter;
        this.addAaskPresenter = addAaskPresenter;
        this.testPresenter = testPresenter;
        this.model = new MainModel(this);
    }

    @Override
    protected void restoreState() {

    }

    public TaskPresenter getTaskPresenter() {
        return taskPresenter;
    }

    public AddTaskPresenter getAddAaskPresenter() {
        return addAaskPresenter;
    }

    public GraphPresenter getGraphPresenter() {
        return graphPresenter;
    }

    public TestPresenter getTestPresenter() {
        return testPresenter;
    }
}
