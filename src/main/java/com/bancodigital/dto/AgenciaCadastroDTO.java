package com.bancodigital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenciaCadastroDTO {

	private String nomeAgencia;
	private Long idBanco;
}
