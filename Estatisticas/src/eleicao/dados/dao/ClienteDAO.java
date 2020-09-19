package eleicao.dados.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import eleicao.dados.bd.Database;
import eleicao.dados.model.Dado;
import eleicao.dados.parser.DadoParser;

public class DadoDAO {
	
	public static void commit(Dado dado) throws SQLException {
		
		String query = (dado.isEmpty()) ? DadoParser.getInsertQuery(dado) : DadoParser.getUpdateQuery(dado);
		
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		st.executeUpdate(query);
		
		st.close();
		c .close();
		
	}

}
