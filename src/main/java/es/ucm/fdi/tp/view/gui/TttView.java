package es.ucm.fdi.tp.view.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard.Shape;
import es.ucm.fdi.tp.ttt.TttState;

@SuppressWarnings("serial")
public class TttView<S extends GameState <S,A>, A extends GameAction<S,A>> extends RectBoardGameView<S, A>{
	
	
	
	private int numOfRows;
	private int numOfCols;
	private TttState state;
	

	public TttView(TttState state) {
		this.state = state;
		this.board = state.getBoard();
		/*
		 JPanel ff = new JPanel();
		 ff.setLayout(new BorderLayout());
		 ff.add(new JLabel("hhh"));
		 this.add(ff);*/
		
		this.numOfCols = 3;
		this.numOfRows = 3;
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
		
		
	}

	@Override
	protected void keyTyped(int keyCode) {
		
		

	}

	@Override
	public Shape getShape(int player) {
		return player == 0 || player == 1 ? Shape.CIRCLE : Shape.RECTANGLE;

	}

	@Override
	protected Color getBackground(int row, int col) {
		return Color.LIGHT_GRAY;
	}

	@Override
	protected Color getColor(int player) {
		if (player == 0)
			return Color.BLUE;
		
		else if (player == 1)
			return Color.RED;
		
		return Color.LIGHT_GRAY;
	}
	


}
