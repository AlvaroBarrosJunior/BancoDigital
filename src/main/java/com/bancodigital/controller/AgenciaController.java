package com.bancodigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bancodigital.dto.AgenciaCadastroDTO;
import com.bancodigital.model.AgenciaModel;
import com.bancodigital.repository.AgenciaRepository;
import com.bancodigital.service.AgenciaService;
import com.bancodigital.utils.BancoDigitalException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Agencia")
@RestController
@RequestMapping("agencia")
public class AgenciaController {

	@Autowired
	private AgenciaRepository agenciaRepository;
	
	@Autowired
	private AgenciaService agenciaService;
	
	@Operation(summary = "Buscar todas as agencias", description = "Retorna todas as Agencias cadastradas no Banco de Dados")
	@GetMapping(value = "", produces = "application/json;charset=utf-8")
	public ResponseEntity<List<AgenciaModel>> buscarTodasAsAgencias(){
		List<AgenciaModel> lista = agenciaRepository.findAll();
		return ResponseEntity.ok(lista);
	}
	
	@Operation(summary = "Buscar todas as agencias do banco", description = "Retorna todas as Agencias de um determinado Banco cadastradas no Banco de Dados")
	@GetMapping(value = "{idBanco}", produces = "application/json;charset=utf-8")
	public ResponseEntity<List<AgenciaModel>> buscarAgenciasPorBanco(@PathVariable Long idBanco){
		List<AgenciaModel> lista = agenciaRepository.findByIdBanco(idBanco);
		return ResponseEntity.ok(lista);
	}
	
	@Operation(summary = "Cadastrar agencia", description = "Cadastra uma nova Agencia")
	@PostMapping(value = "novo", produces = "application/json;charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AgenciaModel> cadastrarAgencia(@RequestBody AgenciaCadastroDTO requisicao) throws BancoDigitalException{
		
		AgenciaModel banco = agenciaService.cadastrarAgencia(requisicao);
		return ResponseEntity.status(HttpStatus.CREATED).body(banco);
	}
}
