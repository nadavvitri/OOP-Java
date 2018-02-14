package filters;
import java.io.File;
import java.util.Objects;

/**
 * Created by nadav vitri & avichail vogel on 25/05/2017.
 */

/**
 * This class extend Filter class and represent the "file" filter - returns files that there names are equal
 * to the given value.
 */
public class File_Fillter extends Filter {

    private String value;

    /**
     * Constructor
     * @param someValue - value we check if equals to the file name.
     * @param isNot - true if "NOT" is in the command line of under "FILTER".
     */
    public File_Fillter(String someValue, boolean isNot) {
        value = someValue;
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
                if (!Objects.equals(pathname.getName(), value)){
                    return true;}
            }else if (Objects.equals(pathname.getName(), value)){
                return true;}
        }return false;
    }
}
