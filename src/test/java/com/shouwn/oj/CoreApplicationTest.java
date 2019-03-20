package com.shouwn.oj;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.shouwn.oj.repository")
public class CoreApplicationTest {
	public void contextLoads() {
	}
}