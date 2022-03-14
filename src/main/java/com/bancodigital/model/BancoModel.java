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
@Table(name="tb001_banco", schema="public")
@SequenceGenerator(name="sq_id_banco", sequenceName = "seq_id_banco", schema="public", allocationSize=1, initialValue=1)
public class BancoModel implements Serializable{
	
	private static final long serialVersionUID = 6155214675319248540L;

	@Id
	@GeneratedValue(generator = "sq_id_banco", strategy = GenerationType.SEQUENCE)
	@Column(name="id_banco")
	private Long idBanco;
	
	@Column(name="no_banco")
	private String nomeBanco;

}
