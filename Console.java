package sprint4_0.project;

import java.util.Scanner;

import sprint4_0.project.Board.Cell;
import sprint4_0.project.Board.GameState;

public class Console {
private Board board;
private ComputerPlayer computerBlue;
private ComputerPlayer computerRed;

	public Console(Board board) {
		this.board = board;
		//this.computerBlue = new ComputerPlayer(board, 'B');
        //this.computerRed = new ComputerPlayer(board, 'R');
	}
	
	private boolean isOver() {
		GameState state = board.getGameState();
		if (state == GameState.PLAYING)
			return false;
		if (board.getGameState() == GameState.DRAW) {
			System.out.println("Draw!");
		} else if (board.getGameState() == GameState.BLUE_WON) {
			System.out.println("BLUE Player Won!");
		} else if (board.getGameState() == GameState.RED_WON) {
			System.out.println("RED Player Won!");
		}
		return true;
	}
public void startGame() {
    Scanner scanner = new Scanner(System.in);
    Cell letter = null;
    boolean done = false;
    
    while (!done) {
    	char currentPlayer = board.getCurrentPlayer();
        board.printBoard();
        if (currentPlayer == 'B' && computerBlue != null) {
            // Computer's turn for Blue
            computerBlue.makeAutoMove();
            System.out.println("Computer (BLUE) made a move.");
        } else if (currentPlayer == 'R' && computerRed != null) {
            // Computer's turn for Red
            computerRed.makeAutoMove();
            System.out.println("Computer (RED) made a move.");
        } else {
		    System.out.println("Enter S or O to place on the board, Q to Quit: ");
		    String letterInput = scanner.nextLine();     
		    if (letterInput.equalsIgnoreCase("q")) {
		        System.out.println("Thanks for playing!");
		        //done = false;
		        break;
		    }	 
		    else if (letterInput.equalsIgnoreCase("s")) {
		    	letter = Cell.LETTERS;
		    }
		    else if (letterInput.equalsIgnoreCase("o")) {
		        letter = Cell.LETTERO;
		    }
		    else {
		        System.out.println("Invalid input. Try again.");
		        continue;
		    }
		    System.out.println("Enter row: ");
		    String rowInput = scanner.nextLine();
		
		    System.out.println("Enter column: ");
		    String colInput = scanner.nextLine();
		        
		    int row = Integer.parseInt(rowInput);
		    
		    int col = Integer.parseInt(colInput);
		        
		    if (row < 0 || row >= board.getBoardSize() || col < 0 || col >= board.getBoardSize() || board.getCell(row, col) != Cell.EMPTY) {
		        System.out.println("Invalid move. Try again.");
		    }
		    else {
				board.makeMove(row, col, letter);
		    }
		}
        done = isOver();
    }
    board.printBoard();
    scanner.close();
}
public static void main(String[] args) {
	// Initial game mode choice
    System.out.println("Choose game mode: 'S' for Simple or 'G' for General: ");
    Scanner scanner = new Scanner(System.in);
    String modeInput = scanner.nextLine();
    Board board;
    if (modeInput.equalsIgnoreCase("s")) {
    	System.out.println("Choose board size (3 - 10): ");
    	String input = scanner.nextLine();
    	int size = Integer.parseInt(input);
    	board = new SimpleGame(size);
    } else if (modeInput.equalsIgnoreCase("g")) {
    	System.out.println("Choose board size (3 - 10): ");
    	String input = scanner.nextLine();
    	int size = Integer.parseInt(input);
        board = new GeneralGame(size);
    } else {
        System.out.println("Invalid input. Exiting.");
        scanner.close();
        return;
    }
    // Prompt the user to choose the player type for the Blue player
    System.out.println("Choose player type for BLUE: 'H' for Human or 'C' for Computer: ");
    String playerTypeBlue = scanner.nextLine();

    // Prompt the user to choose the player type for the Red player
    System.out.println("Choose player type for RED: 'H' for Human or 'C' for Computer: ");
    String playerTypeRed = scanner.nextLine();

    // Initialize the Console
    Console console = new Console(board);

    // Set up the player type for the computer players
    if (playerTypeBlue.equalsIgnoreCase("c")) {
        console.computerBlue = new ComputerPlayer(board, 'B');
    }
    if (playerTypeRed.equalsIgnoreCase("c")) {
        console.computerRed = new ComputerPlayer(board, 'R');
    }
    console.startGame();
    scanner.close();
}
}
