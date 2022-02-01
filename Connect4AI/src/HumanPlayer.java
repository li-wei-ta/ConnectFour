import java.util.Scanner;

public class HumanPlayer extends Player {

	public HumanPlayer(char symbol, Board board, String name) {
		super(symbol, board, name);
	}

	@Override
	public void makeMove(Board board) {
		System.out.print(this.name + " please input your move:");
		Scanner scan = new Scanner(System.in);
		int column_num = scan.nextInt();
		boolean wasSuccessful = board.checkInsert(column_num);
		if (!wasSuccessful) {
			System.out.println("Don't be reckless, the column is full, insert again at " + "different column.");
			this.makeMove(board);
		} else {
			board.insert(this.symbol, column_num);
		}
	}

}
