package com.example.test2project.plans;

import java.io.Serializable;

public class PlanBean implements Serializable {
    private boolean isFinished;
    private String title;
    private String info;

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
