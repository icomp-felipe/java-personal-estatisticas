package eleicao.dados.dao;

import java.io.*;
import java.sql.*;
import java.util.*;

import eleicao.dados.bd.Database;
import eleicao.dados.model.Cliente;
import eleicao.dados.parser.ClienteParser;

public class ClienteDAO {
	
	public static void commit(final Cliente dado) throws SQLException {
		
		String query = (dado.isEmpty()) ? ClienteParser.getInsertQuery(dado) : ClienteParser.getUpdateQuery(dado);
		
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		st.executeUpdate(query);
		
		st.close();
		c .close();
		
	}
	
	public static Cliente complete(final Cliente cliente) throws SQLException {
		
		String query = ClienteParser.getQuery(cliente);
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		ResultSet rs = st.executeQuery(query); 
		
		if (rs.next()) {
			
			cliente.setCPF        (rs.getString("CLIENTE_CPF"        ));
			cliente.setNascimento (rs.getString("CLIENTE_DTNASC"     ));
			cliente.setComplemento(rs.getString("CLIENTE_COMPLEMENTO"));
			cliente.setUF         (rs.getString("CLIENTE_UF"         ));
			cliente.setCEP        (rs.getString("CLIENTE_CEP"        ));
			cliente.setEmail      (rs.getString("CLIENTE_EMAIL"      ));
			cliente.setObs        (rs.getString("CLIENTE_OBS"        ));
			
		}
		
		st.close();
		c .close();
		
		return cliente;
	}

	public static ArrayList<Cliente> list(final String field) throws SQLException {
		
		ArrayList<Cliente> clientList = new ArrayList<Cliente>();
		
		String query = ClienteParser.getListQuery(field);
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		
		while (rs.next()) {
			
			Cliente cliente = new Cliente();
			
			cliente.setID        (rs.getInt   ("CLIENTE_ID_PK"     ));
			cliente.setNome      (rs.getString("CLIENTE_NOME"      ));
			cliente.setLogradouro(rs.getString("CLIENTE_LOGRADOURO"));
			cliente.setNumero    (rs.getString("CLIENTE_NUM_CASA"  ));
			cliente.setBairro    (rs.getString("CLIENTE_BAIRRO"    ));
			cliente.setCidade    (rs.getString("CLIENTE_CIDADE"    ));
			cliente.setFixo      (rs.getString("CLIENTE_FIXO"      ));
			cliente.setCelular   (rs.getString("CLIENTE_CEL"       ));
			
			clientList.add(cliente);
			
		}
		
		st.close();
		c .close();
		
		return clientList;
	}
	
	public static void remove(final Cliente cliente) throws SQLException {
		
		String query = ClienteParser.getDeleteQuery(cliente);
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		st.executeUpdate(query);
		
		st.close();
		c .close();
		
	}
	
	public static void dump(final File file) throws IOException, SQLException {
		
		ObjectOutputStream stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		
		String query = ClienteParser.getListQuery();
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		
		while (rs.next()) {
			
			Cliente cliente = new Cliente();
			
			cliente.setID         (rs.getInt   ("CLIENTE_ID_PK"      ));
			cliente.setNome       (rs.getString("CLIENTE_NOME"       ));
			cliente.setCPF        (rs.getString("CLIENTE_CPF"        ));
			cliente.setNascimento (rs.getString("CLIENTE_DTNASC"     ));
			cliente.setLogradouro (rs.getString("CLIENTE_LOGRADOURO" ));
			cliente.setComplemento(rs.getString("CLIENTE_COMPLEMENTO"));
			cliente.setNumero     (rs.getString("CLIENTE_NUM_CASA"   ));
			cliente.setBairro     (rs.getString("CLIENTE_BAIRRO"     ));
			cliente.setCidade     (rs.getString("CLIENTE_CIDADE"     ));
			cliente.setUF         (rs.getString("CLIENTE_UF"         ));
			cliente.setCEP        (rs.getString("CLIENTE_CEP"        ));
			cliente.setFixo       (rs.getString("CLIENTE_FIXO"       ));
			cliente.setCelular    (rs.getString("CLIENTE_CEL"        ));
			cliente.setEmail      (rs.getString("CLIENTE_EMAIL"      ));
			cliente.setObs        (rs.getString("CLIENTE_OBS"        ));
			cliente.setDataInsert (rs.getString("CLIENTE_DTINSERT"   ));
			cliente.setDataUpdate (rs.getString("CLIENTE_DTUPDATE"   ));

			stream.writeObject(cliente);
			
		}
		
		st.close();
		c .close();
		
		stream.close();
		
	}
	
	public static void restore(final File file) throws ClassNotFoundException, IOException, SQLException {
		
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream stream = new ObjectInputStream(fis);
		
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		st.executeUpdate("DELETE FROM CLIENTE");
		
		while (true) {
			
			if (fis.available() == 0)
				break;
			
			Cliente cliente = (Cliente) stream.readObject();
			String  query   =  ClienteParser.getRestoreQuery(cliente);
			
			System.out.println(query);
			
			st.executeUpdate(query);
			
		}
		
		st.close();
		c .close();
		
		stream.close();
		
	}
	
}
