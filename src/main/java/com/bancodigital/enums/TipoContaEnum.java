package com.bancodigital.enums;

public enum TipoContaEnum {

	CORRENTE(1, "Conta Corrente"),
	POUPANCA(1, "Conta Poupança");
	
	private Integer codigo;
	private String descricao;
	
	private static TipoContaEnum[] values = TipoContaEnum.values();
	
	private TipoContaEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static TipoContaEnum getTipoContaEnumByCodigo(int codigo) {
        for(TipoContaEnum t : values) {
            if(t.getCodigo().equals(codigo)) {
                return t;
            }
        }
        
        return null;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
