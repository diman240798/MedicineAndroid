package com.nanicky.medclient.base;

public class BaseModel<P extends BasePresenter> {
    private final P presenter;

    public BaseModel(P presenter) {
        this.presenter = presenter;
    }
}
