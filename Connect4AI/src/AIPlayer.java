import java.util.Random;
import java.util.ArrayList;

public class AIPlayer extends Player {
	private final char OPPONENT_SYMBOL;

	public AIPlayer(char symbol, Board board, String name) {
		super(symbol, board, name);
		if (symbol == 'R') {
			this.OPPONENT_SYMBOL = 'B';
		} else {
			this.OPPONENT_SYMBOL = 'R';
		}
	}

	@Override
	public void makeMove(Board board) {
		char[][] copyBoard = this.board.boardCopy();
//		int bestCol = this.board.getBestMove(copyBoard, symbol, OPPONENT_SYMBOL);
//		this.board.insert(symbol, bestCol);
		int[] bestScoreCol = this.board.miniMax(copyBoard, 10,
				-100000, 1000000, true, this.symbol, this.OPPONENT_SYMBOL);
		int bestCol = bestScoreCol[1];
		this.board.insert(this.symbol, bestCol);
		// make move with minimax
	}

}
/*
		boolean hasInserted = false;
		for (int column = 1; column <= 7; column++) {
			if (board.checkInsert(column)) {
				board.insert(this.symbol, column);
				if (board.containsWin()) {
					hasInserted = true;
					break;
				} else {
					board.removeChecker(column);
				}

				board.insert(OPPONENT_SYMBOL, column);
				if (board.containsWin()) {
					board.removeChecker(column);
					board.insert(this.symbol, column);
					hasInserted = true;
					break;
				} else {
					board.removeChecker(column);
				}

			}

		}
		if (!hasInserted) {
			Random rand = new Random();
			while (!hasInserted) {
				int randomNum = rand.nextInt(7) + 1;
				if (board.checkInsert(randomNum)) {
					board.insert(this.symbol, randomNum);
					hasInserted = true;
				}
			}
		}
 */
