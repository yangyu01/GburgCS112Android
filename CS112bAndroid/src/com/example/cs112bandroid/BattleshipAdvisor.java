package com.example.cs112bandroid;
import java.util.Scanner;
import java.util.Stack;

/**
 * BattleshipAdvisor - A text interface to the guessing advice of a BattleshipGuesser.  
 * Specify your BattleshipGuesser in the main method below in order to run this as a standalone application.  
 * The BattleshipGuesser will make successive guesses until all ships are sunk.
 * User text input consists of feedback for guesses: "m"=miss, "h"=hit, "2"/"3"/"4"/"5"=ship sunk of length 2/3/4/5, respectively.
 * @author Todd W. Neller
 */
public class BattleshipAdvisor {
	private BattleshipGuesser guesser;
	private int sinkCount;
	private Stack<Position> guessStack = new Stack<Position>();
	private Stack<String> responseStack = new Stack<String>();

	/**
	 * Construct the BattleshipAdvisor with a given BattleshipGuesser.
	 * @param guesser BattleshipGuesser that generates guess advice.
	 */
	public BattleshipAdvisor(BattleshipGuesser guesser) {
		this.guesser = guesser;
	}



	/**
	 * Play a game using the BattleshipGuesser provided in the constructor.
	 */
	public void playGame() {
		System.out.println("Set up a Battleship grid with hidden ships of length 5, 4, 3, 3, and 2.");
		System.out.println("Press Enter when you are ready to begin.");
		Scanner in = new Scanner(System.in);
		in.nextLine();
		guessStack.clear();
		responseStack.clear();
		guesser.initialize();
		String[] instructions = {"Commands:", "m = miss", "h = hit", 
				"<number> = sunk ship of length <number> (e.g. 4 = You sank my battleship!)",
		"u = undo"};
		for (String line : instructions)
			System.out.println(line);
		sinkCount = 0;
		while (sinkCount < 5) {
			Position guess = guesser.getGuess();
			guessStack.push(guess);
			String input = "";
			boolean validInput = false;
			while (!validInput) {
				System.out.print(guess + "? ");
				input = in.nextLine().trim();
				if ("mh2345u".indexOf(input) >= 0)
					validInput = true;
				else
					System.err.println("Invalid Input: " + input);
			}
			if (input.equals("u")) { // undo
				if (responseStack.isEmpty()) {
					System.err.println("Nothing to undo.");
				}
				else {
					responseStack.pop();
					guessStack.pop();
					guessStack.pop();
					guesser.initialize();
					sinkCount = 0;
					for (int i = 0; i < guessStack.size(); i++) {
						System.out.printf("%d. %s %s\n", i, guessStack.get(i), responseStack.get(i));
						processAnswer(guessStack.get(i), responseStack.get(i));
					}
				}
			}
			else {
				responseStack.push(input);
				processAnswer(guess, input);
			}
		}
		in.close();
		System.out.println("Game won in " + guessStack.size() + " guesses.");
	}

	/**
	 * Process the user feedback for the given guess.
	 * @param guess Position guessed by the BattleshipGuesser
	 * @param input user String feedback of the form "m", "h", "2", "3", "4", or "5".
	 * Any other input will cause the program to terminate.
	 */
	private void processAnswer(Position guess, String input) {
		boolean hit = false; // assume a miss
		int sunkenShipLength = 0;
		if (input.equals("m")) { // do nothing - miss already assumed
		}
		else if (input.equals("h")) { // note a hit
			hit = true; 
		}
		else if ("2345".indexOf(input) >= 0) { // ship sunk - note a hit and parse the length of the sunken ship
			hit = true;
			sinkCount++;
			sunkenShipLength = Integer.parseInt(input);
		}
		else { // terminate with an error for non-validated input
			System.err.println("input not validated: " + input);
			System.exit(1);
		}
		guesser.report(guess, hit, sunkenShipLength); // report the feedback to the BattleshipGuesser
	}

	/**
	 * Play a Battleship game using a text interface and the BattleshipGuesser specified below.
	 * @param args (not used)
	 */
	public static void main(String[] args) {
		// TODO - Try out the advice of your BattleshipGuesser here:
		new BattleshipAdvisor(new MyBattleshipGuesser()).playGame();
	}

}
