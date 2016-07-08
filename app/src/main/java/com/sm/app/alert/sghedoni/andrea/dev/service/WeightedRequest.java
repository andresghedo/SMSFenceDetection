package com.sm.app.alert.sghedoni.andrea.dev.service;

/**
 * Created by andrea on 08/07/16.
 */
public class WeightedRequest {

    private int level;

    private long updateTimeMs;

    private int priority;

    public WeightedRequest() {}

    public WeightedRequest(int level, long updateTimeMs, int priority) {
        this.level = level;
        this.updateTimeMs = updateTimeMs;
        this.priority = priority;
    }

    /* SETTER */
    public void setLevel(int level) {
        this.level = level;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setUpdateTimeMs(long updateTimeMs) {
        this.updateTimeMs = updateTimeMs;
    }

    /* GETTER */
    public int getLevel() {
        return this.level;
    }

    public int getPriority() {
        return this.priority;
    }

    public long getUpdateTimeMs() {
        return this.updateTimeMs;
    }

}
