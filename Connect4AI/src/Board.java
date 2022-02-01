import java.util.ArrayList;
import java.util.Collections;

public class Board {

	private final int NUM_OF_COLUMNS = 7;
	private final int NUM_OF_ROW = 6;
	private char[][] board;
	private int latestRow;
	private int latestCol;
	private char emptySlot = '\u0000';

	public Board() {
		this.board = new char[NUM_OF_ROW][NUM_OF_COLUMNS];
	}

	public void printBoard() {
		for (int row = 0; row < this.NUM_OF_ROW; row++) {
			for (int column = 0; column < this.NUM_OF_COLUMNS; column++) {
				if (column != 6) {
					if (this.board[row][column] == 0){
						System.out.print("| ");
					}else {
						System.out.print("|" + this.board[row][column]);
					}
				} else {
					if (this.board[row][column] == 0){
						System.out.print("| |");
					}else {
						System.out.print("|" + this.board[row][column] + "|");
					}
				}
			}
			System.out.println();
		}
	}

	//Insert board instance
	public void insert(char symbol, int column) {
		for (int row = 1; row < this.NUM_OF_ROW; row++) {
			if (this.board[row][column] != 0) {
				int rowOnTop = row - 1;
				this.board[rowOnTop][column] = symbol;
				this.latestRow = rowOnTop;
				this.latestCol = column;
				break;
			}
			if (row == (this.NUM_OF_ROW - 1)) {
				this.board[row][column] = symbol;
				this.latestRow = row;
				this.latestCol = column;
				break;
			}
		}
	}

	//Insert copy of the board
	public void insertCopy(char[][] boardCopy, char symbol, int column){
		for (int row = 1; row < this.NUM_OF_ROW; row++) {
			if (boardCopy[row][column] != 0) {
				boardCopy[row-1][column] = symbol;
				break;
			}
			if (row == (this.NUM_OF_ROW - 1)) {
				boardCopy[row][column] = symbol;
				break;
			}
		}
	}
	public boolean checkInsert(int column) {
		if (this.board[0][column] != 0) {
			return false;
		}
		return true;
	}

	public boolean containsWin() {
		// check horizontal
		if (this.latestRow <= 2 && this.board[latestRow][latestCol] != 0) {
			if ((this.board[latestRow][latestCol] == this.board[latestRow + 1][latestCol])
					&& (this.board[latestRow][latestCol] == this.board[latestRow + 2][latestCol])
					&& (this.board[latestRow][latestCol] == this.board[latestRow + 3][latestCol])) {
				return true;
			}
		}
		// check vertical
		for (int col = 0; col <= 3; col++) {
			if (this.board[latestRow][col] != 0) {
				if ((this.board[latestRow][col] == this.board[latestRow][col + 1])
						&& (this.board[latestRow][col] == this.board[latestRow][col + 2])
						&& (this.board[latestRow][col] == this.board[latestRow][col + 3])) {
					return true;
				}
			}
		}
		// check positive diagonals
		for (int col = 0; col < NUM_OF_COLUMNS - 3; col++){
			for ( int row = 3 ; row < NUM_OF_ROW; row ++){
				if (this.board[latestRow][latestCol] != 0){
					if ((this.board[row][col] == this.board[latestRow][latestCol])
							&&(this.board[row-1][col+1] == this.board[latestRow][latestCol])
							&&(this.board[row-2][col+2] == this.board[latestRow][latestCol])
							&&(this.board[row-3][col+3] == this.board[latestRow][latestCol])){
						return true;
					}
				}
			}
		}
		// check negative diagonals
		for (int col = 0; col < NUM_OF_COLUMNS - 3; col++){
			for ( int row = 0 ; row < NUM_OF_ROW - 3; row ++){
				if (this.board[latestRow][latestCol] != 0){
					if ((this.board[row][col] == this.board[latestRow][latestCol])
							&&(this.board[row+1][col+1] == this.board[latestRow][latestCol])
							&&(this.board[row+2][col+2] == this.board[latestRow][latestCol])
							&&(this.board[row+3][col+3] == this.board[latestRow][latestCol])){
						return true;
					}
				}
			}
		}
		return false;
	}

	// Fetches the best moves out of all the columns
	public int getBestMove(char[][] bCopy, char symbol, char opponentSym){
		ArrayList<Integer> validCol = validColumns(bCopy);
		char[][] boardCopy = new char[6][7];
		int bestScore = 0;
		int bestCol = validCol.get((int) (Math.random() * validCol.size()));
		for (int col : validCol){
			for (int i = 0; i < 6; i++){
				for (int j =0; j < 7; j++){
					boardCopy[i][j] = this.board[i][j];
				}
			}
			insertCopy(boardCopy, symbol, col);
			int score = calcScore(boardCopy, symbol, opponentSym);
			if (bestScore < score){
				bestScore = score;
				bestCol = col;
			}
		}
		return bestCol;
	}

	//calculates the score of each column
	private int calcScore(char[][] boardCopy, char symbol, char opponentSymbol){
		int maxScore = 0;
		// prioritizing center column
		for (int row = 0 ; row < NUM_OF_ROW-3; row++){
			char[] centerArray = new char[4];
			for (int interval = 0; interval < 4; interval++){
				centerArray[interval] = boardCopy[row+interval][3];
			}
			maxScore += duplicates(centerArray, symbol) * 5;
		}
		//horizontal score
		for (int row = 0; row < NUM_OF_ROW ; row++){
			for (int col = 0; col < NUM_OF_COLUMNS-3 ; col ++){
				char [] horizontal = new char[4];
				for (int interval = 0; interval < 4; interval++){
					horizontal[interval] = boardCopy[row][col + interval];
				}
				maxScore += evalArrayFourScore(horizontal, symbol, opponentSymbol);
			}
		}
		//Vertical score
		for (int col = 0; col < NUM_OF_COLUMNS; col++){
			for (int row = 0; row < NUM_OF_ROW-3; row++){
				char [] verticalArray = new char[4];
				for (int interval = 0; interval < 4; interval++){
					verticalArray[interval] = boardCopy[row+interval][col];
				}
				maxScore += evalArrayFourScore(verticalArray, symbol, opponentSymbol);
			}
		}
		// Positive diagonals score
		for (int row = 3; row < NUM_OF_ROW; row++){
			for (int col = 0; col < NUM_OF_COLUMNS-3; col++){
				char [] pDiagonalArray = new char[4];
				for (int interval = 0; interval < 4; interval++){
					pDiagonalArray[interval] = boardCopy[row-interval][col+interval];
				}
				maxScore += evalArrayFourScore(pDiagonalArray, symbol, opponentSymbol);
			}
		}

		// Negative diagonals score
		for (int col = 0; col < NUM_OF_COLUMNS - 3; col++){
			for ( int row = 0 ; row < NUM_OF_ROW - 3; row ++) {
				char [] nDiagonalArray = new char[4];
				for (int interval = 0; interval < 4; interval++){
					nDiagonalArray[interval] = boardCopy[row+interval][col+interval];
				}
				maxScore += evalArrayFourScore(nDiagonalArray, symbol, opponentSymbol);
			}
		}

		return maxScore;
	}

	private int evalArrayFourScore(char[] window, char symbol, char opponentSymbol){
		int score = 0;
		if (duplicates(window, symbol) == 4){
			score += 10;
		} else if ((duplicates(window, symbol) == 3) && (duplicates (window, emptySlot) == 1)){
			score += 3;
		} else if ((duplicates(window, symbol) == 2) && (duplicates(window, emptySlot) == 2)){
			score += 1;
		}
		if ((duplicates(window, opponentSymbol) == 3) && (duplicates(window, emptySlot) == 1)){
			score -= 2;
		}
		return score;
	}

	public int [] miniMax(char[][] boardCopy, int depth, int alpha, int beta, boolean isAIPlayer, char AIsymbol, char opponentSymbol){
		ArrayList<Integer> validCol = validColumns(boardCopy);

		if (depth == 0 || terminalNode(boardCopy, AIsymbol, opponentSymbol)){
			int [] scoreCol = new int [2];
			if (boardCopyWinner(boardCopy, AIsymbol)){
				scoreCol[0] = 1000000;
				scoreCol[1] = -1;
				return scoreCol;
			} else if (boardCopyWinner(boardCopy, opponentSymbol)){
				scoreCol[0] = -1000000;
				scoreCol[1] = -1;
				return scoreCol;
			} else {
				scoreCol[0] = calcScore(boardCopy, AIsymbol, opponentSymbol);
				scoreCol[1] = -1;
				return scoreCol;
			}
		}
		if (isAIPlayer){
			int bestCol = validCol.get((int) (Math.random() * validCol.size()));
			int maxValue = -1000000000;
			int [] scoreColumn = new int[2];
			for (int col : validCol){
				char[][] copyBoard = new char[NUM_OF_ROW][NUM_OF_COLUMNS];
				for (int i = 0; i < 6; i++){
					for (int j =0; j < 7; j++){
						copyBoard[i][j] = boardCopy[i][j];
					}
				}
				insertCopy(copyBoard, AIsymbol, col);
				int score = miniMax(copyBoard, depth-1, alpha, beta, false, AIsymbol, opponentSymbol)[0];
				if (score > maxValue){
					maxValue = score;
					bestCol = col;
				}
				alpha = Math.max(maxValue, alpha);
				if (alpha >= beta){
					break;
				}
			}
			scoreColumn[0] = maxValue;
			scoreColumn[1] = bestCol;
			return scoreColumn;
		} else{
			int bestCol = validCol.get((int) (Math.random() * validCol.size()));
			int minValue = 1000000000;
			int [] scoreColumn = new int[2];
			for (int col : validCol){
				char[][] copyBoard = new char[NUM_OF_ROW][NUM_OF_COLUMNS];
				for (int i = 0; i < 6; i++){
					for (int j =0; j < 7; j++){
						copyBoard[i][j] = boardCopy[i][j];
					}
				}
				insertCopy(copyBoard, opponentSymbol, col);
				int score = miniMax(copyBoard, depth-1, alpha, beta, true, AIsymbol, opponentSymbol)[0];
				if (score < minValue){
					minValue = score;
					bestCol = col;
				}
				beta = Math.min(minValue, beta);
				if (alpha >= beta){
					break;
				}

			}
			scoreColumn[0] = minValue;
			scoreColumn[1] = bestCol;
			return scoreColumn;
		}
	}

	private boolean terminalNode(char[][] boardCopy, char symbol, char opponentSymbol){
		if (boardCopyWinner(boardCopy, symbol) || boardCopyWinner(boardCopy, opponentSymbol) || validColumns(boardCopy).size() == 0){
			return true;
		}
		return false;
	}

	//count the number of duplicates
	public int duplicates(char[] arraySymbols, char symbol){
		int counter = 0;
		for (char sym : arraySymbols){
			if (sym == symbol){
				counter ++;
			}
		}
		return counter;
	}

	//Get all valid columns
	public ArrayList<Integer> validColumns(char [][] boardCopy){
		ArrayList<Integer> validDrops = new ArrayList<>();
		for (int column = 0; column < NUM_OF_COLUMNS; column ++){
			if (boardCopy[0][column] == emptySlot){
				validDrops.add(column);
			}
		}
		return validDrops;
	}

	public boolean isTie() {
		for (int row = 0; row < this.NUM_OF_ROW; row++) {
			for (int column = 0; column < this.NUM_OF_COLUMNS; column++) {
				if (this.board[row][column] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public void reset() {
		this.board = new char[NUM_OF_ROW][NUM_OF_COLUMNS];
	}

	private boolean boardCopyWinner(char [][] boardCopy, char symbol){
		// check horizontal
		for (int col = 0; col < NUM_OF_COLUMNS-3; col++){
			for (int row = 0; row < NUM_OF_ROW; row ++){
				if ((boardCopy[row][col] == symbol)
						&& (boardCopy[row][col+1] == symbol)
						&& (boardCopy[row][col+2] == symbol)
						&& (boardCopy[row][col+3] == symbol)){
					return true;
				}
			}
		}
		// check vertical
		for (int col = 0; col < NUM_OF_COLUMNS; col++){
			for (int row = 0; row < NUM_OF_ROW - 3; row++){
				if ((boardCopy[row][col] == symbol)
						&& (boardCopy[row+1][col] == symbol)
						&& (boardCopy[row+2][col] == symbol)
						&& (boardCopy[row+3][col] == symbol)){
					return true;
				}
			}
		}
		// check positive diagonals
		for (int col = 0; col < NUM_OF_COLUMNS - 3; col++){
			for ( int row = 3 ; row < NUM_OF_ROW; row ++){
				if ((boardCopy[row][col] == symbol)
						&&(boardCopy[row-1][col+1] == symbol)
						&&(boardCopy[row-2][col+2] == symbol)
						&&(boardCopy[row-3][col+3] == symbol)){
					return true;
				}
			}
		}
		// check negative diagonals
		for (int col = 0; col < NUM_OF_COLUMNS - 3; col++){
			for ( int row = 0 ; row < NUM_OF_ROW - 3; row ++){
				if ((boardCopy[row][col] == symbol)
						&&(boardCopy[row+1][col+1] == symbol)
						&&(boardCopy[row+2][col+2] == symbol)
						&&(boardCopy[row+3][col+3] == symbol)){
					return true;
				}
			}
		}
		return false;
	}
	public char[][] boardCopy(){
		char[][] boardCopy = new char[NUM_OF_ROW][NUM_OF_COLUMNS];
		for (int i = 0; i < 6; i++){
			for (int j =0; j < 7; j++){
				boardCopy[i][j] = this.board[i][j];
			}
		}
		return boardCopy;
	}
}
