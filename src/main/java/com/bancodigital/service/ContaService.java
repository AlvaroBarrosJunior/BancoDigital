package com.bancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancodigital.dto.ContaCadastroDTO;
import com.bancodigital.model.ContaModel;
import com.bancodigital.repository.ContaRepository;
import com.bancodigital.utils.BancoDigitalException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
	
	public ContaModel cadastrarConta(ContaCadastroDTO requisicao, Long idUsuario) throws BancoDigitalException {
		
		ContaModel conta = contaRepository.findByAgenciaTipoECliente(requisicao.getIdAgencia(), requisicao.getTipoConta(), idUsuario);
		
		if (conta == null) {			
			conta = new ContaModel();
			conta.setIdAgencia(requisicao.getIdAgencia());
			conta.setIdCliente(idUsuario);
			conta.setTipoConta(requisicao.getTipoConta());
			conta.setSaldoConta(0D);
			
			return contaRepository.saveAndFlush(conta);
		} else {
			throw new BancoDigitalException("Registro já existente, verifique informações da conta!");
		}
	}
	
	public ContaModel sacarValor(Long nuConta, Double valor) throws BancoDigitalException {
		ContaModel conta = contaRepository.findByNuConta(nuConta);
		
		if (conta != null) {
			if (conta.getSaldoConta() >= valor) {				
				conta.setSaldoConta(conta.getSaldoConta()-valor);
				
				contaRepository.atualizarSaldo(conta.getSaldoConta(), nuConta);
			} else {
				throw new BancoDigitalException("Saldo insuficiente!");
			}
			
			return conta;
		} else {
			throw new BancoDigitalException("Conta não encontrada!");
		}
	}
	
	
}
