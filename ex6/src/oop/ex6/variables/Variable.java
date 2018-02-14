package oop.ex6.variables;
import oop.ex6.exceptions.TextExceptions;
import java.util.regex.*;

/**
 * Created by avichail & nadav vitri on 12/06/2017.
 */

/**
 * This class represent every variable in the program and hold all his data
 */
public class Variable {
    private final String INT = "int", STRING = "String", BOOLEAN = "boolean",DOUBLE = "double",CHAR = "char";
    private final String INT_REGEX = "\\-?\\d+";
    private final String DOUBLE_REGEX = "\\-?\\d+(\\.\\d+|\\d*)";
    private final String STRING_REGEX = "\"[^,\"\\\\']*\"";
    private final String BOOLEAN_REGEX = "(true|false|\\d+(\\.\\d+|\\d*))";
    private final String CHAR_REGEX = "\'[^,]\'";
    private String name, type;
    private boolean hasValidValue = false, isFinal, isMethodParam;

    /**
     * Constructor
     * @param hasFinalMod - if the variable is final.
     * @param someType - type of the variable.
     * @param someName - name of the variable.
     * @param value - value of the variable.
     * @param inMethodDec - if the variable is inside method parameters.
     * @param wasAssigned - if the variable was assign with value.
     * @throws TextExceptions - code is illegal ( 1 ).
     */
    public Variable(boolean hasFinalMod, String someType, String someName, String value, boolean wasAssigned,
                    boolean inMethodDec) throws TextExceptions {
        setType(someType);
        name = someName;
        isMethodParam = inMethodDec;
        if(wasAssigned && !inMethodDec){
            hasValidValue = checkValue(someType, value);
            // if the value type is not matching to the variable type
            if (!hasValidValue){
                throw new TextExceptions();
            }else {
                // if the variable is final or not
                isFinal = hasFinalMod;}
        }// if the variable is method parameter and is final
        else if (hasFinalMod && isMethodParam){
            isFinal = hasFinalMod;
        }// if the final variable assigned or the variable assigned in the method parameters
        else if((wasAssigned && isMethodParam)||(hasFinalMod && !isMethodParam)){
            throw new TextExceptions();
        }
    }

    /**
     * Constructor for assignment with variable
     * @param hasFinalMod - if the variable is final.
     * @param someType - type of the variable.
     * @param someName - name of the variable.
     * @param inMethodDec - if the variable is inside method parameters.
     * @throws TextExceptions - code is illegal ( 1 ).
     */
    public Variable(boolean hasFinalMod, String someType, String someName, boolean inMethodDec)
            throws TextExceptions {
        setType(someType);
        name = someName;
        if (hasFinalMod && !inMethodDec){
            isFinal = hasFinalMod;
        }// if we assigned final variable
        else if (hasFinalMod && inMethodDec){
            throw new TextExceptions();
        }
    }

    /**
     * This function check the type of the variable.
     * @param someType - the part of the code in the line that represent the variable type.
     */
    private void setType(String someType){
        switch (someType){
            case INT:{
                type = INT;
                break;}
            case STRING:{
                type = STRING;
                break;}
            case BOOLEAN:{
                type = BOOLEAN;
                break;}
            case DOUBLE:{
                type = DOUBLE;
                break;}
            case CHAR:{
                type = CHAR;
                break;}
        }
    }

    /**
     * This function check if the variable assignment is legal by checking if the type of the value is
     * compare to the variable type.
     * @param varType - the type of the variable.
     * @param someValue - value we want to check if compare to the variable type.
     * @return true iff type of value match to the variable type.
     */
    public boolean checkValue(String varType, String someValue){
        Pattern intRegex = Pattern.compile(INT_REGEX);
        Pattern doubleRegex = Pattern.compile(DOUBLE_REGEX);
        Pattern stringRegex = Pattern.compile(STRING_REGEX);
        Pattern booleanRegex = Pattern.compile(BOOLEAN_REGEX);
        Pattern charRegex = Pattern.compile(CHAR_REGEX);
        switch (varType){
            case INT:{
                Matcher m = intRegex.matcher(someValue);
                return  m.matches();}
            case DOUBLE:{
                Matcher m = doubleRegex.matcher(someValue);
                return  m.matches();}
            case (STRING): {
                Matcher m = stringRegex.matcher(someValue);
                return  m.matches();}
            case BOOLEAN: {
                Matcher m = booleanRegex.matcher(someValue);
                return  m.matches();}
            case CHAR: {
                Matcher m = charRegex.matcher(someValue);
                return  m.matches();}
            default:{return false;}
        }
    }

    /**
     * Getter of the variable type.
     * @return variable type.
     */
    public String getType(){
        return type;}

    /**
     * Getter of the variable name.
     * @return variable name.
     */
    public String getName(){
        return name.trim();}

    /**
     * Getter if the variable is final or not.
     * @return true if variable is final, else false.
     */
    public boolean getIsFinal(){
        return isFinal;}

    /**
     * Getter if the variable was assign with value.
     * @return true iff the variable was assigned.
     */
    public boolean getIfAssigned(){
        return hasValidValue;}

    /**
     * Getter if the variable is a parameter inside the method.
     * @return true iff variable is a parameter.
     */
    public boolean getIsMethodParam(){
        return isMethodParam;
    }
}
