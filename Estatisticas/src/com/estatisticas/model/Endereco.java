package com.estatisticas.model;

/** Classe auxiliar que contém a modelagem de um endereço.
 *  Útil para recuperação de endereços físicos da web. */
public class Endereco {
	
	// Atributos
	private final String logradouro, bairro, cidade, uf;
	
	/** Construtor setando parâmetros.
	 *  @param logradouro - logradouro
	 *  @param bairro - bairro
	 *  @param cidade - cidade ou município
	 *  @param uf - unidade de federação (estado) */
	public Endereco(final String logradouro, final String bairro, final String cidade, final String uf) {
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
	}

	/** Recupera o logradouro do endereço.
	 *  @return Logradouro do endereço. */
	public String getLogradouro() {
		return this.logradouro;
	}

	/** Recupera o bairro do endereço.
	 *  @return Bairro do endereço. */
	public String getBairro() {
		return this.bairro;
	}

	/** Recupera a cidade ou município do endereço.
	 *  @return Cidade ou município do endereço. */
	public String getCidade() {
		return this.cidade;
	}

	/** Recupera a unidade de federação do endereço (estado).
	 *  @return Unidade de federação (estado) do endereço. */
	public String getUF() {
		return this.uf;
	}

}
