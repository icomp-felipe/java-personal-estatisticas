package com.estatisticas.model;

/** Classe Usuario - representa um usuário do sistema
 *  @author Felipe André Souza da Silva 
 *  @version 1.00, 10/09/2014 */
public class Usuario {

	/* Atributos da classe */
	private String login, senha;

	/** Construtor inicializando os atributos */
	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	/** Recupera uma string de login no banco de dados */
	public String getLoginString() {
		return "SELECT * FROM USUARIO WHERE USER_LOGIN_PK='" + login + "' AND USER_PASSWORD=PASSWORD('" + senha + "')";
	}
	
	/** Recupera uma string de atualização de senha no banco de dados */
	public String getUpdateString() {
		return "UPDATE USUARIO SET USER_PASSWORD=PASSWORD('" + senha + "') WHERE USER_LOGIN_PK='" + login + "'";
	}
	
}
