package com.rabo.bank.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabo.bank.service.RaboStmtService;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class RaboCustStmtTest {

	@MockBean
	private RaboStmtService RaboStmtService;

	@Autowired
    private TestRestTemplate restTemplate;


	@Test
    public void test() {
        this.restTemplate.getForEntity(
            "/statement/generate", Object[].class).getBody();

    }
}
