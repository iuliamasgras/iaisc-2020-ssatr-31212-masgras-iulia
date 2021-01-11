package service;

import model.Arc;
import model.PN;
import model.Place;
import model.Transition;
import util.PNWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PNService {
    private PN pn;
    private String outFileName;
    private PNWriter PNWriter;

    public PNService(PN pn, String outFileName) {
        this.pn = pn;
        this.outFileName = outFileName;
        this.PNWriter = new PNWriter(this.outFileName);
    }

    /**
     * Execute simulation
     */
    public void startSimulation() {
        /*check if network is halted or not*/
        boolean isHalted = isNetworkHalted();

        System.out.println("Start simulation");

        if (pn.getIterationNumber() == -1) {
            /*run until petri network is halted*/
            while (!isHalted) {
                executeOperation();

                /*check is network is halted or not*/
                isHalted = isNetworkHalted();
            }
        } else {
            for (int i = 0; i < pn.getIterationNumber() && !isHalted; i++) {
                executeOperation();

                /*check is network is halted or not*/
                isHalted = isNetworkHalted();
            }
        }
        System.out.println("Pentri Network halted");
    }

    /**
     * Check if a transition from petriNetwork is enabled or not
     *
     * @param transition - transition
     * @return
     */
    private boolean isTransitionEnabled(Transition transition) {
        for (Arc arc : transition.getPlacesIn()) {
            if (pn.getPlacesInformation().get(arc.getFrom()).getPlaceTokenNumber() < arc.getCapacity()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Return list of current enabled transition from petri network
     *
     * @return
     */
    private List<Transition> getEnabledTransitions() {
        List<Transition> enabledTransitionList = null;

        for (Transition transition : pn.getCurrentTransitionList()) {
            if (isTransitionEnabled(transition)) {
                /*we have a transition enabled*/
                if (enabledTransitionList == null) {
                    enabledTransitionList = new ArrayList<>();
                }

                enabledTransitionList.add(transition);
            }
        }

        return enabledTransitionList;
    }

    /**
     * Check if petriNetwork is halted or not
     *
     * @return
     */
    private boolean isNetworkHalted() {
        /*get enabled transition list*/
        List<Transition> enabledTransitionList = getEnabledTransitions();

        return ((enabledTransitionList == null) || (enabledTransitionList.isEmpty()));
    }

    /**
     * Pick one transition from a list of transitions
     *
     * @param transitions
     * @return
     */
    private Transition pickRandomTransition(List<Transition> transitions) {
        Random random = new Random();

        return transitions.get(random.nextInt(transitions.size()));
    }

    /**
     * Eliminate tokens from in places for a certain transition
     *
     * @param transition
     */
    private void eliminateTokensFromPlaceIn(Transition transition) {
        Place place;
        for (Arc arc : transition.getPlacesIn()) {
            place = pn.getPlacesInformation().get(arc.getFrom());
            place.setPlaceTokenNumber(place.getPlaceTokenNumber() - arc.getCapacity());
        }
    }

    /**
     * Put tokens from in places to out places for a certain transition
     *
     * @param transition
     */
    private void putTokensInPlaceOut(Transition transition) {
        Place place;
        for (Arc arc : transition.getPlacesOut()) {
            place = pn.getPlacesInformation().get(arc.getTo());
            place.setPlaceTokenNumber(place.getPlaceTokenNumber() + arc.getCapacity());
        }
    }

    /**
     * Fire one transition
     *
     * @param transition
     */
    private void fire(Transition transition) {
        eliminateTokensFromPlaceIn(transition);
        putTokensInPlaceOut(transition);
    }

    /**
     * Print every placeName and it's token value
     */
    private String getPlacesState() {
        final String[] currentState = {""};

        pn.getPlacesInformation().forEach((placeName, place) -> {
            System.out.print(placeName + ": " + place.getPlaceTokenNumber() + "\t");
            currentState[0] += placeName + ": " + place.getPlaceTokenNumber() + "\t";
        });
        System.out.println();

        return currentState[0];
    }

    /**
     * Execute Operation(for Petri Network without temporal conditions)
     */
    private void executeOperation() {
        /*get random transition*/
        Transition transition = pickRandomTransition(getEnabledTransitions());

        /*fire that transition*/
        fire(transition);

        /*print current info*/
        PNWriter.writeToFile(getPlacesState());
    }
}
