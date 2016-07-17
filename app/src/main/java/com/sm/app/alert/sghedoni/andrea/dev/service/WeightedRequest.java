package com.sm.app.alert.sghedoni.andrea.dev.service;

/**
 *  Class that provides attribute of a Location Request.
 *  These attribute are found by "Better Approach" evaluation.
 *  @author Andrea Sghedoni
 */
public class WeightedRequest {

    /* eval level 5(max),4,3,2,1,0(min) */
    private int level;

    /* time in ms between location updates */
    private long updateTimeMs;

    /* accuracy of location */
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
