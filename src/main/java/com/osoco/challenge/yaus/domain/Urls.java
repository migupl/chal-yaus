package com.osoco.challenge.yaus.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Simulate database info
 */
public class Urls {

    // Capacity and load factor should be fixed (it's not needed at this point)
    private Map<Integer, String> urls = new HashMap<Integer, String>();

    private static final long STEP_TO_POSITIVE = (long) Integer.MAX_VALUE + 2;

    /*
        Conversion to long (ShortenAlgorithms only for positive numbers greatest than zero).
     */
    public String put(String url) {
        int id = url.hashCode();
        String shorten = ShortenAlgorithm.getShortUrl(getPositiveHashCode(id));

        urls.put(id, url);
        return shorten;
    }

    public int size() {
        return urls.size();
    }

    public String get(String shorten) {
        return urls.get(getRealHashCode(ShortenAlgorithm.getId(shorten)));
    }

    private long getPositiveHashCode(int hashCode) {
        return (long) hashCode + STEP_TO_POSITIVE;
    }

    private int getRealHashCode(long positiveHashCode) {
        return (int) (positiveHashCode - STEP_TO_POSITIVE);
    }
}
