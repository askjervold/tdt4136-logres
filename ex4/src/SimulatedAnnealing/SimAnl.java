package SimulatedAnnealing;

import java.util.ArrayList;
import java.util.Random;

// The Simulated Annealing algorithm
public class SimAnl {
	
	private State currentState;	// The current state
	private State bestState;	// The best state found so far
	private double temp;		// T, the temperature of the process
	private double dT;			// How much to decrease T by per iteration
	private double targetF;		// The F value we want to reach
	private Random rng;			// Random number generator used to choose between exploring and exploiting
	
	public SimAnl(State initState, double maxTemp, double dT) {
		this.currentState = initState;
		this.bestState = initState;
		this.temp = maxTemp;
		this.dT = dT;
		this.targetF = 1.0;
		rng = new Random();
	}
	
	public static void main(String args[]) {
		System.out.println("Simulating annealing...\n");
		ArrayList<SimAnl> puzzles = new ArrayList<SimAnl>();
		puzzles.add(new SimAnl(new EggCarton(5, 5, 2), 1000, 0.1));
		puzzles.add(new SimAnl(new EggCarton(6, 6, 2), 1000, 0.1));
		puzzles.add(new SimAnl(new EggCarton(8, 8, 1), 1000, 0.1));		// This looks like the 8-queens puzzle
		puzzles.add(new SimAnl(new EggCarton(10, 10, 3), 1000, 0.1));
		
		for (SimAnl puzzle : puzzles) {
			// We want to anneal until the fire runs out (temp=0) or we find an optimal solution (F(P) = F_target) 
			while (puzzle.temp > 0 && puzzle.currentState.evaluate() < puzzle.targetF) {
				puzzle.anneal();
				puzzle.temp -= puzzle.dT;
			}
			
			System.out.println("Solution found:");
			System.out.println(puzzle.currentState);
		}
	}

	public void anneal() {
		// F(P)
		double initF = currentState.evaluate();
		
		// Generate all neighbors
		ArrayList<State> neighbors = currentState.generateNeighbors();
		
		// Find the best neighbor
		for (State neighbor : neighbors) {
			if (neighbor.evaluate() > bestState.evaluate()) {
				// P_max <- neighbor with highest F
				bestState = neighbor;
			}
		}
		
		double q = (bestState.evaluate() - initF)/initF;
		double p = Math.min(1, Math.pow(Math.E, -q/temp));
		double x = rng.nextDouble();
		
		// Exploit!
		if (x > p) {
			// Move on to the best neighbor
			currentState = bestState;
		}
		// Explore!
		else {
			// Choose a random neighbor
			currentState = neighbors.get((int) (rng.nextDouble()*neighbors.size()));
		}
	}
}
