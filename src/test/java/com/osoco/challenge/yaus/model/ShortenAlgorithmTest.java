package com.osoco.challenge.yaus.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShortenAlgorithmTest {

    @Test
    public void getShortenFor12345ShouldBednh() {
        assertEquals("dnh", ShortenAlgorithm.getShortUrl(12345));
    }

    @Test
    public void getIdFordnhShouldBe12345() {
        assertEquals(12345, ShortenAlgorithm.getId("dnh"));
    }

    @Test
    public void algorithmShouldBeBijective() {
        int id = 123456789;

        assertEquals(id, ShortenAlgorithm.getId(ShortenAlgorithm.getShortUrl(id)));
    }
}
