import java.util.ArrayList;

public class Main{
	public static void main(String[] args) {

		Board board = new Board();
		ConnectFour game = new ConnectFour(board);
		game.setPlayer1(new HumanPlayer('R', board, "David"));
		game.setPlayer2(new AIPlayer('B', board, "Computer"));

		game.playGame();
//		int[][] test = {{1,2,3},{4,5,6}, {7,8,9}};
//		int[][] testCopy = new int[3][3];
//		System.out.println(testCopy[0][0] == '\u0000');
	}
//	char [] test = new char [4];
//		test[0] = 'a';
//		test[1] = 'a';
//		test[2] = 'n';
//		test[3] = 'a';
//		System.out.println(board.duplicates(test, 'a'));
}