
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
		int[] bestScoreCol = this.board.miniMax(copyBoard, 10,
				-100000, 1000000, true, this.symbol, this.OPPONENT_SYMBOL);
		int bestCol = bestScoreCol[1];
		this.board.insert(this.symbol, bestCol);
	}

}
