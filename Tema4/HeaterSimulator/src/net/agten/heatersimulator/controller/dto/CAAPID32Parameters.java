package net.agten.heatersimulator.controller.dto;

public class CAAPID32Parameters {
	private int pGain = 100;
	private int iGain = 4;
	private int dGain = 100;
	private int outputDivisor = -64;

	public int getpGain() {
		return pGain;
	}
	public void setpGain(int pGain) {
		this.pGain = pGain;
	}

	public int getiGain() {
		return iGain;
	}
	public void setiGain(int iGain) {
		this.iGain = iGain;
	}

	public int getdGain() {
		return dGain;
	}
	public void setdGain(int dGain) {
		this.dGain = dGain;
	}

	public int getOutputDivisor() {
		return outputDivisor;
	}
	public void setOutputDivisor(int outputDivisor) {
		this.outputDivisor = outputDivisor;
	}
	
}
