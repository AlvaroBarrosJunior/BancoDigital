package com.bancodigital.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb004_cliente", schema="public")
@SequenceGenerator(name="sq_id_cliente", sequenceName = "seq_id_cliente", schema="public", allocationSize=1, initialValue=1)
public class ClienteModel implements Serializable{

	private static final long serialVersionUID = 5940769522858846419L;
	
	@Id
	@GeneratedValue(generator = "sq_id_cliente", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_cliente")
	private Long idCliente;
	
	@Column(name = "no_cliente")
	private String nomeCliente;
	
	@Column(name = "senhas_cliente")
	private String senhaCliente;

}
