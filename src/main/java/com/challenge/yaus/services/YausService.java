package com.challenge.yaus.services;

import com.challenge.yaus.domain.ShortenUrl;
import com.challenge.yaus.domain.Urls;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class YausService {

    private Urls session_urls = new Urls();

    public void setShortenUrl(ShortenUrl typedUrl) {
        typedUrl.setShorten(session_urls.put(typedUrl.getTyped()));
    }

    public String getRedirection(String shorten) throws NotFoundException {
        String url = session_urls.get(shorten);

        if (null == url) {
            throw new NotFoundException("Shorten reference not found.");
        }

        return url;
    }
}
