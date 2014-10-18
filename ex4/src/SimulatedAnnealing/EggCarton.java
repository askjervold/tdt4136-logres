package SimulatedAnnealing;

import java.util.ArrayList;

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
		carton = new boolean[this.m][this.n];
		this.k = k;
		maxEggs = this.n * k;
		this.numberOfEggs = 0;
	}
	
	@Override
	public int compareTo(State arg0) {
		if (this.evaluate() > arg0.evaluate()) return 1;
		else if (this.evaluate() < arg0.evaluate()) return -1;
		return 0;
	}
	
	public EggCarton clone() {
		EggCarton clone = new EggCarton(m, n, k);
		boolean[][] box = new boolean[m][n];
		clone.numberOfEggs = this.numberOfEggs;
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				box[i][j] = carton[i][j];
			}
		}
		clone.setCarton(box);
		return clone;
	}
	
	private void setCarton(boolean[][] carton) {
		this.carton = carton;
	}

	public String toString() {
		String output = "";
		
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
		double usedCapacity = numberOfEggs/maxEggs;
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
		
		double scaledPenalty = Math.pow(penalty/maxPenalty, 0.2);
		
		return usedCapacity/scaledPenalty;
	}
	
	public ArrayList<State> generateNeighbors() {
		ArrayList<State> neighbors = new ArrayList<State>();
		
		
		
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
		if (carton[x][y]) numberOfEggs--;
		else numberOfEggs++;
		carton[x][y] = !carton[x][y];
	}
	
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
