package com.osoco.challenge.yaus;

import org.junit.Before;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OsocoChallengeYausApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
public abstract class OsocoChallengeYausApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	protected FilterChainProxy secFilter;

	protected MockMvc mockMvc;

	@Before
	public void setup() {
		assertNotNull(wac);
		assertNotNull(secFilter);

		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilter(secFilter)
				.build();
	}
}
