package org.pc.portfolio;
import java.util.Random;
/*
 * @author karthikBalasundaram
 */
public class MonteCarloSimulation {
	Random random = new Random();
	private double initMean;
	private double initSD;
	private double mean; //mean for gaussian 
	private double sd; //sd for gaussian
	private int initN=2;
	private int n=2;
	
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
	
	public void updateMeanAndSD(double nextNumber){
		double M2 = getSD()*getSD() *(getN()-1); //sd = sqrt(M2/n-1) => M2 = sd*sd*(n-1)
		
		double newMean = (getMean()*getN()+nextNumber)/(getN()+1);
		
		//Based on single-pass method to compute variance, http://jonisalonen.com/2013/deriving-welfords-method-for-computing-variance/
		M2 = M2 + (nextNumber - getMean()) * (nextNumber - newMean);
		double newSD = Math.sqrt(M2/getN());
		
		setMean(newMean);
		setSD(newSD);
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getSD() {
		return sd;
	}

	public void setSD(double sd) {
		this.sd = sd;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getInitMean() {
		return initMean;
	}

	public void setInitMean(double initMean) {
		this.initMean = initMean;
	}

	public double getInitSD() {
		return initSD;
	}

	public void setInitSD(double initSD) {
		this.initSD = initSD;
	}

	public int getInitN() {
		return initN;
	}

	public void setInitN(int initN) {
		this.initN = initN;
	}	
}