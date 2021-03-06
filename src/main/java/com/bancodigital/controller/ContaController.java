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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancodigital.dto.ContaCadastroDTO;
import com.bancodigital.dto.DepositoSaqueDTO;
import com.bancodigital.dto.TranferenciaDTO;
import com.bancodigital.model.ClienteModel;
import com.bancodigital.model.ContaModel;
import com.bancodigital.repository.ContaRepository;
import com.bancodigital.service.ClienteService;
import com.bancodigital.service.ContaService;
import com.bancodigital.utils.BancoDigitalException;
import com.bancodigital.utils.ErrorHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Conta")
@RestController
@RequestMapping("conta")
@CrossOrigin("*")
public class ContaController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@ApiOperation(value = "Cadastrar conta", notes = "Cadastrar uma nova Conta (tipos de conta: Conta Corrente - 1, Conta Poupanca - 2)")
	@PostMapping(value = "novo", produces = "application/json;charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cadastrarConta(@RequestBody ContaCadastroDTO requisicao, @RequestHeader("Authorization") String token) throws BancoDigitalException{
		
		ClienteModel cliente = clienteService.validacao(token);
		if (cliente != null) {
			ContaModel conta = contaService.cadastrarConta(requisicao, cliente.getIdCliente());
			return ResponseEntity.status(HttpStatus.CREATED).body(conta);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado ou usu??rio n??o autorizado, realize login novamente");
	}
	
	@ApiOperation(value = "Buscar contas", notes = "Buscar todas as Contas do Cliente logado")
	@GetMapping(value = "", produces = "application/json;charset=utf-8")
	public ResponseEntity<?> buscarContasCliente(@RequestHeader("Authorization") String token) throws BancoDigitalException{
		try {
			
			ClienteModel cliente = clienteService.validacao(token);
			
			if (cliente != null) {
				List<ContaModel> listaConta = contaRepository.findByIdCliente(cliente.getIdCliente());
				return ResponseEntity.status(HttpStatus.CREATED).body(listaConta);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler("Token expirado ou usu??rio n??o autorizado, realize login novamente"));
		} catch (BancoDigitalException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler(e.getMessage()));
		}
	}
	
	@ApiOperation(value = "Buscar conta", notes = "Buscar uma Conta espec??fica do Cliente logado")
	@GetMapping(value = "{nuConta}", produces = "application/json;charset=utf-8")
	public ResponseEntity<?> buscarContasCliente(@PathVariable Long nuConta,@RequestHeader("Authorization") String token) throws BancoDigitalException{
		try {
			
			ClienteModel cliente = clienteService.validacao(token);
			
			if (cliente != null) {
				ContaModel conta = contaRepository.findByNuConta(nuConta);
				if (cliente.getIdCliente() == conta.getIdCliente())
					return ResponseEntity.status(HttpStatus.CREATED).body(conta);
				else
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler("Cliente logado n??o ?? o titular da conta!"));
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler("Token expirado ou usu??rio n??o autorizado, realize login novamente"));
		} catch (BancoDigitalException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler(e.getMessage()));
		}
	}
	
	@ApiOperation(value = "Despositar em conta", notes = "Realizar deposito em uma Conta do Cliente logado")
	@PutMapping(value = "deposito", produces = "application/json;charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> depositarValor(@RequestBody DepositoSaqueDTO requisicao, @RequestHeader("Authorization") String token){
		try {			
			ClienteModel cliente = clienteService.validacao(token);
			
			if (cliente != null) {
				ContaModel conta = contaRepository.findByNuConta(requisicao.getNuConta());
				if (cliente.getIdCliente() == conta.getIdCliente()) {
					conta.setSaldoConta(conta.getSaldoConta()+requisicao.getValor());
					contaRepository.atualizarSaldo(conta.getSaldoConta(), conta.getNuConta());
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(conta);
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler("Conta n??o pertencente ao usuario logado!"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler("Token expirado ou usu??rio n??o autorizado, realize login novamente"));
			}
		} catch (BancoDigitalException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorHandler(e.getMessage()));
		}
	}
	
	@ApiOperation(value = "Sacar de conta", notes = "Realizar saque em uma Conta do Cliente logado")
	@PutMapping(value = "saque", produces = "application/json;charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sacarValor(@RequestBody DepositoSaqueDTO requisicao, @RequestHeader("Authorization") String token){
		try {			
			ClienteModel cliente = clienteService.validacao(token);
			
			if (cliente != null) {
				ContaModel conta = contaRepository.findByNuConta(requisicao.getNuConta());
				if (cliente.getIdCliente() == conta.getIdCliente()) {
					conta = contaService.sacarValor(conta.getNuConta(), requisicao.getValor());
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(conta);
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler("Conta n??o pertencente ao usuario logado!"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler("Token expirado ou usu??rio n??o autorizado, realize login novamente"));
			}
		} catch (BancoDigitalException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorHandler(e.getMessage()));
		}
	}
	
	@ApiOperation(value = "Trasferencias em contas", notes = "BRealizar transferencia de uma Conta do Cliente logado para qualquer Conta cadastrada no sistema")
	@PutMapping(value = "transferencia", produces = "application/json;charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> transferirValor(@RequestBody TranferenciaDTO requisicao, @RequestHeader("Authorization") String token){
		try {			
			ClienteModel cliente = clienteService.validacao(token);
			
			if (cliente != null) {
				ContaModel contaOrigem = contaRepository.findByNuConta(requisicao.getNuContaOrigem());
				if (cliente.getIdCliente() == contaOrigem.getIdCliente()) {
					contaOrigem = contaService.sacarValor(requisicao.getNuContaOrigem(), requisicao.getValor());
					ContaModel contaDestino = contaRepository.findByNuConta(requisicao.getNuContaDestino());
					contaDestino.setSaldoConta(contaDestino.getSaldoConta()+requisicao.getValor());
					contaRepository.atualizarSaldo(contaDestino.getSaldoConta(), contaDestino.getNuConta());
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(contaOrigem);
				} else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler("Conta de origem n??o pertencente ao usuario logado!"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler("Token expirado ou usu??rio n??o autorizado, realize login novamente"));
			}
		} catch (BancoDigitalException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorHandler(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorHandler(e.getMessage()));
		}
	}
}
