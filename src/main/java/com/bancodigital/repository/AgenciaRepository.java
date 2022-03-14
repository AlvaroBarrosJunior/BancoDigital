package com.bancodigital.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bancodigital.model.AgenciaModel;

@Transactional
public interface AgenciaRepository extends JpaRepository<AgenciaModel, Long> {

	@Query("SELECT a FROM AgenciaModel a WHERE a.nomeAgencia = ?1 AND a.idBanco = ?2")
	AgenciaModel findByNomeAgenciaEIdBanco(String nomeAgencia, Long idBanco);
	
	List<AgenciaModel> findByIdBanco(Long idBanco);
	
}
