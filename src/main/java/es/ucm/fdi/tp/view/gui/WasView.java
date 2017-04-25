package es.ucm.fdi.tp.view.gui;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasState;
import es.ucm.fdi.tp.extra.jboard.JBoard.Shape;

@SuppressWarnings("serial")
public class WasView extends RectBoardGameView<WasState, WasAction>{

	private int numOfRows;
	private int numOfCols;
	private WasState state;
	private int col_o;
	private int row_o;
	private int count;
	
	public WasView(WasState state) {
		this.state = state;
		this.board = state.getBoard();
		this.count = 0;
		
		this.numOfCols = 8;
		this.numOfRows = 8;
		
	}
	
	@Override
	protected int getNumCols() {
		
		return this.numOfCols;
	}

	@Override
	protected int getNumRows() {
		
		return this.numOfRows;
	}

	@Override
	protected Integer getPosition(int row, int col) {
		int a = board[row][col];
		if( a==-1)return null;
		else return a;
	}

	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		this.count++;

		if (count == 1){
			
			 if( this.state.getTurn()==state.at(row, col)){
				 this.col_o = col;
				 this.row_o = row;
				
			 }
			 else
				 count=0;
			 System.out.println("Micky");
				
			
		}
		else if (count == 2){
			 System.out.println("Mouse");
			 WasAction action = new WasAction(this.state.getTurn(), row, col, col_o, row_o);
			 if(action != null)
				 this.control.makeMove(action);
			
			this.count = 0;
		}
		
		
	}

	@Override
	protected void keyTyped(int keyCode) {
		 System.out.println(keyCode);
		 
		 if (this.count==1 &&keyCode == KeyEvent.VK_ESCAPE){
			 	this.count = 0;
			
			
		}
		
	}

	@Override
	public Shape getShape(int player) {
		return player == 0 || player == 1 ? Shape.CIRCLE : Shape.RECTANGLE;

	}
	

	@Override
	protected Color getBackground(int row, int col) {
		 return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
	}
	
	@Override
	protected Color getColor(int player) {
		if (player == 0)
			return Color.BLUE;
		
		else 
			return Color.RED;
		
		//return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
	
	}
	
	
}
