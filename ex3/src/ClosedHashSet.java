import java.util.Objects;
import static java.util.Objects.hash;
/**
 * In this model each cell in the hash table is a reference to a String.
 *a hash-set based on closed-hashing with quadratic probing. Extends SimpleHashSet.
 */
public class ClosedHashSet extends SimpleHashSet {

    private String[] hashSet;
    private String deleted = "";

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet() {
        super();
        this.hashSet = new String[this.capacity];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor - The upper load factor of the hash table.
     * @param lowerLoadFactor - The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.hashSet = new String[this.capacity];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data- Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data){
        this();
        for (String string : data)
            this.add(string);
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        int i = 0;
        int index = matchMaker(searchVal, i);
        while (this.hashSet[index] != null)  {
            if (Objects.equals(this.hashSet[index], searchVal)) {
                return true;
            } else {
                index = matchMaker(searchVal, i++);
            }
        }
        return false;
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
            if (((float)(this.size() + 1) / this.capacity()) > this.upperLoadFactor) {
                this.reHashing(INCREASE_TABLE);
            }
            int i = 0;
            int index = matchMaker(newValue, i);
            while (this.hashSet[index] != null && (!Objects.equals(this.hashSet[index], deleted))){
                index = matchMaker(newValue, i++);
                }
            // update the counter of the elements in the hash set
            currentNumberOfElements++;
            this.hashSet[index] = newValue;
            return true;
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
            int i = 0;
            int index = matchMaker(toDelete, i);
            while (this.hashSet[index] != null){
                if (Objects.equals(this.hashSet[index], toDelete)){
                    // update the counter of the elements in the hash set
                    this.hashSet[index] = deleted;
                    currentNumberOfElements--;
                    return true;
                }
                index = matchMaker(toDelete, i++);
            }
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
        String[] oldHashSet = this.hashSet;
        if (IncreaseOrDecrease == INCREASE_TABLE)
            this.hashSet = new String[this.capacity() * BY_TWO];
        else {
            this.hashSet = new String[this.capacity() / BY_TWO];
            if (this.capacity() < 1)
                this.capacity = 1;
        }
        this.currentNumberOfElements = NO_ELEMENTS_IN_HASH_SET;
        for (String string : oldHashSet){
            if (string != null){
                if (!string.equals(deleted)) {
                    this.add(string);
            }
            }
        }
    }
    /**
     * @return - The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return this.hashSet.length;
    }

    /**
     * this function calculate a index for string in the hash set. This index is then fitted to the
     * appropriate range [0:tableSize-1]. Note that for i=0, which is the first attempt, this simply means
     * the hashCode of value .
     * @param someString to add/search/find in the hash set.
     * @param i a where i is constant.
     * @return hash code for value.
     */
    private int matchMaker(String someString, int i){
        int index = (hash(someString) + (i + i * i) / 2) & this.capacityMinusOne();
        return index;
    }
}
