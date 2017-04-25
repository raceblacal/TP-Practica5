package es.ucm.fdi.tp.view.console;

import java.util.List;

import es.ucm.fdi.tp.base.model.*;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleController<S extends GameState <S,A>, A extends GameAction<S,A>> implements Runnable{
	List<GamePlayer> players;
	GameTable<S, A> game;
	
	
	public ConsoleController(List<GamePlayer> players, GameTable <S,A> game){
		this.players = players;
		this.game = game;
	}
	@Override
	public void run() {
		int playerCount = 0;
  		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
  		
		this.game.start(); //notifican
		
		
		while (!this.game.getState().isFinished()) {
			A action = players.get(this.game.getState().getTurn()).requestAction(game.getState());
			this.game.execute(action); //notifican
	
		}
	}
	
	 
}
