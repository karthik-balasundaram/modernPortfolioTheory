package org.pc.portfolio;
import java.util.Random;
/*
 * @author karthikBalasundaram
 */
public class MonteCarloSimulation {
	Random random = new Random();
	private double initMean; //This is the initial mean value
	private double initSD; //This is the initial sd value
	private double mean; //This is the updated mean after generating next value in the simulation 
	private double sd; //This is the updated mean after generating next value in the simulation
	private int initN=2; //This is the initial number of observation in the series which gives the historical mean and sd values
	private int n=2; //This is the number of observation in this series, this is incremented after a new value is generated
	
	public MonteCarloSimulation(double mean, double sd, int n){
		this(mean,sd);
		this.setInitN(n);
		this.setN(n);
	}
	
	public MonteCarloSimulation(double mean, double sd){
		this.setInitMean(mean);
		this.setMean(mean);
		this.setInitSD(sd);
		this.setSD(sd);
	}
	
	//This method resets the mean, sd and n to their initial values so that we can restart the simulation
	public void reset(){
		this.setMean(getInitMean());
		this.setSD(getInitSD());
		this.setN(getInitN());
	}
	
	//Can update the next random number generator function here.
	private double nextRandomNumber(){
		//the returned number will have mean=0.0 and sd=1.0
		return random.nextGaussian();
	}
	
	/* This method generates the next value by getting a random number using the distribution 
	*  defined in nextRandomNumber(). This number is from a distribution with mean 0 and std dev of 1
	*  That value is used to get the next number for the distribution with the current sd and mean value
	*  The generated number is used to update the overall mean and sd
	*  @return nextNumber
	*/
	public double nextValue(){
		//Generate next random number using defined distribution
		double nextValueInDistribution = nextRandomNumber();
		
		//Compute next Number based on historical mean & sd
		double nextNumber = nextValueInDistribution * getSD() + getMean();
		
		//Update overall mean and sd
		updateMeanAndSD(nextNumber);
		
		setN(getN() + 1);
		return nextNumber;
	}
	
	/* This method updates mean and sd for the distribution using the newly generated number.
	*  The newly generated number is n+1 th observation in the existing series of n observations
	*/
	public void updateMeanAndSD(double nextNumber){
		double M2 = getSD()*getSD() *(getN()-1); //sd = sqrt(M2/n-1) => M2 = sd*sd*(n-1)
		
		double newMean = (getMean()*getN()+nextNumber)/(getN()+1);
		
		//Based on single-pass method to compute variance, http://jonisalonen.com/2013/deriving-welfords-method-for-computing-variance/
		M2 = M2 + (nextNumber - getMean()) * (nextNumber - newMean);
		double newSD = Math.sqrt(M2/getN());
		
		setMean(newMean);
		setSD(newSD);
	}
	/**
 	* @return mean
 	*/
	public double getMean() {
		return mean;
	}
	/**
 	* @param mean mean to set
 	*/
	public void setMean(double mean) {
		this.mean = mean;
	}
	/**
 	* @return sd standard deviation
 	*/
	public double getSD() {
		return sd;
	}
	/**
 	* @param sd standard deviation to set
 	*/
	public void setSD(double sd) {
		this.sd = sd;
	}
	/**
 	* @return n number of observations
 	*/
	public int getN() {
		return n;
	}
	/**
 	* @param n number of observations to set
 	*/
	public void setN(int n) {
		this.n = n;
	}
	/**
 	* @return initMean initial mean
 	*/
	public double getInitMean() {
		return initMean;
	}
	/**
 	* @param initMean initial mean to set
 	*/
	public void setInitMean(double initMean) {
		this.initMean = initMean;
	}
	/**
 	* @return initSD initial standard deviation
 	*/
	public double getInitSD() {
		return initSD;
	}
	/**
 	* @param initSD initial standard deviation to set
 	*/
	public void setInitSD(double initSD) {
		this.initSD = initSD;
	}
	/**
 	* @return initN initial number of observations
 	*/
	public int getInitN() {
		return initN;
	}
	/**
 	* @param initN initial number of observations to set
 	*/
	public void setInitN(int initN) {
		this.initN = initN;
	}	
}
