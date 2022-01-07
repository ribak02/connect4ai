import java.util.Arrays;

public class OnePlayer {
	
	// instance variables
	private int human = 1;
	private int ai = 2;
	private int currentPlayer = 1;
	
	private int[][] board;
	private int thisCol;
	private int count = 0;
	
	// constructor
	public OnePlayer() {
		
		// 6 x 7 board
		board  = new int[6][7];
		for (int[] row: board) {
			Arrays.fill(row, 0);
		}
	}
	
	// AI methods (maximizer is AI, minimizer is human)
	public int minimax(int[][] board, int depth, boolean isMaximizer) {
		
		count++;
		int score = evaluate(board);
		if (depth == 0 || checkWin() != 0) {
			return score;
		}
		if (isMaximizer) {
			int maxEval = Integer.MIN_VALUE;
			for (int r=0;r<board.length;r++) {
				for (int c=0;c<board[r].length;c++) {
					placeToken(c);
					int eval = minimax(board, depth - 1, false);
					//System.out.println("eval: " + eval);
					maxEval = Math.max(maxEval, eval);
					//System.out.println("Max eval: " + maxEval);
					removeToken(c);
				}
			}
			return maxEval;
		}
		// not maximizer
		else {
			int minEval = Integer.MAX_VALUE;
			for (int r=0;r<board.length;r++) {
				for (int c=0;c<board[r].length;c++) {
					placeToken(c);
					int eval = minimax(board, depth - 1, true);
					minEval = Math.min(minEval, eval);
					removeToken(c);
				}
			}
			return minEval;
		}
	}
	
	public int findBestMove() {
		int bestVal = Integer.MIN_VALUE;
		int bestMove = -1;
		
		for (int r=0;r<board.length;r++) {
			for (int c=0;c<board[r].length;c++) {
				placeToken(c);
				int moveVal = minimax(board, 2, false);
				//System.out.println("moveVal: " + moveVal);
				removeToken(c);
				
				if (moveVal > bestVal) {
					bestMove = c;
					bestVal = moveVal;
				}
			}
		}
		System.out.println("Best Move: " + bestMove);
		System.out.println("count: " + count);
		return bestMove;
	}
	
	// to improve AI manipulate this method
	public int evaluate(int[][] board) {
		int score = 0;
		if (checkWin() == ai) {score += 1000;}
		if (checkWin() == human) {score += -1000;}
		if (check2InRow() == ai) {score += 10;}
		if (check2InRow() == human) {score += -10;}
		if (check3InRow() == ai) {score += 50;}
		if (check3InRow() == human) {score += -50;}
		return score;
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
	
	public void setCurrentPlayer(int player) {
		currentPlayer = player;
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
	
	public void removeToken(int col) {
		for (int i=0;i<6;i++) {
			if (board[i][col] != 0) {
				board[i][col] = 0;
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
				// replicated concept from @4castle on StackExchange
				
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
	
	public int check2InRow() {
		
		final int HEIGHT = board.length;
		final int WIDTH = board[0].length;
		
		for (int row=0;row<HEIGHT;row++) {
			for (int col=0;col<WIDTH;col++) {
				
				int player = board[row][col];
				
				// checks right
				if (col + 1 < WIDTH && player != 0 && 
						player == board[row][col+1]) {
					return player;
				}
				// checks down
				if (row - 1 >= 0 && player != 0 && 
						player == board[row-1][col]) {
					return player;
				}
				// checks down-right
				if (row - 1 >= 0 && col + 1 < WIDTH && player != 0 &&
						player == board[row-1][col+1]) {
					return player;
				}
				// checks down-left
				if (row - 1 >= 0 && col - 1 >= 0 && player != 0 &&
						player == board[row-1][col-1]) {
					return player;
				}
			}
		}
		return 0;
	}
	
	public int check3InRow() {
		
		final int HEIGHT = board.length;
		final int WIDTH = board[0].length;
		
		for (int row=0;row<HEIGHT;row++) {
			for (int col=0;col<WIDTH;col++) {
				
				int player = board[row][col];
				
				// checks right
				if (col + 2 < WIDTH && player != 0 && 
						player == board[row][col+1] &&
						player == board[row][col+2]) {
					return player;
				}
				// checks down
				if (row - 2 >= 0 && player != 0 && 
						player == board[row-1][col] &&
						player == board[row-2][col]) {
					return player;
				}
				// checks down-right
				if (row - 2 >= 0 && col + 2 < WIDTH && player != 0 &&
						player == board[row-1][col+1] &&
						player == board[row-2][col+2]) {
					return player;
				}
				// checks down-left
				if (row - 2 >= 0 && col - 2 >= 0 && player != 0 &&
						player == board[row-1][col-1] &&
						player == board[row-2][col-2]) {
					return player;
				}
			}
		}
		return 0;
	}
	
	public void play() {
		
		if (currentPlayer == human) {
			try {
				placeToken(thisCol);
				currentPlayer = ai; // changes turn
			}
			catch(Exception exc) {
				System.out.println("That col is full");
			}
		}
		// currentPlayer is AI
		else {
			try {
				placeToken(findBestMove());
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
