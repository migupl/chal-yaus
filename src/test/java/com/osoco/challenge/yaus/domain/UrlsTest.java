package com.osoco.challenge.yaus.domain;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class UrlsTest {

    private static final String AN_URL = "http://an.url.es";
    private Urls urls;

    @Before
    public void setup() {
        urls = new Urls();
    }

    @Test
    public void putAnUrlShouldIncrementNumberOfUrlsSaved() {
        urls.put(AN_URL);

        assertEquals(1, urls.size());
    }

    @Test
    public void putAnUrlShouldReturnShortenUrl() {
        String shorten = urls.put(AN_URL);

        assertNotNull(shorten);
    }

    @Test
    public void putAnUrlShouldReturnShortenUrlNoEmpty() {
        String hashCodeNegative = "http://this.url.is/negative/hashcode/aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaargh";
        String shorten = urls.put(hashCodeNegative);

        assertFalse(hashCodeNegative.hashCode() > 0);
        assertFalse(shorten.isEmpty());
    }

    @Test
    public void putAnAddedUrlShouldNotIncrementUrlsSaved() {
        urls.put(AN_URL);
        urls.put(AN_URL);

        assertEquals(1, urls.size());
    }

    @Test
    public void useShortenShouldReturnUrlSaved() {
        String shorten = urls.put(AN_URL);

        assertEquals(AN_URL, urls.get(shorten));
    }
}
