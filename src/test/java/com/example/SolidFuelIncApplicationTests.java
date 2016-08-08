package com.example;

import com.example.services.person.client.ClientServiceI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SolidFuelIncApplication.class)
@WebAppConfiguration
public class SolidFuelIncApplicationTests {

	@Test
	public void contextLoads() {
	}

}
