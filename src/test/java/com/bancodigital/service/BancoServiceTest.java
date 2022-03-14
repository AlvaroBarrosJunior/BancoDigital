package com.bancodigital.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bancodigital.dto.BancoCadastroDTO;
import com.bancodigital.model.BancoModel;
import com.bancodigital.repository.BancoRepository;
import com.bancodigital.utils.BancoDigitalException;

@SpringBootTest
public class BancoServiceTest {
	
	@Autowired
	BancoService bancoService;
	
	@MockBean
	BancoRepository bancoRepository;
	
	@Test
	public void deveCadastrarBanco() {
		BancoCadastroDTO cadastro = new BancoCadastroDTO("Comvalor");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		BancoModel bancoSalvar = new BancoModel(null, "Comvalor");
		
		when(bancoRepository.saveAndFlush(bancoSalvar)).thenReturn(banco);
		when(bancoRepository.findByNomeBanco(cadastro.getNomeBanco()))
			.thenReturn(null);
		
		try {
			BancoModel teste = bancoService.cadastrarBanco(cadastro);
			
			Assertions.assertEquals(banco, teste);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
	}

}
