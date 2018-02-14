package filters;
import exceptions.*;
import java.io.File;

/**
 * Created by nadav vitri & avichail vogel on 24/05/2017.
 */

/**
 * This class extend Filter class and represent the "greater_than" filter - returns files that there sizes
 * are strictly greater then the given size.
 */
public class Greater_than extends Filter {

    private final int KILO = 1024;
    private double smallerSize;

    /**
     * Constructor
     * @param smallerSize - the lower size bound.
     * @param isNot - true if "NOT" is in the command line of under "FILTER".
     * @param lineNum - the number of this command line in the commands file.
     * @throws Type1Exception if in the second part of the line something else write that is not exactly
     * "YES" or "NO".
     */
    public Greater_than(double smallerSize, boolean isNot ,int lineNum) throws Type1Exception {
        if (lineNum >= 0){
            this.smallerSize = smallerSize;
        }else {
            throw new Type1Exception(lineNum);}
        not = isNot;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname - The abstract pathname to be tested.
     * @return true if and only if pathname should be included.
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname.isFile()) {
            if (not) {
                return pathname.length() / KILO < smallerSize;
            } else {
                return pathname.length() / KILO > smallerSize;
            }
        }return false;
    }
}
