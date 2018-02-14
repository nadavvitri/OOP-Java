package exceptions;

/**
 * Created by nadav vitri & avichail vogel on 24/05/2017.
 */

/**
 * This class handle Type II Errors.
 * Invalid usage (i.e., anything other than two program arguments, where the first is theSource Directory and
 * the second is the Commands File).
 * I/O problems - errors occurring while accessing the Commands File.
 * A bad sub-section name (i.e., not FILTER/ORDER). Sub-section names are case sensitive (e.g., filter is an
 * illegal sub-section name, and should result in an error).
 * Bad format of the Commands File (e.g., no ORDER sub-section).
 * Type II errors result in printing "ERROR: " with the informative message for the specific error.
 */
public class Type2Exception extends Exception {

    private String WARNING = "ERROR: ";
    private String message;

    /**
     * Constructor
     * @param typeError - the specific type 2 error occur is the program.
     */
    public Type2Exception(String typeError){
        message = typeError;
    }

    /**
     * the detail message string of this throwable.
     * @return the detail message string of this Throwable instance (which may be null).
     */
    @Override
    public String getMessage() {
        return WARNING + message;
    }
}
