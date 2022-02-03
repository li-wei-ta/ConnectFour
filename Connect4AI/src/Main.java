import java.util.ArrayList;

public class Main{
	public static void main(String[] args) {

		Board board = new Board();
		ConnectFour game = new ConnectFour(board);
		game.setPlayer1(new HumanPlayer('R', board, "David"));
		game.setPlayer2(new AIPlayer('B', board, "Computer"));

		game.playGame();
	}
}
