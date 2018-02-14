import java.util.LinkedList;

/**
 * this class define a wrapper-class that has-a LinkedList<String> and delegates methods to it,
 */
public class MyLinkedList {

    protected LinkedList<String> myLinkedList;

    public MyLinkedList() {
        this.myLinkedList = new LinkedList<String>();
    }

    /**
     * Appends the specified element to the end of this list.
     * @param e - element to be appended to this list
     * @return true.
     */
    public boolean add(String e){
        return myLinkedList.add(e);
    }

    /**
     * Returns true if this list contains the specified element. More formally, returns true if and only if
     * this list contains at least one element e such that.
     * @param e - element whose presence in this list is to be tested.
     * @return true if this list contains the specified element.
     */
    public boolean contains(String e){
        return myLinkedList.contains(e);
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present. If this list
     * does not contain the element, it is unchanged.
     * @param e - element to be removed from this list, if present.
     * @return true if this list contained the specified element.
     */
    public boolean remove(String e){
            return myLinkedList.remove(e);
    }


    /**
     * @returns true if this collection contains no elements.
     */
    public boolean isEmpty(){
        return myLinkedList.isEmpty();
    }
    /**
     * Pops an element from the stack represented by this list. In other words, removes and returns the
     * first element of this list.
     * @returns the element at the front of this list(which is the top of the stack represented by this list)
     */
    public String pop() {
        return myLinkedList.pop();
    }

}
