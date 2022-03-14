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

import com.bancodigital.dto.BancoCadastroDTO;
import com.bancodigital.model.BancoModel;
import com.bancodigital.repository.BancoRepository;
import com.bancodigital.service.BancoService;
import com.bancodigital.utils.BancoDigitalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;

@SpringBootTest
public class BancoControllerTest {

	@Autowired
	private BancoController bancoController;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private BancoService bancoService;
	
	@MockBean
	private BancoRepository bancoRepository;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(bancoController);
	}
	
	@Test
	public void deveRetornarTodosOsBancos() throws JsonProcessingException {
		List<BancoModel> lista = new ArrayList<BancoModel>();
		lista.add(new BancoModel(1L, "Comvalor"));
		lista.add(new BancoModel(2L, "Soulbank"));
		
		when(bancoRepository.findAll()).thenReturn(lista);
		
		String token = "aaaa";
		given()
			.accept(ContentType.JSON)
			.header("Authorization", "Token " + token )
		.when()
			.get("banco")
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(ContentType.JSON)
			.body(is(equalTo(objectMapper.writeValueAsString(lista))));
	}
	
	@Test
	public void deveCadastrarBanco() throws BancoDigitalException, JsonProcessingException {
		
		BancoCadastroDTO cadastro = new BancoCadastroDTO("Comvalor");
		BancoModel banco = new BancoModel(1L, "Comvalor");
		
		when(bancoService.cadastrarBanco(cadastro)).thenReturn(banco);
		
		given()
		.contentType(ContentType.JSON)
		.body(cadastro)
	.when()
		.post("/banco/novo")
	.then()
		.statusCode(HttpStatus.CREATED.value())
		.contentType(ContentType.JSON)
		.body(is(equalTo(objectMapper.writeValueAsString(banco))));
	}
}
