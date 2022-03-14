package com.bancodigital.utils;

public class BancoDigitalException extends Exception {

	
	private static final long serialVersionUID = -7818601949577561516L;

	public BancoDigitalException() {
		
	}
	
	public BancoDigitalException(String mensagem) {
		super(mensagem);
	}
	
	public BancoDigitalException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
