package com.osoco.challenge.yaus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OsocoChallengeYausApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
public class OsocoChallengeYausApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private FilterChainProxy secFilter;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		assertNotNull(wac);
		assertNotNull(secFilter);

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
				.addFilter(secFilter)
				.build();
	}

	@Test
	public void homeGetAccessShouldBeAllowed() throws Exception {
		this.mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"));
	}

	@Test
	public void homeNoGetAccessShouldBeForbbiden() throws Exception {
		this.mockMvc.perform(put("/"))
				.andExpect(status().isForbidden());
	}
}
