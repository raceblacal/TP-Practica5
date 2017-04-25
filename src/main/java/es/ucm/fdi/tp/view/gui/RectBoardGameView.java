package es.ucm.fdi.tp.view.gui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;

import javax.swing.*;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.extra.jboard.JBoard.Shape;


@SuppressWarnings("serial")
public abstract class RectBoardGameView<S extends GameState <S,A>, A extends GameAction<S,A>> extends GameView<S,A>// contiene la representación visual del tablero.
{
	
	

	private GameWindow<S,A> gameWindow;
	protected int[][] board;
	private JTextField rows;
	private JTextField cols;
	protected GameController<S,A> control;
	protected JBoard jboard;

	
	
	
	

	private int _CELL_HEIGHT = 50;
	private int _CELL_WIDTH = 50;
	private int _SEPARATOR = -2;

	

	public RectBoardGameView() {
		
		

		
		initGUI();
	}
	
	
	


	private void initGUI() {
		setBorder(BorderFactory.createRaisedBevelBorder());
		
		this.setLayout(new BorderLayout());
		//JPanel ff = new JPanel();
		
		 jboard= new JBoard(){

			@Override
			protected void keyTyped(int keyCode) {
				RectBoardGameView.this.keyTyped(keyCode); //Presionar ESC// TODO Auto-generated method stub
				
			}

			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
				RectBoardGameView.this.mouseClicked( row,  col,  clickCount,  mouseButton);				
			}

			@Override
			protected Shape getShape(int player) {
				return RectBoardGameView.this.getShape(player);
			}

			@Override
			protected Color getColor(int player) {
				return RectBoardGameView.this.getColor(player);
			
			}

			@Override
			protected Integer getPosition(int row, int col) {
				return RectBoardGameView.this.getPosition(row,col);
			
			}

			@Override
			protected Color getBackground(int row, int col) {
				return RectBoardGameView.this.getBackground(row,col);
			}

			@Override
			protected int getNumRows() {
				
				return RectBoardGameView.this.getNumRows();
			}

			@Override
			protected int getNumCols() {
				// TODO Auto-generated method stub
				return  RectBoardGameView.this.getNumRows();
			}
		};
		
		this.add(jboard,BorderLayout.CENTER);
		//ff.add(this.jboard);
		//this.add(ff);
		
		
	}

		
	
	
	
	
	
	//--------------------------------------------------------


	

	@Override
	public void enable() {
		this.enable = true;
	}

	@Override
	public void disable() {
		this.enable = false;
		
	}
	
	@Override
	public void update(S gameState){
		this.board = gameState.getBoard();
	} 

	@Override
	public void setController(GameController<S,A> gameCtrl) {

		this.control = gameCtrl;
	}
	
	//Incluye otros métodos abstractos para que los implementen las subclases.
	protected abstract int getNumCols() ;//devuelve el número de filas del tablero.
	protected abstract int getNumRows(); //devuelve el número de columnas.
	protected abstract Integer getPosition(int row, int col); //devuelve el valor de una posicion del tablero en el estado actual del juego.
	
	//Un método que se llame cuando se pulse sobre una casilla del tablero.
	//Si se utiliza un MouseListener puede ser el siguiente:
	//pero puede ser más sencillo si se utiliza un tablero de JButton y
	//ActionListener 
	protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);
	
	protected abstract void keyTyped(int i);

	protected abstract Shape getShape(int player);
	protected abstract Color getBackground(int row, int col);






}
