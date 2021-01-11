package net.agten.heatersimulator.domain;

import java.util.Random;

/**
 * This class represents the following circuit, measuring a heater's
 * temperature using an NTC thermistor:
 * 
 *    Vref_________
 *               _|_
 *              |   |
 *              |R2 |
 *              |___|
 *                |______________V (to ADC)
 *               _|_     |
 *              |the|   _|_
 *              |rmi|   ___C1
 *              |sto|    |
 *              |_r_|    |
 *   GND__________|______|
 *   
 *  The thermistor is specified by its beta value and has a lag of a specified
 *  number of seconds. The circuit's output is assumed to be measured by a 
 *  10-bit ADC.
 *  
 * @author Pieter Agten
 *
 */
public class Thermistor {
	/**
	 * The heater this thermistor is attached to.
	 */
	private final Heater heater;

	/**
	 * An circular buffer of previous heater temperatures, to implement the lag.
	 */
	private final double heaterTemp[];
	private int bufLast;

	/**
	 * The thermistor's beta value.
	 */
	private final double beta;
	
	/**
	 * The value of resistor 2 in ohms.
	 */
	private final double r2;
	
	/**
	 * The thermistor circuit's k-value.
	 */
	private final double k;

	/**
	 * The random number generator for the noise simulation.
	 */
	private final Random random;
	
	/**
	 * The gain multiplier for the random noise.
	 */
	private final double noiseGain;
	
	
	
	/**
	 * Constructs a new thermistor instance with a given heater, lag, null-
	 * resistance, null-temperature, beta value and r2-value.
	 * 
	 * @param heater	the heater this thermistor is attached to
	 * @param lag		the lag between the heater's actual temperature and the
	 * 					temperature measured by this thermistor
	 * @param r0		the thermistor's resistance at temperature t0
	 * @param t0		the temperature at which the thermistor has resistance 
	 * 					r0, in degrees Kelvin
	 * @param beta		the thermistor's beta value
	 * @param r2		the value of resistor r2 in the thermistor circuit in 
	 * 					ohms 
	 * @param noiseGain	the gain multiplier for the random noise added to the
	 * 					thermistor readings
	 */
	public Thermistor(Heater heater, int lag, double r0, double t0,
			double beta, double r2, double noiseGain) {
		this.heater = heater;
		this.heaterTemp = new double[lag];
		this.beta = beta;
		this.r2 = r2;
		this.random = new Random(0);
		this.noiseGain = noiseGain;
		this.k = r0 * Math.exp(-beta/t0);

		for (int i = 0; i < lag; ++i) {
			heaterTemp[i] = heater.getEnvironment().getTemperature();
		}
		this.bufLast = 0;
		pushTemperature(heater.getTemperature());
	}

	/**
	 * Returns the temperature measured by this thermistor in degrees Kelvin.
	 * 
	 * @return	the temperature measured by this thermistor in degrees Kelvin
	 */
	public double getTemperature() {
		double noise = (random.nextDouble()*2-1)*noiseGain;
		if (heaterTemp.length > 0) {
			return heaterTemp[bufLast] + noise;
		} else {
			return heater.getTemperature() + noise;
		}
	}

	/**
	 * Advances time by one second and updates this thermistor's state based on
	 * the heater's new temperature.
	 */
	public void tickSecond() {
		heater.tickSecond();
		pushTemperature(heater.getTemperature());
	}

	/**
	 * Returns the 10-bit ADC value corresponding to the temperature measured 
	 * by this thermistor.
	 * 
	 * @return	the 10-bit ADC value corresponding to the temperature measured
	 * 			by this thermistor
	 */
	public short getAdcValue() {
		return temperatureToAdc(getTemperature());
	}

	/**
	 * Returns the 10-bit ADC value corresponding to the specified temperature,
	 * as if measured by this thermistor.
	 * 
	 * @param degreesKelvin	the temperature to convert to a 10-bit ADC value, 
	 * 						in degrees Kelvin
	 * @return	the 10-bit ADC value corresponding to the specified temperature,
	 * 			as if measured by this thermistor.
	 */
	public short temperatureToAdc(double degreesKelvin) {
		double x = (r2 / Math.exp(beta/degreesKelvin) / k) + 1;
		int result = (int)Math.round(1024d / x);
		return (short)Math.max(Math.min(result,1024), 0);
	}
	
	/**
	 * Add the specified temperature to the circular buffer.
	 * 
	 * @param t	the temperature to add to the circular buffer
	 */
	private void pushTemperature(double t) {
		if (heaterTemp.length > 0) {
			heaterTemp[bufLast] = t;
			bufLast = (bufLast + 1) % heaterTemp.length;
		}
	}

}
