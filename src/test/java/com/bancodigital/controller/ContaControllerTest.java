package com.bancodigital.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.bancodigital.dto.ContaCadastroDTO;
import com.bancodigital.dto.DepositoSaqueDTO;
import com.bancodigital.dto.TranferenciaDTO;
import com.bancodigital.model.AgenciaModel;
import com.bancodigital.model.BancoModel;
import com.bancodigital.model.ClienteModel;
import com.bancodigital.model.ContaModel;
import com.bancodigital.repository.ContaRepository;
import com.bancodigital.service.ClienteService;
import com.bancodigital.service.ContaService;
import com.bancodigital.utils.BancoDigitalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;

@SpringBootTest
public class ContaControllerTest {
	
	private static final String TOKEN ="eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDcyOTI4MjksInN1YiI6IjMiLCJleHAiOjE2NDcyOTQ2Mjl9.Y9EB4k7R-O47-ugc9RTpJvnWEXj9mHCfOjdppiv6Aqs";

	@Autowired
	private ContaController contaController;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ContaService contaService;
	
	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private ContaRepository contaRepository;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(contaController);
	}
	
	@Test
	public void deveCriarConta() throws BancoDigitalException, JsonProcessingException {
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		ContaModel contaCadastrada = new ContaModel(1L, 1, 0D, 1L, 1L, null, null);
		
		ContaCadastroDTO contaCadastro = new ContaCadastroDTO(1, 1L);
		
		when(clienteService.validacao(TOKEN)).thenReturn(cliente);
		when(contaService.cadastrarConta(contaCadastro, cliente.getIdCliente())).thenReturn(contaCadastrada);
		
		given()
			.contentType(ContentType.JSON)
			.header("Authorization", TOKEN)
			.body(contaCadastro)
		.when()
			.post("conta/novo")
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(contaCadastrada))));
	}
	
	@Test
	public void deveBuscarContasCliente() throws BancoDigitalException, JsonProcessingException {
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		ContaModel conta1 = new ContaModel(1L, 1, 0D, 1L, 1L, cliente, agencia);
		ContaModel conta2 = new ContaModel(1L, 2, 0D, 1L, 1L, cliente, agencia);
		
		List<ContaModel> listaContas = new ArrayList<ContaModel>();
		listaContas.add(conta1);
		listaContas.add(conta2);
		
		
		when(clienteService.validacao(TOKEN)).thenReturn(cliente);
		when(contaRepository.findByIdCliente(cliente.getIdCliente())).thenReturn(listaContas);
		
		given()
			.header("Authorization", TOKEN)
		.when()
			.get("conta")
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(listaContas))));
	}
	
	@Test
	public void deveDepositarConta() throws BancoDigitalException, JsonProcessingException {
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		ContaModel conta = new ContaModel(1L, 1, 0D, 1L, 1L, cliente, agencia);
		ContaModel contaAtualizada = new ContaModel(1L, 1, 50D, 1L, 1L, cliente, agencia);
		
		DepositoSaqueDTO deposito = new DepositoSaqueDTO(1L, 50D);
		
		when(clienteService.validacao(TOKEN)).thenReturn(cliente);
		when(contaRepository.findByNuConta(1L)).thenReturn(conta);
		doNothing().when(contaRepository).atualizarSaldo(50D, 1L);
		
		given()
			.contentType(ContentType.JSON)
			.header("Authorization", TOKEN)
			.body(deposito)
		.when()
			.put("conta/deposito")
		.then()
			.statusCode(HttpStatus.ACCEPTED.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(contaAtualizada))));
	}
	
	@Test
	public void deveSacarConta() throws BancoDigitalException, JsonProcessingException {
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		ContaModel conta = new ContaModel(1L, 1, 100D, 1L, 1L, cliente, agencia);
		ContaModel contaAtualizada = new ContaModel(1L, 1, 50D, 1L, 1L, cliente, agencia);
		
		DepositoSaqueDTO saque = new DepositoSaqueDTO(1L, 50D);
		
		when(clienteService.validacao(TOKEN)).thenReturn(cliente);
		when(contaRepository.findByNuConta(1L)).thenReturn(conta);
		when(contaService.sacarValor(1L, 50D)).thenReturn(contaAtualizada);
		
		given()
			.contentType(ContentType.JSON)
			.header("Authorization", TOKEN)
			.body(saque)
		.when()
			.put("conta/saque")
		.then()
			.statusCode(HttpStatus.ACCEPTED.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(contaAtualizada))));
	}
	
	@Test
	public void deveTransferirConta() throws BancoDigitalException, JsonProcessingException {
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		ContaModel contaOrigem = new ContaModel(1L, 1, 100D, 1L, 1L, cliente, agencia);
		ContaModel contaAtualizadaOrigem = new ContaModel(1L, 1, 50D, 1L, 1L, cliente, agencia);
		ContaModel contaDestino = new ContaModel(2L, 2, 0D, 1L, 1L, cliente, agencia);
		ContaModel contaAtualizadaDestino = new ContaModel(2L, 2, 50D, 1L, 1L, cliente, agencia);
		
		TranferenciaDTO transferencia = new TranferenciaDTO(2L, 1L, 50D);
		
		when(clienteService.validacao(TOKEN)).thenReturn(cliente);
		when(contaRepository.findByNuConta(1L)).thenReturn(contaOrigem);
		when(contaRepository.findByNuConta(2L)).thenReturn(contaDestino);
		when(contaService.sacarValor(1L, 50D)).thenReturn(contaAtualizadaOrigem);
		doNothing().when(contaRepository).atualizarSaldo(50D, 2L);
		
		given()
			.contentType(ContentType.JSON)
			.header("Authorization", TOKEN)
			.body(transferencia)
		.when()
			.put("conta/transferencia")
		.then()
			.statusCode(HttpStatus.ACCEPTED.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(contaAtualizadaOrigem))));
	}
	
	@Test
	public void deveBuscarContaClientePorNuConta() throws BancoDigitalException, JsonProcessingException {
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		ContaModel conta = new ContaModel(1L, 2, 0D, 1L, 1L, cliente, agencia);
		
		when(clienteService.validacao(TOKEN)).thenReturn(cliente);
		when(contaRepository.findByNuConta(1L)).thenReturn(conta);
		
		given()
			.header("Authorization", TOKEN)
		.when()
			.get("conta/{idConta}", 1L)
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(conta))));
	}
}
