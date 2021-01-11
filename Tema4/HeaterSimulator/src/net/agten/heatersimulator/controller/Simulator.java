package net.agten.heatersimulator.controller;

import net.agten.heatersimulator.controller.dto.*;
import net.agten.heatersimulator.domain.Environment;
import net.agten.heatersimulator.domain.Heater;
import net.agten.heatersimulator.domain.Thermistor;
import net.agten.heatersimulator.domain.algorithm.*;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 * This class is a facade for the heater simulation.
 *
 * @author Pieter Agten
 */
public class Simulator {

    public XYDataset runSimulation(SimulationParameters params, CAAPID16Parameters pidParams) {
        ControllerAlgorithm pid = new CAAPID16(
                pidParams.getpGain(),
                pidParams.getiGain(),
                pidParams.getdGain(),
                pidParams.getOutputDivisor());
        return runSimulation(params, pid);
    }

    public XYDataset runSimulation(SimulationParameters params, CAAPID32Parameters pidParams) {
        ControllerAlgorithm pid = new CAAPID32(
                pidParams.getpGain(),
                pidParams.getiGain(),
                pidParams.getdGain(),
                pidParams.getOutputDivisor());
        return runSimulation(params, pid);
    }

    public XYDataset runSimulation(SimulationParameters params, FuzzyParameters pidParams) {
        ControllerAlgorithm pid = new FuzzyCtrl(
                pidParams.getpGain(),
                pidParams.getiGain(),
                pidParams.getdGain(),
                pidParams.getOutputDivisor());
        return runSimulation(params, pid);
    }

    public XYDataset runSimulation(SimulationParameters params, PID32Parameters pidParams) {
        ControllerAlgorithm pid = new PID32(
                pidParams.getpGain(),
                pidParams.getiGain(),
                pidParams.getdGain(),
                pidParams.getIntegralWindupGuard(),
                pidParams.getOutputDivisor());
        return runSimulation(params, pid);
    }

    public XYDataset runSimulation(SimulationParameters params, ControllerAlgorithm controller) {
        Environment environment = new Environment(
                celsiusToKelvin(params.getEnvironmentTemperature()));
        Heater heater = new Heater(
                params.getHeaterPower(),
                params.getHeaterThermalMass(),
                params.getHeaterHeatTransferCoeffient(),
                environment);
        Thermistor thermistor = new Thermistor(
                heater,
                params.getThermistorLag(),
                params.getThermistorR0(),
                celsiusToKelvin(params.getThermistorT0()),
                params.getThermistorBeta(),
                params.getThermistorR2(),
                params.getThermistorNoiseGain());

        double targetTemp = celsiusToKelvin(params.getTargetTemperature());
        controller.setTargetAdc(thermistor.temperatureToAdc(targetTemp));

        final XYSeries g = new XYSeries("Goal");
        final XYSeries t = new XYSeries("Temperature");
        final XYSeries p = new XYSeries("Power");

        int step = 0;
        while (step < params.getNbSeconds()) {
            g.add(step, params.getTargetTemperature());
            t.add(step, kelvinToCelcius(heater.getTemperature()));
            double power = ((double) controller.nextValue(thermistor.getAdcValue())) / PID32.MAX_RESULT;
            p.add(step, power * 100);
            heater.setPower(power);
            thermistor.tickSecond();
            step += 1;
        }


        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(g);
        dataset.addSeries(t);
        dataset.addSeries(p);
        return dataset;
    }

    private double celsiusToKelvin(double t) {
        return t + 273.15d;
    }

    private double kelvinToCelcius(double t) {
        return t - 273.15d;
    }
}
