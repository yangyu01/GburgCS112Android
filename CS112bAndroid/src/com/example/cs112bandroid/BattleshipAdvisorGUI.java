package com.example.cs112bandroid;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * BattleshipAdvisor - A GUI interface to the guessing advice of a BattleshipGuesser.  
 * Specify your BattleshipGuesser in the main method below in order to run this as a standalone application.  
 * The BattleshipGuesser will make successive guesses until all ships are sunk.
 * User input consists of feedback for guesses: miss, hit, ship sunk of length 2/3/4/5, undo, reset.
 * @author Todd W. Neller with MyBattleshipGuesser by Tessa Thorsen and Victoria Blaisdell
 */
@SuppressWarnings("serial")
public class BattleshipAdvisorGUI extends JFrame {
	/**
	 * Size of the Battleship grid.
	 */
	static final int SIZE = Position.SIZE;
	/**
	 * Constant indicating that the guesser has not yet guessed this grid cell.
	 */
	static final int UNKNOWN = 0;
	/**
	 * Constant indicating that the guesser has guessed and missed in this grid cell.
	 */
	static final int MISS = 1;
	/**
	 * Constant indicating that the guesser has guessed and hit (but not sunk) a ship in this grid cell.
	 */
	static final int HIT = 2;
	/**
	 * Constant indicating that the guesser has guessed, hit, and sunk a ship in this grid cell.
	 */
	static final int SUNK = 3;

	private BattleshipGuesser guesser = new MyBattleshipGuesser();
	private ArrayList<Integer> unsunkLengths = new ArrayList<Integer>();
	private Stack<Position> guessStack = new Stack<Position>();
	private Stack<String> responseStack = new Stack<String>();
	private Position guess = Position.gridPositions[0][0];
	private JFrame frame = this;
	
	/**
	 * Construct the BattleshipAdvisor with a given BattleshipGuesser.
	 * @param guesser BattleshipGuesser that generates guess advice.
	 */
	public BattleshipAdvisorGUI(BattleshipGuesser guesser) {
		this.guesser = guesser;
		init();
	}
	
	private void init() {
		setTitle("Battleship Advisor");
		int width = 500;
		setSize(width, (int) (width * 1.61803399));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		Container pane = getContentPane();
		reset();
		pane.add(new GridPanel(), BorderLayout.CENTER);
		pane.add(getButtonPanel(), BorderLayout.SOUTH);
		setJMenuBar(createMenuBar());
		setVisible(true);
	}
	
	/**
	 * Go back to initial guesser state and report all guesses and responses to guesser from the beginning.
	 */
	private void reset() {
		guesser.initialize();
		unsunkLengths.clear();
		for (int length : new int[] {5, 4, 3, 3, 2})
			unsunkLengths.add(length);
		for (int i = 0; i < guessStack.size(); i++)
			report(guessStack.get(i), responseStack.get(i));
		guess = guesser.getGuess();
		repaint();
	}
	
	class GridPanel extends JPanel implements MouseListener {
		HashMap<String, ImageIcon> imageMap = new HashMap<String, ImageIcon>();
		int ulx, uly, cellSize;
		
		public GridPanel() {
			// load images
			// 1) board images
			String[] imageStrings = {"unknown", "miss", "hit", "2", "3", "4", "5"};
			for (String imageString : imageStrings) 
				imageMap.put(imageString, new ImageIcon(imageString + ".png"));
			// 2) row label images
			for (int i = (int) 'a'; i <= (int) 'j'; i++)
				imageMap.put("label" + (char) i, new ImageIcon("label" + (char) i + ".png"));
			// 3) column label images
			for (int i = 1; i <= 10; i++)
				imageMap.put("label" + i, new ImageIcon("label" + i + ".png"));
			// add listener for user guess position selections (to override guess of MyBattleshipGuesser)
			addMouseListener(this);
		}
		
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setBackground(Color.BLACK);
			g2.setPaint(Color.BLACK);
			int width = getWidth();
			int height = getHeight();
			g2.fillRect(0, 0, width, height); // black background
			
			// compute the upper-left-x, upper-left-y, and cell size of a 12-by-12 square grid
			// (This grid will include wraparound row/column labels and the 10-by-10 game grid.)
			int margin = 5;
			int gridSize = Math.max(0, Math.min(width - margin * 2, height - margin * 2));
			ulx = (width - gridSize) / 2;
			uly = (height - gridSize) / 2;
			cellSize = gridSize / 12; // 2 label cells plus 10 board cells 
			
			// draw the row and column labels around the boundaries of a 12-by-12 grid
			for (int i = 0; i < 10; i++) {
				Image image = imageMap.get("label" + (char) ('a' + i)).getImage();
				int x = ulx;
				int y = uly + (i + 1) * cellSize;
				g2.drawImage(image, x, y, x + cellSize, y + cellSize, 0, 0, 512, 512, null);
				x = ulx + (SIZE + 1) * cellSize;
				g2.drawImage(image, x, y, x + cellSize, y + cellSize, 0, 0, 512, 512, null);
				image = imageMap.get("label" + (i + 1)).getImage();
				x = ulx + (i + 1) * cellSize;
				y = uly;
				g2.drawImage(image, x, y, x + cellSize, y + cellSize, 0, 0, 512, 512, null);
				y = uly + (SIZE + 1) * cellSize;
				g2.drawImage(image, x, y, x + cellSize, y + cellSize, 0, 0, 512, 512, null);
			}
			
			// build grid image names
			String[][] imageNames = new String[SIZE][SIZE];
			for (int i = 0; i < SIZE; i++)
				for (int j = 0; j < SIZE; j++) 
					imageNames[i][j] = "unknown";
			for (int i = 0; i < guessStack.size(); i++) {
				Position guess = guessStack.get(i);
				imageNames[guess.row][guess.col] = responseStack.get(i);
			}
			
			// draw grid images
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					int x = ulx + (j + 1) * cellSize;
					int y = uly + (i + 1) * cellSize;
					ImageIcon icon = imageMap.get(imageNames[i][j]);
					Image image = icon.getImage();
					g2.drawImage(image, x, y, x + cellSize, y + cellSize, 0, 0, 512, 512, null);
				}
			}
			
			// highlight guess position
			if (!unsunkLengths.isEmpty()) {
				g2.setPaint(new Color(1f, 1f, 0f, .5f));
				g2.fillRect(ulx + (guess.col + 1) * cellSize, uly + (guess.row + 1) * cellSize, cellSize, cellSize);
			}
			
			// draw grid lines
			g2.setPaint(Color.BLACK);
			for (int i = 0; i < SIZE - 1; i++) {
				g2.draw(new Line2D.Double(ulx + (i + 2) * cellSize, uly, ulx + (i + 2) * cellSize, uly + (SIZE + 2) * cellSize));
				g2.draw(new Line2D.Double(ulx, uly + (i + 2) * cellSize, ulx + (SIZE + 2) * cellSize, uly + (i + 2) * cellSize));
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// do nothing
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// do nothing
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// do nothing			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// do nothing
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			int row = (e.getY() - uly) / cellSize - 1;
			int col = (e.getX() - ulx) / cellSize - 1;
//			System.out.println(row + " " + col);
			if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
				Position pos = Position.gridPositions[row][col];
				if (!guessStack.contains(pos))
					guess = pos;
			}
			repaint();
		}
	}

	ResponseListener responseListener = new ResponseListener();

	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		// miss button
		JButton missButton = new JButton("Miss");
		missButton.setActionCommand("miss");
		missButton.addActionListener(responseListener);
		buttonPanel.add(missButton);
		
		// hit button
		JButton hitButton = new JButton("Hit");
		hitButton.setActionCommand("hit");
		hitButton.addActionListener(responseListener);
		buttonPanel.add(hitButton);
		
		// sunk button - causes popup menu with unsunk length options
        final JButton sunkButton = new JButton("Sunk");
        sunkButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	JPopupMenu popup = new JPopupMenu();
            	for (int unsunkLength : unsunkLengths) {
            		String lengthStr = String.valueOf(unsunkLength);
            		JMenuItem item = new JMenuItem(lengthStr);
            		item.addActionListener(responseListener);
            		popup.add(item);
            	}
            	popup.show(sunkButton, 0, 0);
            }
        });
        buttonPanel.add(sunkButton);
		return buttonPanel;
	}
	
	class ResponseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (unsunkLengths.isEmpty()) return;
			String response = e.getActionCommand();
			guessStack.add(guess);
			responseStack.add(response);
			report(guess, response);
			guess = guesser.getGuess();
			repaint();
		}
	}
	
	/**
	 * Report to guesser on currently selected guess position according to user's button selection.
	 * @param guess guess position
	 * @param response response action command
	 */
	private void report(Position guess, String response) {
		if (response.equals("miss"))
			guesser.report(guess, false, 0);
		else if (response.equals("hit"))
			guesser.report(guess, true, 0);
		else {
			int sunkLength = Integer.parseInt(response);
			unsunkLengths.remove((Integer) sunkLength);
			guesser.report(guess, true, sunkLength);
			if (unsunkLengths.isEmpty())
				JOptionPane.showMessageDialog(frame, "You've won in " + guessStack.size() + " guesses!");
		}
	}

	private JMenuBar createMenuBar() {
		// Create reset item
		JMenuItem resetItem = new JMenuItem("New Game");

		class ResetListener implements ActionListener 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (JOptionPane.showConfirmDialog(
					    frame,
					    "Are you sure you want to reset to a new game?",
					    "New Game Confirmation",
					    JOptionPane.YES_NO_OPTION) == 0) {
				guessStack.clear();
				responseStack.clear();
				reset();
				}
			}
		}

		resetItem.addActionListener(new ResetListener());

		// Create undo item
		JMenuItem undoItem = new JMenuItem("Undo");

		class UndoListener implements ActionListener 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (!guessStack.isEmpty()) {
					guessStack.pop();
					responseStack.pop();
					reset();
				}
			}
		}

		undoItem.addActionListener(new UndoListener());

		// Add items to game menu
		JMenu gameMenu = new JMenu("Game");
		gameMenu.add(resetItem);
		gameMenu.add(undoItem);

		// Add game menu to menu bar and return
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(gameMenu);
		return menuBar;
	}

	/**
	 * Play a Battleship game using a GUI interface and the BattleshipGuesser specified below.
	 * @param args (not used)
	 */
	public static void main(String[] args) {
		new BattleshipAdvisorGUI(new MyBattleshipGuesser());

	}

}
