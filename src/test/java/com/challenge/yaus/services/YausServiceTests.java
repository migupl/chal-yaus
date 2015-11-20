package com.challenge.yaus.services;

import com.challenge.yaus.domain.ShortenUrl;
import com.challenge.yaus.domain.Urls;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class YausServiceTests {

    private static final String TYPED_URL = "http://a.typed.es/url";

    private YausService yausService = new YausService();

    private ShortenUrl shortenUrl;

    @Before
    public void setup() {
        shortenUrl = new ShortenUrl(TYPED_URL);
        assertNull(shortenUrl.getShorten());
    }

    @Test
    public void aTypedUrlShouldReturnAShortenUrl() {
        String expected = new Urls().put(shortenUrl.getTyped());

        yausService.setShortenUrl(shortenUrl);

        assertEquals(expected, shortenUrl.getShorten());
    }

    @Test
    public void aShortenUrlShouldReturnOriginalLongUrl() throws NotFoundException {
        yausService.setShortenUrl(shortenUrl);

        assertEquals(TYPED_URL, yausService.getRedirection(shortenUrl.getShorten()));
    }

    @Test(expected = NotFoundException.class)
    public void anUnknownShortenShouldThrowNotFoundException() throws NotFoundException {
        yausService.getRedirection("UnknownShorten");
    }
}
