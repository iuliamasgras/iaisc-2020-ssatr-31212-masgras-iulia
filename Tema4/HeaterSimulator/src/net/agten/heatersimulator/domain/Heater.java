package net.agten.heatersimulator.domain;


/**
 * This class represents the heater.
 * 
 * @author Pieter Agten
 *
 */
public class Heater {

	/**
	 * Maximum heater power in watt.
	 */
	private final double power;
	
	/**
	 * Thermal mass in J/K.
	 */
	private final double thermalMass;
	
	/**
	 * Heat transfer coefficient determining the amount of heat dissipation to
	 * the environment.
	 */
	private final double heatTransferCoeffient;
	
	/**
	 * The environment the heater is located in.
	 */
	private final Environment environment;
	
	
	/**
	 * The current temperature of the heater.
	 */
	private double temperature;
	
	/**
	 * The current power percentage of the heater.
	 */
	private double powerPercentage;
	
	
	/**
	 * Constructs a new heater instance, with a given maximum power, thermal 
	 * mass, heat transfer coefficient, within a given environment.
	 *  
	 * @param power			the maximum power of the heater in watt
	 * @param thermalMass	the thermal mass of the heater in joule/kelvin
	 * @param heatTransferCoeffient	the heat transfer coefficient determining 
	 * 						the amount of heat dissipation to the environment 
	 * 						in W/(m²K)
	 * @param environment	the environment the heater is located in
	 */
	public Heater(double power, double thermalMass, 
			double heatTransferCoeffient, Environment environment) {
		// Heat-up from the heating element
		this.power = power;
		this.thermalMass = thermalMass;
		
		// Dissipation to the environment
		this.heatTransferCoeffient = heatTransferCoeffient;
		this.environment = environment;
		
		reset();
	}
	

	/**
	 * Advances time by one second and updates this heater's temperature 
	 * accordingly.
	 */
	public void tickSecond() {
		final double origTemperature = this.temperature;
		// Heatup from heating element:
		this.temperature += power*powerPercentage/thermalMass;
		
		// Dissipation to the environment:
		this.temperature += heatTransferCoeffient * 
				(environment.getTemperature() - origTemperature);
	}
	
	
	/**
	 * Resets this heater to its initial state. The temperature is set to the
	 * environment temperature and the power is set to zero. 
	 */
	public void reset() {
		this.temperature = this.environment.getTemperature();
		this.powerPercentage = 0;
	}
	
	
	/**
	 * Sets the percentage of this heater's maximum power to use during the 
	 * next tick.
	 *  
	 * @param powerPercentage	the percentage of this heater's maximum power 
	 * 							to use during the next tick
	 * @throws IllegalArgumentException	thrown if the specified power 
	 * 					percentage is less than 0 or greater than 1 
	 *  
	 */
	public void setPower(double powerPercentage) {
		if (powerPercentage < 0) {
			throw new IllegalArgumentException("powerPercentage cannot be " +
					"less than 0");
		}
		if (powerPercentage > 1) {
			throw new IllegalArgumentException("powerPercentage cannot be " +
					"greater than 1");
		}
		this.powerPercentage = powerPercentage;
	}
	
	/**
	 * Returns the current temperature of this heater in degrees Kelvin.
	 * 
	 * @return the current temperature of this heater in degrees Kelvin
	 */
	public double getTemperature() {
		return this.temperature;
	}

	/**
	 * Returns the environment of this heater.
	 * 
	 * @return the environment of this heater
	 */
	public Environment getEnvironment() {
		return environment;
	}
	
}
