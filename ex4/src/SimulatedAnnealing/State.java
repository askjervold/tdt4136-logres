package SimulatedAnnealing;

import java.util.ArrayList;

// A general State class for a Simulated Annealing process
public abstract class State implements Cloneable {

	public abstract double evaluate();
	public abstract ArrayList<State> generateNeighbors();
}
