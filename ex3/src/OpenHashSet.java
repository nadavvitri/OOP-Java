/**
 * a hash-set based on chaining. Extends SimpleHashSet.
 * Note: the capacity of a chaining based hash-set is simply the number of buckets (the length of the
 * array of lists).
 */
public class OpenHashSet extends SimpleHashSet {

    private MyLinkedList[] hashSet;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        super();
        this.hashSet = new MyLinkedList[this.capacity];
        this.initialize();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor - The upper load factor of the hash table.
     * @param lowerLoadFactor - The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.hashSet = new MyLinkedList[this.capacity];
        this.initialize();
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data- Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data){
        this();
        this.initialize();
        for (String string : data)
            this.add(string);
    }
    /**
     * This function initialize every cell in the hash table with linked list (instead off null).
     */
    private void initialize() {
        for (int i = 0; i < this.capacity(); i++)
            this.hashSet[i] = new MyLinkedList();
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        int index = searchVal.hashCode() & this.capacityMinusOne();
        return this.hashSet[index].contains(searchVal);
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        // check if value is already in the hash set
        if (!this.contains(newValue)) {
            // if the hash set is full
            if (((float)(this.size() + 1) / this.capacity()) > this.upperLoadFactor) {
                this.reHashing(INCREASE_TABLE);
            }
            int index = newValue.hashCode() & this.capacityMinusOne();
            // update the counter of the elements in the hash set
            currentNumberOfElements++;
            return this.hashSet[index].add(newValue);
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        // check if value is already in the hash set
        if (this.contains(toDelete)) {
            // if the hash set is "empty" more then necessary
            if (((float)(this.size() - 1) / this.capacity()) < this.lowerLoadFactor) {
                this.reHashing(DECREASE_TABLE);
            }
            int index = toDelete.hashCode() & this.capacityMinusOne();
            // update the counter of the elements in the hash set
            currentNumberOfElements--;
            return this.hashSet[index].remove(toDelete);
        }
        return false;
    }

    /**
     * this function call when the table is full (upper load factor) and increased the table capacity.
     * After changing the capacity (i.e. allocating a new table), re-hashing is required.
     * This function call to other function and receive array of tha elements from the hash table and
     * add all the the new hash table.
     * @param IncreaseOrDecrease - int -1 or 1 for increase or decrease the hash table size.
     */
    @Override
    public void reHashing(int IncreaseOrDecrease) {
        MyLinkedList[] oldHashSet = this.hashSet;
        // checking if it's necessary to increase or decrease the hash set
        if (IncreaseOrDecrease == INCREASE_TABLE)
            this.hashSet = new MyLinkedList[this.capacity() * BY_TWO];
        else {
            this.hashSet = new MyLinkedList[this.capacity() / BY_TWO];
            if (this.capacity() < 1)
                this.capacity = 1;
        }
        this.initialize();
        this.currentNumberOfElements = NO_ELEMENTS_IN_HASH_SET;
        for (MyLinkedList linkedList : oldHashSet)
                while (!linkedList.isEmpty()){
                    this.add(linkedList.pop());
                }
    }
    /**
     * @return - The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return this.hashSet.length;
    }
}
