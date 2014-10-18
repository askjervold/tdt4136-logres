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
	private long seed;
	
	public SimAnl(State initState, double maxTemp, double dT, long seed) {
		this.currentState = initState;
		this.bestState = initState;
		this.temp = maxTemp;
		this.dT = dT;
		this.targetF = 1.0;
		this.seed = seed;
		rng = new Random(seed);
	}
	
	public static void main(String args[]) {
		System.out.println("Simulating annealing...");
		ArrayList<SimAnl> puzzles = new ArrayList<SimAnl>();
		puzzles.add(new SimAnl(new EggCarton(5, 5, 2), 1000, 0.1, 250991));
		puzzles.add(new SimAnl(new EggCarton(6, 6, 2), 1000, 0.1, 250991));
		puzzles.add(new SimAnl(new EggCarton(8, 8, 1), 1000, 0.1, 250991));
		puzzles.add(new SimAnl(new EggCarton(10, 10, 3), 1000, 0.1, 250991));
		
		for (SimAnl puzzle : puzzles) {
			while (puzzle.temp > 0 && puzzle.currentState.evaluate() < puzzle.targetF) {
				puzzle.anneal();
				puzzle.temp -= puzzle.dT;
			}
			
			System.out.println("Solution found for puzzle with seed " + puzzle.seed + ":");
			System.out.println(puzzle.currentState);
		}
	}

	public void anneal() {
		double initF = currentState.evaluate();
		ArrayList<State> neighbors = currentState.generateNeighbors();
		
		for (State neighbor : neighbors) {
			if (neighbor.evaluate() > bestState.evaluate()) {
				setBestState(neighbor);
			}
		}
		
		double q = (bestState.evaluate() - initF)/initF;
		double p = Math.min(1, Math.pow(Math.E, -q/temp));
		double x = rng.nextDouble();
		
		if (x > p) {	// Exploit!
			currentState = bestState;
		}
		else {			// Explore!
			currentState = neighbors.get((int) (rng.nextDouble()*neighbors.size()));
		}
	}
	
	private void setBestState(State state) {
		this.bestState = state;
	}
}
