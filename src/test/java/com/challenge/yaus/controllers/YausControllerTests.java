package com.challenge.yaus.controllers;

import com.challenge.yaus.ChallengeYausApplicationTests;
import com.challenge.yaus.domain.ShortenUrl;
import com.challenge.yaus.services.YausService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class YausControllerTests extends ChallengeYausApplicationTests {

    private static final String ENDPOINT = "/";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    public void getWithoutPathRequestShouldReturnIndexPage() throws Exception {
        ShortenUrl expected = new ShortenUrl();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("typed", is(expected.getTyped())))
                .andExpect(model().attribute("shorten", is(expected.getShorten())));
    }

    @Test
    public void postRequestShouldShouldReturnShortenUrl() throws Exception {
        assertThanRequestGetAShortenUrl(new ShortenUrl("http://an.ulr"));
    }

    @Test
    public void getWithShortenUrlAsPathRequestShouldRedirect() throws Exception {
        String url = "http://an.ulr";
        ShortenUrl shortenUrl = new ShortenUrl(url);

        assertThanRequestGetAShortenUrl(shortenUrl);

        YausService yausServiceMock = mock(YausService.class);
        doReturn(url).when(yausServiceMock).getRedirection(shortenUrl.getShorten());

        assertThanShortenRedirectToUrl(shortenUrl.getShorten(), yausServiceMock
                , "http://an.ulr", HttpStatus.MOVED_PERMANENTLY);
    }

    @Test
    public void getWithShortenUrlAsPathPathRequestWhenNotExistsShouldRedirectToError() throws Exception {
        String shorten = "notExiSTs";

        YausService yausServiceMock = mock(YausService.class);
        doThrow(NotFoundException.class).when(yausServiceMock).getRedirection(shorten);

        assertThanShortenRedirectToView(shorten, yausServiceMock
                , "404", HttpStatus.NOT_FOUND);
    }

    @Test
    public void otherRequestShouldBeMethodNotAllowed() throws Exception {
        mockMvc.perform(put(ENDPOINT))
                .andExpect(status().isMethodNotAllowed());
    }

    private void assertThanShortenRedirectToUrl(String shorten, YausService yausServiceMocked
            , String redirectName, HttpStatus httpStatus) throws Exception {
        assertThanShortenRedirectTo(shorten, yausServiceMocked
                , redirectName, httpStatus, false);
    }

    private void assertThanShortenRedirectToView(String shorten, YausService yausServiceMocked
            , String redirectName, HttpStatus httpStatus) throws Exception {
        assertThanShortenRedirectTo(shorten, yausServiceMocked
                , redirectName, httpStatus, true);
    }

    private void assertThanRequestGetAShortenUrl(ShortenUrl url) throws Exception {
        MvcResult mvcResult = doPostAndReturn(url);

        assertThanReturnedShortenUrlIrOkAndAssign(url, mvcResult.getResponse());
    }

    private MvcResult doPostAndReturn(ShortenUrl url) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MockHttpServletRequestBuilder post = post(ENDPOINT, url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(url));

        return mockMvc.perform(post)
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$typed", is(url.getTyped())))
                .andExpect(jsonPath("$shorten", isA(String.class)))
                .andReturn();
    }

    private void assertThanReturnedShortenUrlIrOkAndAssign(ShortenUrl url
            , MockHttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ShortenUrl resUrl = objectMapper.readValue(response.getContentAsByteArray(), ShortenUrl.class);

        assertNotNull(resUrl.getShorten());
        assertFalse(resUrl.getShorten().isEmpty());

        assertEquals(url.getTyped(), resUrl.getTyped());
        url.setShorten(resUrl.getShorten());
    }

    private void assertThanShortenRedirectTo(String shorten, YausService yausServiceMocked
            , String redirectName, HttpStatus httpStatus, boolean redirectToView) throws Exception {
        setStandAloneSetup(yausServiceMocked);

        mockMvc.perform(get("/{shorten}", shorten))
                .andExpect(status().is(httpStatus.value()))
                .andExpect(redirectToView ? view().name(redirectName) : redirectedUrl(redirectName));

        verify(yausServiceMocked).getRedirection(shorten);
    }

    private void setStandAloneSetup(YausService yausServiceMocked) {
        YausController yausController = new YausController(yausServiceMocked);

        mockMvc = MockMvcBuilders
                .standaloneSetup(yausController)
                .addFilter(secFilter)
                .build();

        assertNotNull(mockMvc);
    }
}
