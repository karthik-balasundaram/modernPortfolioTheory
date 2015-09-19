package org.pc.portfolio;
import java.util.Random;
/*
 * @author karthikBalasundaram
 */
public class ModernPortfolio {
	Random random = new Random();
	
	private MonteCarloSimulation monteCarloSimulation;
	private double initialInvestment;
	private double investment;
	private double inflationAdjustedRate = 1.0;
	private double inflationRate = 0.035;

	private int iterations = 0;
	
	public ModernPortfolio(double initialInvestment, double inflationRate, double _return, double _risk, int numYears){
		this(initialInvestment, inflationRate);
		this.setMonteCarloSimulation(new MonteCarloSimulation(_return, _risk, numYears));
	}
	
	public ModernPortfolio(double initialInvestment, double inflationRate, double _return, double _risk){
		this(initialInvestment, inflationRate);
		this.setMonteCarloSimulation(new MonteCarloSimulation(_return, _risk) );
	}
	
	private ModernPortfolio(double initialInvestment, double inflationRate){
		this.setInitialInvestment(initialInvestment);
		this.setInvestment(initialInvestment);
		this.setInflationRate(inflationRate);		
	}	
	
	public void computeReturnInNextXYears(int x){
		for(int i=0;i<x;i++){
			setInflationAdjustedRate(getInflationAdjustedRate() + getInflationAdjustedRate()*inflationRate);
			
			//Compute return for this year
			double currentReturnPercent = this.getMonteCarloSimulation().nextValue();
			//System.out.println(currentReturnPercent);
			//compute return on investment using the currentReturnPercent and add it to portfolio Total
			setInvestment(getInvestment() + (getInvestment() * currentReturnPercent) );
			
			//Increment iterations
			setIterations(getIterations() + 1);
		}
	}
	
	public void reset(){
		this.setInvestment(initialInvestment);
		this.setInflationAdjustedRate(1.0);
		this.setIterations(0);
		monteCarloSimulation.reset();
	}
	
	public double getInflationAdjustedPortfolioTotal() {
		return getInvestment()/getInflationAdjustedRate();
	}
	
	
	public double getInvestment() {
		return investment;
	}
	private void setInvestment(double investment) {
		this.investment = investment;
	}
	
	public double getInitialInvestment() {
		return initialInvestment;
	}
	private void setInitialInvestment(double initialInvestment) {
		this.initialInvestment = initialInvestment;
	}
	public double getInflationAdjustedRate() {
		return inflationAdjustedRate;
	}
	private void setInflationAdjustedRate(double inflationAdjustedRate) {
		this.inflationAdjustedRate = inflationAdjustedRate;
	}
	public double getInflationRate() {
		return inflationRate;
	}
	private void setInflationRate(double inflationRate) {
		this.inflationRate = inflationRate;
	}


	public int getIterations() {
		return iterations;
	}

	private void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public MonteCarloSimulation getMonteCarloSimulation() {
		return monteCarloSimulation;
	}

	private void setMonteCarloSimulation(MonteCarloSimulation monteCarloSimulation) {
		this.monteCarloSimulation = monteCarloSimulation;
	}

	public double getReturn(){
		return monteCarloSimulation.getMean();		
	}
	
	public double getRisk(){
		return monteCarloSimulation.getSD();		
	}	
}