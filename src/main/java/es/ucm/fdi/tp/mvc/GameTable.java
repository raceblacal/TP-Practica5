         package es.ucm.fdi.tp.mvc;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;
import es.ucm.fdi.tp.ttt.TttState;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {
	
    // define fields here
	S initState; //s extends gamestate
	S actualState;
	
	ArrayList<GameObserver<S,A>> observers;
	
    public GameTable(S initState) {
        this.initState = initState;
        observers = new ArrayList<GameObserver<S,A>>();
    }
    public void start() {
        this.actualState = initState;
        String s = infoMessage(EventType.Start);
        GameEvent<S, A> event = new GameEvent<S,A>(EventType.Start, null, this.actualState, null, s);
        
        for (GameObserver<S, A> ob : observers)
        	ob.notifyEvent(event);
        
    }
    public void stop() {
    	GameEvent<S, A> event;
    	if (this.actualState.isFinished()){
    		String s = infoMessage(EventType.Stop);
	    	event = new GameEvent<S, A>(EventType.Stop, null, this.actualState, null, s);
    	}
    	else {
    		String s = infoMessage(EventType.Error);
    		event = new GameEvent<S, A>(EventType.Error, null, this.actualState, new GameError("Error, the game has already been stopped."), s);
    	}
    	for (GameObserver<S, A> ob : observers)
         	ob.notifyEvent(event);
    }
   
    public void execute(A action) {
    	GameEvent<S, A> event;
    	boolean ok = false;
    	
    	if (!this.actualState.isFinished()){ 
	    	List<A> valid = this.actualState.validActions(this.actualState.getTurn());
	    	for (A act : valid ){ 
	    		if (ok == false)
	    			ok = act.equals(action); //si nuestra accion es igual a alguna de la lista
	    	}
	    	if(!ok){ //si no hay ninguna accion igual
	    		String s = infoMessage(EventType.Error);
	    		event = new GameEvent<S, A>(EventType.Error, action, this.actualState, new GameError("Error, invalid move"), s);
	    	}
	    		
	    	else { //si es una accion valida aplica el movimiento y actualiza el estado
	    		this.actualState = action.applyTo(this.actualState);
	    		if (!this.actualState.isFinished()){
	    			String s = infoMessage(EventType.Change);
	    			event = new GameEvent<S, A>(EventType.Change, action, this.actualState, null,  s);
	    			
	    		}else{
	    			String s = infoMessage(EventType.Info);
	    			event = new GameEvent<S, A>(EventType.Info, action, this.actualState, null, s);
	    			this.stop();
	    		}
	    	}
    	}
    	else { //si el juego esta parado notifica error
    		String s = infoMessage(EventType.Error);
    		event = new GameEvent<S, A>(EventType.Error, action, this.actualState, new GameError("Error, the game is stopped"), s);
    	}
    	
    	if(event.getType() != EventType.Info){
    	for (GameObserver<S, A> ob : observers)
         	ob.notifyEvent(event);
    	}
    }
    public S getState() {
        return this.actualState;
    }

    public void addObserver(GameObserver<S, A> ob) {
        this.observers.add(ob);
    }
    public void removeObserver(GameObserver<S, A> ob) {
        this.observers.remove(ob);
    }
    
    private String infoMessage(EventType e) {
    	String s = "";
    	switch(e){
	    case Start:
			s = "Initial state:\n" + this.actualState;
			break;
		case Change:
			s ="After action:\n" + this.actualState;
			break;
		case Error:
			s ="Error";
			break;
		case Stop:
			if (this.actualState.getWinner() != -1){				
				s ="After action:\n" + this.actualState;
				String endText = "The game ended: ";
				int winner = actualState.getWinner();
				if (winner != -1) {
					endText += "player " + actualState.getTurn() + ", Player " + actualState.getWinner() + " won!";
				}
				s += endText;
			}
			else {
				s ="Game Over";
			}
			
			break;
		case Info:
				s =  this.actualState.toString();
			break;
		default:
			break;
	    }
    	return s;
    }
	public GameState<S,A> getInitState() {
		return this.initState;
	}
    
}
