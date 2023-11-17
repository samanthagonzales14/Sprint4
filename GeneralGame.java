package sprint4_0.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralGame extends Board {
	private List<SOSPattern> formedSOSPatterns;
	private Map<SOSPattern, Character> sosPatternPlayers = new HashMap<>();
	private Cell[][] board;
    private char currentPlayer;
    private int boardSize;
    private int blueCount;
    private int redCount;
    private GameState currentGameState;
    
	public GeneralGame() {
		this.formedSOSPatterns = new ArrayList<>();
		this.boardSize = 3; // Default size
        this.board = new Cell[3][3]; // set board for default size
        formedSOSPatterns.clear();
        initializeBoard();
	}
	
	public GeneralGame(int size) {
		this.formedSOSPatterns = new ArrayList<>();
    	this.boardSize = size; // Set size of the board
        this.board = new Cell[size][size]; // set the board for the chosen size
        formedSOSPatterns.clear();
        initializeBoard();
    }
	
    public void setBoardSize(int size) {
    	this.boardSize = size;
    	this.board = new Cell[size][size];
    	formedSOSPatterns.clear();
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
        currentGameState = GameState.PLAYING;
        currentPlayer = 'B';
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
        
        if (!checkForSOS() && currentGameState == GameState.PLAYING) {
	        currentPlayer = (currentPlayer == 'B') ? 'R' : 'B';
	        System.out.println("Switching players. Current Player is " + currentPlayer);
        }
        else {
        	
        	System.out.println("Player " + currentPlayer + " formed an SOS!");
        	System.out.println("Blue Player Count is: " + blueCount);
        	System.out.println("Red Player Count is: " + redCount);
        	printList();
        }
        
        updateGameState(currentPlayer);
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
    	    		
    	if (isBoardFull() && blueCount > redCount) {
    		currentGameState = GameState.BLUE_WON;
    		System.out.println("Winner is " + currentGameState);
    		
    	}
    	else if (isBoardFull() && redCount > blueCount) {
    		currentGameState = GameState.RED_WON;
    		System.out.println("Winner is " + currentGameState);
    	}
    	else if (isBoardFull() && blueCount == redCount) {
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
    
    // Function to check if an SOS pattern is already in the list
    private boolean isPatternInList(SOSPattern pattern, List<SOSPattern> patternList) {
        for (SOSPattern existingPattern : patternList) {
            if (existingPattern.equals(pattern)) {
                return true; // Pattern already exists in the list
            }
        }
        return false; // Pattern is not in the list
    }
    
    public void printList() {
    	for (int i = 0; i < formedSOSPatterns.size(); i++) {
    		System.out.println(formedSOSPatterns.get(i) + " ");
    	}
    }
    
    public boolean checkForSOS() {   
    	Cell[] symbols = { Cell.LETTERS, Cell.LETTERO, Cell.LETTERS }; // The SOS pattern to check for
    	boolean sosFound = false;

        // Check rows for SOS
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize - 2; col++) {
                if (board[row][col] == symbols[0] &&
                    board[row][col + 1] == symbols[1] &&
                    board[row][col + 2] == symbols[2]) {
                	SOSPattern newPattern = new SOSPattern(symbols[0], row, col, "row");
                	if (!isPatternInList(newPattern, formedSOSPatterns)) {
                		formedSOSPatterns.add(newPattern);
                		sosPatternPlayers.put(newPattern, currentPlayer); // Store the player who formed the SOS
                		sosFound = true;
                		countSOS();
                	}               	
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
                	if (!isPatternInList(newPattern, formedSOSPatterns)) {
                		formedSOSPatterns.add(newPattern);
                		sosPatternPlayers.put(newPattern, currentPlayer); // Store the player who formed the SOS
                		sosFound = true;
                		countSOS();
                	}
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
                	if (!isPatternInList(newPattern, formedSOSPatterns)) {
                		formedSOSPatterns.add(newPattern);
                		sosPatternPlayers.put(newPattern, currentPlayer); // Store the player who formed the SOS
                		sosFound = true;
                		countSOS();
                	}
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
                	if (!isPatternInList(newPattern, formedSOSPatterns)) {
                		formedSOSPatterns.add(newPattern);
                		sosPatternPlayers.put(newPattern, currentPlayer); // Store the player who formed the SOS
                		sosFound = true;
                		countSOS();
                	}
                }
            }
        }
        return sosFound;
    }
}
