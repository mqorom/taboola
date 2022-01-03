import java.util.HashMap;
import java.util.Map;

/**
 * LRU cache where we need map to store nodes addresses & double linked list to help us to mantain LRU policy
 * @param <K>
 * @param <V>
 */
public class Cache<K, V> implements CacheOperations<K, V> {
    private final int CACHE_SIZE;

    private Map<K, V> map;

    public Cache(int cacheSize) {
        this.CACHE_SIZE = cacheSize;
        this.map = new HashMap<K, V>(CACHE_SIZE);
    }


    public <V> V get(K k) {
        V value = (V) this.map.get(k);

        if (value == null) { // Entry is not in our cache
            // 1. Get it from DB
            // 2. If cache is not full --> add it to the map & also to the head of double linked list
            // 3. If cache is full then evict the tail item from double linked list and then add the current entry to the map and return
        }else{ // Entry in our cache
            // 1. move the entry to the head of double linked and return it
        }
        return null;
    }

    public void put(K key, V value) {
        // 1. If item in the cache:
            // a. Update it in the cache and also send an update request to DB
            // b. Move the item to the head of double link list
        // 2. If item is not in the cache:
           // a. If cache is full then just evict the tail of double link list and add entry to the head of the double link list and send update request to DB
           // b. If cache is not full then just add new entry to the head of double link list and then send update request to DB
    }

    public boolean isFull() {
        return map.size() < CACHE_SIZE;
    }

    public int getSize() {
        return this.CACHE_SIZE;
    }
}
