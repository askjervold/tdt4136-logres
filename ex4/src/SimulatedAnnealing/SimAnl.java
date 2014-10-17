package SimulatedAnnealing;

import java.util.Random;

public class SimAnl {
	
	State startState;	// The start state
	double maxTemp;		// T_max, the start value for temp T
	double temp;		// T, the temperature of the process
	double targetF;		// The F value we want to reach
	double dT;			// How much to decrease T by per iteration
	Random rng;			// Random number generator used to choose between exploring and exploiting
	
	public SimAnl() {
		rng = new Random();
	}
	
	public static void main(String args[]) {
		System.out.println("Simulating annealing...");
	}
}
