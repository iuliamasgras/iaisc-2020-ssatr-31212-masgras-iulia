package net.agten.heatersimulator.domain;

/**
 * This class represents the environment around the heater. The environment has
 * a temperature, which is assumed to remain constant.
 * 
 * @author Pieter Agten
 *
 */
public class Environment {
	/**
	 * This environment's temperature in degrees Kelvin.
	 */
	private final double temperature;
	
	/**
	 * Constructs a new environment, with a given temperature.
	 * 
	 * @param temperature	the environment's constant temperature in degrees 
	 * 						Kelvin
	 */
	public Environment(double temperature) {
		this.temperature = temperature;
	}
	
	/**
	 * Returns this environment's temperature.
	 * 
	 * @return this environment's temperature
	 */
	public double getTemperature() {
		return this.temperature;
	}
}
