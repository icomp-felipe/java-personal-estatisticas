package com.estatisticas.dao;

import java.io.*;
import java.sql.*;
import java.util.*;

import com.estatisticas.bd.Database;
import com.estatisticas.model.Cliente;

/** Contém métodos de comunicação entre a aplicação Java e o banco de dados.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 1.5, 20/09/2020 */
public class ClienteDAO {
	
	/** Implementa o INSERT e UPDATE ao mesmo tempo, usando como base o ID da classe.
	 *  Caso tenha um ID, significa que esta deve ser atualizada, do contrário, trata-se
	 *  de um novo cliente.
	 *  @param cliente - um cliente do sistema
	 *  @throws SQLException quando alguma falha de comunicação com o BD acontece. */
	public static void commit(final Cliente cliente) throws SQLException {
		
		String query = (cliente.isEmpty()) ? ClienteParser.getInsertQuery(cliente) : ClienteParser.getUpdateQuery(cliente);
		
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		st.executeUpdate(query);
		
		st.close();
		c .close();
		
	}
	
	/** Completa os dados de um cliente recuperado pelo método <code>list</code>.
	 *  @param cliente - um cliente do sistema, apenas com os dados mostrados na tela de busca.
	 *  @throws SQLException quando alguma falha de comunicação com o BD acontece.
	 *  @see #list */
	public static void complete(final Cliente cliente) throws SQLException {
		
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
			cliente.setObservacoes(rs.getString("CLIENTE_OBS"        ));
			
		}
		
		st.close();
		c .close();
		
	}

	/** Salva todos os clientes do banco de dados em um arquivo binário.
	 *  @param file - arquivo de backup
	 *  @throws IOException quando alguma falha de E/S de arquivo acontece.
	 *  @throws SQLException quando alguma falha de comunicação com o BD acontece. */
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
			cliente.setObservacoes(rs.getString("CLIENTE_OBS"        ));
			cliente.setDataInsert (rs.getString("CLIENTE_DTINSERT"   ));
			cliente.setDataUpdate (rs.getString("CLIENTE_DTUPDATE"   ));

			stream.writeObject(cliente);
			
		}
		
		st.close();
		c .close();
		
		stream.close();
		
	}
	
	/** Verifica se um nome de cliente já foi previamente cadastrado no sistema.
	 *  @param nome - nome do cliente
	 *  @return 'true' caso haja registro do nome na base de dados ou 'false' caso contrário (ou haja alguma falha de comunicação com o BD). */
	public static boolean exists(final String nome) {
		
		try {
			
			String query = ClienteParser.getExistsQuery(nome);
			Connection c = Database.INSTANCE.getConnection();
			Statement st = c.createStatement();
			
			ResultSet   rs = st.executeQuery(query);
			boolean status = rs.next();
			
			st.close();
			c .close();
			
			return status;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		return false;
	}
	
	/** Recupera alguns dados de cliente para serem mostrados na tela de busca.
	 *  @param field - string de busca
	 *  @throws SQLException quando alguma falha de comunicação com o BD acontece. */
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
	
	/** Remove um cliente do banco de dados, de acordo com seu ID.
	 *  @param cliente - cliente que se deseja remover da base de dados
	 *  @throws SQLException quando alguma falha de comunicação com o BD acontece. */
	public static void remove(final Cliente cliente) throws SQLException {
		
		String query = ClienteParser.getDeleteQuery(cliente);
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		st.executeUpdate(query);
		
		st.close();
		c .close();
		
	}
	
	/** Recupera de um arquivo de backup para o banco de dados.<br>
	 *  Nota: no ato da recuperação, todos os clientes da base de dados são apagados e os novos do arquivo, carregados.
	 *  @param file - arquivo de backup
	 *  @throws ClassNotFoundException quando a modelagem descrita no arquivo não é a mesma da implementada no código, ou não existe.
	 *  @throws IOException quando alguma falha de E/S de arquivo acontece.
	 *  @throws SQLException quando alguma falha de comunicação com o BD acontece. */
	public static void restore(final File file) throws ClassNotFoundException, IOException, SQLException {
		
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream stream = new ObjectInputStream(fis);
		
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		
		Connection c = Database.INSTANCE.getConnection();
		Statement st = c.createStatement();
		
		// Primeiro leio o arquivo por completo, caso dê algum erro, o BD continua intacto
		while (true) {
			
			if (fis.available() == 0)
				break;
			
			Cliente cliente = (Cliente) stream.readObject();
			listaClientes.add(cliente);
			
		}
		
		stream.close();
		
		// Se cheguei aqui, significa que o arquivo foi lido com sucesso
		st.executeUpdate("DELETE FROM CLIENTE");
		
		for (Cliente cliente: listaClientes)
			st.executeUpdate(ClienteParser.getRestoreQuery(cliente));
		
		st.close();
		c .close();
		
	}
	
}
