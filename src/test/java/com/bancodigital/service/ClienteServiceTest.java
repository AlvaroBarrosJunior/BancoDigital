package com.bancodigital.service;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bancodigital.dto.ClienteCadastroDTO;
import com.bancodigital.dto.LoginDTO;
import com.bancodigital.model.ClienteModel;
import com.bancodigital.repository.ClienteRepository;
import com.bancodigital.utils.BancoDigitalException;
import com.bancodigital.utils.TokenUtils;

@SpringBootTest
public class ClienteServiceTest {

	private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDcxNzgzOTYsInN1YiI6IjEiLCJleHAiOjE2NDcxODAxOTZ9.2xYsxLCq9BzKRfmVEo9y3_K2sRP-iJcN-KgntvdLwwE";
	
	ClienteModel cliente = new ClienteModel();
	LoginDTO loginDto = new LoginDTO();
	
	@Autowired
	ClienteService clienteService;
	
	@MockBean
	ClienteRepository clienteRepository;
	
	@BeforeEach
	public void setup() {
		cliente.setIdCliente(1L); 
		cliente.setNomeCliente("Teste da Silva");
		cliente.setSenhaCliente("teste123");
		
		loginDto.setNomeCliente("Teste da Silva");
		loginDto.setSenhaCliente("teste123");
	}
	
	@Test
	public void deveLogarCliente() {
		when(clienteRepository.findByNomeESenha(loginDto.getNomeCliente(),loginDto.getSenhaCliente()))
			.thenReturn(cliente);
		try(MockedStatic<TokenUtils> tokenUtils = Mockito.mockStatic(TokenUtils.class)){
				tokenUtils.when(()->TokenUtils.generateToken(cliente)).thenReturn(TOKEN);
				String teste = clienteService.login(loginDto);		
			Assertions.assertEquals(TOKEN, teste);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deveFalharNoLogin() {
		when(clienteRepository.findByNomeESenha(loginDto.getNomeCliente(),loginDto.getSenhaCliente()))
			.thenReturn(null);
		try(MockedStatic<TokenUtils> tokenUtils = mockStatic(TokenUtils.class)){
			
			String teste = clienteService.login(loginDto);
			
			Assertions.assertThrows(BancoDigitalException.class, null);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deveCadastrarCliente() {
		ClienteCadastroDTO cadastro = new ClienteCadastroDTO("Teste da Silva", "teste123");
		ClienteModel clienteCadastro = new ClienteModel(null, "Teste da Silva", "teste123");
		
		when(clienteRepository.saveAndFlush(clienteCadastro)).thenReturn(cliente);
		when(clienteRepository.findByNomeESenha(loginDto.getNomeCliente(),loginDto.getSenhaCliente()))
		.thenReturn(null);
		
		try {
			ClienteModel teste = clienteService.cadastro(cadastro);
			
			Assertions.assertEquals(cliente, teste);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deveFalharNoCadastro() {
		ClienteCadastroDTO cadastro = new ClienteCadastroDTO("Teste da Silva", "teste123");
		ClienteModel clienteCadastro = new ClienteModel(null, "Teste da Silva", "teste123");
		
		when(clienteRepository.saveAndFlush(clienteCadastro)).thenReturn(cliente);
		when(clienteRepository.findByNomeESenha(cadastro.getNomeCliente(),cadastro.getSenhaCliente()))
		.thenReturn(cliente);
		
		try {
			ClienteModel teste = clienteService.cadastro(cadastro);
			
			Assertions.assertThrows(BancoDigitalException.class, null);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void devePassarValidacao() {
//		try {			
//			ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
//			Optional<ClienteModel> clienteoptional = Optional.of(cliente);
//			when(clienteRepository.findById(1L)).thenReturn(clienteoptional);
//			
//			ClienteModel teste = clienteService.validacao(TOKEN);
//			
//			Assertions.assertEquals(cliente, teste);
//		} catch (BancoDigitalException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void deveFalharValidacao() {
		try {			
			ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
			Optional<ClienteModel> clienteoptional = Optional.of(cliente);
			when(clienteRepository.findById(1L)).thenReturn(clienteoptional);
			
			ClienteModel teste = clienteService.validacao(TOKEN);
			
			Assertions.assertThrows(BancoDigitalException.class, null);
		} catch (BancoDigitalException e) {
			e.printStackTrace();
		}
	}
	
}
