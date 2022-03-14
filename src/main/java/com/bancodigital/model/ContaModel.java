package com.bancodigital.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb003_conta", schema="public")
@SequenceGenerator(name="sq_nu_conta", sequenceName = "seq_nu_conta", schema="public", allocationSize=1, initialValue=1)
public class ContaModel implements Serializable{
	
	private static final long serialVersionUID = -4166999464158037604L;
	
	@Id
	@GeneratedValue(generator = "sq_nu_conta", strategy = GenerationType.SEQUENCE)
	@Column(name="nu_conta")
	private Long nuConta;
	
	@Column(name="tipo_conta")
	private Integer tipoConta;
	
	@Column(name="saldo_conta")
	private Double saldoConta;
	
	@Column(name = "id_cliente")
	private Long idCliente;
	
	@Column(name = "id_agencia")
	private Long idAgencia;
	
	@OneToOne
	@JoinColumn(name="id_cliente", insertable = false, updatable = false, nullable = false, foreignKey = @ForeignKey(name="fk_id_cliente"))
	private ClienteModel cliente;
	
	@OneToOne
	@JoinColumn(name="id_agencia", insertable = false, updatable = false, nullable = false, foreignKey = @ForeignKey(name="fk_id_agencia"))
	private AgenciaModel agencia;
	
}
