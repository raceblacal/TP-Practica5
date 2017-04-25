package es.ucm.fdi.tp.view.gui;

import java.awt.BorderLayout;



import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.base.Utils;
import es.ucm.fdi.tp.base.model.*;
import es.ucm.fdi.tp.extra.jcolor.MyTableModel;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;


@SuppressWarnings("serial")
public class GameWindow<S extends GameState<S, A>,A extends GameAction<S, A>> extends JFrame implements GameObserver<S, A> {

	
	private GamePlayer rand;
	private GamePlayer smart;
	
	
	private GameController<S,A> control;
	
	//-----MAPA DE COLORES-------------
	private Map<Integer, Color> colors;
	
	//----COMPONENTES VISUALES-----
	private JButton movAl;
	private JButton movRand;
	private MyTableModel tableModel;
	private JButton restart;
	private JButton fin;
	private GameView<S,A> gameView;
	private JTextArea txtArea;
	
	private int playerId;
	
	public GameWindow(int playerId, GamePlayer randPlayer, GamePlayer smartPlayer, 
			GameView<S, A> gameView, GameController<S, A> gameCtrl) { 
		
		super("Player " + playerId);
		this.rand = randPlayer;
		this.smart = smartPlayer;
		this.gameView = gameView;
		this.control = gameCtrl;
		gameView.setController(gameCtrl);
		this.playerId = playerId;
	
		
			
		
		//--------------------WINDOW------------------------------
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(650, 600);
		
		if (playerId == 0) this.setLocation(0, 40);
		else if (playerId == 1) this.setLocation(650, 40);
		
		//-------------------------JTOOLBAR-------------------------------------
		JToolBar toolbar = new JToolBar();
		toolbar.setRollover(true); 
		
		this.movAl = new JButton("");
		movAl.setIcon(new ImageIcon(Utils.loadImage("dice.png")));
		this.movRand = new JButton ("");
		movRand.setIcon(new ImageIcon(Utils.loadImage("nerd.png")));
		this.restart = new JButton("");
		restart.setIcon(new ImageIcon(Utils.loadImage("restart.png")));
		this.fin = new JButton ("");
		fin.setIcon(new ImageIcon(Utils.loadImage("exit.png")));
		
		toolbar.add(movAl);
		toolbar.addSeparator();
		toolbar.add(movRand);
		toolbar.addSeparator();
		toolbar.add(restart);
		toolbar.addSeparator();
		toolbar.add(fin);
		toolbar.addSeparator();
		toolbar.addSeparator();
		
		toolbar.add(new JLabel("Player mode:   "));
		
		
		toolbar.add(new JComboBox(new String[]{"Random","Smart", "Manual"}));
		
		//---------------------TEXT AREA-----------------------------
		JPanel pnlright = new JPanel();
		pnlright.setLayout(new BorderLayout());
		
		JLabel txtLabel = new JLabel("Info messages:");
		
		pnlright.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtArea = new JTextArea("", 20, 15);
		txtArea.setEditable(false);
		
		pnlright.add(txtLabel, BorderLayout.NORTH);
		
		pnlright.add(new JScrollPane(txtArea), BorderLayout.CENTER);
		
		
	//----------------------TABLE ejemplo---------------------------------
		this.tableModel = new MyTableModel();
		this.tableModel.getRowCount();
		colors = new HashMap<>();
		
		JTable table = new JTable(this.tableModel) {
			private static final long serialVersionUID = 1L;

			// THIS IS HOW WE CHANGE THE COLOR OF EACH ROW
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);

				// the color of row 'row' is taken from the colors table, if
				// 'null' setBackground will use the parent component color.
				if (col == 1)
					comp.setBackground(colors.get(row));
				else
					comp.setBackground(Color.WHITE);
				comp.setForeground(Color.BLACK);
				return comp;
			}
		};
		
		
		JPanel pnlColor = new JPanel(new BorderLayout());
		//table.setToolTipText("Click on a row to change the color of a player");
		//pnlColor.add(table);
		//pnlright.add(new JScrollPane(txtArea));
		
		pnlColor.add(table,BorderLayout.SOUTH);
		JLabel titulo = new JLabel("player´s colors: ");
		
		titulo.setBackground(Color.LIGHT_GRAY);
		titulo.setOpaque(true);
		
		pnlColor.add(titulo, BorderLayout.NORTH);
		 
		pnlright.add(pnlColor, BorderLayout.SOUTH);
		
		
	
		//-------------------------ADD------------------------
	
		
		this.getContentPane().add(this.gameView, BorderLayout.CENTER);
		this.getContentPane().add(toolbar,BorderLayout.NORTH);
		this.getContentPane().add(pnlright,BorderLayout.EAST);
		this.setVisible(true);
		
		
		//------------------------ACTION LISTENER----------------------------
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameWindow.this.gameView.enable();
				control.startGame();
				
			}
		});	
		
		
		this.control.addObserver(this);
		

		
		
	}





	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				handleEvent(e); // actualiza componentes visuales.
			}
		
		});
		

	}

	
	protected void handleEvent(GameEvent<S, A> e) {
		
		switch(e.getType()){
			case Start:
				
				
				this.txtArea.append("The game starts!\n");
				
			//	this.repaint();
		
				break;
			case Change: 
				this.txtArea.append("The game starts!\n");
				
				this.repaint();
				break;
			case Error:
				break;
			case Stop:
				control.stopGame();
				this.gameView.enable = false;
				break;
			case Info:
				break;
		}
	}
	
}

