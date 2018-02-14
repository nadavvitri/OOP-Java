package oop.ex4.data_structures;
/**
 * Created by nadav vitri on 08/05/2017.
 */
public class MyNode {

    private int data, height ;
    private MyNode leftNode, rightNode;

    /**
     * Constructor for MyNode Object.
     * @param data - data for the head of the node.
     */
    public MyNode(int data) {
        this.data = data;
        this.height  = 0;
        this.leftNode = null;
        this.rightNode = null;
    }

    /**
     * get the data from the Node.
     * @return node data.
     */
    public int getData() {
        return this.data;
    }

    /**
     * set the data to Node.
     * @param data to add.
     */
    public void setData(int data) {
        this.data = data;
    }

    /**
     * get the left node of the head.
     * @return leftNode.
     */
    public MyNode getLeftNode() {
        return this.leftNode;
    }
    /**
     * set a new MyNode object in the left node.
     * @param node - data for the head of the node.
     */
    public void setLeftNode(MyNode node){
        this.leftNode = node;
    }
    /**
     * get the right node of the head.
     * @return rightNode.
     */
    public MyNode getRightNode() {
        return this.rightNode;
    }

    /**
     * set a new MyNode object in the right node.
     * @param node - data for the head of the node.
     */
    public void setRightNode(MyNode node){
        this.rightNode = node;
    }

    /**
     * get the height  of the node.
     * @return the high of the node.
     */
    public int getHeight (){
        return this.height ;
    }
    /**
     * set height for node.
     * @param height - new height for node.
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
