package com.bancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancodigital.dto.AgenciaCadastroDTO;
import com.bancodigital.model.AgenciaModel;
import com.bancodigital.repository.AgenciaRepository;
import com.bancodigital.utils.BancoDigitalException;

@Service
public class AgenciaService {

	@Autowired
	AgenciaRepository agenciaRepository;
	
	public AgenciaModel cadastrarAgencia(AgenciaCadastroDTO requisicao) throws BancoDigitalException {
		
		AgenciaModel agencia = agenciaRepository.findByNomeAgenciaEIdBanco(requisicao.getNomeAgencia(), requisicao.getIdBanco());
		
		if (agencia == null) {
			agencia = new AgenciaModel();
			agencia.setNomeAgencia(requisicao.getNomeAgencia());
			agencia.setIdBanco(requisicao.getIdBanco());
			
			return agenciaRepository.saveAndFlush(agencia);
		} else {
			throw new BancoDigitalException("Registro já existente!");
		}
	}
}
