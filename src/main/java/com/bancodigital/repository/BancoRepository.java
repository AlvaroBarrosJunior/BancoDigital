package com.bancodigital.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancodigital.model.BancoModel;

@Transactional
public interface BancoRepository extends JpaRepository<BancoModel, Long> {

	BancoModel findByNomeBanco(String nomeBanco);
}
