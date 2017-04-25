package es.ucm.fdi.tp.launcher.MainTest;

import static org.junit.Assert.*;

import org.junit.Test;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.launcher.MainPR4;

public class MainTest {

	@Test
	public void toomuchArguments() {
	
		MainPR4 main = new MainPR4();
		String[] args ={"ff","fff","ff","hh"};
		assertEquals(" too much arguments",false,main.numeroArgs( args));
		
		
		
		
		
	}
	
	@Test
	public void wrongName(){
		String[] args ={"ff","fff","ff"};
		MainPR4 main = new MainPR4();
		
		assertEquals("wrong name",null, main.createInitialState(args[0]));
		
		
	}


}
