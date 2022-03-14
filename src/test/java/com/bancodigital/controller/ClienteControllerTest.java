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

import com.bancodigital.dto.ClienteCadastroDTO;
import com.bancodigital.dto.LoginDTO;
import com.bancodigital.model.ClienteModel;
import com.bancodigital.repository.ClienteRepository;
import com.bancodigital.service.ClienteService;
import com.bancodigital.utils.BancoDigitalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;

@SpringBootTest
public class ClienteControllerTest {

	private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwibm9tZUNsaWVudGUiOiJUZXN0ZSBkYSBTaWx2YSIsInNlbmhhQ2xpZW50ZSI6IlRlc3RlMTIzIn0.hIun_jeWewRztx2lWSkAe3Xbxt_YVkiHyrtgcpAUkmc";

	@Autowired
	private ClienteController clienteController;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ClienteService clienteService;
	
	@MockBean
	private ClienteRepository clienteRepository;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(clienteController);
	}
	
	@Test
	public void deveCriarCliente() throws BancoDigitalException, JsonProcessingException {
		
		ClienteCadastroDTO cadastro = new ClienteCadastroDTO("Teste da Silva", "teste123");
		ClienteModel cliente = new ClienteModel(1L, "Teste da Silva", "teste123");
		
		when(clienteService.cadastro(cadastro)).thenReturn(cliente);
		
		given()
			.contentType(ContentType.JSON)
			.body(cadastro)
		.when()
			.post("/cliente/novo")
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(cliente))));

	}
	
	@Test
	public void deveLogarCliente() throws BancoDigitalException {
		LoginDTO login = new LoginDTO("Teste da Silva", "teste123");
		
		when(clienteService.login(login)).thenReturn(TOKEN);
		
		given()
			.contentType(ContentType.JSON)
			.body(login)
		.when()
			.post("/cliente/login")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(TOKEN)));
	}
	
	@Test
	public void deveRetornarTodosOsClientes() throws JsonProcessingException {
		List<ClienteModel> lista = new ArrayList<ClienteModel>();
		lista.add(new ClienteModel(1L, "Teste da Silva", "teste123"));
		lista.add(new ClienteModel(2L, "Maria Testeira", "teste456"));
		
		when(clienteRepository.findAll()).thenReturn(lista);
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("cliente")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(lista))));
	}
	
}
