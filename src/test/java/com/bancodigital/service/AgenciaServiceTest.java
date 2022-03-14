package com.bancodigital.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bancodigital.dto.AgenciaCadastroDTO;
import com.bancodigital.dto.BancoCadastroDTO;
import com.bancodigital.model.AgenciaModel;
import com.bancodigital.model.BancoModel;
import com.bancodigital.repository.AgenciaRepository;
import com.bancodigital.utils.BancoDigitalException;

@SpringBootTest
public class AgenciaServiceTest {

	@Autowired
	AgenciaService agenciaService;
	
	@MockBean
	AgenciaRepository agenciaRepository;
	
	@Test
	public void deveCadastrarBanco() {
		AgenciaCadastroDTO cadastro = new AgenciaCadastroDTO("Faria Lima", 1L);
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		AgenciaModel bancoSalvar = new AgenciaModel(null, "Faria Lima", 1L, null);
		
		when(agenciaRepository.saveAndFlush(bancoSalvar)).thenReturn(agencia);
		when(agenciaRepository.findByNomeAgenciaEIdBanco(cadastro.getNomeAgencia(), cadastro.getIdBanco()))
			.thenReturn(null);
		
		try {
			AgenciaModel teste = agenciaService.cadastrarAgencia(cadastro);
			
			Assertions.assertEquals(agencia, teste);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
	}
}
