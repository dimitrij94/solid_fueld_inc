package com.example.controllers;

import com.example.SolidFuelIncApplication;
import com.example.configuration.PersistenceConfig;
import com.example.configuration.SecurityConfig;
import com.example.configuration.WebMvcConfig;
import com.example.domain.Address;
import com.example.domain.Admin;
import com.example.domain.Client;
import com.example.domain.ClientOrder;
import com.example.services.admin.AdminServiceI;
import com.example.services.client.ClientServiceI;
import com.example.services.order.OrderServiceI;
import com.example.utils.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import javassist.NotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithSecurityContext;
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

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Dmitrij on 13.08.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = {SolidFuelIncApplication.class, PersistenceConfig.class, WebMvcConfig.class, SecurityConfig.class})
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public class OrderControllerTest {
    @Resource
    FilterChainProxy filterChain;

    private MockMvc mockMvc;

    @Autowired
    AdminServiceI adminService;

    @Autowired
    OrderServiceI orderService;

    @Autowired
    private TestUtils testUtils;

    @Resource
    private WebApplicationContext webApplicationContext;

    private final Address mockAddress = new Address("Kyiv", "Vasylkob srt.", 53);

    private final Client mockClient = new Client("dima.kost.94@gmail.com", "Dmitriy Kostiushko","0989785514");

    private final String adminName = "Dimitrij94";
    private final String adminPassword = "d147896325";

    private ClientOrder mockOrder = new ClientOrder(1000, "Deliver it fast!", mockClient);
    private byte[] jsonMockOrder;

    @PostConstruct
    public void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(filterChain).build();
        jsonMockOrder = testUtils.convertToJson(mockOrder);
    }

    @Test
    public void testCreateOrder() throws Exception {
        mockMvc.perform(post("/order")
                .with(csrf().asHeader())
                .content(jsonMockOrder)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
    }
}