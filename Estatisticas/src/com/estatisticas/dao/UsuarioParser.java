package com.estatisticas.dao;

import com.estatisticas.model.Usuario;

/** Contém as classes que montam queries de BD com base nos dados de um {@link Usuario}.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 1.5, 21/09/2020 */
public class UsuarioParser {

	/** Recupera uma string de login no banco de dados.
	 *  @return String de login no banco de dados. */
	protected static String getLoginString(final Usuario usuario) {
		return String.format("SELECT * FROM USUARIO WHERE USER_LOGIN_PK='%s' AND USER_PASSWORD=PASSWORD('%s')", usuario.getLogin(), usuario.getSenha());
	}

	/** Recupera uma string de atualização de login no banco de dados.
	 *  @return String de atualização de login no banco de dados. */
	protected static String getUpdateLoginString(final String newLogin) {
		return String.format("UPDATE USUARIO SET USER_LOGIN_PK='%s'", newLogin);
	}

	/** Recupera uma string de atualização de senha no banco de dados.
	 *  @return String de atualização de senha no banco de dados. */
	protected static String getUpdatePasswordString(final String newPassword) {
		return String.format("UPDATE USUARIO SET USER_PASSWORD=PASSWORD('%s')", newPassword);
	}

}
