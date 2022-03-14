package com.bancodigital.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.bancodigital.model.ClienteModel;

@Sql(value="/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value="/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DataJpaTest
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ClienteRepositoryTeste {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Test
	public void deveBuscarPorNomeESenha(){
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		ClienteModel teste = clienteRepository.findByNomeESenha("Teste da Silva", "teste123");
		
		Assertions.assertEquals(cliente, teste);
	}

}
