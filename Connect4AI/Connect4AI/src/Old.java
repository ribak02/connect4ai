import java.util.Arrays;
import java.util.Scanner;

public class Old {
	
	Scanner scan = new Scanner(System.in);

	// instance variables
	private int human = 1;
	private int ai = 2;
	private int currentPlayer = 1;
	
	private int[][] board;
	
	// constructor
	public Old() {
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
	
	public int getRandInt() {
		return (int)(Math.random() * 6); // random # between 0-6
	}
	
	public void humanMove(int col) {
		
		for (int i=5;i>=-1;i--) {
			if (board[i][col] == 0) {
				board[i][col] = human;
				break;
			}
		}
	}
	
	public void aiMove(int col) {
		
		for (int i=5;i>=-1;i--) {
			if (board[i][col] == 0) {
				board[i][col] = ai;
				break;
			}
		}
	}
	
	public int checkWin() {
		
		final int HEIGHT = board.length;
		final int WIDTH = board[0].length;
		int count = 0;
		
		for (int row=0;row<HEIGHT;row++) {
			for (int col=0;col<WIDTH;col++) {
				
				int player = board[row][col];
				
				// checks for tie
				for (int i=0;i<6;i++) {
					if (board[i][0] != 0) {
						count++;
					}
				}
				if (count == 6) {return 0;}
				
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
		
		getBoard();
		while(checkWin() == 0) {
			if (currentPlayer == human) {
				try {
					humanMove(scan.nextInt());
					currentPlayer = ai; // changes turn
				}
				catch(Exception exc) {
					System.out.println("That col is full");
				}
			}
			else {
				try {
				//aiMove(getRandInt());
				// to play as a 2nd human
				aiMove(scan.nextInt());
				currentPlayer = human; // changes turn
				}
				catch(Exception exc) {
					System.out.println("That col is full");
				}
			}
			getBoard();
		}
		System.out.println("Winner: " + checkWin());
	}
	
	// main method
	public static void main(String[] args) {
		Old c4 = new Old();
		c4.play();
	}
}
