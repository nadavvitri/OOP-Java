package oop.ex4.data_structures;
/**
 * Created by nadav vitri on 08/05/2017.
 */
/**
 * Implements an AVL tree
 */
public class AvlTree extends BinaryTree  {

    /* Constructors */
    /**
     * The default constructor.
     */
    public AvlTree() {
        this.root = null;
    }

    /**
     * A constructor that builds a new AVL tree containing all unique values in the input array.
     * @param data the values to add to tree.
     */
    public AvlTree(int[] data) {
        super();
        try {
        for (int number : data) {
            add(number);}
        }catch (NullPointerException e){
            throw new NullPointerException();}}

    /**
     * A copy constructor that creates a deep copy of the given AvlTree. The new tree
     * contains all the values of the given tree, but not necessarily in the same structure.
     * @param avlTree an AVL tree.
     */
    public AvlTree(AvlTree avlTree) {
        super();
        try {
            for (int number : avlTree){
                add(number);}
        }catch (NullPointerException e){
            throw new NullPointerException();}}

    /**
     * add helper that call recursively until we reach to null place and then make new node there.
     * @param newValue - to add to the avl tree.
     * @param node - the root of the tree.
     * @return node - if the newValue already exist in the tree then return null. else return the new node.
     */
    private MyNode add(int newValue, MyNode node){
        if (node == null){
            node = new MyNode(newValue);
            // update size
            NumberOfElements++;
        } else if (newValue > node.getData()){
            temp = add(newValue, node.getRightNode());
            // if the this value already exist
            if (temp == null){
                return null;}
            node.setRightNode(temp);
            updateHeights(node);
            node = balance(node);
        } else if (newValue < node.getData()){
            temp = add(newValue, node.getLeftNode());
            // if the this value already exist
            if (temp == null){
                return null;}
            node.setLeftNode(temp);
            updateHeights(node);
            node = balance(node);
        }else {       // if the value already exist
            return null;
        }
        return node;}

    /* Public Methods */
    /*
    * Add a new node with the given key to the tree.
    * @param newValue the value of the new node to add.
    * @return true if the value to add is not already in the tree and it was successfully added,
    * false otherwise.
    */
    public boolean add(int newValue){
        if (this.root == null){
            this.root = new MyNode(newValue);
            NumberOfElements++;
            return true;}
        MyNode temp = add(newValue, this.root);
        // if the value already exist
        if (temp == null){
            return false;}
        this.root = temp;
        return true;}

    /**
     * this function calculate the heights difference between to the left subtree and the right subtree.
     * @param node - to check the difference between his subtrees.
     * @return int - (left subtree height - right subtree height).
     */
    private int calculateDifference(MyNode node){
    int leftSubtree, rightSubtree;
        if (node.getRightNode() == null){
            rightSubtree = -1;
        }else {
            rightSubtree = node.getRightNode().getHeight();
        }if (node.getLeftNode() == null){
            leftSubtree = -1;
        }else {
            leftSubtree = node.getLeftNode().getHeight();}
        return leftSubtree - rightSubtree;}

    /**
     * this function check if the node is balance by checking the difference between left and right subtrees.
     * @param node to check his balance.
     * @return node - 0 if the balance is OK, -1 if the balance violate because the left subtree or 1 if the
     * balance violate because the right subtree.
     */
    private MyNode balance(MyNode node) {
        // if the balance violate caused from the left subtree
        if (calculateDifference(node) == 2){
            if (calculateDifference(node.getLeftNode()) < 0){
                // LR
                node.setLeftNode(rotateLeft(node.getLeftNode()));
                return rotateRight(node);
            }else {
                return rotateRight(node);}
        // if the balance violate caused from the right subtree
        }if (calculateDifference(node) == -2){
            if (calculateDifference(node.getRightNode()) > 0){
                // RL
                node.setRightNode(rotateRight(node.getRightNode()));
                return rotateLeft(node);
            }else {
                return rotateLeft(node);}
        }
        return node;}

    /**
     * if the balance violate caused from the right subtree then rotate left
     */
    private MyNode rotateLeft(MyNode node){
        MyNode temp = node.getRightNode();
        node.setRightNode(temp.getLeftNode());
        temp.setLeftNode(node);
        updateHeights(node);
        updateHeights(temp);
        return temp;}

    /**
     * if the balance violate caused from the left subtree then rotate right
     */
    private MyNode rotateRight(MyNode node) {
        MyNode temp = node.getLeftNode();
        node.setLeftNode(temp.getRightNode());
        temp.setRightNode(node);
        updateHeights(node);
        updateHeights(temp);
        return temp;}

    /**
     * this function calculate the height of the node. if both subtree are null then his height is 0.
     * if one of the his subtree is null then take the other subtree height + 1. if they both are not null
     * then the height of the node is determine by the max subtree (height) + 1.
     * @param node - to set his height.
     */
    private void updateHeights(MyNode node){
        if (node.getLeftNode() == null && node.getRightNode() == null){
            node.setHeight(0);
        } else if (node.getLeftNode() == null){
            node.setHeight(node.getRightNode().getHeight() + 1);
        } else if (node.getRightNode() == null){
            node.setHeight(node.getLeftNode().getHeight() + 1 );
        } else{
            node.setHeight(Math.max(node.getRightNode().getHeight(), node.getLeftNode().getHeight()) + 1);}}

    /**
     * Removes the node with the given value from the tree, if it exists.
     * @param toDelete the value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    public boolean delete(int toDelete) {
        if (this.size() == 1){
            if (root.getData() == toDelete){
                root = null;
                NumberOfElements--;
                return true;
            }else{
                return false;}}
        // if node is not exist
        if (contains(toDelete) == NOT_FOUND){
            return false;}
        MyNode node = delete(root, toDelete);
        root = node;
        // update size
        NumberOfElements--;
        return true;
    }

    /**
     *delete function helper that call recursively and delete the node.
     * @param root of the avl tree.
     * @param toDelete - int we want to delete.
     * @return the root after we delete the value we want (if there is in the avl tree this value).
     */
    private MyNode delete(MyNode root, int toDelete){
        if (root == null){
            return root;
        }else if (toDelete < root.getData()){
            root.setLeftNode(delete(root.getLeftNode(), toDelete));
        }else if (toDelete > root.getData()){
            root.setRightNode(delete(root.getRightNode(), toDelete));}
        // if we found the node with the data we want to delete
        else if (toDelete == root.getData()){
            // if we need to delete a leaf
            if (root.getRightNode() == null && root.getLeftNode() == null){
                root = null;}
            // if the node we want to delete has a only one subtree
            else if (root.getRightNode() == null || root.getLeftNode() == null){
                if (root.getRightNode() == null){
                    root = root.getLeftNode();
                }else {
                    root = root.getRightNode();}
            }// if the node has two subtrees
            else {
                MyNode inorderSuccessor = findMinNode(root.getRightNode());
                root.setData(inorderSuccessor.getData());
                root.setRightNode(delete(root.getRightNode(), inorderSuccessor.getData()));}}
        // if the node we deleted is a leaf
        if (root == null){
            return root;}
        updateHeights(root);
        // check if necessary to balance the tree
        root = balance(root);
        return root;}

    /**
     *this function return the the minimum node in a subtree.
     * @param subtree to find for him his min nod.
     * @return the minimum node of the subtree.
     */
    private MyNode findMinNode(MyNode subtree){
        MyNode currentNode = subtree;
        while (currentNode.getLeftNode() != null){
            currentNode = currentNode.getLeftNode();
        }
        return currentNode;
    }

    /**
     * @return the number of nodes in the tree.
     */
    public int size() {
        return NumberOfElements;
    }

    /* Static Methods */
    /**
     * Calculates the minimum number of nodes in an AVL tree of height h.
     * @param h the height of the tree (a non−negative number) in question.
     * @return the minimum number of nodes in an AVL tree of the given height.
     */
    public static int findMinNodes(int h) {
        return (int)(Math.round(((Math.sqrt(5)+2)/ Math.sqrt(5))*Math.pow((1+ Math.sqrt(5))/2,h)-1));
    }

    /**
     * Calculates the maximum number of nodes in an AVL tree of height h.
     * @param h the height of the tree (a non−negative number) in question.
     * @return the maximum number of nodes in an AVL tree of the given height.
     */
    public static int findMaxNodes(int h) {
        return (int)(Math.pow(2, h + 1) - 1);
    }

}

