package com.estatisticas.dao;

import java.sql.*;
import com.estatisticas.bd.Database;
import com.estatisticas.model.Usuario;

/** Faz a ponte entre as classes de usuários Java e os objetos do banco de dados.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 2.0, 20/09/2020 */
public class UsuarioDAO {
	
	/** Faz o login no sistema.
	 *  @param usuario - usuário do sistema.
	 *  @throws SQLException quando acontece algum erro de comunicação com o banco de dados. */
	public static boolean tryLogin(final Usuario usuario) throws SQLException {
		
		boolean status;
		String  query  = UsuarioParser.getLoginString(usuario);
		
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		status = rs.next();
		
		st.close();
		c .close();
			
		return status;
	}
	
	/** Troca o login do sistema.
	 *  @param newLogin - novo login
	 *  @throws SQLException quando acontece algum erro de comunicação com o banco de dados. */
	public static void changeLogin(final String newLogin) throws SQLException {
		
		String query = UsuarioParser.getUpdateLoginString(newLogin);
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		st.executeUpdate(query);
		
		st.close();
		c .close();
		
	}
	

	/** Atualiza a senha do usuário no sistema.
	 *  @param newPassword - nova senha de acesso
	 *  @throws SQLException quando acontece algum erro de comunicação com o banco de dados. */
	public static void changePassword(final String newPassword) throws SQLException {
		
		String query = UsuarioParser.getUpdatePasswordString(newPassword);
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		st.executeUpdate(query);
		
		st.close();
		c .close();
		
	}
	
}
