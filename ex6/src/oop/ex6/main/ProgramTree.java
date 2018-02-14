package oop.ex6.main;
import oop.ex6.exceptions.TextExceptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.*;
import oop.ex6.variables.*;

/**
 * Created by avichail & nadav vitri on 12/06/2017.
 */

/**
 * This structure represents a written code, represented as a tree data structure where every "node" is a
 * scope. Each scope holds information about his upper scope, any inner scope that it holds( as it sons) and
 * the information that was written in it such as variable declarations and the scope's code lines.
 */
class ProgramTree {
    // Constants and fields used by any ProgramTree object
    final Regex regex = new Regex();
    final String METHOD = "method";
    private final String INT = "int", BOOLEAN = "boolean",DOUBLE = "double";
    boolean isFinalLine = false;
    final int ONLY_SCOPE_IS_OPEN = 1;
    private String lineType, methodName;
    private Scope currentScope, globalScope;

    /**
     * Creates new ProgramTree object. Every program tree object has a global scope as it root. The object
     * receives a string array holding s-java code lines, and tries to build a tree out of that code.
     * @param codeToCheck - string array holding s-java code lines
     * @throws TextExceptions - if there were any syntax error that accord in the file.
     */
    ProgramTree(String[] codeToCheck) throws TextExceptions {
        globalScope = new Scope();
        currentScope = globalScope;
        firstCheck(codeToCheck);}

    /**
     * Goes over all the code, updates all the global variables, global conditions and all the method
     * (while keeping for every method all it code lines). Then runs a test on each method lines.
     * @param codeLines - string array holding s-java code lines
     * @throws TextExceptions - if there were any syntax error that accord in the file.
     */
    private void firstCheck(String[] codeLines) throws TextExceptions {
        int bracesCounter = 0;
        // runs all the code lines
        for (String codeLine : codeLines){
            // if the line represents a new method declaration or a new condition
            if (regex.OPEN_BRACES.matcher(codeLine).find()){
                if (currentScope == globalScope){
                    openScopeLine(codeLine);
                }
                else{
                    currentScope.linesToCheck.add(codeLine);
                }
                bracesCounter++;
            }
            // if the line represents a close for method or a condition
            else if (regex.CLOSE_SCOPE.matcher(codeLine).matches()){
                currentScope.linesToCheck.add(codeLine);
                if (bracesCounter == ONLY_SCOPE_IS_OPEN) {
                    if (currentScope.parent != null) {
                        currentScope = currentScope.parent;
                    } else {
                        throw new TextExceptions();
                    }
                }
                bracesCounter--;
            }
            // if the line represents a code inside a method or condition
            else if (bracesCounter > 0){
                currentScope.linesToCheck.add(codeLine);
            }
            // if the line is a "return;" line
            else if (regex.RETURN_LINE.matcher(codeLine).find()){
                continue;
            }
            // if the line end with a ";"
            else if (regex.SEMICOLON.matcher(codeLine).find()) {
                codeLine = isFinalLine(codeLine);
                semicolonLine(codeLine);
            }
            // if the line starts with a "//" or if it's empty
            else if (regex.COMMENT_LINE.matcher(codeLine).find() ||
                    regex.IF_EMPTY_STRING.matcher(codeLine).matches()){
                continue;
            }
            // illegal line
            else {
                throw new TextExceptions();
            }
        }
        // if at least one scope was'nt closed with a '}'
        if (bracesCounter != 0){
            throw new TextExceptions();
        }
        // runs code check on each method's code lines
        for (Scope tempScope : globalScope.innerScopes){
            currentScope = tempScope;
            buildTree(tempScope.linesToCheck);
        }
    }

    /**
     * Goes over each method code lines and continue to build the program tree.
     * @param codeLines - string array holding s-java code lines
     * @throws TextExceptions - if there were any syntax error that accord in the file.
     */
    private void buildTree(List<String> codeLines) throws TextExceptions {
        String previousLine = null;
        for(String codeLine:codeLines){
            // check is the "final" modifier appears in the line
            codeLine = isFinalLine(codeLine);
            // if the line is a "return;" line
            if (regex.RETURN_LINE.matcher(codeLine).find()){
                previousLine = codeLine;
                continue;
            }
            // continue iff the line closed with ';'
            else if (regex.SEMICOLON.matcher(codeLine).find()) {
                semicolonLine(codeLine);
            }
            // continue iff the line closed with '{'
            else if (regex.OPEN_BRACES.matcher(codeLine).find()) {
                openScopeLine(codeLine);
            }
            // continue iff the line closed with '}'
            else if (regex.CLOSE_SCOPE.matcher(codeLine).matches()){
                closeScope(previousLine);
            }
            // if the line starts with a "//" or if it's empty
            else if (regex.COMMENT_LINE.matcher(codeLine).find() ||
                    regex.IF_EMPTY_STRING.matcher(codeLine).matches()){
                continue;
            }
            //illegal line
            else {
                throw new TextExceptions();
            }
        }
    }

    /**
     * Handel lines that represent a new method declaration or a condition
     * @param line - code to check
     * @throws TextExceptions - syntax error
     */
    private void openScopeLine(String line) throws TextExceptions {
        line = line.replaceAll("[{]", "");
        line = line.trim();
        Matcher methodMatcher = regex.IS_METHOD.matcher(line);
        Matcher conditionMatcher = regex.CONDITION.matcher(line);
        // if it's a new method code
        if (methodMatcher.lookingAt()) {
            line = line.substring(methodMatcher.end());
            line = line.trim();
            Matcher nameMatcher = regex.NAME_OF_METHOD.matcher(line);
            // if the method has valid name
            if (nameMatcher.lookingAt()) {
                methodName = nameMatcher.group(0);
                // adds the new method to the tree
                MethodScope methodScope = new MethodScope(currentScope, methodName);
                currentScope.innerScopes.add(methodScope);
                currentScope = methodScope;
                line = line.substring(nameMatcher.end());
                line = checkBraces(line);
                // updates the method parameters
                methodParams(line.split(","));
            }
        }
        // if it's a condition code
        else if (conditionMatcher.lookingAt()) {
            ConditionScope conditionScope = new ConditionScope(currentScope);
            // adds the new condition to the tree
            currentScope.innerScopes.add(conditionScope);
            currentScope = conditionScope;
            line = checkBraces(line.substring(conditionMatcher.end()).trim());
            String[] splittedLine = line.split("[||]{2}|&&{1}");
            // checks if the condition holds a boolean value
            for (String linePart : splittedLine){
                isBooleanValue(linePart);
            }
        }
        // illegal syntax
        else {
            throw new TextExceptions();
        }
    }

    /**
     * Handel a code that call's for a scope close
     * @param previousLine - the line before "{"
     * @throws TextExceptions - syntax error
     */
    private void closeScope(String previousLine) throws TextExceptions {
        // if the scope to close is a method
        if (Objects.equals(currentScope.type, METHOD)) {
            Matcher returnMatcher = regex.RETURN_LINE.matcher(previousLine);
            // if the method didn't end with a return line
            if (!returnMatcher.matches()) {
                throw new TextExceptions();
            }
            // get to the upper scope, if available.
            if (currentScope.parent != null) {
                currentScope = currentScope.parent;
            } else {
                throw new TextExceptions();
            }
        }
    }

    /**
     * Handel lines that end with a semicolon, such as variable declaration, variable assignment and 'return'.
     * @param line - code to check
     * @throws TextExceptions - syntax error
     */
    private void semicolonLine (String line) throws TextExceptions {
        line = line.replaceAll(";", "");
        line = line.trim();
        Matcher typeMatcher = regex.HAS_TYPE.matcher(line);
        Matcher methodNameMatcher = regex.NAME_OF_METHOD.matcher(line);
        Matcher nameMatcher = regex.VAR_ASGMNT.matcher(line);
        // if the line holds a type modifier
        if (typeMatcher.lookingAt()) {
            lineType = typeMatcher.group(1);
            line = line.substring(typeMatcher.end());
            // create a new Variable object for the current scope
            variableFactory(line.split(","));
        }
        // if the line is a variable reassignment code
        else if (nameMatcher.matches()) {
            if (!checkVarAssignment(nameMatcher.group(1), nameMatcher.group(2))) {
                throw new TextExceptions();
            }
        }
        // if the line is a code to call a method
        else if (methodNameMatcher.lookingAt()){
            String name = methodNameMatcher.group(1);
            line = line.substring(methodNameMatcher.end());
            line = checkBraces(line);
            // checks if the method even exist and if it was called right
            checkValidMethod(name, line.split(","));
        }
    }
    /**
     * This method add new Variable objects to the current scope variable list.
     * @param subStrings - new variables code
     * @throws TextExceptions - syntax error
     */
    private void variableFactory(String[] subStrings) throws TextExceptions {
        for (String var : subStrings) {
            var = var.trim();
            // checks if the variable was declared right
            checkValidDeclaration(var);
            Matcher nameMatcher = regex.VAR_NAME.matcher(var);
            Matcher assignmentMatcher = regex.VAR_ASGMNT.matcher(var);
            // if the variable was only declared (with no assignment)
            if (nameMatcher.matches()) {
                currentScope.scopeVariables.add(new Variable(isFinalLine, lineType, var, null,
                        false,false));
            }
            // if the variable was only declared and assigned
            else if (assignmentMatcher.matches()) {
                // if it was assigned with another variable
                if (isVariable(lineType, assignmentMatcher.group(2))){
                    currentScope.scopeVariables.add(new Variable(isFinalLine, lineType,
                            assignmentMatcher.group(1), false));
                }
                // if it was assigned with a value
                else {
                    currentScope.scopeVariables.add(new Variable(isFinalLine, lineType,
                            assignmentMatcher.group(1), assignmentMatcher.group(2),
                            true,false));
                }
            }
            else {
                throw new TextExceptions();}
        }
    }

    /**
     * This method checks if a specific method even exist and if it was called right
     * @param methodName - method name
     * @param params - method's parameters
     * @return - true iff the method was called right
     * @throws TextExceptions - if the method doesn't exist
     */
    private boolean checkValidMethod(String methodName, String[] params) throws TextExceptions {
        // get the root of all the scopes (global scope)
        for (Scope curScope : globalScope.innerScopes) {
            // if such method exist with the same params number
            if ((Objects.equals(curScope.type, METHOD)) && (Objects.equals(curScope.name, methodName))
                    && (curScope.parameters.size() == params.length || Objects.equals(params[0], ""))) {
                int counter = 0;
                // if the method was called with the right params
                for (Variable variable : curScope.parameters) {
                    if (!isVariable(variable.getType(), params[counter].trim())) {
                        if (!variable.checkValue(variable.getType(), params[counter].trim())) {
                            throw new TextExceptions();
                        }
                    }
                    counter++;
                }return true;
            }
        }
        throw new TextExceptions();
    }

    /**
     * In case of a new variable declaration, checks if it valid. Means, if that variable name wasn't
     * assigned already in the current scope.
     * @param varName - variable name to check
     * @throws TextExceptions - syntax error
     */
    private void checkValidDeclaration(String varName) throws TextExceptions {
        // check if there is variable with the same name in the current scope
        for (Variable var : currentScope.scopeVariables){
            if (Objects.equals(var.getName().trim(), varName.trim())){
                throw new TextExceptions();}
        }
    }
    /**
     * In case of a new assignment, checks if it's a valid one. Means, if that variable was declared in
     * the current scope or in it upper scope's.
     * @param name - variable name to check
     * @param value - the value to checks
     * @return - true iff the assignment was right, false otherwise.
     */
    private boolean checkVarAssignment(String name, String value){
        Scope tempScope = currentScope;
        // checks in any of it parents scope
        while (currentScope != null){
            // check is the variable was declared somewhere legal
            for (Variable var : currentScope.scopeVariables){
                if (Objects.equals(var.getName(), name)){
                    // if the assignment was right according to the declaration
                    if (var.checkValue(var.getType(), value) && !var.getIsFinal()){
                        currentScope = tempScope;
                        return true;
                    }
                }
            }
            currentScope = currentScope.parent;
        }
        currentScope = tempScope;
        // if the assignment was wrong
        return false;
    }
    /**
     * checks is a given variable can be assigned with another variable, according to their types
     * @param varToAssignTo - the variable the is getting "copied"
     * @param assignedVar - the variable to assign
     * @return - true iff the given variable can be assigned, false otherwise.
     */
    private boolean validTypeAssignment(String varToAssignTo ,String assignedVar) {
        return Objects.equals(varToAssignTo, assignedVar) || Objects.equals(assignedVar, DOUBLE) &&
                (Objects.equals(varToAssignTo, INT)) || Objects.equals(assignedVar, BOOLEAN) &&
                ((Objects.equals(varToAssignTo, INT) || (Objects.equals(varToAssignTo, DOUBLE))));
    }
    /**
     * Updates the parameters eah method holds
     * @param subStrings - the param
     * @throws TextExceptions - syntax error
     */
    private void methodParams(String[] subStrings) throws TextExceptions {
        for (String var : subStrings){
            var = var.trim();
            var = isFinalLine(var);
            var = var.trim();
            Matcher typeMatcher = regex.HAS_TYPE.matcher(var);
            // the current param type
            if (typeMatcher.lookingAt()){
                lineType = typeMatcher.group(0);
                var = var.substring(typeMatcher.end());
                Matcher nameMatcher = regex.VAR_NAME.matcher(var);
                // the current param name
                if (nameMatcher.matches()){
                    checkValidDeclaration(var);
                    currentScope.scopeVariables.add(new Variable(isFinalLine, lineType, var, null,
                            false, true));
                    currentScope.parameters.add(new Variable(isFinalLine, lineType, var, null,
                            false, true));
                }else {
                    throw new TextExceptions();
                }
            }
            // illegal syntax
            else if (!Objects.equals(var, "")){
                throw new TextExceptions();
            }
        }
    }

    /**
     * Erases the first and last braces of the line
     * @param line - line to update
     * @return - updated line
     * @throws TextExceptions - syntax error
     */
    private String checkBraces(String line) throws TextExceptions {
        line = line.trim();
        Matcher openBraceMatcher = regex.REG_OPEN_BRACE.matcher(line);
        Matcher closeBraceMatcher = regex.REG_CLOSE_BRACE.matcher(line);
        if (openBraceMatcher.find() && closeBraceMatcher.find()){
            line = line.replaceFirst("[(]", "");
            if(line.endsWith(")"))
            {
                line = line.substring(0,line.length() - 1);
            }
            return line;
        }else {
            throw new TextExceptions();
        }
    }



    /**
     * Checks if the given value is a boolean one (int\double\boolean)
     * @param value - value to check
     * @throws TextExceptions - syntax error
     */
    private void isBooleanValue(String value) throws TextExceptions {
        value = value.trim();
        boolean validValueExist = false;
        if(regex.VAR_NAME.matcher(value).find()) {
            Matcher methodNameMatcher = regex.NAME_OF_METHOD.matcher(value);
            // if the value to check is a method name (illegal)
            if(methodNameMatcher.lookingAt()) {
                String name = methodNameMatcher.group(1);
                for (Scope method : globalScope.innerScopes) {
                    if (Objects.equals(method.name, name)) {
                        throw new TextExceptions();
                    }
                }
            }
            Scope tempScope = currentScope;
            while (currentScope != null) {
                for (Variable var : currentScope.scopeVariables) {
                    // if the value is a variable name
                    if (Objects.equals(var.getName(), value)) {
                        // if the variable isn't boolean one
                        if (!regex.BOOLEAN_TYPE.matcher(var.getType()).matches() || !var.getIfAssigned()) {
                            throw new TextExceptions();
                        }
                        else {
                            validValueExist = true;
                        }
                    }
                }
                currentScope = currentScope.parent;
            }
            currentScope = tempScope;
            // not valid boolean call
            if (!validValueExist && !regex.BOOLEAN_VALUE.matcher(value).matches()) {
                throw new TextExceptions();
            }
        }else if (!regex.BOOLEAN_VALUE.matcher(value).matches()){
            throw new TextExceptions();
        }
    }


    /**
     * If the line has a final modifier
     * @param line -- line to check
     * @return - true iff does, false otherwise
     */
    private String isFinalLine(String line) {
        Matcher finalMatcher = regex.IS_FINAL.matcher(line);
        if (finalMatcher.lookingAt()) {
            isFinalLine = true;
            line = line.substring(finalMatcher.end());
        } else {
            isFinalLine = false;
        }
        return line.trim();
    }


    /**
     * Checks if the variable was assigned with a variable or with a value
     * @param type - current variable type
     * @param valueToCheck - value to check if it's a variable
     * @return - true iff it's a variable, false if it's a value
     * @throws TextExceptions - syntax error
     */
    private boolean isVariable(String type, String valueToCheck) throws TextExceptions {
        Scope tempScope = currentScope;
        while (currentScope != null){
            for (Variable var : currentScope.scopeVariables){
                if (Objects.equals(var.getName(), valueToCheck)){
                    if ((var.getIsMethodParam()||var.getIfAssigned()) &&
                            validTypeAssignment(var.getType(),type)){
                        currentScope = tempScope;
                        return true;
                    }else {
                        throw new TextExceptions();}
                }
            }
            currentScope = currentScope.parent;
        }
        currentScope = tempScope;
        return false;
    }
}

    /**
     * This class represents the basic scope (AKA global scope). Each Scope is a "node" in Program tree object.
     */
    class Scope {
        final String GLOBAL = "global scope" ,METHOD = "method", CONDITION = "condition";
        String name;
        List<String> linesToCheck = new ArrayList<>();
        List<Scope> innerScopes = new ArrayList<>();
        List<Variable> scopeVariables = new ArrayList<>();
        List<Variable> parameters = new ArrayList<>();
        Scope parent;
        String type;
        Scope() {
            parent = null;
            type = GLOBAL;
            name = null;
        }
    }

    class MethodScope extends Scope{
        /**
         * method scope constructor
         * @param parent - scope parent
         * @param methodName - scope name
         */
        MethodScope(Scope parent, String methodName){
            super();
            this.parent = parent;
            type = METHOD;
            name = methodName;
        }
    }

    class ConditionScope extends Scope{
        /**
         * condition scope constructor
         * @param parent - scope parent
         */
        ConditionScope(Scope parent){
            super();
            this.parent = parent;
            type = CONDITION;
            name = null;
        }
    }

