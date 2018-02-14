package oop.ex6.exceptions;

/**
 * Created by avichail & nadav vitri on 12/06/2017.
 */

/**
 * This class represent the syntax error in the sjava file.
 */
public class TextExceptions extends Exception {

    private String WARNING = "Syntax error in file!";

    public TextExceptions(){
    }
    /**
     * the detail message string of this throwable.
     * @return the detail message string of this Throwable instance (which may be null).
     */
    @Override
    public String getMessage() {
        return (WARNING);
    }
}
