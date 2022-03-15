package com.bancodigital.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancodigital.dto.ClienteCadastroDTO;
import com.bancodigital.dto.LoginDTO;
import com.bancodigital.dto.TokenResponse;
import com.bancodigital.model.ClienteModel;
import com.bancodigital.repository.ClienteRepository;
import com.bancodigital.service.ClienteService;
import com.bancodigital.utils.BancoDigitalException;
import com.bancodigital.utils.ErrorHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Cliente")
@RestController
@RequestMapping("cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@ApiOperation(value = "Cadastrar cliente", notes = "Cadastrar uma nova Cliente")
	@PostMapping(value = "novo", produces = "application/json;charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cadastrarCliente(@RequestBody ClienteCadastroDTO requisicao) throws BancoDigitalException{
		try {
			ClienteModel cliente = clienteService.cadastro(requisicao);
			return ResponseEntity.status(HttpStatus.CREATED).body(cliente);			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorHandler(e.getMessage()));
		}
	}
	
	@ApiOperation(value = "Login", notes = "Logar no sistema")
	@PostMapping(value = "login", produces = "application/json;charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cadastrarCliente(@RequestBody LoginDTO requisicao) throws BancoDigitalException{
		
		try {
			String token = clienteService.login(requisicao);
			return ResponseEntity.ok(new TokenResponse(token));			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorHandler(e.getMessage()));
		}
	}
	
	@ApiOperation(value = "Buscar Clientes", notes = "Buscar todos os clientes cadastrados no sistema")
	@GetMapping(value = "", produces = "application/json;charset=utf-8")
	public ResponseEntity<?> buscarTodosOsClientes(){
		try {			
			List<ClienteModel> lista = clienteRepository.findAll();
			List<String> retorno = new ArrayList<String>();
			for (ClienteModel c : lista) {
				retorno.add(c.getNomeCliente());
			}
			return ResponseEntity.ok(retorno);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorHandler(e.getMessage()));
		}
	}
	
	@ApiOperation(value = "Detalhes cliente", notes = "Busca todos os detalhes do cliente logado")
	@GetMapping(value = "logado", produces = "application/json;charset=utf-8")
	public ResponseEntity<?> buscarCliente(@RequestHeader(value="Authorization") String token){
		try {			
			ClienteModel cliente = clienteService.validacao(token);
			return ResponseEntity.ok(cliente);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorHandler(e.getMessage()));
		}
	}

}
	