package org.pc.test;
import java.text.DecimalFormat;
import java.util.Arrays;

import org.pc.src.ModernPortfolio;

public class ModernPortfolioTheoryTest {

	//Initial investment amount(in Dollars)
	private static final double initialInvestment=100000; 
	//Inflation Rate
	private static final double inflationRate = 0.035; 
	//This is the number of years based on which the initial value for return and risk is computed
	private static final int numYearsPassed = 20; 
	//The number of years at which the Future value of portfolio needs to be predicted 
	private static final int numYearsToPredict = 20;
	//number of simulations to run
	private static final int numSimulations = 10000;
	//percentile for best/worst cases
	private static final double percentile = 0.1;
	
	public static void main(String[] args) {
		testAggresivePortfolio();
		testVeryConservativePortfolio();
	}
	
	private static void testAggresivePortfolio() {
		System.out.println("Aggresive portfolio : ");
		ModernPortfolio aggressive = new ModernPortfolio(initialInvestment, inflationRate, 0.094324,0.15675, numYearsPassed);
		double[] predictions = runSimulations(aggressive,numSimulations);
		printResults(predictions);		
	}

	private static void testVeryConservativePortfolio() {
		System.out.println("Very Conservative portfolio : ");
		ModernPortfolio veryConservative = new ModernPortfolio(initialInvestment, inflationRate, 0.06189,0.063438, numYearsPassed);
		double[] predictions = runSimulations(veryConservative,numSimulations);
		printResults(predictions);
	}
	
	private static void printResults(double[] predictions) {
		Arrays.sort(predictions);
		double median = (predictions[numSimulations/2] + predictions[numSimulations/2 - 1]) / 2;
		System.out.println("Average Worst "+percentile*100+"th percentile Portfolio Balance : "+formatDollars(predictions[(int)(numSimulations*percentile)]));
		System.out.println("Median Portfolio Balance : "+formatDollars(median));
		System.out.println("Average Best "+percentile*100+"th percentile Portfolio Balance : "+formatDollars(predictions[(int)(numSimulations - numSimulations*percentile)]));
		System.out.println();
	}

	private static double[] runSimulations(ModernPortfolio modernPortfolio, int sampleSize){
		double[] predictions = new double[sampleSize];
		for(int i=0;i<sampleSize;i++){
			modernPortfolio.computeReturnInNextXYears(numYearsToPredict);
			predictions[i] = modernPortfolio.getInflationAdjustedPortfolioTotal();
			modernPortfolio.reset();
		}
		return predictions;
	}
	
	private static String formatDollars(double n){
		 return new DecimalFormat("#.##").format(n);
	}
}
