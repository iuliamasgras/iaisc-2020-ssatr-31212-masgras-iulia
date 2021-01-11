package net.agten.heatersimulator.controller.dto;

public class SimulationParameters {
    private int nbSeconds = 300;
    private double targetTemperature = 240;

    private double environmentTemperature = 20d;

    private double heaterPower = 12d * 12d / 6.8d;
    private double heaterThermalMass = 1.0d;
    private double heaterHeatTransferCoeffient = 0.02d;

    private int thermistorLag = 5;
    private double thermistorR0 = 100000;
    private double thermistorT0 = 25;
    private double thermistorBeta = 4092;
    private double thermistorR2 = 4700;
    private double thermistorNoiseGain = 1;

    public int getNbSeconds() {
        return nbSeconds;
    }

    public void setNbSeconds(int nbSeconds) {
        this.nbSeconds = nbSeconds;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public double getEnvironmentTemperature() {
        return environmentTemperature;
    }

    public void setEnvironmentTemperature(double environmentTemperature) {
        this.environmentTemperature = environmentTemperature;
    }

    public double getHeaterPower() {
        return heaterPower;
    }

    public void setHeaterPower(double heaterPower) {
        this.heaterPower = heaterPower;
    }

    public double getHeaterThermalMass() {
        return heaterThermalMass;
    }

    public void setHeaterThermalMass(double heaterThermalMass) {
        this.heaterThermalMass = heaterThermalMass;
    }

    public double getHeaterHeatTransferCoeffient() {
        return heaterHeatTransferCoeffient;
    }

    public void setHeaterHeatTransferCoeffient(double heaterHeatTransferCoeffient) {
        this.heaterHeatTransferCoeffient = heaterHeatTransferCoeffient;
    }

    public int getThermistorLag() {
        return thermistorLag;
    }

    public void setThermistorLag(int thermistorLag) {
        this.thermistorLag = thermistorLag;
    }

    public double getThermistorR0() {
        return thermistorR0;
    }

    public void setThermistorR0(double thermistorR0) {
        this.thermistorR0 = thermistorR0;
    }

    public double getThermistorT0() {
        return thermistorT0;
    }

    public void setThermistorT0(double thermistorT0) {
        this.thermistorT0 = thermistorT0;
    }

    public double getThermistorBeta() {
        return thermistorBeta;
    }

    public void setThermistorBeta(double thermistorBeta) {
        this.thermistorBeta = thermistorBeta;
    }

    public double getThermistorR2() {
        return thermistorR2;
    }

    public void setThermistorR2(double thermistorR2) {
        this.thermistorR2 = thermistorR2;
    }

    public double getThermistorNoiseGain() {
        return thermistorNoiseGain;
    }

    public void setThermistorNoiseGain(double thermistorNoiseGain) {
        this.thermistorNoiseGain = thermistorNoiseGain;
    }
}
