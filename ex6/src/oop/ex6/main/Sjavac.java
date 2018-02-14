package oop.ex6.main;
import oop.ex6.exceptions.TextExceptions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

/**
 * Created by avichail & nadav vitri on 12/06/2017.
 */

/**
 * This class runs test on a file, in order to test if that file holds a valid s-java code.
 */
public class Sjavac {

    private final static String LEGAL_CODE = "0", ILLEGAL_CODE = "1", IO_ERROR = "2";

    /**
     * Converts the file into string array in order to use it easily.
     * @param code - The file that holds the supposed s-java code
     * @return - String array containing all the files lines
     * @throws FileNotFoundException - If failed to find the given file.
     */
    private static String[] fileToStringArray(File code) throws FileNotFoundException {
        Scanner sc = new Scanner(code);
        List<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        return lines.toArray(new String[0]);
    }

    /**
     * The main method who runs the show. Receives the file address, tries to build a program tree out of it
     * and catch all the exception that might accord.
     * @param args - The program arguments
     */
    public static void main(String[] args) {
        // Tries to create a Program tree object. If a new object was created it means the code can be
        // compiled. Else, the program tree class will throw an exception.
        try {
            String[] codeToCheck = fileToStringArray(new File(args[0]));
            ProgramTree programTree = new ProgramTree(codeToCheck);
            System.out.println(LEGAL_CODE);
        }
        // Any syntax error that accord in the file
        catch (TextExceptions e){
            System.err.println(e.getMessage());
            System.out.println(ILLEGAL_CODE);
        }
        // I/O exceptions
        catch (IOException e){
            System.err.println(e.getMessage());
            System.out.println(IO_ERROR);
        }
    }
}
