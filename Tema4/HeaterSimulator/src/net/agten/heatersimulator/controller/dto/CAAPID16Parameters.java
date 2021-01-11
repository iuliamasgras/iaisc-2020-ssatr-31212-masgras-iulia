package net.agten.heatersimulator.controller.dto;

public class CAAPID16Parameters {
	private short pGain = 18;
	private short iGain = 1;
	private short dGain = 30;
	private short outputDivisor = -16;
	
	public short getpGain() {
		return pGain;
	}
	public void setpGain(short pGain) {
		this.pGain = pGain;
	}
	
	public short getiGain() {
		return iGain;
	}
	public void setiGain(short iGain) {
		this.iGain = iGain;
	}
	
	public short getdGain() {
		return dGain;
	}
	public void setdGain(short dGain) {
		this.dGain = dGain;
	}
	
	public short getOutputDivisor() {
		return outputDivisor;
	}
	public void setOutputDivisor(short outputDivisor) {
		this.outputDivisor = outputDivisor;
	}
	
}
