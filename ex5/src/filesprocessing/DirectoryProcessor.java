package filesprocessing;
import exceptions.*;
import filters.*;
import orders.Order;
import java.io.*;
import java.util.*;

/**
 * Created by nadav vitri & avichail vogel on 24/05/2017.
 */

/**
 * The main class that run the program.
 */
public class DirectoryProcessor {

    private File folder, commandFile;
    private Filter currentFilter;
    private Order currentOrder;
    private File[] filteredList;
    private String[] orderCommand;
    private int lineCounter;
    private boolean isNot = false;

    private static int BETWEEN_CONMAND_LENGTH = 4,NON_BETWEEN_COMMAND_LENGTH = 3;
    private static String ARGS_ERROR = "problems in arguments!";
    private static String IO_ERROR = "Input/Output problem!";
    private static String FILTER_MISSING = "sub-section FILTER is missing";
    private static String ORDER_MISSING = "sub-section ORDER is missing";
    private static final String HIDDEN = "hidden",BETWEEN = "between",GREATER_THAN="greater_than",FILE = "file"
            ,SMALLER_THAN = "smaller_than",EXECUTABLE = "executable",WRITABLE = "writable",SUFFIX = "suffix",
            PREFIX = "prefix",CONTAINS = "contains",ALL="all",NOT = "NOT",FILTER = "FILTER",ORDER = "ORDER";


    /**
     * Constructor
     * @param sourcedir - the path to the folder we want to filter and order.
     * @param commandfile - the file with the Orders and Filters commands.
     */
    public DirectoryProcessor(String sourcedir, String commandfile) {
        folder = new File(sourcedir);
        commandFile = new File(commandfile);
    }

    /**
     * The factory of the Filters - the function check what filter asked in the command file and call to
     * create the wanted filter. the default filter is "all".
     * @param filterLine - the line under FILTER (the line with the command for filter).
     * @return the wanted filter.
     */
    private Filter filterFactory(String filterLine){
        String[] parts = filterLine.split("#");
            try {
                validNot(parts, parts[0]);
                switch (parts[0]){
                case HIDDEN: {
                    return new Hidden(parts[1], isNot, lineCounter);
                }
                case BETWEEN:{
                    return new Between(Double.parseDouble(parts[1]),Double.parseDouble(parts[2])
                            ,isNot, lineCounter);
                }
                case GREATER_THAN:{
                    return new Greater_than(Double.parseDouble(parts[1]),isNot, lineCounter);
                }
                case SMALLER_THAN:{
                    return new Smaller_than(Double.parseDouble(parts[1]), isNot, lineCounter);
                }
                case EXECUTABLE: {
                    return new Executable(parts[1], isNot, lineCounter);
                }
                case WRITABLE: {
                    return new Writable(parts[1], isNot, lineCounter);
                }
                case SUFFIX: {
                    return new Suffix(parts[1], isNot);
                }
                case PREFIX: {
                    return new Prefix(parts[1], isNot);
                }
                case CONTAINS: {
                    return new Contains(parts[1], isNot);
                }
                case FILE: {
                    return new File_Fillter(parts[1], isNot);
                }
                case ALL: {
                    return new Filter();
                }
                default: {
                    throw new Type1Exception(lineCounter);}
                }
            }catch (Type1Exception e){
                System.err.println(e.getMessage());
                return new Filter();}
    }

    /**
     * This function check if "NOT" has been write in the line of commands for filter. if "NOT" is in the
     * line we simply change to the opposite the previous condition.
     * @param args the line under FILTER.
     * @param currentCase the second arg - if is "between" we need to check the last part of the line which
     * is the fourth part of the line.
     * @throws Type1Exception - if there is something in the last part of the line that is not exactly "NOT".
     */
    private void validNot(String[] args, String currentCase) throws Type1Exception {
        if (args.length == NON_BETWEEN_COMMAND_LENGTH && !Objects.equals(currentCase, BETWEEN)){
            if (Objects.equals(args[2], NOT)){
                isNot = true;
            }else {
                throw new Type1Exception(lineCounter);}}
        // in case between enter we need to check the fourth part of the line
        else if (args.length == BETWEEN_CONMAND_LENGTH && Objects.equals(currentCase, BETWEEN)){
            if (Objects.equals(args[3], NOT)){
                isNot = true;
            }else {
                throw new Type1Exception(lineCounter);}}
    }

    /**
     * This function read the commands file line by line (and update the lineCounter to save the line number
     * if a problem raised suddenly), and send to filter Factory the line of the commands for filter and the
     * line of the commands for ordering to Order.
     * At the end the function print all the files in the desirable ordering and after filtering.
     * @throws IOException - if an Input/Output exception of some sort has occurred.
     */
    private void filterDir() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(commandFile));
        try {
            String line = null;
            // if no more lines the readLine() returns null
            while ((line = br.readLine()) != null) {
                lineCounter ++;
                // reading lines until the end of the file
                if (Objects.equals(line, FILTER)){
                    line = br.readLine();
                    lineCounter ++;
                    currentFilter = filterFactory(line);
                    filteredList = folder.listFiles(currentFilter);
                    line = br.readLine();
                    lineCounter ++;
                    if (Objects.equals(line, ORDER)){
                        br.mark(lineCounter);
                        line = br.readLine();
                        if (line != null && !Objects.equals(line, FILTER)) {
                            orderCommand = line.split("#");
                            lineCounter++;
                        }
                        // if after ORDER there is FILTER or null
                        else {
                            orderCommand = new String[0];
                            br.reset();
                        }
                        currentOrder = new Order(filteredList, orderCommand, lineCounter);
                        currentOrder.printFilesNames();}}
                }}
        finally {
            br.close();}
    }

    /**
     * This function check for type two error. there are many potential type 2 error. in simple words we
     * first check the input/output or args problems. after that we run over the command file and check every
     * section with FILTER and ORDERS are there and if ther wrote correctly.
     * @param args - the args that enter to the program.
     * @throws Type2Exception - if type 2 or errors occurs (more detail in the type2exception).
     */
    private static void checkForType2Error(String[] args) throws Type2Exception, IOException {
        File commandFile = new File(args[1]);
        File folder = new File(args[0]);
        String[] commandArray = commandFileToArray(commandFile);
        if (args.length != 2 || !commandFile.isFile() || !folder.isDirectory()){
            throw new Type2Exception(ARGS_ERROR);}
        if (!commandFile.canExecute() || !commandFile.canRead()){
            throw new Type2Exception(IO_ERROR);}
        for (int i = 0; i < commandArray.length; i++){
            if (!Objects.equals(commandArray[i], FILTER)){
                throw new Type2Exception(FILTER_MISSING);}
            else if (commandArray.length == i + 1){
                throw new Type2Exception(ORDER_MISSING);}
            // there are less two lines in advance
            else if (commandArray.length > i+2){
                i += 2;}
            // if "FILTER" in line then and there are less two lines check if there "ORDER"
            if (!Objects.equals(commandArray[i], ORDER)){
                throw new Type2Exception(ORDER_MISSING);
            }
            if (commandArray.length != i + 1 && !Objects.equals(commandArray[i + 1], FILTER) ){
                i++;
                if (commandArray.length != i + 1 && !Objects.equals(commandArray[i + 1], FILTER)){
                    throw new Type2Exception(FILTER_MISSING);}
            }
        }
    }

    /**
     * This function get the command file and return text file content line by line into array.
     * @param commandFile - File with the commands lines.
     * @return array content all the lines from the file.
     * @throws IOException if an Input/Output exception of some sort has occurred.
     */
    private static String[] commandFileToArray(File commandFile) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(commandFile));
        String str = null;
        List<String> linesList = new ArrayList<String>();
        while((str = in.readLine()) != null){
            linesList.add(str);}
        String[] linesArray = linesList.toArray(new String[linesList.size()]);
        return linesArray;}

    /**
     * The main function that run the program.
     * @param args - the argument for the program.
     */
    public static void main(String[] args) {
        try {
            //first check for type two errors
            checkForType2Error(args);
            DirectoryProcessor bla = new DirectoryProcessor(args[0], args[1]);
            bla.filterDir();
        } catch (Type2Exception | IOException e) {
            System.err.println(e.getMessage());}
    }
}
