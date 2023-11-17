package sprint4_0.project;

import java.util.List;
import java.util.Map;


public abstract class Board {
    
    public static enum Cell { EMPTY, LETTERS, LETTERO }
        
    private Cell[][] board;
    private char currentPlayer;
    private int boardSize;
    private int blueCount;
    private int redCount;
    private boolean isSimpleGame;
    
    public enum GameState {
		PLAYING, DRAW, BLUE_WON, RED_WON
    }
    protected GameState currentGameState;
    
    public Board() {
    	 this.boardSize = 3;
         this.board = new Cell[3][3];
         this.currentPlayer = 'B'; // Blue player starts
         this.isSimpleGame = true; // Default game mode
         this.currentGameState = GameState.PLAYING;
         initializeBoard();
    }
    public Board(int size) {
    	this.boardSize = size;
        this.board = new Cell[size][size];
        this.currentPlayer = 'B'; // Blue player starts
        this.isSimpleGame = true; // Default game mode
        this.currentGameState = GameState.PLAYING;        
        initializeBoard();
    }
        
    public Board(int size, boolean mode) {
    	this.boardSize = size;
        this.isSimpleGame = mode;
        this.currentPlayer = 'B';
        this.board = new Cell[size][size];
        this.currentGameState = GameState.PLAYING;
        initializeBoard();
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
    	// Initialize board with EMPTY cells
    	 for (int i = 0; i < boardSize; i++) {
             for (int j = 0; j < boardSize; j++) {
                 board[i][j] = Cell.EMPTY;
             }
         }
    }
    
    public abstract void newGame();
    
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
    public void setGameMode(boolean mode) {
    	this.isSimpleGame = mode; // true for Simple game, false for General game mode
    }
    
    public boolean getGameMode() {
    	return isSimpleGame;
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
      
    public boolean makeMove(int row, int col, Cell cell) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || board[row][col] != Cell.EMPTY) {
            return false; // Invalid move
        }
        board[row][col] = cell;
        currentPlayer = (currentPlayer == 'B') ? 'R' : 'B';
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
    protected abstract boolean checkForSOS();
    public abstract boolean isBoardFull();
    protected abstract void updateGameState(char turn);
    protected abstract int getBlueCount();
    protected abstract int getRedCount();
    protected abstract List<SOSPattern> getFormedSOSPatterns();
    protected abstract Map<SOSPattern, Character> getSOSPatternPlayers();
}
