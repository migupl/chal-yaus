package com.osoco.challenge.yaus.model;

/**
   See <a href="http://www.geeksforgeeks.org/how-to-design-a-tiny-url-or-url-shortener/" />
 **/
public class ShortenAlgorithm {

    // 62 possible characters
    private static final String SHORT_URL_CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String getShortUrl(long id) {
        StringBuilder shorturl = new StringBuilder();

        // Convert given integer id to a base 62 number
        while (id > 0) {

            // use above map to store actual character
            // in short url
            shorturl.append(SHORT_URL_CHARSET.charAt((int)(id % 62L)));
            id = id / 62L;
        }

        // Reverse shortURL to complete base conversion
        return shorturl.reverse().toString();
    }

    public static long getId(String shortUrl) {
        return shortUrl.chars()
                .mapToObj(i -> (long) SHORT_URL_CHARSET.indexOf(i))
                .reduce(0L, (a, b) -> a * 62L + b);
    }
}
