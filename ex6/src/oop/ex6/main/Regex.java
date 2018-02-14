package oop.ex6.main;
import java.util.regex.Pattern;

/**
 * Created by avichail & nadav vitri on 12/06/2017.
 */

/**
 * This class holds almost all necessary regex in this package
 */
class Regex {
    // These constant strings build the pattern for all the regexs used in the Regex class
    final String FINAL = "(final)";
    final String TYPE = "(int|boolean|char|double|String)";
    final String BOOLEAN_VAL_REGEX = "(true|false|\\d+(\\.\\d+|\\d*))";
    final String BOOLEAN_TYPE_REGEX = "(int|double|boolean)";
    final String CONDITION_NAME = "(if|while)";
    final String VOID = "(void)";
    final String RETURN = "(return)";
    final String END_LINE = ";\\s*$";
    final String EMPTY_STRING ="\\s*";
    final String CLOSE_BRACE = "[)]\\s*$";
    final String OPEN_BRACE = "\\s*^[(]";
    final String METHOD_END_LINE = "\\{\\s*$";
    final String CLOSE_CURLY_BRACE = "^\\s*}\\s*$";
    final String COMMENT = "^\\s*[/]{2}";
    final String NAME = "(\\s*[A-Za-z]\\w*|_\\w+)";
    final String METHOD_NAME = "^([A-Za-z]\\w*)";
    final String VALUE =  "\\-?\\w+(\\.\\w+|\\w*)|(\\'[^,]\\')|(\"\\s*[^,]*\\s*\")";
    final String VARIABLE_ASSIGNMENT = NAME+"\\s*=\\s*(\"*" + VALUE + "\"*)";


    // Variables declaration and assignment patterns
    final Pattern VAR_ASGMNT = Pattern.compile(VARIABLE_ASSIGNMENT);
    final Pattern IS_FINAL = Pattern.compile(FINAL);
    final Pattern HAS_TYPE = Pattern.compile(TYPE);
    final Pattern VAR_NAME = Pattern.compile(NAME);
    final Pattern RETURN_LINE = Pattern.compile(RETURN+END_LINE);
    final Pattern COMMENT_LINE = Pattern.compile(COMMENT);
    final Pattern SEMICOLON = Pattern.compile(END_LINE);
    final Pattern OPEN_BRACES = Pattern.compile(METHOD_END_LINE);
    final Pattern CLOSE_SCOPE = Pattern.compile(CLOSE_CURLY_BRACE);
    final Pattern IS_METHOD = Pattern.compile(VOID);
    final Pattern NAME_OF_METHOD = Pattern.compile(METHOD_NAME);
    final Pattern REG_CLOSE_BRACE = Pattern.compile(CLOSE_BRACE);
    final Pattern REG_OPEN_BRACE = Pattern.compile(OPEN_BRACE);
    final Pattern CONDITION = Pattern.compile(CONDITION_NAME);
    final Pattern BOOLEAN_VALUE = Pattern.compile(BOOLEAN_VAL_REGEX);
    final Pattern BOOLEAN_TYPE = Pattern.compile(BOOLEAN_TYPE_REGEX);
    final Pattern IF_EMPTY_STRING = Pattern.compile(EMPTY_STRING);

}
