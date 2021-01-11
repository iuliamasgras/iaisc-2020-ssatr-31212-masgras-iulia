package model;

import java.util.LinkedHashMap;
import java.util.List;


public class PN {
    /*number of iterations*/
    private int iterationNumber;

    /*current place information*/
    private LinkedHashMap<String, Place> placesInformation;

    /*list of transitions*/
    private List<Transition> currentTransitionList;

    public int getIterationNumber() {
        return iterationNumber;
    }

    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    public LinkedHashMap<String, Place> getPlacesInformation() {
        return placesInformation;
    }

    public void setPlacesInformation(LinkedHashMap<String, Place> placesInformation) {
        this.placesInformation = placesInformation;
    }

    public List<Transition> getCurrentTransitionList() {
        return currentTransitionList;
    }

    public void setCurrentTransitionList(List<Transition> currentTransitionList) {
        this.currentTransitionList = currentTransitionList;
    }
}
