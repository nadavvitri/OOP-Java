package filters;
import java.io.File;

/**
 * Created by nadav vitri & avichail vogel on 24/05/2017.
 */

/**
 * This class extend Filter class and represent the "suffix" filter - returns files that the given value is
 * the suffix of there names.
 */
public class Suffix extends Filter {

    private String filterBySuffix;

    /**
     * Constructor
     * @param suffix - the suffix we check if equal to filename under the folder.
     * @param isNot - true if "NOT" is in the command line of under "FILTER".
     */
    public Suffix(String suffix, boolean isNot) {
        filterBySuffix = suffix;
        not = isNot;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname - The abstract pathname to be tested.
     * @return true if and only if pathname should be included.
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname.isFile()){
            if (not){
                if (!pathname.getName().endsWith(filterBySuffix)){
                    return true;}}
            else if (pathname.getName().endsWith(filterBySuffix)){
                return true;}
        }return false;
    }

}
