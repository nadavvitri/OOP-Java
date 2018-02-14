/**
 * Created by nadav vitri on 16/03/2017.
 */

/**
 * The Move class represents a move in the Nim game by a player. A move consists of the row on which it is
 * applied, the left bound (inclusive) of the sequence of sticks to mark, and the right bound
 * inclusive) of the same sequence.
 */
public class Move {

    private int inRow;
    private int inLeft;
    private int inRight;

    /**
     * Constructs a Move object with the given parameters
     * @param inRow - The row on which the move is performed.
     * @param inLeft - The left bound of the sequence to mark.
     * @param inRight - The right bound of the sequence to mark.
     */
    public Move(int inRow, int inLeft, int inRight){
        this.inRow = inRow;
        this.inLeft = inLeft;
        this.inRight = inRight;
    }

    /**
     * @return The left bound of the stick sequence to mark.
     */
    public int getLeftBound(){
        return this.inLeft;
    }

    /**
     * @return The right bound of the stick sequence to mark.
     */
    public int getRightBound(){
        return this.inRight;
    }

    /**
     * @return The row on which the move is performed.
     */
    public int getRow(){
        return this.inRow;
    }

    /**
     * @return a string representation of the move. For example, if the row is 2, the left bound of the
     * sequence is 3 and the right bound is 5, this function will return
     * the string "2:3-5" (without any spaces).
     */
    public String toString(){
        return this.getRow() + ":" + this.getLeftBound() + "-" + this.getRightBound();
    }
}
