package es.ucm.fdi.tp.launcher;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WasState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Raquel Blanco Morago and Celia Calvo González
 * Main of the game
 */
public class MainPR4 {
	/**
	 * It take charge of the game process (request move, apply move, change turn, etc)
	 * @param initialState
	 * @param players
	 */
	public static <S extends GameState<S, A>, A extends GameAction<S, A>> void playGame(GameState<S, A> initialState,
			List<GamePlayer> players) {
		int playerCount = 0;
  		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;
		System.out.println("Initial state:\n" + initialState);
		
		while (!currentState.isFinished()) {
			// request move
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			
			// apply move
			currentState = action.applyTo(currentState);
			System.out.println("After action:\n" + currentState);
			
		

			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner != -1) {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}
	}

	/**
	 * Main
	 * @param args Syntax: GAMENAME PLAYERTYPE1 PLAYERTYPE2
	 */
	public static void main(String... args) {
		if(!numeroArgs(args)){
			System.err.println("Wrong paremeters, too much or too less. Valid Syntax: GAMENAME PLAYERTYPE1 PLAYERTYPE2");
			System.exit(1);
		}
		GameState<?, ?> gameState = createInitialState(args[0]);
		if(gameState == null){
			System.err.println(args[0] + " is not a name of game.");
			System.exit(1);
			}
		List<GamePlayer>  players = new ArrayList<GamePlayer>();
		for (int i = 1; i < args.length; i++) {
            
            GamePlayer p = createPlayer( args[i], "Player(" + i + ")" , args[0]);
            players.add( p);
		}
		
		playGame(gameState, players) ;

	}
	
	/**
	 * It checks the game name and create the game (WasSrate or TttState)
	 * @param gameName
	 * @return a gameState that matches the name of the game
	 */
	public static GameState<?, ?> createInitialState (String gameName){
		if (gameName.equalsIgnoreCase("WAS")) 
			return new WasState();
		
		if( gameName.equalsIgnoreCase("TTT"))
			return new TttState(3);
		
		else 
			return null;
		
		
	}
	
	/**
	 * Creates the initial state and asociates with a new player depending the playertype 
	 * @param playerType can be console, smart or rand
	 * @param playerName can be 1 or 2
	 * @param gameName can be Ttt or Was
	 * @return
	 */
	public static GamePlayer createPlayer( String playerType, String playerName, String gameName){
		if (playerType.equalsIgnoreCase("CONSOLE")){
		    Scanner in = new Scanner(System.in);
			return new ConsolePlayer(playerName, in);
		}
		else if (playerType.equalsIgnoreCase("SMART"))
			return new SmartPlayer(playerName, createInitialState(gameName).getPlayerCount());
		
		else if (playerType.equalsIgnoreCase("RAND"))
			return new RandomPlayer(playerName);
		
		else {
			System.err.println(playerType + " is not a name of player, wrong "+ playerName);
			System.exit(1);
			return null;
		}
	}
	
	/**
	 * 
	 * @param args
	 * @return
	 */
	public static boolean numeroArgs(String[] args) {
		return args.length ==3;
	}
	
	
}
