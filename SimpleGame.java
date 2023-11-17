package sprint4_0.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleGame extends Board {
	
    private Cell[][] board;
    private char currentPlayer;
    private int boardSize;
    private int blueCount;
    private int redCount;
    private GameState currentGameState;
    private List<SOSPattern> formedSOSPatterns;
	private Map<SOSPattern, Character> sosPatternPlayers = new HashMap<>();
    
    public SimpleGame() {
    	this.formedSOSPatterns = new ArrayList<>();
    	this.boardSize = 3; // Default size
        this.board = new Cell[3][3]; // set board for default size
        initializeBoard();
        formedSOSPatterns.clear();
    }
    public SimpleGame(int size) {
    	this.formedSOSPatterns = new ArrayList<>();
    	this.boardSize = size; // Set size of the board
        this.board = new Cell[size][size]; // set the board for the chosen size
        initializeBoard();
        formedSOSPatterns.clear();
    }
    public void setBoardSize(int size) {
    	this.boardSize = size;
    	this.board = new Cell[size][size];
        initializeBoard();
    }
    
    public int getBoardSize() {
    	return boardSize;
    }
    
    public void initializeBoard() {
    	// Initialize the board with EMPTY cells
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
        currentGameState = GameState.PLAYING; // Set starting gameState to Playing
        currentPlayer = 'B';	// Set first player to Blue Player
        redCount = 0;
        blueCount = 0;
    }
    @Override
    public void newGame() {
    	initializeBoard();
    	formedSOSPatterns.clear();
    }
    
    public Cell[][] getBoard() {
        return board;
    }
    
    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public void setCurrentPlayer(char player) {
        this.currentPlayer = player;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    public GameState getGameState() {
		return currentGameState;
	}

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < boardSize && column >= 0 && column < boardSize)
            return board[row][column];
        else
            return Cell.EMPTY;
    }

    public Cell getSymbol(int row, int col) {
        return board[row][col];
    }
    
    public int getBlueCount() {
    	return blueCount;
    }
    
    public int getRedCount() {
    	return redCount;
    }
    
    public List<SOSPattern> getFormedSOSPatterns() {
        return formedSOSPatterns;
    }
    
    public Map<SOSPattern, Character> getSOSPatternPlayers() {
        return sosPatternPlayers;
    }

    @Override
    public boolean makeMove(int row, int col, Cell cell) {
        if (currentGameState != GameState.PLAYING) {
        	System.out.println("Game is already over");
            return false; // Game is already over
            
        }

        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || board[row][col] != Cell.EMPTY) {
            return false; // Invalid move
        }

        board[row][col] = cell;
        updateGameState(currentPlayer);
        if (currentGameState == GameState.PLAYING) {
	        currentPlayer = (currentPlayer == 'B') ? 'R' : 'B';
	        System.out.println("Switch players");
        }
        
        return true;
    }
    
    public void countSOS() {
    	if (currentPlayer == 'B') {
    		blueCount += 1;
    	}
    	else {
    		redCount += 1;
    	}
    }    

    public void updateGameState(char turn) {
    	if (checkForSOS()) {
            currentGameState = (turn == 'B') ? GameState.BLUE_WON : GameState.RED_WON;
            System.out.println("Winner is " + currentGameState);
        } else if (isBoardFull()) {
            currentGameState = GameState.DRAW;
            System.out.println("Game is a Draw");
        }
    }
    
   public boolean isBoardFull() {
    	for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] == Cell.EMPTY) {
                    return false;	// If an empty cell is found, the board is not full
                }
            }
    	}
    	return true; // If no empty cell is found, the board is full
    }
    
    public boolean checkForSOS() {   
    	Cell[] symbols = { Cell.LETTERS, Cell.LETTERO, Cell.LETTERS }; // The SOS pattern to check for

        // Check rows for SOS
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize - 2; col++) {
                if (board[row][col] == symbols[0] &&
                    board[row][col + 1] == symbols[1] &&
                    board[row][col + 2] == symbols[2]) {
                	SOSPattern newPattern = new SOSPattern(symbols[0], row, col, "row");
                	formedSOSPatterns.add(newPattern);
            		sosPatternPlayers.put(newPattern, currentPlayer);
                	countSOS();
                    return true;
                }
            }
        }

        // Check columns for SOS
        for (int col = 0; col < boardSize; col++) {
            for (int row = 0; row < boardSize - 2; row++) {
                if (board[row][col] == symbols[0] &&
                    board[row + 1][col] == symbols[1] &&
                    board[row + 2][col] == symbols[2]) {
                	SOSPattern newPattern = new SOSPattern(symbols[0], row, col, "column");
                	formedSOSPatterns.add(newPattern);
            		sosPatternPlayers.put(newPattern, currentPlayer);
                	countSOS();
                    return true;
                }
            }
        }

        // Check diagonals (from top-left to bottom-right)
        for (int row = 0; row < boardSize - 2; row++) {
            for (int col = 0; col < boardSize - 2; col++) {
                if (board[row][col] == symbols[0] &&
                    board[row + 1][col + 1] == symbols[1] &&
                    board[row + 2][col + 2] == symbols[2]) {
                	SOSPattern newPattern = new SOSPattern(symbols[0], row, col, "diagTlBr");
                	formedSOSPatterns.add(newPattern);
            		sosPatternPlayers.put(newPattern, currentPlayer);
                	countSOS();
                    return true;
                }
            }
        }

        // Check diagonals (from top-right to bottom-left)
        for (int row = 0; row < boardSize - 2; row++) {
            for (int col = boardSize - 1; col >= 2; col--) {
                if (board[row][col] == symbols[0] &&
                    board[row + 1][col - 1] == symbols[1] &&
                    board[row + 2][col - 2] == symbols[2]) {
                	SOSPattern newPattern = new SOSPattern(symbols[0], row, col, "diagTrBl");
                	formedSOSPatterns.add(newPattern);
            		sosPatternPlayers.put(newPattern, currentPlayer);
                	countSOS();
                    return true;
                }
            }
        }

        return false;
    }
}
