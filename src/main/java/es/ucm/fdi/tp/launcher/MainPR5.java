//PRACTICA 5

package es.ucm.fdi.tp.launcher;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.*;
import es.ucm.fdi.tp.base.player.*;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.console.*;
import es.ucm.fdi.tp.view.gui.*;
import es.ucm.fdi.tp.was.WasState;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JLabel;



/**
 * @author Raquel Blanco Morago and Celia Calvo González
 * Main of the game
 */
public class MainPR5 {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static GameTable <?,?> createGame (String gType){
		GameState<?,?> gamestate = createInitialState (gType);
		return new GameTable(gamestate);
		
	}
	
	
	
	
	
	private static < S extends GameState <S, A>,A extends GameAction<S, A>>
	void startConsoleMode(String gType,GameTable <S, A> game, String playerModes[]){
		
		List<GamePlayer> players = new ArrayList<GamePlayer>();
		//playerModes es el tipo de los dos jugadores
		for (int i = 0; i < playerModes.length ; ++i){
			GamePlayer gp = createPlayer(playerModes[i], "Player " + i, playerModes.length);
			players.add(gp);
		}
		ConsoleController<S,A> control = new ConsoleController<S,A>(players, game);
		
		new ConsoleView<S,A>(game);
		
		control.run();
	
	}
	
	
	
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startGUIMode(
	String gType, GameTable<S, A> game, String playerModes[]) {
		List<GamePlayer> players = new ArrayList<GamePlayer>();
		

		GameController<S,A> control = new GameController<S,A>(players, game);
		for (int i = 0; i < playerModes.length ; ++i){
			GamePlayer gp = createPlayer(playerModes[i], "Player " + i, playerModes.length);
			players.add(gp);
			int playerid = i;
			try {
				EventQueue.invokeAndWait(new Runnable(){

					@Override
					public void run() {
						if(gType.equalsIgnoreCase("ttt"))
							new GameWindow(playerid, null, null, new TttView(new TttState(3)), control);
						else if (gType.equalsIgnoreCase("was"))
							new GameWindow(playerid, null, null, new WasView(new WasState()), control);
						
					}});
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
				
		}
		control.startGame();
		
	}
		
		
		

	
	private static void usage() {
		System.out.println(""); //?????????????
	}

	public static void main(String[] args) {
 		if (args.length < 2) {
		usage();
		System.exit(1);
		}
		GameTable<?, ?> game = createGame(args[0]);
		if (game == null) {
		System.err.println("Invalid game");
		usage();
		System.exit(1);
		}
		String[] otherArgs = Arrays.copyOfRange(args, 2, args.length);
		switch (args[1]) {
		case "console":
		startConsoleMode(args[0], game, otherArgs);
		break;
		case "gui":
		startGUIMode(args[0],game,otherArgs);
		break;
		default:
		System.err.println("Invalid view mode: "+args[1]);
		usage();
		System.exit(1);
		}
		}
	


	
	/**
	 * It checks the game name and create the game (WasSrate or TttState)
	 * @param gameName
	 * @return a gameState that matches the name of the game
	 */
	public static GameState<?, ?> createInitialState (String gameName){
		if (gameName.equalsIgnoreCase("was")) 
			return new WasState();
		
		if( gameName.equalsIgnoreCase("ttt"))
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
	public static GamePlayer createPlayer( String playerType, String playerName, int playerCount){
		if (playerType.equalsIgnoreCase("manual")){
		    Scanner in = new Scanner(System.in);
			return new ConsolePlayer(playerName, in);
		}
		else if (playerType.equalsIgnoreCase("smart"))
			return new SmartPlayer(playerName, playerCount);
		
		else if (playerType.equalsIgnoreCase("rand"))
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
		return args.length == 3;
	}
	
	
}
