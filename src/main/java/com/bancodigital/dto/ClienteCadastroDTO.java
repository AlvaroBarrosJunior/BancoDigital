package com.bancodigital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCadastroDTO {

	private String nomeCliente;
	private String senhaCliente;
}
