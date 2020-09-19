package eleicao.dados.model;

import java.sql.*;
import eleicao.dados.bd.Database;
import com.phill.libs.ui.AlertDialog;

/** Classe ObjetoDAO - faz a ponte entre as classes de usuários Java e os objetos do banco de dados
 *  @author Felipe André Souza da Silva 
 *  @version 1.00, 10/09/2014 */
public class UsuarioDAO {
	
	/** Faz o login no sistema  */
	public static String tryLogin(Usuario usuario) {
		
		String username = null;
		
		try {
			
			Connection c = Database.INSTANCE.getConnection();
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery(usuario.getLoginString());
			
			if (rs.next())
				username = new String(rs.getString(1));
			
			st.close();
			c .close();
			
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}

		return username;
	}

	/** Atualiza a senha do usuário no sistema */
	public static void changePassword(String newPassword) {
		
		try {
			
			Connection c = Database.INSTANCE.getConnection();
			Statement st = c.createStatement();
			st.executeUpdate("UPDATE USUARIO SET USER_PASSWORD=PASSWORD('" + newPassword + "');");
			
			AlertDialog.info("Senha atualizada com sucesso!");
		}
		catch (SQLException exception) {
			AlertDialog.error("Falha ao trocar senha!\nVerifique a pasta de tracing!");
			exception.printStackTrace();
		}
		
	}

	/** Troca o login do sistema.
	 *  @param newLogin - novo login
	 *  @throws SQLException quando há alguma falha de conexão com o banco de dados */
	public static void changeLogin(final String newLogin) throws SQLException {
		
		String query = String.format("UPDATE USUARIO SET USER_LOGIN_PK=''",newLogin);
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		st.executeUpdate(query);
		
		st.close();
		c .close();
		
	}
	
}
