package net.agten.heatersimulator;

import net.agten.heatersimulator.gui.GraphWindow;

public class Main {
    public final static String NAME = "PID Controller Simulator";
    public final static String VERSION = "1.0";
    public final static String AUTHOR = "Pieter Agten";
    public final static String AUTHOR_EMAIL = "pieter.agten@gmail.com";

    public static void main(String[] args) throws InterruptedException {
        GraphWindow window = new GraphWindow();
        window.show();
    }
}
