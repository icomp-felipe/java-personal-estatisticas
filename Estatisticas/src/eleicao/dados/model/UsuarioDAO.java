package eleicao.dados.model;

import java.sql.*;
import javax.swing.JOptionPane;

import eleicao.dados.bd.Database;
import eleicao.dados.utils.AlertDialog;

/** Classe ObjetoDAO - faz a ponte entre as classes de usuários Java e os objetos do banco de dados
 *  @author Felipe André Souza da Silva 
 *  @version 1.00, 10/09/2014 */
public class UsuarioDAO extends BancoSQL {
	
	/** Faz o login no sistema  */
	public static String tryLogin(Usuario usuario) {
		String username = null;
		conecta();
		
		try {
			Statement st = getStatement();
			ResultSet rs = st.executeQuery(usuario.getLoginString());
			if (rs.next())
				username = new String(rs.getString(1));
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		finally {
			desconecta();
		}

		return username;
	}

	/** Atualiza a senha do usuário no sistema */
	public static void changePassword(String newPassword) {
		conecta();
		
		try {
			Statement st = getStatement();
			st.executeUpdate("UPDATE USUARIO SET USER_PASSWORD=PASSWORD('" + newPassword + "');");
			AlertDialog.informativo("Senha atualizada com sucesso!");
		}
		catch (SQLException exception) {
			AlertDialog.erro("Falha ao trocar senha!\nVerifique a pasta de tracing!");
			exception.printStackTrace();
		}
		finally {
			desconecta();
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
