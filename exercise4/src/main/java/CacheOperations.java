public interface CacheOperations<K, V> {
    <V> V get(K k);

    void put(K key, V value);

    boolean isFull();

    int getSize();
}
