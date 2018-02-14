/**
 * The Competition class represents a Nim competition between two players, consisting of a given number of
 * rounds. It also keeps track of the number of victories of each player.
 */
import java.util.Scanner;
public class Competition {

    private Player player1;
    private Player player2;
    boolean displayMessage;
    private int player1Wins;
    private int player2Wins;
    static int negativeNum = -1;
    private Move move1;
    private int firstPlayerId = 1;
    private int secondPlayerId = 1;
    private int humanPlayerType = 4;
    private int noMoreSticks = 0;


    /**
     * Receives two Player objects, representing the two competing opponents, and a flag determining
     * whether messages should be displayed.
     * @param player1 - The Player objects representing the first player.
     * @param player2 - The Player objects representing the second player.
     * @param displayMessage - a flag indicating whether game play messages should be printed to the console.
     */
	public Competition(Player player1, Player player2, boolean displayMessage){
	    this.player1 = player1;
	    this.player2 = player2;
	    this.displayMessage = displayMessage;
	    this.player1Wins = 0;
	    this.player2Wins = 0;

	}

//	public voi
    /**
     * If playerPosition = 1, the results of the first player is returned. If playerPosition = 2, the result
     * of the second player is returned. If playerPosition equals neiter, -1 is returned.
     * @param playerPosition - int for number of player.
     * @return the number of victories of a player.
     */
    public int getPlayerScore(int playerPosition){
	    if (playerPosition == firstPlayerId)
	        return this.player1Wins;
	    else if (playerPosition == secondPlayerId)
	        return this.player2Wins;
	    else
	        return negativeNum;
    }

    /**
     *Shows wins status to user if there is a human player.
     */
    public void showsWinToUser(boolean displayMessage){
        if (displayMessage){ // shows the wins (if one of the players is human)
            if (this.player2Wins > this.player1Wins)
                System.out.println("Player " + this.player2.getPlayerId() + " won!");
            else
                System.out.println("Player " + this.player1.getPlayerId() + " won!");}
    }

    /**
     *Run the game only for one turn.
     */
    public void playOneRound(){
        Board board = new Board();
        Player currentPlayer = player1;
        // shows this string if one of the players is Human.
        if ((player1.getPlayerType() == humanPlayerType) || (player2.getPlayerType() == humanPlayerType)){
            displayMessage = true;
            System.out.println("Welcome to the sticks game!");
        }while (board.getNumberOfMarkedSticks() <= 24){ // run the game until all the sticks marked
            if (displayMessage){
                System.out.println("Player " + currentPlayer.getPlayerId() + ", it is now your turn!");
                move1 = currentPlayer.produceMove(board);
                while (board.markStickSequence(move1) != noMoreSticks){
                    System.out.println("Invalid move. Enter another:");
                    move1 = currentPlayer.produceMove(board);
                }board.markStickSequence(move1);
                System.out.println("Player " + currentPlayer.getPlayerId() + " made the move: " +
                        move1.toString());}
            else move1 = currentPlayer.produceMove(board);
            board.markStickSequence(move1);
            if (board.getNumberOfUnmarkedSticks() == noMoreSticks) { // if the player mark the last stick
                if (currentPlayer.getPlayerId() == firstPlayerId)
                    this.player2Wins++;
                else
                    this.player1Wins++;
                break;}
            if (currentPlayer.getPlayerId() == firstPlayerId)
                currentPlayer = player2;
            else
                currentPlayer = player1;}
        showsWinToUser(displayMessage); // shows the wins (if one of the players is human)
    }

    /**
     *Run the game for the given number of rounds.
     * @param numberOfRounds - number of rounds to play.
     */
    public void playMultipleRounds(int numberOfRounds){
        for (int i = 0; i < numberOfRounds; i++){
            playOneRound();
        }
        System.out.println("The results are " + this.player1Wins + ":" + this.player2Wins);
    }

    /**
	 * The method runs a Nim competition between two players according to the three user-specified arguments. 
	 * (1) The type of the first player, which is a positive integer between 1 and 4: 1 for a Random computer
	 *     player, 2 for a Heuristic computer player, 3 for a Smart computer player and 4 for a human player.
	 * (2) The type of the second player, which is a positive integer between 1 and 4.
	 * (3) The number of rounds to be played in the competition.
	 * @param args an array of string representations of the three input arguments, as detailed above.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int p1Type = Integer.parseInt(args[0]);
		int p2Type = Integer.parseInt(args[1]);
		int numGames = Integer.parseInt(args[2]);
        Player player1 = new Player(p1Type, 1, scanner);
        Player player2 = new Player(p2Type,2,scanner);
		Competition competition = new Competition(player1,player2,false);
		System.out.println("Starting a Nim competition of " + numGames + " rounds between a "
                + player1.getTypeName() + " player and a " + player2.getTypeName() + " player.");
		competition.playMultipleRounds(numGames);
		scanner.close();

	}	
	
}
