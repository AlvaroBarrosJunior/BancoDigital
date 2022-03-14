package com.bancodigital.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.bancodigital.dto.AgenciaCadastroDTO;
import com.bancodigital.model.AgenciaModel;
import com.bancodigital.model.BancoModel;
import com.bancodigital.repository.AgenciaRepository;
import com.bancodigital.service.AgenciaService;
import com.bancodigital.utils.BancoDigitalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;

@SpringBootTest
public class AgenciaControllerTest {

	@Autowired
	private AgenciaController agenciaController;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private AgenciaService agenciaService;
	
	@MockBean
	private AgenciaRepository agenciaRepository;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(agenciaController);
	}
	
	@Test
	public void deveRetornarTodasAsAgencias() throws JsonProcessingException {
		BancoModel banco1 = new BancoModel(1L, "Comvalor");
		BancoModel banco2 = new BancoModel(2L, "Soulbank");
		
		List<AgenciaModel> lista = new ArrayList<AgenciaModel>();
		lista.add(new AgenciaModel(1L, "Faria Lima", 1L, banco1));
		lista.add(new AgenciaModel(2L, "Faria Lima", 2L, banco2));
		
		when(agenciaRepository.findAll()).thenReturn(lista);
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("agencia")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(lista))));
	}
	
	@Test
	public void deveRetornarAgenciasPorBanco() throws JsonProcessingException {
		BancoModel banco = new BancoModel(1L, "Comvalor");
		
		List<AgenciaModel> lista = new ArrayList<AgenciaModel>();
		lista.add(new AgenciaModel(1L, "Faria Lima", 1L, banco));
		lista.add(new AgenciaModel(2L, "Bras", 1L, banco));
		
		when(agenciaRepository.findByIdBanco(1L)).thenReturn(lista);
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("agencia/" + 1L)
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(lista))));
	}
	
	@Test
	public void deveCadastrarBanco() throws BancoDigitalException, JsonProcessingException {
		
		AgenciaCadastroDTO cadastro = new AgenciaCadastroDTO("Faria Lima", 1L);
		BancoModel banco = new BancoModel(1L, "Comvalor");
		AgenciaModel agencia = new AgenciaModel(1L, "Faria Lima", 1L, banco);
		
		when(agenciaService.cadastrarAgencia(cadastro)).thenReturn(agencia);
		
		given()
		.contentType(ContentType.JSON)
		.body(cadastro)
	.when()
		.post("/agencia/novo")
	.then()
		.statusCode(HttpStatus.CREATED.value())
		.contentType(ContentType.JSON)
		.body(is(equalTo(objectMapper.writeValueAsString(agencia))));
	}
}
