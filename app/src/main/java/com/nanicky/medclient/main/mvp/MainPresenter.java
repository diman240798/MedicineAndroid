package com.nanicky.medclient.main.mvp;

import com.nanicky.medclient.base.BasePresenter;
import com.nanicky.medclient.main.Item;

public class MainPresenter extends BasePresenter<MianView, MainModel> {



    public MainPresenter() {
        this.model = new MainModel(this);
    }

    @Override
    protected void restoreState() {

    }

}
