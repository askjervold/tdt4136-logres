package SimulatedAnnealing;

public class EggCarton extends State {

	private int[][] carton; // The carton to hold the eggs
	private int m, n; // The dimensions of the carton
	private int k; // The allowed number of eggs in a row/column/diagonal
	
	public EggCarton(int m, int n, int k) {
		carton = new int[m][n];
		this.m = m;
		this.n = n;
	}
	
	@Override
	public int compareTo(State arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public double evaluate() {
		return 1.0;
	}
	
	public void placeEgg(int x, int y) {
		if (carton[x][y] == 0) {
			carton[x][y] = 1;
			return;
		}
		else return; //TODO: Do something else here
	}

}
