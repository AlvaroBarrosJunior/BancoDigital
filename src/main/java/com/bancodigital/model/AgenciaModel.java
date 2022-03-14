package com.bancodigital.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb002_agencia", schema="public")
@SequenceGenerator(name="sq_id_agencia", sequenceName = "seq_id_agencia", schema="public", allocationSize=1, initialValue=1)
public class AgenciaModel implements Serializable{
	
	private static final long serialVersionUID = 2034704183680735765L;
	
	@Id
	@GeneratedValue(generator = "sq_id_agencia", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_agencia")
	private Long idAgencia;
	
	@Column(name="no_agencia")
	private String nomeAgencia;
	
	@Column(name="id_banco")
	private Long idBanco;
	
	@ManyToOne
	@JoinColumn(name="id_banco", insertable = false, updatable = false, nullable = false, foreignKey = @ForeignKey(name="fk_id_banco"))
	private BancoModel banco;

}
