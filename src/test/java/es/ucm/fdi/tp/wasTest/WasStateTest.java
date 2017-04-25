package es.ucm.fdi.tp.wasTest;

import static org.junit.Assert.*;



import org.junit.Test;

import es.ucm.fdi.tp.was.WasState;

public class WasStateTest {

	@Test
	public void corneredWolf() {
		WasState prevstate = new WasState();
		
		int board [][] = prevstate.getBoard();
		board[0][1]=-1;
		board[0][3]=-1;
		board[0][5]=-1;
		board[0][7]=-1;
	
		
		
		board[6][1]=1;
		
		WasState state = new WasState(prevstate,  board, false, -1);
		
			assertEquals("Cornered wolf",true,state.isWinner( board));
	}
	@Test
	public void wolfOnTop(){
	WasState state = new WasState();
		
		int board [][] = state.getBoard();
		board[0][1]=-1;
		board[0][3]=-1;
		board[0][5]=-1;
		board[0][7]=-1;
	
		board [0][1]=0;
		
		
		board[7][0]=1;
		
		
		
			assertEquals("Wolf on top",true,state.isWinner( board));
	}
	
	@Test
	public void wolfActions(){
		WasState prevstate = new WasState();
		
		
		
		
		
		assertEquals("Wolf on top",1,prevstate.validActions(0).size());
		
		int board [][] = prevstate.getBoard();
		board[7][0]=-1;
		board[6][1]=0;
		
		WasState state = new WasState(prevstate,  board, false, -1);
		assertEquals("Wolf  actions",4,state.validActions(0).size());
		
	}
	
	@Test
	public void sheepMoves(){
		WasState prevstate = new WasState();
		 
		assertEquals("Sheep moves",7,prevstate.validActions(1).size());
	}
	
	
}








/*

• un lobo rodeado resulta en victoria de las ovejas
• un lobo en una casilla con y = 0 resulta en victoria del lobo
• un lobo en su posición inicial sólo tiene 1 acción válida; y tras llevarla a cabo,
en su siguiente turno, tiene 4 acciones válidas.
• una oveja en su posición inicial tiene 2 acciones válidas; y si está en un lateral,
tiene 1 acciones válidas.

*/
