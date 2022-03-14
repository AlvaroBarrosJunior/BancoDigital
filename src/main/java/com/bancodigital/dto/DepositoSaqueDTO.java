package com.bancodigital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositoSaqueDTO {

	private Long nuConta;
	private Double valor;
}
