import java.util.Arrays;

public class TwoPlayer {
	
	// instance variables
	private int human = 1;
	private int human2 = 2;
	private int currentPlayer = 1;
	
	private int[][] board;
	private int thisCol;
	
	// constructor
	public TwoPlayer() {
		
		// 6 x 7 board
		board  = new int[6][7];
		for (int[] row: board) {
			Arrays.fill(row, 0);
		}
	}
	
	// methods
	public void getBoard() {
		String temp = "";
		for (int[] row: board) {
			temp += Arrays.toString(row) + "\n";
		}
		System.out.println(temp);
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void butToCol(int col) {
		thisCol = col;
	}
	
	public void placeToken(int col) {
		for (int i=5;i>=-1;i--) {
			if (board[i][col] == 0) {
				board[i][col] = currentPlayer;
				break;
			}
		}
	}
	
	public boolean boardEquals(int a, int b, int c) {
		return board[a][b] == c;
	}
	
	public int checkWin() {
		
		final int HEIGHT = board.length;
		final int WIDTH = board[0].length;
		
		for (int row=0;row<HEIGHT;row++) {
			for (int col=0;col<WIDTH;col++) {
				
				int player = board[row][col];
				
				// checks for tie
				int count = 0;
				for (int i=0;i<WIDTH;i++) {
					if (board[0][i] != 0) {
						count++;
					}
				}
				if (count == 7) {return -1;}
				
				// iterates through board + checks surroundings for matches
				// thank you @4castle on StackExcange for idea
				
				// checks right
				if (col + 3 < WIDTH && player != 0 && 
						player == board[row][col+1] &&
						player == board[row][col+2] &&
						player == board[row][col+3]) {
					return player;
				}
				// checks down
				if (row - 3 >= 0 && player != 0 && 
						player == board[row-1][col] &&
						player == board[row-2][col] &&
						player == board[row-3][col]) {
					return player;
				}
				// checks down-right
				if (row - 3 >= 0 && col + 3 < WIDTH && player != 0 &&
						player == board[row-1][col+1] &&
						player == board[row-2][col+2] &&
						player == board[row-3][col+3]) {
					return player;
				}
				// checks down-left
				if (row - 3 >= 0 && col - 3 >= 0 && player != 0 &&
						player == board[row-1][col-1] &&
						player == board[row-2][col-2] &&
						player == board[row-3][col-3]) {
					return player;
				}
			}
		}
		return 0; // no winner
	}
	
	public void play() {
		
		if (currentPlayer == human) {
			try {
				placeToken(thisCol);
				currentPlayer = human2; // changes turn
			}
			catch(Exception exc) {
			System.out.println("That col is full");
			}
		}
		else {
			try {
				placeToken(thisCol);
				currentPlayer = human; // changes turn
			}
			catch(Exception exc) {
					System.out.println("That col is full");
			}
		}
		getBoard();	
		
		if (checkWin() == -1) {
			System.out.println("Tie");
		}
		if (checkWin() != 0) {
			System.out.println("Winner: " + checkWin());
		}
		
	}
}
