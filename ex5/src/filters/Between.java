package filters;
import exceptions.*;
import java.io.File;

/**
 * Created by nadav vitri & avichail vogel on 24/05/2017.
 */

/**
 * This class extend Filter class and represent the "between" filter - returns files that there sizes are
 * between (inclusive) the given numbers (in k-bytes).
 */
public class Between extends Filter {

    private final int KILO = 1024;
    private double lowSize, highSize;

    /**
     * Constructor
     * @param lowSize - the lower size bound.
     * @param highSize - the upper size bound.
     * @param lineNum - the number of this command line in the commands file.
     * @param isNot - true if "NOT" is in the command line of under "FILTER".
     * @throws Type1Exception if in the second part of the line something else write that is not exactly
     * "YES" or "NO".
     */
    public Between(double lowSize, double highSize, boolean isNot, int lineNum) throws Type1Exception {
        if (lowSize >= 0 && highSize >= 0 && highSize >= lowSize){
            this.lowSize = lowSize;
            this.highSize = highSize;
        } else {
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
                return pathname.length() / KILO < lowSize || pathname.length() / KILO > highSize;
            } else {
                return pathname.length() / KILO >= lowSize && pathname.length() / KILO <= highSize;}
        }return false;
    }
}
