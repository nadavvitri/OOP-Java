package orders;
import exceptions.*;
import java.io.File;
import java.util.*;

/**
 * Created by nadav vitri & avichail vogel on 24/05/2017.
 */

/**
 * This class order the given filtered files list to the wanted order we ask.
 * Abs - sort files by absolute name going from ‘a’ to ‘z’.
 * Type - sort files by file type, going from ‘a’ to ‘z’.
 * Size - sort files by file size, going from smallest to largest.
 */
public class Order {

    private final String TYPE = "type";
    private final String ABS = "abs", SIZE = "size";
    private final int FIRST_ELEMENT_WINS = 1, SECOND_ELEMENT_WINS = -1, EQUALS_ELEMENTS = 0;
    private boolean isReversed = false;
    private int lineNum;
    private String orderType = ABS;
    private File[] orderedList;
    private Comparator<File> orderKind;

    /**
     * Constructor
     * @param filteredList - the filtered files list.
     * @param orderCommands - the line with the command for order from the command file.
     * @param lineNum - the number of this command line in the commands file.
     */
    public Order(File[] filteredList, String[] orderCommands, int lineNum){
        this.lineNum = lineNum;
        try {
            validOrderType(orderCommands);
            validOrderReverse(orderCommands);
            if (Objects.equals(orderType, TYPE)) {
                typeSort(filteredList);}
            else if (Objects.equals(orderType, ABS)) {
                absSort(filteredList);}
            else {
                sizeSort(filteredList);}
        }catch (Type1Exception e){
            System.err.println(e.getMessage());
            absSort(filteredList);}
        orderedList = filteredList;
    }

    /**
     * sorting the filtered files according to their absolute name from 'a' to 'z' ( or from 'z' to 'a' if
     * the REVERSE suffix was given).
     * @param filteredList - the filtered files list
     */
    private void absSort(File[] filteredList){
        orderKind = (o1, o2) -> {
            if (o1.getAbsolutePath().compareTo(o2.getAbsolutePath()) == EQUALS_ELEMENTS){
                return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
            }
            else if (o1.getAbsolutePath().compareTo(o2.getAbsolutePath()) > EQUALS_ELEMENTS){
                return FIRST_ELEMENT_WINS;
            }else {return SECOND_ELEMENT_WINS;}
        };
        Arrays.sort(filteredList,orderKind);
    }

    /**
     * sorting the filtered files according to their type from 'a' to 'z' ( or from 'z' to 'a' if the REVERSE
     * suffix was given).
     * @param filteredList - the filtered files list.
     */
    private void typeSort(File[] filteredList){
        orderKind = (o1, o2) -> {
            String o1Type = getFileExtension(o1);
            String o2Type = getFileExtension(o2);
            if (o1Type.compareTo(o2Type) == EQUALS_ELEMENTS){
                return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
            }
            else if (o1Type.compareTo(o2Type) > EQUALS_ELEMENTS){
                return FIRST_ELEMENT_WINS;
            }else {return SECOND_ELEMENT_WINS;}
        };
        Arrays.sort(filteredList,orderKind);
    }


    /**
     * sorting the filtered files according to their size from 'a' to 'z' ( or from 'z' to 'a' if the REVERSE
     * suffix was given).
     * @param filteredList - the filtered files list
     * */
    private void sizeSort(File[] filteredList){
        orderKind = (o1, o2) -> {
            if (o1.length() - (o2.length()) == EQUALS_ELEMENTS){
                return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
            }
            else if (o1.length() > o2.length()){
                return FIRST_ELEMENT_WINS;
            }else {return SECOND_ELEMENT_WINS;}
        };
        Arrays.sort(filteredList,orderKind);
    }

    /**
     * This function check if "REVERSE" has been write in the line of commands for filter. if "REVERSE" is
     * in the line we simply change to the opposite of this order.
     * @param orderCommand - the full line under "ORDER".
     * @throws Type1Exception - if there is something in the last part of the line that is not exactly
     * "REVERSE" or some command to order that is not type/abs/size.
     */
    private void validOrderReverse(String[] orderCommand) throws Type1Exception {
        if (orderCommand.length == 2) {
            if (Objects.equals(orderCommand[1], "REVERSE")) {
                isReversed = true;
            } else {
                throw new Type1Exception(lineNum);
            }
        }
    }

    /**
     * This function check for type 1 errors in the line.
     * @param orderCommand - the line under "ORDER" in the command file.
     * @throws Type1Exception - if there is something in the last part of the line that is not exactly
     * "REVERSE" or some command to order that is not type/abs/size.
     */
    private void validOrderType (String[] orderCommand)throws Type1Exception{
        if (orderCommand.length == 1 || orderCommand.length == 2){
            if (!Objects.equals(orderCommand[0], TYPE) && !Objects.equals(orderCommand[0], ABS) &&
                    !Objects.equals(orderCommand[0], SIZE)){
                throw new Type1Exception(lineNum);
            }else {orderType = orderCommand[0];}
        }
    }

    /**
     * This function get full file name and return the extension of the file.
     * @param file - the full name of the file.
     * @return the suffix of the file name.
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";}
    }

    /**
     * prints all names of the files in the ordered list.
     */
    public void printFilesNames() {
        if(isReversed){
            for (int i = orderedList.length-1; i >= 0; i--){
                System.out.println(orderedList[i].getName());}
        }else {
            for (File file : orderedList){
                System.out.println(file.getName());}
        }
    }
}

