package com.bancodigital.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.bancodigital.model.AgenciaModel;
import com.bancodigital.model.BancoModel;

@Sql(value="/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value="/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AgenciaRepositoryTest {
	
	@Autowired
	private AgenciaRepository agenciaRepository;
	
	@Test
	@Order(1)
	public void deveBuscarAgencia() {
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		
		AgenciaModel teste = agenciaRepository.findByNomeAgenciaEIdBanco("Faria Lima", 1L);
		
		Assertions.assertEquals(agencia, teste);
	}
	
	@Test
	@Order(2)
	public void deveFalharAoBuscarAgencia() {
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(2L, "Faria Lima", 1L, banco);
		
		AgenciaModel teste = agenciaRepository.findByNomeAgenciaEIdBanco("Faria Lima", 2L);
		
		Assertions.assertNotEquals(agencia, teste);
	}

}
