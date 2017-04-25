package es.ucm.fdi.tp.view.console;

import es.ucm.fdi.tp.base.model.*;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleView <S extends GameState <S,A>, A extends GameAction<S,A>> implements GameObserver<S,A>{
	private ConsoleController<S,A> control;
	private GameObservable<S, A> gameTable;
	
	public ConsoleView(GameObservable<S, A> gameTable) {
		this.gameTable = gameTable;
		gameTable.addObserver(this);
	  }
	
	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		System.out.println(e.toString());
		
	}

}
