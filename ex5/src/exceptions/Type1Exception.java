package exceptions;

/**
 * Created by nadav vitri & avichail vogel on 24/05/2017.
 */

/**
 * This class handle Type I Errors.
 * A bad FILTER/ORDER name (e.g., greaaaater_than). These names are also case sensitive (e.g., Size is an
 * illegal order name, and should result in an error).
 * Bad parameters to the hidden/writable/executable filters (anything other than YES/NO).
 * Bad parameters to the greater_than/between/smaller_than filters (negative number).
 * Illegal values for the between filter (for example - between#15#7).
 * Type I errors should result in printing "Warning in line X" and continuing normally. X is the line number
 * where the FILTER/ORDER problem occurred, where 1 is the line number of the first line.
 */
public class Type1Exception extends Exception {

    private String WARNING = "Warning in line ";
    private int lineNumber;

    /**
     * Constructor
     * @param line - the number of the line where problem occur.
     */
    public Type1Exception(int line){
        lineNumber = line;}

    /**
     * the detail message string of this throwable.
     * @return the detail message string of this Throwable instance (which may be null).
     */
    @Override
    public String getMessage() {
        return (WARNING + lineNumber);
    }
}
