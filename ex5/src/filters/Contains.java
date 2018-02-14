package filters;
import java.io.File;

/**
 * Created by nadav vitri & avichail vogel on 25/05/2017.
 */

/**
 * This class extend Filter class and represent the "contains" filter - returns files that the value is
 * contained in there files names (excluding path).
 */
public class Contains extends Filter {

    private String value;

    /**
     * Constructor
     * @param someValue - value we check if the file name contain.
     * @param isNot - true if "NOT" is in the command line of under "FILTER".
     */
    public Contains(String someValue, boolean isNot) {
        value = someValue;
        not = isNot;}

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname - The abstract pathname to be tested.
     * @return true if and only if pathname should be included.
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname.isFile()) {
            if (not) {
                if (!pathname.getName().contains(value)) {
                    return true;
                }
            } else if (pathname.getName().contains(value)) {
                return true;}}
        return false;
    }
}