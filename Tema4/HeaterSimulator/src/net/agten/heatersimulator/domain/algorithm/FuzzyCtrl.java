package net.agten.heatersimulator.domain.algorithm;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

public class FuzzyCtrl implements ControllerAlgorithm {

    /**
     * The maximum result returned by the PID algorithm.
     */
    public final static short MAX_RESULT = 255;
    /**
     * The initial target ADC value.
     */
    public final static short INITIAL_TARGET_ADC = 830;

    /**
     * The gain of the proportional component.
     */
    private final short pGain;
    /**
     * The gain of the integral component.
     */
    private final short iGain;
    /**
     * The gain of the differential component.
     */
    private final short dGain;

    /**
     * Integer by which to divide the algorithm's output.
     */
    private final short outputDivisor;

    /**
     * The target ADC value.
     */
    private short targetAdc;

    /**
     * The current integral state.
     */
    private short iState = 0;
    /**
     * The last seen ADC value.
     */
    private short lastAdc = 1024;

    /**
     * Constructs a new PID algorithm instance, using the given algorithm
     * parameters. The initial target ADC value is set to 830.
     *
     * @param pGain         the gain for proportional component of the algorithm
     * @param iGain         the gain for integral component of the algorithm
     * @param dGain         the gain for derivative component of the algorithm
     * @param iWindupGuard  the maximum absolute value of the integral component of the
     *                      algorithm
     * @param outputDivisor integer by which to divide the algorithm's output
     */
    public FuzzyCtrl(short pGain, short iGain, short dGain, short outputDivisor) {
        if (outputDivisor == 0) {
            throw new IllegalArgumentException("outputDivisor cannot be 0");
        }
        this.pGain = pGain;
        this.iGain = iGain;
        this.dGain = dGain;
        this.outputDivisor = outputDivisor;
        this.targetAdc = INITIAL_TARGET_ADC;
    }

    /**
     * Calculate the output value of this PID algorithm, based on a given 10-bit
     * ADC value.
     *
     * @param curAdc a 10-bit ADC value representing the current reading
     * @return a value between 0 and MAX_RESULT representing the relative amount
     * of power to apply in order to reach the target ADC value
     */
    public short nextValue(short curAdc) {

        int error = this.targetAdc - curAdc;
        String filename = "heater.fcl";
        FIS fis = FIS.load(filename, true);

        if (fis == null) {
            System.err.println("Can't load file: '" + filename + "'");
            System.exit(1);
        }

        // Get default function block
        FunctionBlock fb = fis.getFunctionBlock(null);

        // Set inputs
        fb.setVariable("input_error", error);

        // Evaluate
        fb.evaluate();

        // Show output variable's chart
        fb.getVariable("output_power").defuzzify();

        int result = (int) fb.getVariable("output_power").getValue();
        //System.out.println("Result: " + result);

        return (short) Math.max(Math.min(result, MAX_RESULT), 0);
    }

    /**
     * Sets the target ADC value.
     *
     * @param targetAdc the 10-bit target ADC value
     */
    public void setTargetAdc(short targetAdc) {
        this.targetAdc = targetAdc;
    }
}
