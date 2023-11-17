package sprint4_0.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sprint4_0.project.Board;
import sprint4_0.project.Board.Cell;
import sprint4_0.project.ComputerPlayer;
import sprint4_0.project.SimpleGame;

class TestComputerPlayer {
	private ComputerPlayer computerPlayer;
	private Board board;

	@BeforeEach
	void setUp() throws Exception {
		board = new SimpleGame();
		computerPlayer = new ComputerPlayer(board, 'B');
		
	}

	@AfterEach
	void tearDown() throws Exception {
		board = null;
		computerPlayer = null;
	}

	 @Test
	 public void testMakeWinningMove() {
		 // Scenario: Board in playing state, not full, and winning move possible
		 board.makeMove(0, 0, Cell.LETTERS);
	     board.makeMove(0, 2, Cell.LETTERS);
	     boolean moveMade = computerPlayer.makeWinningMove(); // make the winning move by place O in between S's on board
	     assertTrue(moveMade);
	 }
	 
	 @Test
	 public void testMakeAutoMove() {
		 // Scenario: Board in playing state, not full, and winning move possible
		 board.makeMove(0, 0, Cell.LETTERS);
	     board.makeMove(0, 2, Cell.LETTERS);
	     computerPlayer.makeAutoMove();
	     
	     assertEquals(Board.GameState.BLUE_WON, board.getGameState());
	     
	     board.newGame(); // Reset the board
	     
	     // Scenario: Board in playing state, not full, no winning move, but opponent has a winning move
	     board.makeMove(0, 0, Cell.LETTERS);
	     board.makeMove(0, 1, Cell.LETTERS);
	     board.makeMove(1, 2, Cell.LETTERO);
	     computerPlayer.makeAutoMove(); // Move made should not result in opponent winning nor a draw
	     
	     assertNotEquals(Board.GameState.RED_WON, board.getGameState());
	     assertNotEquals(Board.GameState.DRAW, board.getGameState());
	     
	     board.newGame(); // Reset the board
	     
	     // Scenario: Board in playing state, not full, no winning move for both sides
	     int initialEmptyCells = computerPlayer.getNumberOfEmptyCells();
	     computerPlayer.makeAutoMove(); 
	     
	     // Assert that a random move was made by checking the number of empty cells
	     assertEquals(initialEmptyCells - 1, computerPlayer.getNumberOfEmptyCells());
	 }
	 
	 @Test
	 public void testMakeFirstMove() {
		 int initialEmptyCells = computerPlayer.getNumberOfEmptyCells();
	     computerPlayer.makeFirstMove();
	     assertEquals(initialEmptyCells - 1, computerPlayer.getNumberOfEmptyCells());
	     
	 }

}
