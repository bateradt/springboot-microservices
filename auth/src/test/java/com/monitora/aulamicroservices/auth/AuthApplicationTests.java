package com.monitora.aulamicroservices.gateway.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class AuthApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	@Test
	public void gerarSenha() {
		System.out.printf("Hash: {}" + new BCryptPasswordEncoder().encode("123"));
	}


}
