package com.example.controllers;

import com.example.SolidFuelIncApplication;
import com.example.configuration.PersistenceConfig;
import com.example.configuration.SecurityConfig;
import com.example.configuration.WebMvcConfig;
import com.example.domain.Address;
import com.example.domain.Admin;
import com.example.domain.Client;
import com.example.services.admin.AdminServiceI;
import com.example.services.client.ClientServiceI;
import com.example.utils.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import javassist.NotFoundException;
import org.junit.After;
import org.junit.Before;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    AdminServiceI adminService;

    @Autowired
    ClientServiceI clientService;

    @Autowired
    private TestUtils testUtils;

    @Resource
    private WebApplicationContext webApplicationContext;

    private final Address mockAddress = new Address("Kyiv", "Vasylkob srt.", 53);
    private final Client mockClient = new Client("dima.kost.94@gmail.com", "Dmitriy Kostiushko", mockAddress, "0989785514");
    private final Client mockInvalidClient = new Client(null, null, null, null);

    private byte[] mockJsonClient;
    private final String adminName = "Dimitrij94";
    private final String adminPassword = "d147896325";

    @PostConstruct
    public void setUp() throws JsonProcessingException {
        mockJsonClient = testUtils.convertToJson(mockClient);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(filterChain).build();
    }


    @Before
    public void tesAdmin() {
        assert adminService.findByUserName(adminName) != null;
    }

    @Test
    public void testCreateClient() throws Exception {
        mockMvc.perform(post("/client")
                .with(csrf().asHeader())
                .with(httpBasic(adminName, adminPassword))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJsonClient))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetClient() throws Exception {
        Client client = clientService.save(mockClient);
        mockMvc.perform(get("/client/" + client.getId())
                .with(csrf().asHeader())
                .with(httpBasic(adminName, adminPassword))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        clientService.delete(client.getId());
    }

    @Test
    public void testDeleteClient() throws Exception {
        Client client = clientService.save(mockClient);
        mockMvc.perform(delete("/client/" + client.getId())
                .with(csrf().asHeader())
                .with(httpBasic(adminName, adminPassword))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
        assert clientService.find(client.getId()) == null;
    }

    @Test
    public void testUpdateClient() throws Exception {
        String testName = "Test";
        Client mockClient = this.mockClient;
        mockClient.setName(testName);
        Client client = clientService.save(mockClient);
        mockMvc.perform(put("/client/" + client.getId())
                .with(csrf().asHeader())
                .with(httpBasic(adminName, adminPassword))
                .content(testUtils.convertToJson(mockClient))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
        assert clientService.find(client.getId()).getName().equals(testName);
        clientService.delete(client.getId());
    }

    @Test
    public void testPostInvalidClient() throws Exception {
        mockMvc.perform(post("/client")
                .with(csrf().asHeader())
                .with(httpBasic(adminName, adminPassword))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(testUtils.convertToJson(mockInvalidClient)))
                .andExpect(status().isBadRequest());
    }


}