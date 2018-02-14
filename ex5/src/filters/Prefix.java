package filters;
import java.io.File;

/**
 * Created by nadav vitri & avichail vogel on 25/05/2017.
 */

/**
 * This class extend Filter class and represent the "prefix" filter - returns files that the given value is
 * the prefix of there names.
 */
public class Prefix extends Filter {

    private String filterByPrefix;

    /**
     * Constructor
     * @param prefix - the prefix we check if equal to filename under the folder.
     * @param isNot - true if "NOT" is in the command line of under "FILTER".
     */
    public Prefix(String prefix, boolean isNot) {
        filterByPrefix = prefix;
        not = isNot;}

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname - The abstract pathname to be tested.
     * @return true if and only if pathname should be included.
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname.isFile()){
            if (not){
                if (!pathname.getName().startsWith(filterByPrefix)){
                    return true;}}
            else if (pathname.getName().startsWith(filterByPrefix)){
                return true;}
        }return false;
    }
}
