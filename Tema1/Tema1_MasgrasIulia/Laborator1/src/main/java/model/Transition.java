package model;

import util.PNUtils;

import java.util.List;



public class Transition {
    /*transition name*/
    private String transitionName;

    /*min transition time*/
    private int transitionMinTime;

    /*max transition time*/
    private int transitionMaxTime;

    /*list of places that enter in transition*/
    private List<Arc> placesIn;

    /*list of places that out of transition*/
    private List<Arc> placesOut;

    /*it's a value between transitionMinTime and transitionMaxTime; By default it's -1 => which means that transition is not fired*/
    private int timeForExecute = -1;

    public Transition() {
        timeForExecute = PNUtils.generateRandomNumber(getTransitionMinTime(), getTransitionMaxTime());
    }

    public String getTransitionName() {
        return transitionName;
    }

    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }

    public int getTransitionMinTime() {
        return transitionMinTime;
    }

    public void setTransitionMinTime(int transitionMinTime) {
        this.transitionMinTime = transitionMinTime;
    }

    public int getTransitionMaxTime() {
        return transitionMaxTime;
    }

    public void setTransitionMaxTime(int transitionMaxTime) {
        this.transitionMaxTime = transitionMaxTime;
    }

    public List<Arc> getPlacesIn() {
        return placesIn;
    }

    public void setPlacesIn(List<Arc> placesIn) {
        this.placesIn = placesIn;
    }

    public List<Arc> getPlacesOut() {
        return placesOut;
    }

    public void setPlacesOut(List<Arc> placesOut) {
        this.placesOut = placesOut;
    }

    public int getTimeForExecute() {
        return timeForExecute;
    }

    public void setTimeForExecute(int timeForExecute) {
        this.timeForExecute = timeForExecute;
    }
}
