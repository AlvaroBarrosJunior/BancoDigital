package com.bancodigital.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bancodigital.model.ClienteModel;

@Transactional
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
	
	@Query("SELECT c FROM ClienteModel c WHERE nomeCliente = ?1 AND senhaCliente = ?2")
	ClienteModel findByNomeESenha(String nome, String senha);
}
