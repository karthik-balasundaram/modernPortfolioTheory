package org.pc.portfolio;
import java.util.Random;
/*
 * @author karthikBalasundaram
 */
public class ModernPortfolio {
	Random random = new Random();
	
	private MonteCarloSimulation monteCarloSimulation;
	private double initialInvestment; //this is the initial investment amount
	private double investment; // this is the latest investment/portfolioTotal after computeReturnInNextXYears() has been called for n number of times.
	private double inflationAdjustedRate = 1.0; //inflation adjusted rate is updated with the given inlation rate for every call of computeReturnInNextXYears() 
	private double inflationRate = 0.035;

	private int iterations = 0;
	
	//Constructor to be called if numYears used to compute historical risk/return  is given
	public ModernPortfolio(double initialInvestment, double inflationRate, double _return, double _risk, int numYears){
		this(initialInvestment, inflationRate);
		this.setMonteCarloSimulation(new MonteCarloSimulation(_return, _risk, numYears));
	}
	
	//Constructor to be called if numYears used to compute historical risk/return is not given
	public ModernPortfolio(double initialInvestment, double inflationRate, double _return, double _risk){
		this(initialInvestment, inflationRate);
		this.setMonteCarloSimulation(new MonteCarloSimulation(_return, _risk) );
	}
	
	//private constructor used by the above public constructors
	private ModernPortfolio(double initialInvestment, double inflationRate){
		this.setInitialInvestment(initialInvestment);
		this.setInvestment(initialInvestment);
		this.setInflationRate(inflationRate);		
	}	
	
	/* This method updates value for investment and inflationAdjustedRate after x years.
	*  Calling inflationAdjustedRate(10) would update investment and inflationAdjustedRate after 10 years.
	*  inflationAdjustedRate() can now be called with argument x, 
	*  will result in updating investment and inflationAdjustedRate after x years from the result of previous call inflationAdjustedRate()
	*/
	public void computeReturnInNextXYears(int x){
		for(int i=0;i<x;i++){
			//Update inflationAdjustedRate for each iteration/year
			setInflationAdjustedRate(getInflationAdjustedRate() + getInflationAdjustedRate()*inflationRate);
			
			//Compute return percent for this year
			double currentReturnPercent = this.getMonteCarloSimulation().nextValue();
			
			//compute return on investment using the currentReturnPercent and add it to portfolio Total
			setInvestment(getInvestment() + (getInvestment() * currentReturnPercent) );
			
			//Increment iterations
			setIterations(getIterations() + 1);
		}
	}
	
	/* Calling reset will set investment, inflationAdjustedRate and iterations to their initial values
	*  that was set during object initialization.
	*/
	public void reset(){
		this.setInvestment(initialInvestment);
		this.setInflationAdjustedRate(1.0);
		this.setIterations(0);
		monteCarloSimulation.reset();
	}
	
	/* This is the inflationAdjusted portfolio Total that is computed using the portfolio total and the inflationAdjustedRate after x iterations/years. 
	*/
	public double getInflationAdjustedPortfolioTotal() {
		return getInvestment()/getInflationAdjustedRate();
	}
	
	/**
 	* @return investment
 	*/
	public double getInvestment() {
		return investment;
	}
	/**
 	* @param investment investment to set
 	*/
	private void setInvestment(double investment) {
		this.investment = investment;
	}
	/**
 	* @return initialInvestment
 	*/
	public double getInitialInvestment() {
		return initialInvestment;
	}
	/**
 	* @param initialInvestment initialInvestment to set
 	*/
	private void setInitialInvestment(double initialInvestment) {
		this.initialInvestment = initialInvestment;
	}
	/**
 	* @return inflationAdjustedRate
 	*/
	public double getInflationAdjustedRate() {
		return inflationAdjustedRate;
	}
	/**
 	* @param inflationAdjustedRate inflationAdjustedRate to set
 	*/
	private void setInflationAdjustedRate(double inflationAdjustedRate) {
		this.inflationAdjustedRate = inflationAdjustedRate;
	}
	/**
 	* @return inflationRate
 	*/
	public double getInflationRate() {
		return inflationRate;
	}
	/**
 	* @param inflationRate inflationRate to set
 	*/
	private void setInflationRate(double inflationRate) {
		this.inflationRate = inflationRate;
	}
	/**
 	* @return iterations
 	*/
	public int getIterations() {
		return iterations;
	}
	/**
 	* @param iterations iterations to set
 	*/
	private void setIterations(int iterations) {
		this.iterations = iterations;
	}
	/**
 	* @return monteCarloSimulation
 	*/
	public MonteCarloSimulation getMonteCarloSimulation() {
		return monteCarloSimulation;
	}
	/**
 	* @param monteCarloSimulation monteCarloSimulation to set
 	*/
	private void setMonteCarloSimulation(MonteCarloSimulation monteCarloSimulation) {
		this.monteCarloSimulation = monteCarloSimulation;
	}
	/**
 	* @return return
 	*/
	public double getReturn(){
		return monteCarloSimulation.getMean();		
	}
	/**
 	* @return risk
 	*/
	public double getRisk(){
		return monteCarloSimulation.getSD();		
	}	
}
