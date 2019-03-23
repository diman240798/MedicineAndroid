package com.nanicky.medclient.main;


public class Item {

    private String name;
    private String description;
    private int progress;

    public Item(String name, String description, int progress) {
        this.name = name;
        this.description = description;
        this.progress = progress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}