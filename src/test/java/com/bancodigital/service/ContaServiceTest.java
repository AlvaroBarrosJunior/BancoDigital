package com.bancodigital.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bancodigital.dto.ContaCadastroDTO;
import com.bancodigital.enums.TipoContaEnum;
import com.bancodigital.model.AgenciaModel;
import com.bancodigital.model.BancoModel;
import com.bancodigital.model.ClienteModel;
import com.bancodigital.model.ContaModel;
import com.bancodigital.repository.ClienteRepository;
import com.bancodigital.repository.ContaRepository;
import com.bancodigital.utils.BancoDigitalException;

@SpringBootTest
public class ContaServiceTest {

	@Autowired
	ContaService contaService;
	
	@MockBean
	ContaRepository contaRepository;
	
	@MockBean
	ClienteRepository clienteRepository;
	
	@Test
	public void deveCadastrarConta() {
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		ContaModel conta = new ContaModel(1L, TipoContaEnum.CORRENTE.getCodigo(), 0D, 1L, 1L, cliente, agencia);
		ContaModel contaCadastro = new ContaModel(null, TipoContaEnum.CORRENTE.getCodigo(), 0D, 1L, 1L, null, null);
		ContaCadastroDTO cadastro = new ContaCadastroDTO(TipoContaEnum.CORRENTE.getCodigo(), 1L);
		
		when(contaRepository.saveAndFlush(contaCadastro)).thenReturn(conta);
		when(contaRepository.findByAgenciaTipoECliente(1L, TipoContaEnum.CORRENTE.getCodigo(), 1L)).thenReturn(null);
		
		try {
			ContaModel teste = contaService.cadastrarConta(cadastro, 1L);
			
			Assertions.assertEquals(conta, teste);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void deveFalharCadastroConta() {
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		ContaModel conta = new ContaModel(1L, TipoContaEnum.CORRENTE.getCodigo(), 0D, 1L, 1L, cliente, agencia);
		ContaModel contaCadastro = new ContaModel(null, TipoContaEnum.CORRENTE.getCodigo(), null, 1L, 1L, null, null);
		ContaCadastroDTO cadastro = new ContaCadastroDTO(TipoContaEnum.CORRENTE.getCodigo(), 1L);
		
		when(contaRepository.saveAndFlush(contaCadastro)).thenReturn(conta);
		when(contaRepository.findByAgenciaTipoECliente(1L, TipoContaEnum.CORRENTE.getCodigo(), 1L)).thenReturn(conta);
		
		try {
			ContaModel teste = contaService.cadastrarConta(cadastro, 1L);
			
			Assertions.assertThrows(BancoDigitalException.class, null);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void deveSacarDinheiro() {
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		ContaModel conta = new ContaModel(1L, TipoContaEnum.CORRENTE.getCodigo(), 100D, 1L, 1L, cliente, agencia);
		ContaModel contaSacada = new ContaModel(1L, TipoContaEnum.CORRENTE.getCodigo(), 50D, 1L, 1L, cliente, agencia);
		
		when(contaRepository.findByNuConta(1L)).thenReturn(conta);
		doNothing().when(contaRepository).atualizarSaldo(50D, 1L);
		
		
		try {
			ContaModel teste = contaService.sacarValor(1L, 50D);
			
			Assertions.assertEquals(contaSacada, teste);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deveFalharSaqueContaInesistente() {
		
		when(contaRepository.findByNuConta(1L)).thenReturn(null);
		
		try {
			ContaModel teste = contaService.sacarValor(1L, 50D);
			
			Assertions.assertThrows(BancoDigitalException.class, null);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deveFalharSaqueSaldoInsuficiente() {
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		ContaModel conta = new ContaModel(1L, TipoContaEnum.CORRENTE.getCodigo(), 40D, 1L, 1L, cliente, agencia);
		
		when(contaRepository.findByNuConta(1L)).thenReturn(conta);
		
		try {
			ContaModel teste = contaService.sacarValor(1L, 50D);
			
			Assertions.assertThrows(BancoDigitalException.class, null);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
	}
	
}
