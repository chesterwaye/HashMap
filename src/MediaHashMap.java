/**
 * Class: MediaHashMap
 *
 * @author Chester Waye
 * @version 1.0 * Course : ITEC 3150, Spring 2021 Written: 4/13/2021
 * Written: January 18, 2012
 * <p>
 * <p>
 * This class –now describe what the class does
 * <p>
 * Purpose: –Describe the purpose of this class
 */

import java.util.LinkedList;
import java.util.Set;

public class MediaHashMap<String , Media> implements MyMap<String,Media> {
    // Define the default hash table size. Must be a power of 2
    private static int DEFAULT_INITIAL_CAPACITY = 4;

    // Define the maximum hash table size. 1 << 30 is same as 2^30
    private static int MAXIMUM_CAPACITY = 1 << 30;

    // Current hash table capacity. Capacity is a power of 2
    private int capacity;

    // Define default load factor
    private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f;

    // Specify a load factor used in the hash table
    private float loadFactorThreshold;

    // The number of entries in the map
    private int size = 0;

    // Hash table is an array with each cell that is a linked list
    LinkedList<MyMap.Entry<String ,Media>>[] table;

    /** Construct a map with the default capacity and load factor */
    public MediaHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    /** Construct a map with the specified initial capacity and
     * default load factor */
    public MediaHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
    }

    /** Construct a map with the specified initial capacity
     * and load factor */
    public MediaHashMap(int initialCapacity, float loadFactorThreshold) {
        if (initialCapacity > MAXIMUM_CAPACITY)
            this.capacity = MAXIMUM_CAPACITY;
        else
            this.capacity = trimToPowerOf2(initialCapacity);

        this.loadFactorThreshold = loadFactorThreshold;
        table = new LinkedList[capacity];
    }

    /** Remove all of the entries from this map */
    public void clear() {
        size = 0;
        removeEntries();
    }

    /** Return true if the specified key is in the map */
    public boolean containsKey(String key) {
        return get(key) != null;
    }

    /** Return true if this map contains the value */
    public boolean containsValue(Media value) {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<String, Media>> bucket = table[i];
                for (Entry<String, Media> entry: bucket)
                    if (entry.getValue().equals(value))
                        return true;
            }
        }

        return false;
    }

    /** Return a set of entries in the map */
    public java.util.Set<MyMap.Entry<String,Media>> entrySet() {
        java.util.Set<MyMap.Entry<String , Media>> set =
                new java.util.HashSet<MyMap.Entry<String, Media>>();

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<String , Media>> bucket = table[i];
                for (Entry<String, Media> entry: bucket)
                    set.add(entry);
            }
        }

        return set;
    }

    /** Return the value that matches the specified key */
    public Media get(String key) {
        int bucketIndex = hash(key.hashCode());
        if (table[bucketIndex] != null) {
            LinkedList<Entry<String , Media>> bucket = table[bucketIndex];
            for (Entry<String , Media> entry: bucket)
                if (entry.getKey().equals(key))
                    return entry.getValue();
        }

        return null;
    }

    /** Return true if this map contains no entries */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Return a set consisting of the keys in this map */
    public Set<String > keySet() {
        java.util.Set<String> set = new java.util.HashSet<String>();

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<String, Media>> bucket = table[i];
                for (Entry<String, Media> entry: bucket)
                    set.add(entry.getKey());
            }
        }

        return set;
    }

    /** Add an entry (key, value) into the map */
    public Media put(String key, Media value) {
        if (get(key) != null) { // The key is already in the map
            int bucketIndex = hash(key.hashCode());
            LinkedList<Entry<String, Media>> bucket = table[bucketIndex];
            for (Entry<String, Media> entry: bucket)
                if (entry.getKey().equals(key)) {
                    Media oldValue = entry.getValue();
                    // Replace old value with new value
                    entry.value = value;
                    // Return the old value for the key
                    return oldValue;
                }
        }

        // Check load factor
        if (size >= capacity * loadFactorThreshold) {
            if (capacity == MAXIMUM_CAPACITY)
                throw new RuntimeException("Exceeding maximum capacity");

            rehash();
        }

        int bucketIndex = hash(key.hashCode());

        // Create a linked list for the bucket if it is not created
        if (table[bucketIndex] == null) {
            table[bucketIndex] = new LinkedList<Entry<String , Media>>();
        }

        // Add a new entry (key, value) to hashTable[index]
        table[bucketIndex].add(new MyMap.Entry<String, Media>(key, value));

        size++; // Increase size

        return value;
    }

    /** Remove the entries for the specified key */
    public void remove(String key) {
        int bucketIndex = hash(key.hashCode());

        // Remove the first entry that matches the key from a bucket
        if (table[bucketIndex] != null) {
            LinkedList<Entry<String, Media>> bucket = table[bucketIndex];
            for (Entry<String, Media> entry: bucket)
                if (entry.getKey().equals(key)) {
                    bucket.remove(entry);
                    size--; // Decrease size
                    break; // Remove just one entry that matches the key
                }
        }
    }

    /** Return the number of entries in this map */
    public int size() {
        return size;
    }

    /** Return a set consisting of the values in this map */
    public java.util.Set<Media> values() {
        java.util.Set<Media> set = new java.util.HashSet<Media>();

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LinkedList<Entry<String, Media>> bucket = table[i];
                for (Entry<String, Media> entry: bucket)
                    set.add(entry.getValue());
            }
        }

        return set;
    }

    /** Hash function */
    private int hash(int hashCode) {
        return supplementalHash(hashCode) & (capacity - 1);
    }

    /** Ensure the hashing is evenly distributed */
    private static int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /** Return a power of 2 for initialCapacity */
    private int trimToPowerOf2(int initialCapacity) {
        int capacity = 1;
        while (capacity < initialCapacity) {
            capacity <<= 1;
        }

        return capacity;
    }

    /** Remove all entries from each bucket */
    private void removeEntries() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                table[i].clear();
            }
        }
    }

    /** Rehash the map */
    private void rehash() {
        java.util.Set<Entry<String, Media>> set = entrySet(); // Get entries
        capacity <<= 1; // Double capacity
        table = new LinkedList[capacity]; // Create a new hash table
        size = 0; // Reset size to 0

        for (Entry<String, Media> entry: set) {
            put(entry.getKey(), entry.getValue()); // Store to new table
        }
    }


    public java.lang.String toString() {
        StringBuilder builder = new StringBuilder("[");

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && table[i].size() > 0)
                for (Entry<String, Media> entry: table[i])
                    builder.append(entry);
        }

        builder.append("]");
        return builder.toString();
    }

}

