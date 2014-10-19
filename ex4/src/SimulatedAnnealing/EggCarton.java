package SimulatedAnnealing;

import java.util.ArrayList;

// A concrete implementation of the State to solve the Egg Carton Puzzle
public class EggCarton extends State implements Cloneable {

	private boolean[][] carton;	// The carton to hold the eggs
	private int m, n;			// The dimensions of the carton
	private int k;				// The allowed number of eggs in a row/column/diagonal
	private int maxEggs;		// The maximum number of eggs possible in an optimal solution
	private int numberOfEggs;	// The number of eggs currently placed
	
	public EggCarton(int m, int n, int k) {
		if (m > n) {
			this.m = m;
			this.n = n;
		}
		else {
			this.n = m;
			this.m = n;
		}
		// Start with an empty carton
		carton = new boolean[this.m][this.n];
		this.numberOfEggs = 0;
		
		this.k = k;
		maxEggs = this.n * k;
	}
	
	// Copy every trait of the carton
	public EggCarton clone() {
		EggCarton clone = new EggCarton(m, n, k);
		boolean[][] box = new boolean[m][n];
		clone.numberOfEggs = this.numberOfEggs;
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				box[i][j] = carton[i][j];
			}
		}
		clone.carton = box;
		return clone;
	}

	// Visualize the carton
	public String toString() {
		String output = "m=" + m + ", n=" + n + ", k=" + k + "\n";
		output += "Placed " + numberOfEggs + " eggs.\n";
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (carton[i][j]) output += "0 ";
				else output += ". ";
			}
			output += "\n";
		}
		
		return output;
	}
	
	@Override
	public double evaluate() {
		// If we can't fit more without violating the constraints, there's no point in continuing
		if (numberOfEggs == maxEggs) return 1.0; 
		
		// The more of the capacity (max no. eggs satisfying the constraints) we use, the better
		double usedCapacity = numberOfEggs/maxEggs;
		
		// We want to penalize solutions that clump all the eggs together
		double maxPenalty = k*m*n;
		double penalty = 0;
		
		// Penalize empty rows
		for (int row = 0; row < m; row++) {
			int eggsInRow = 0;
			for (int col = 0; col < n; col++) {
				if (carton[row][col]) eggsInRow++;
			}
			penalty += Math.pow((k - eggsInRow), 2);
		}
		
		// Penalize empty cols
		for (int col = 0; col < n; col++) {
			int eggsInCol = 0;
			for (int row = 0; row < m; row++) {
				if (carton[row][col]) eggsInCol++;
			}
			penalty += Math.pow((k - eggsInCol), 2);
		}
		
		// Scale the penalty to the max penalty
		double scaledPenalty = penalty/maxPenalty;
		
		return usedCapacity/scaledPenalty;
	}
	
	public ArrayList<State> generateNeighbors() {
		ArrayList<State> neighbors = new ArrayList<State>();
		
		// Toggle all the eggs in the carton to create all neighbors
		// Neighbors are those cartons that differ from the input by one slot
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				EggCarton newCarton = this.clone();
				newCarton.toggleEgg(i, j);
				if (newCarton.isValid()) neighbors.add(newCarton);
			}
		}
		
		return neighbors;
	}
	
	public void toggleEgg(int x, int y) {
		if (carton[x][y]) numberOfEggs--;	// If there's an egg here, we'll remove it
		else numberOfEggs++;				// If not, we'll add one
		carton[x][y] = !carton[x][y];		// Take out/place egg
	}
	
	// Check if the configuration meets the constraints (no more than k eggs per row/col/diagonal
	public boolean isValid() {
		// Count rows
		for (int i = 0; i < m; i++) {
			int eggsInRow = 0;
			for (int j = 0; j < n; j++) {
				if (carton[i][j]) { 
					eggsInRow++;
				}
			}
			if (eggsInRow > k) {
				return false;
			}
		}
		
		// Count columns
		for (int j = 0; j < n; j++) {
			int eggsInColumn = 0;
			for (int i = 0; i < m; i++) {
				if (carton[i][j]) {
					eggsInColumn++;
				}
			}
			if (eggsInColumn > k) {
				return false;
			}
		}
		
		// Count \-diagonals from top row
		for (int col = 0; col < n; col++) {
			int eggsInDiagonal = 0;
			for (int x = 0, y = col; x < m && y < n; x++, y++) {
				if (carton[x][y]) {
					eggsInDiagonal++;
				}
			}
			if (eggsInDiagonal > k) {
				return false;
			}
		}
		
		// Count /-diagonals from top row
		for (int col = n-1; col >= 0; col--) {
			int eggsInDiagonal = 0;
			for (int x = 0, y = col; x < m && y >= 0; x++, y--) {
				if (carton[x][y]) {
					eggsInDiagonal++;
				}
			}
			if (eggsInDiagonal > k) {
				return false;
			}
		}
		
		// Count \-diagonals from leftmost col
		for (int row = 1; row < m; row++) {
			int eggsInDiagonal = 0;
			for (int x = row, y = 0; x < m && y < n; x++, y++) {
				if (carton[x][y]) {
					eggsInDiagonal++;
				}
			}
			if (eggsInDiagonal > k) {
				return false;
			}
		}
		
		// Count /-diagonals from rightmost col
		for (int row = 1; row < m; row++) {
			int eggsInDiagonal = 0;
			for (int x = row, y = n-1; x < m && y >= 0; x++, y--) {
				if (carton[x][y]) {
					eggsInDiagonal++;
				}
			}
			if (eggsInDiagonal > k) {
				return false;
			}
		}
		
		return true;
	}
}
