package com.nanicky.medclient.base;

public abstract class BasePresenter<V extends BaseView, M extends BaseModel> {
    protected M model;
    protected V view;

    public void attachView(V view) {
        this.view = view;
        restoreState();
    }

    protected abstract void restoreState();

    public void detachView() {
        this.view = null;
    }
}
