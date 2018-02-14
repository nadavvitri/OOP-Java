package filters;
import java.io.*;

/**
 * Created by nadav vitri & avichail vogel on 24/05/2017.
 */

/**
 * this class implements Interface FileFilter, and all the filter extend her.
 */
public class Filter implements java.io.FileFilter {

    protected boolean not = false;
    protected boolean yes = false;

    /**
     * Constructor
     */
    public Filter(){}

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname - The abstract pathname to be tested.
     * @return true if and only if pathname should be included.
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.isFile();
    }
}
