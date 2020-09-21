package com.estatisticas.model;

/** Modelagem de um usuário do sistema.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 2.0, 20/09/2020 */
public class Usuario {

	// Atributos da classe
	private String login, senha;

	/** Construtor inicializando os atributos.
	 *  @param login - login do sistema
	 *  @param senha - senha de acesso */
	public Usuario(final String login, final String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	/** Setter do login de acesso.
	 *  @param login - login acesso do usuário */
	public void setLogin(final String login) {
		this.login = login;
	}
	
	/** Setter da senha de acesso.
	 *  @param login - senha de acesso do usuário */
	public void setSenha(final String senha) {
		this.senha = senha;
	}

	/** Getter do login de usuário.
	 *  @return Login de usuário. */
	public String getLogin() {
		return this.login;
	}
	
	/** Getter da senha de usuário.
	 *  @return Senha de usuário. */
	public String getSenha() {
		return this.senha;
	}
	
}
