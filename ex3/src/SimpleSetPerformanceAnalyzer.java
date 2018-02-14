import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * This class has a main method that measures and compare the run-times and performance of many types
 * of collection.
 */

public class SimpleSetPerformanceAnalyzer {

    private static SimpleSet[] collections  = new SimpleSet[5];
    private static final int NANO_TO_MS = 1000000;
    private static final int WARM_UP_ITERATIONS = 70000;
    private static final int LINKED_LIST = 3;
    private static String[] fileOne = Ex3Utils.file2array
            ("C:\\Users\\nadav vitri\\IdeaProjects\\ex3\\src\\data1.txt");
    private static String[] fileTwo = Ex3Utils.file2array
            ("C:\\Users\\nadav vitri\\IdeaProjects\\ex3\\src\\data2.txt");


    /**
     * Creating five different types of collection to insert data. 1. OpenHashSet 2. ClosedHashSet
     * 3. TreeSet 4.LinkedList 5. HashSet
     */
    private static void CreateArrayOfDifferentDataStructure() {
        collections [0] = new OpenHashSet();
        collections [1] = new ClosedHashSet();
        collections [2] = new CollectionFacadeSet(new TreeSet<String>());
        collections [3] = new CollectionFacadeSet(new LinkedList<String>());
        collections [4] = new CollectionFacadeSet(new HashSet<String>());
    }

    /**
     * @param number - index from collections that represent the type of the data structure.
     * @return String witch is the name of the collection type.
     */
    private static String typeOfSet(int number) {
        if (number == 0){
            return "Open Hash Set";
        }
        else if (number == 1){
            return "Closed Hash Set";
        }
        else if (number == 2){
            return "Tree Set";
        }
        else if (number == 3){
            return "Linked List";
        }
        return "Hash Set";
    }

    /**
     * this function calculate time for "contains' operation of strings for each collection type.
     * @param value - string we want to check if the set contain or not.
     */
    private static void takeTimeForContains(String value) {
        long difference;
        for (int i = 0; i < collections.length; i++){
            // we don't warm up before if is Linked List
            if (i == LINKED_LIST){
                long timeBefore = System.nanoTime();
                collections[i].contains(value);
                difference = System.nanoTime() - timeBefore;
            }else {
                for (int j = 0; j < WARM_UP_ITERATIONS; j++){
                    collections[i].contains(value);
                }
                long timeBefore = System.nanoTime();
                for (int j = 0; j < WARM_UP_ITERATIONS; j++){
                    collections[i].contains(value);
                }difference = (System.nanoTime() - timeBefore) / WARM_UP_ITERATIONS;
            }String typeOfSet = typeOfSet(i);
            System.out.println("Test: checking if contains " + value + " in " + typeOfSet +
                    " took " + difference);
        }
    }

    /**
     * this function calculate time for initializing file of strings for each collection type.
     * @param file - array of strings.
     */
    private static void takeTimeForInitializing(String[] file) {
        CreateArrayOfDifferentDataStructure();
        for (int i = 0; i < collections.length; i++){
            long timeBefore = System.nanoTime();
            for (String string : file) {
                    collections[i].add(string);}
            long difference = System.nanoTime() - timeBefore;
            String typeOfSet = typeOfSet(i);
            System.out.println("Test: Initializing " + typeOfSet + " took " + " " + difference/NANO_TO_MS);
        }
    }

    /**
     * the main that runs the test for Initializing and contain operation.
     * @param args
     */
    public static void main(String[] args){
        takeTimeForInitializing(fileOne);
        takeTimeForContains("hi");
        takeTimeForContains("-13170890158");
        takeTimeForInitializing(fileTwo);
        takeTimeForContains("hi");
        takeTimeForContains("23");
    }
}
