package com.osoco.challenge.yaus.domain;

public class ShortenUrl {

    private String typed;
    private String shorten;

    public ShortenUrl() {
    }

    public ShortenUrl(String typed) {
        this.typed = typed;
    }

    public ShortenUrl(String typed, String shorten) {
        this.typed = typed;
        this.shorten = shorten;
    }

    public String getTyped() {
        return typed;
    }

    public void setTyped(String typed) {
        this.typed = typed;
    }

    public String getShorten() {
        return shorten;
    }

    public void setShorten(String shorten) {
        this.shorten = shorten;
    }
}
