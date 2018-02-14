/**
 * an abstract class implementing SimpleSet.
 */
public abstract class SimpleHashSet implements SimpleSet {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float UPPER_LOAD_FACTOR = 0.75f;
    static final float LOWER_LOAD_FACTOR = 0.25f;
    static final int INITIAL_NUMBER_OF_ELEMENTS = 0;
    static final int CAPACITY_MINUS_ONE_UNIT = 1;
    protected static final int NO_ELEMENTS_IN_HASH_SET = 0;
    protected static final int INCREASE_TABLE = 1;
    protected static final int DECREASE_TABLE = -1;
    protected static final int BY_TWO = 2;

    int capacity, currentNumberOfElements;
    float upperLoadFactor, lowerLoadFactor;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public SimpleHashSet() {
        this(UPPER_LOAD_FACTOR, LOWER_LOAD_FACTOR);
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor - The upper load factor of the hash table.
     * @param lowerLoadFactor - The lower load factor of the hash table.
     */
    public SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        this.currentNumberOfElements = INITIAL_NUMBER_OF_ELEMENTS;
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data- Values to add to the set.
     */
    public SimpleHashSet(java.lang.String[] data){
        this(UPPER_LOAD_FACTOR, LOWER_LOAD_FACTOR);
    }

    /**
     * this function call when the table is full (upper load factor) and increased the table capacity.
     * After changing the capacity (i.e. allocating a new table), re-hashing is required.
     * This function call to other function and receive array of tha elements from the hash table and
     * add all the the new hash table.
     * @param IncreaseOrDecrease - int -1 or 1 for increase or decrease the hash table size.
     */
    public abstract void reHashing(int IncreaseOrDecrease);{}

    /**
     * @return - The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return this.capacity;
    }

    /**
     * @return - The number of elements currently in the set
     */
    public int size() {
        return this.currentNumberOfElements;
    }

    /**
     * this function calculate "capacity - 1 " for the formula to the hashCode to the element's
     * @return capacity - 1
     */
    public int capacityMinusOne() {
        return (this.capacity() - CAPACITY_MINUS_ONE_UNIT);
    }


}
