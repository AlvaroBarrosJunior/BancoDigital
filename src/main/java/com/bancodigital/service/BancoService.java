package com.bancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancodigital.dto.BancoCadastroDTO;
import com.bancodigital.model.BancoModel;
import com.bancodigital.repository.BancoRepository;
import com.bancodigital.utils.BancoDigitalException;

@Service
public class BancoService {
	
	@Autowired
	BancoRepository bancoRepository;
	
	public BancoModel cadastrarBanco(BancoCadastroDTO requisicao) throws BancoDigitalException {
		
		BancoModel banco = bancoRepository.findByNomeBanco(requisicao.getNomeBanco());
		
		if (banco == null) {
			banco = new BancoModel();
			banco.setNomeBanco(requisicao.getNomeBanco());
			
			return bancoRepository.saveAndFlush(banco);
		} else {
			throw new BancoDigitalException("Registro já existente!");
		}
	}

}
