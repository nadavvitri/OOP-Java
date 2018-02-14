package filters;
import exceptions.*;
import java.io.File;
import java.util.Objects;

/**
 * Created by nadav vitri & avichail vogel on 24/05/2017.
 */

/**
 * This class extend Filter class and represent the "executable" filter - returns files that they are
 * executable (permission).
 */
public class Executable extends Filter {

    /**
     * Constructor
     * @param value - the second part of the command line - "YES" or "NO".
     * @param isNot - true if "NOT" is in the command line of under "FILTER".
     * @param lineNum - the number of this command line in the commands file.
     * @throws Type1Exception - if in the second part of the line something else write that is not exactly
     * "YES" or "NO".
     * */
    public Executable(String value, boolean isNot, int lineNum) throws Type1Exception {
        if (Objects.equals(value, "YES")){
            yes = true;}
        else if (Objects.equals(value, "NO")){
            yes = false;
        } else throw new Type1Exception(lineNum);
        // if "NOT" change to the opposite the previous condition
        if (isNot){
            yes = !yes;}
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname - The abstract pathname to be tested.
     * @return true if and only if pathname should be included.
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname.isFile()){
            if ((pathname.canExecute() && yes) || (!pathname.canExecute()) && !yes){
                return true;}
        }return false;
    }
}
