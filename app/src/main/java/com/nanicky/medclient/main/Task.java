package com.nanicky.medclient.main;


public class Task {

    private String name;
    private String description;
    private int attentionLevel;

    public Task(String name, String description, int attentionLevel) {
        this.name = name;
        this.description = description;
        this.attentionLevel = attentionLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAttentionLevel() {
        return attentionLevel;
    }

    public void setAttentionLevel(int attentionLevel) {
        this.attentionLevel = attentionLevel;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}