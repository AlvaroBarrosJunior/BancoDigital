package com.bancodigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancodigital.dto.BancoCadastroDTO;
import com.bancodigital.model.BancoModel;
import com.bancodigital.repository.BancoRepository;
import com.bancodigital.service.BancoService;
import com.bancodigital.utils.BancoDigitalException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Banco")
@RestController
@RequestMapping("banco")
public class BancoController {
	
	@Autowired
	private BancoRepository bancoRepository;
	
	@Autowired
	private BancoService bancoService;
	
	@Operation(summary = "Buscar todos os bancos", description = "Buscar todos os Bancos cadastrados no banco de dados")
	@GetMapping(value = "", produces = "application/json;charset=utf-8")
	public ResponseEntity<List<BancoModel>> buscarTodosOsBancos(){
		List<BancoModel> lista = bancoRepository.findAll();
		return ResponseEntity.ok(lista);
	}
	
	@Operation(summary = "Cadastrar banco", description = "Cadastrar um novo Banco")
	@PostMapping(value = "novo", produces = "application/json;charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BancoModel> cadastrarBanco(@RequestBody BancoCadastroDTO requisicao) throws BancoDigitalException{
		
		BancoModel banco = bancoService.cadastrarBanco(requisicao);
		return ResponseEntity.status(HttpStatus.CREATED).body(banco);
	}

}
