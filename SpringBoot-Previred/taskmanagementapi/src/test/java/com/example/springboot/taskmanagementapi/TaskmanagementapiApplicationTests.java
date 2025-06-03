package com.example.springboot.taskmanagementapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import com.example.springboot.taskmanagementapi.security.SpringSecurityConfig;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(SpringSecurityConfig.class)
class TaskmanagementapiApplicationTests {

	@Test
	void contextLoads() {
	}

}
