package com.bancodigital.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancodigital.dto.ClienteCadastroDTO;
import com.bancodigital.dto.LoginDTO;
import com.bancodigital.model.ClienteModel;
import com.bancodigital.repository.ClienteRepository;
import com.bancodigital.utils.BancoDigitalException;
import com.bancodigital.utils.TokenUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	
	public ClienteModel cadastro(ClienteCadastroDTO requisicao) throws BancoDigitalException {
		
		ClienteModel cliente = clienteRepository.findByNomeESenha(requisicao.getNomeCliente(), requisicao.getSenhaCliente());
		
		if (cliente == null) {
			cliente = new ClienteModel();
			cliente.setNomeCliente(requisicao.getNomeCliente());
			cliente.setSenhaCliente(requisicao.getSenhaCliente());
			
			return clienteRepository.saveAndFlush(cliente);
		} else {
			throw new BancoDigitalException("Registro já existente, altere nome ou senha!");
		}
	}
	
	public String login(LoginDTO loginDto) throws BancoDigitalException {
		
		ClienteModel cliente = clienteRepository.findByNomeESenha(loginDto.getNomeCliente(), loginDto.getSenhaCliente());
		
		if (cliente != null) {			
			String token = TokenUtils.generateToken(cliente);
			return token;
		} else {
			throw new BancoDigitalException("Cliente não encontrado!");
		}
	}
	
	public ClienteModel validacao(String token) throws BancoDigitalException {
		try {
			Claims claims = TokenUtils.decodeToken(token);
			
			if(claims.getExpiration().before(new Date()))
				throw new BancoDigitalException("Token Expirado!");
			
			return clienteRepository.findById(Long.parseLong(claims.getSubject())).get();
			
		} catch (BancoDigitalException e) {
			e.printStackTrace();
			throw e;
		} catch (ExpiredJwtException e) {
			throw new BancoDigitalException("Token Expirado!");
		} catch (Exception e) {
			throw new BancoDigitalException("Token Inválido!");
		}
	}
}
