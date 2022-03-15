package com.bancodigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancodigital.dto.AgenciaCadastroDTO;
import com.bancodigital.model.AgenciaModel;
import com.bancodigital.repository.AgenciaRepository;
import com.bancodigital.service.AgenciaService;
import com.bancodigital.utils.BancoDigitalException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Agencia")
@RestController
@RequestMapping("agencia")
public class AgenciaController {

	@Autowired
	private AgenciaRepository agenciaRepository;
	
	@Autowired
	private AgenciaService agenciaService;
	
	@ApiOperation(value = "Busca agencias", notes = "Buscar todas as agencias cadastradas no sistema")
	@GetMapping(value = "", produces = "application/json;charset=utf-8")
	public ResponseEntity<List<AgenciaModel>> buscarTodasAsAgencias(){
		List<AgenciaModel> lista = agenciaRepository.findAll();
		return ResponseEntity.ok(lista);
	}
	
	@ApiOperation(value = "Buscar agencias do banco", notes = "Buscar todas as agencias cadastradas para o banco indicado")
	@GetMapping(value = "{idBanco}", produces = "application/json;charset=utf-8")
	public ResponseEntity<List<AgenciaModel>> buscarAgenciasPorBanco(@PathVariable Long idBanco){
		List<AgenciaModel> lista = agenciaRepository.findByIdBanco(idBanco);
		return ResponseEntity.ok(lista);
	}
	
	@ApiOperation(value = "Cadastrar agencia", notes = "Cadastrar uma nova agencia")
	@PostMapping(value = "novo", produces = "application/json;charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AgenciaModel> cadastrarAgencia(@RequestBody AgenciaCadastroDTO requisicao) throws BancoDigitalException{
		
		AgenciaModel banco = agenciaService.cadastrarAgencia(requisicao);
		return ResponseEntity.status(HttpStatus.CREATED).body(banco);
	}
}
