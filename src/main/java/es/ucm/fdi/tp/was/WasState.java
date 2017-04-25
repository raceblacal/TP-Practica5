package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;

/**
 * @author Raquel Blanco Morago and Celia Calvo González
 * Class WasState indicates the current state of the game
 * Attributes are:
 * turn: it can be wolf (0) or sheep (1)
 * finished: it indicates if the game is finished or not yet
 * board: the current board
 * winner: indicates if there is a winner, it can be 0 (wolf), 1 (sheep) or -1 (none) 
 * dim: it is static, it is the dimension of the board dimxdim, it must be pair always and >0
 * EMPTY: constant -1
 * serialVersionUID
 */
public class WasState extends GameState <WasState, WasAction>{
	private final int turn;
    private final boolean finished;
    private final int[][] board;
    private final int winner;
    
    final static int dim  = 8;
    final static int SHEEP= 1;
    final static int WOLF= 0;

    final static int EMPTY = -1;
    
	private static final long serialVersionUID = -3678477032925245292L;

	/** Constructor without paremeters
	 * create a board with the sheep on the top and the wolf down on the left
	 * it's the wolf's turn
	 * 
	 */
	public WasState() {
		super(2);

        board = new int[dim][dim];
        
        for (int i=0; i<dim; i++) {
            board[i] = new int[dim];
            for (int j=0; j<dim; j++) 
            	board[i][j] = EMPTY;
        }
        
        for (int j = 0; j < dim; ++j){
        	if (j % 2 == 1)
        		this.board[0][j] = 1;
        	
        }
        this.board[dim - 1][0] = 0;
        this.turn = 0;
        this.winner = -1;
        this.finished = false;
	}
	/**
	 * Constructor with parameters
	 * @param prev is the previous game state
	 * @param board ins the new board for the game
	 * @param finished if the game has ended
	 * @param winner if finished is true 0 or 1 depending of the winner and the turn
	 */
	
	public WasState(WasState prev, int[][] board, boolean finished, int winner) {
    	super(2);
        this.board = board;
        this.turn = (prev.turn + 1) % 2;
        this.finished = finished;
        this.winner = winner;
    }    

	@Override
	public int getTurn() {
		return this.turn;
	}

	@Override
	public List<WasAction> validActions(int playerNumber) {
		 ArrayList<WasAction> valid = new ArrayList<>();
	        if (finished) {
	            return valid;
	        }
	        for (int i = 0; i < dim; ++i){
	        	for(int j = 0; j < dim; ++j){
	        	
		        	if(playerNumber == 1 && at(i, j) == 1){ //sheep	      
		        		this.DownMovements(valid, i, j);
		        	}
		        	
		        	/*else */if (playerNumber == 0 && at(i, j) == 0){ //wolf		 
		        		this.DownMovements(valid, i, j);
		        		this.UpMovements(valid, i, j);
		        	}
	        	}
	        }
			return valid;
	}
	/**
	 * Consults the value in a position
	 * @param row the row of the position
	 * @param col the column of the position
	 * @return the value that is in that position
	 */
	
	 public int at(int row, int col) {
		 if ((row >= 0) && (col < dim) && (row < dim) && (col >= 0))
	        return board[row][col];
		 else return -2;
	 }

	
	@Override
	public boolean isFinished() {
		return this.finished;
	}
	
	

	@Override
	public int getWinner() {
		return this.winner;
	}

	@Override
	public int getDim(){
		return dim;
	}
	
	/**
     * @return a copy of the board
     */
	
	@Override
	public int[][] getBoard(){
		int[][] copy = new int[board.length][];
		for (int i=0; i<board.length; i++) copy[i] = board[i].clone();
		return copy;
	}
/**
 * The sheep win:
 * 	- when they corner the wolf
 * The wolf wins:
 *	- when the sheep have not moves
 *	- when he is on the top
 * @param board the board with the actual position of the sheep and the wolf
 * @return  true if the player with the turn is the winner and false in the other case
 */
	public boolean isWinner( int[][] board) {
		
		if (turn == 1){ //SHEEP
			
		    if(!isValidWolf(board)){
		    	 return true;
		    }
		}
		
		else { //WOLF
			for (int i = 0; i < dim; ++i){
				if (board[0][i] == 0) return true;
			}
			if (!isValidSheep(board))  return true;
		}
		return false;
	}
	
	
	/**
	 * Indicates if the wolf has lost
	 * @param board the current board
	 * @return if the wolf have any valid action
	 */
	 private boolean isValidWolf(int[][] board) { 
		 boolean ok = false;
		 
		for (int i = 0; i < dim; ++i){
			for(int j = 0; j < dim; ++j){
				if (board[i][j] == WOLF) {
					if ((i - 1 >= 0) && (j - 1 >= 0) && board[i -1][j - 1] == -1) ok = true;
					
					else if((i - 1 >= 0) && (j + 1 < dim) &&(board[i - 1][j + 1] == -1)) ok = true;
					
					else if((i + 1 < dim) && (j + 1 < dim) &&(board[i + 1][j + 1] == -1)) ok = true;
					
					else if((i + 1 < dim) && (j - 1 >= 0) && (board[i+1][j-1] == -1)) ok = true;
					
						
				}
			}
		}
		return ok;
	}
	 
	 /**
	  * Indicates if the sheep have lost 
	  * @param board the actual board
	  * @return if the sheep has any valid action
	  */
	 private boolean isValidSheep(int[][] board){
		 boolean ok = false;
		 for (int i = 0; i < dim; ++i){
				for(int j = 0; j < dim; ++j){
					if (board[i][j] == SHEEP) {
						if ((i + 1 < dim) && (j - 1 >= 0) && (board[i + 1][j - 1] == -1))ok = true;
						
						else if((i + 1 < dim && (j + 1 < dim)) && (board[i + 1][j + 1] == -1))ok = true;
						
					}
				}
			}
			return ok;
	 }
	 
	 /**
	  * Draws the current board
	  */
	public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("  ");
	        for (int j = 0; j < dim; ++j){
	        	sb.append( " " + j + "|");
	        }
	        sb.append( "\n");
	        for (int i=0; i<board.length; i++) {
	            sb.append(i +"|");
	            for (int j=0; j<board.length; j++) {
	                sb.append(	 	             
	                			(board[i][j] == SHEEP) ? " S " : board[i][j] == WOLF ? " W ":
	                			(i%2 == WOLF && j % 2 != WOLF) ? " · " :
	                			(i%2 != WOLF && j % 2 == WOLF) ? " · " : "   "
	                		);
	            }
	            sb.append("\n");
	        }
	        return sb.toString();
	    }


	/**
	 * Includes in the valid movements list new down possible movements 
	 * @param valid empty movements list
	 * @param row 
	 * @param col
	 */
	private void DownMovements(List<WasAction> valid, int row, int col){
		
		//DIAGONAL LEFT-DOWN MOVEMENT

			if (this.at(row + 1, col - 1) == -1){
				WasAction wa1 = new WasAction(this.turn, row + 1, col -1, row, col);
				valid.add(wa1);
			}
			
		
		
		//DIAGONAL RIGHT-DOWN MOVEMENT
	
			if (this.at(row + 1, col + 1) == -1){
				WasAction wa2 = new WasAction(turn, row +1, col + 1,  row, col);

				valid.add(wa2);
			}
		
		
	}
	/**
	 *Includes in the valid movements list new up possible movements 
	 * @param valid empty movements list
	 * @param row
	 * @param col
	 */
	private void UpMovements(List<WasAction> valid, int row, int col){
		
		//DIAGONAL LEFT-UP MOVEMENT
	
			if (this.at(row - 1, col - 1) == -1){
				WasAction wa1 = new WasAction(turn, row - 1, col - 1, row, col);
				valid.add(wa1);
			}
		
		//DIAGONAL RIGHT-UP MOVEMENT
		
			if (this.at(row - 1, col + 1) == -1){
				WasAction wa2 = new WasAction(turn, row - 1, col + 1,  row, col);
				valid.add(wa2);
			}
		
	
	}
	
}
