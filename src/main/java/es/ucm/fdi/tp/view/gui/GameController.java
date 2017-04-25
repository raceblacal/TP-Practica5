package es.ucm.fdi.tp.view.gui;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;

public class GameController<S extends GameState<S, A>,A extends GameAction<S, A>> implements Runnable {
	public List<GamePlayer> players;
	protected GameTable<S, A> game;
	
	public GameController(List<GamePlayer> players, GameTable<S, A> game) {
		this.players = players;
		this.game = game;
	}

	@Override
	public void run() {
		/*
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
		*/
		
	}
	
	public void makeMove(A action){
		//realiza un movimiento en la vista se obtiene el movimiento de fomar manual random o smart
		this.game.execute(action);
	}
	
	public void stopGame(){
		this.game.stop();
	} //termina la partida
	
	public void startGame(){
		
		this.game.start();
	} //inicia partida 
	
	
	//ademas de los metodos para añadir o eliminar observadores
	public void addObserver(GameObserver<S, A> ob) {
        this.game.addObserver(ob);
    }
    public void removeObserver(GameObserver<S, A> ob) {
        this.game.removeObserver(ob);
    }
    

}
