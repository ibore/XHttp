package me.ibore.http.simple.cache;

public interface CacheStore {

    CacheStore DEFAULT = new CacheStore() {
        @Override
        public Cache get(String key) {
            return null;
        }

        @Override
        public boolean replace(String key, Cache cache) {
            return true;
        }

        @Override
        public boolean remove(String key) {
            return true;
        }

        @Override
        public boolean clear() {
            return true;
        }
    };

    /**
     * Get the cache.
     *
     * @param key unique key.
     * @return cache.
     */
    Cache get(String key);

    /**
     * Save or set the cache.
     *
     * @param key   unique key.
     * @param cache cache.
     * @return cache.
     */
    boolean replace(String key, Cache cache);

    /**
     * Remove cache.
     *
     * @param key unique.
     * @return cache.
     */
    boolean remove(String key);

    /**
     * Clear all data.
     *
     * @return returns true if successful, false otherwise.
     */
    boolean clear();
}