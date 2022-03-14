package com.bancodigital.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bancodigital.model.ContaModel;

@Transactional
public interface ContaRepository extends JpaRepository<ContaModel, Long> {

	@Query("SELECT c FROM ContaModel c WHERE c.idAgencia = ?1 AND c.tipoConta = ?2 AND c.idCliente = ?3")
	ContaModel findByAgenciaTipoECliente(Long idAgencia, Integer tipoConta, Long idCliente);
	
	List<ContaModel> findByIdCliente(Long idCliente);
	
	ContaModel findByNuConta(Long nuConta);
	
	@Modifying
	@Query("UPDATE ContaModel c SET c.saldoConta = ?1 WHERE c.nuConta = ?2")
	void atualizarSaldo(Double novoSaldo, Long nuConta);
}
