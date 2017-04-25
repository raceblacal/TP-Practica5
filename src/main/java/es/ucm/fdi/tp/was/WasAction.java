package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;

/**
 * 
 * @author Celia Calvo González and Raquel Blanco Morago
 * Class WasAction indicates the current movement or action of a sheep or a wolf
 * Attributes are:
 * player: can be wolf or sheep
 * row_destination: action's destination row
 *  col_destination: action's destination column
 *  row_origin: action's origin row
 *  col_origin: action's origin column
 *  serialVersionUID
 */
public class WasAction implements GameAction<WasState, WasAction>{

	private static final long serialVersionUID = 4409593608427402740L;
	private int player;
	private int row_destination;
	private int col_destination;
	private int row_origin;
	private int col_origin;
	
	/**
	 * The action's constructor
	 * @param player, can be wolf (0) or sheeps(1)
	 * @param row_dest, action's destination row
	 * @param col_dest, action's destination column
	 * @param row_orig, action's origin row
	 * @param col_orig, action's origin column
	 */
	public WasAction(int player, int row_dest, int col_dest, int row_orig, int col_orig){
		this.col_destination = col_dest;
		this.col_origin = col_orig;
		this.player = player;
		this.row_destination = row_dest;
		this.row_origin = row_orig;
	}

	@Override
	public int getPlayerNumber() {
		return this.player;
	}
	
	@Override
	public WasState applyTo(WasState state) {
		
		//make move
		int[][] board = state.getBoard();
		board[this.row_destination][this.col_destination] = player;
		board[this.row_origin][this.col_origin] = -1;
		
		WasState next;

		//update state
        
        if (state.isWinner( board)){ //there's a winner
            next = new WasState(state, board, true, state.getTurn());
            
        }
        else //there isn't a winner
            next = new WasState(state, board, false, -1);
        
        
        return next;
	}
	
	/**
	 * @return wolfOrSheep, a String with the action's information
	 */
	 public String toString() {
		 String wolfOrSheep;
	        if(this.player == 0) wolfOrSheep = "Wolf";
	        else wolfOrSheep = "Sheep";
	        
	        return wolfOrSheep + ", move form "
	        		+ "(" + this.row_origin + ", " + this.col_origin + ") "
	        				+ "to (" + this.row_destination + ", " + this.col_destination + ") " ;
	    }

	@Override
	public boolean equals(WasAction act) {
		return this.col_destination == act.col_destination &&
		this.col_origin == act.col_origin &&
		this.player == act.player &&
		this.row_destination == act.row_destination &&
		this.row_origin == act.row_origin;
		
	}
}
