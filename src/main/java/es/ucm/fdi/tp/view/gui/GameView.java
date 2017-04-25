package es.ucm.fdi.tp.view.gui;

import java.awt.Color;

import javax.swing.JComponent;


import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard.Shape;

@SuppressWarnings("serial")
//OBSERVADOR DEL MODELO
public abstract class GameView <S extends GameState <S,A>, A extends GameAction<S,A>> extends JComponent {
	
	protected boolean enable;

	
	public abstract void enable();// permite al usuario jugar.
	public abstract void disable();// impide jugar.
	public abstract void update(S state); //actualiza la vista

	//Es necesario poder comunicar GameWindow con GameView. Por
	//ejemplo puedes pasar una referencia del controlador a GameView con:
	public abstract void setController(GameController<S, A> gameCtrl);
	protected abstract Shape getShape(int player);
	protected abstract Color getBackground(int row, int col);
	protected abstract Color getColor(int player);
	

	

}
