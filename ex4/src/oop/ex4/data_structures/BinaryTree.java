package oop.ex4.data_structures;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
/**
 * Created by nadav vitri on 10/05/2017.
 */
abstract class BinaryTree implements Iterable<Integer> {

    MyNode temp, root;
    int NumberOfElements = 0;
    static final int NOT_FOUND = -1;

    /* Constructors */
    /**
     * The default constructor.
     */
    BinaryTree () {
        this.root = null;
    }
    /*
    * Add a new node with the given key to the tree.
    * @param newValue the value of the new node to add.
    * @return true if the value to add is not already in the tree and it was successfully added,
    * false otherwise.
    */
    public abstract boolean add (int newValue);

    /**
     * Check whether the tree contains the given input value.
     * @param searchVal the value to search for.
     * @return the depth of the node (0 for the root) with the given value if it was found in
     * the tree, −1 otherwise.
     */
    public int contains(int searchVal) {
        MyNode currentNode = this.root;
        int depth = 0;
        while (currentNode != null){
            if (Objects.equals(currentNode.getData(), searchVal)){
                return depth; }
            else if (searchVal < currentNode.getData()){
                currentNode = currentNode.getLeftNode();
            }else {
                currentNode = currentNode.getRightNode();
            }
            depth++;}
        return NOT_FOUND;}

    /**
     * Removes the node with the given value from the tree, if it exists.
     * @param toDelete the value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    public abstract boolean delete(int toDelete);

    /**
     * @return the number of nodes in the tree.
     */
    public int size() {
        return NumberOfElements;}

    /**
     * @return an iterator for the Avl Tree. The returned iterator iterates over the tree nodes
     * in an ascending order, and does NOT implement the remove() method.
     */
    public Iterator<Integer> iterator(){
        ArrayList<Integer> list = new ArrayList<>();
        list = iteratorHelper(list, root);
        return list.iterator();}

    /**
     * this function call recosvely and get the min node in the tree and after that the next one and so on.
     * @param list - array list to add the numbers.
     * @param root the root of the subtree.
     * @return list of numbers.
     */
    private ArrayList<Integer> iteratorHelper(ArrayList<Integer> list, MyNode root){
        if (root.getLeftNode() != null){
            iteratorHelper(list, root.getLeftNode());
        }
        list.add(root.getData());
        if (root.getRightNode() != null){
            iteratorHelper(list, root.getRightNode());}
        return list;
    }
}
