package com.example.controllers;

import com.example.SolidFuelIncApplication;
import com.example.adapters.AdminUserDetails;
import com.example.adapters.TokenAuthenticationService;
import com.example.configuration.PersistenceConfig;
import com.example.configuration.SecurityConfig;
import com.example.configuration.WebMvcConfig;
import com.example.domain.Address;
import com.example.domain.Admin;
import com.example.domain.Client;
import com.example.handlers.JwtAuthorizationHandler;
import com.example.utils.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Dmitrij on 30.07.2016.
 */
@SpringApplicationConfiguration(classes = {SolidFuelIncApplication.class, PersistenceConfig.class, WebMvcConfig.class, SecurityConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public class ClientControllerTest {

    @Resource
    FilterChainProxy filterChain;

    private MockMvc mockMvc;

    @Autowired
    private JwtAuthorizationHandler handler;

    @Autowired
    private TestUtils testUtils;

    @Resource
    private WebApplicationContext webApplicationContext;

    private Client mockClient;
    private Address mockAddress;
    private byte[] mockJsonClient;
    private Admin admin;
    private String adminCredentials;

    @PostConstruct
    public void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(filterChain).build();
        mockAddress = new Address("Kyiv", "Vasylkob srt.", 53);
        mockClient = new Client("Dmitriy", "Kostiushko", mockAddress);
        mockJsonClient = testUtils.convertToJson(mockClient);
        admin = new Admin("Dimitrij94", "d147896325", "380989785514", true);
        adminCredentials = handler.clientDetailsToToken(new AdminUserDetails(admin));
    }

    @Test
    public void testCreateClient() throws Exception {
        mockMvc.perform(post("/client")
                .with(csrf().asHeader())
                .header(TokenAuthenticationService.AUTH_HEADER_NAME, adminCredentials)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJsonClient))
                .andExpect(status().isOk());
    }


}