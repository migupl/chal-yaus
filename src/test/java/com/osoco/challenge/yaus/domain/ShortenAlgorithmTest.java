package com.osoco.challenge.yaus.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShortenAlgorithmTest {

    @Test
    public void getShortenFor12345ShouldBednh() {
        assertEquals("dnh", ShortenAlgorithm.getShortUrl(12345L));
    }

    @Test
    public void getIdFordnhShouldBe12345() {
        assertEquals(12345L, ShortenAlgorithm.getId("dnh"));
    }

    @Test
    public void algorithmShouldBeBijective() {
        long id = 123456789L;

        assertEquals(id, ShortenAlgorithm.getId(ShortenAlgorithm.getShortUrl(id)));
    }
}
