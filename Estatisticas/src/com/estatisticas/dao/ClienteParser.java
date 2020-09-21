package com.estatisticas.dao;

import com.estatisticas.model.Cliente;
import com.phill.libs.StringUtils;

/** Contém as classes que montam queries de BD com base nos dados de um {@link Cliente}.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 1.5, 20/09/2020 */
public class ClienteParser {
	
	/** Monta a query de remoção de um cliente da base de dados.
	 *  @param cliente - cliente a ser removido da base de dados
	 *  @return Query de remoção do cliente do BD. */
	protected static String getDeleteQuery(final Cliente cliente) {
		return "DELETE FROM CLIENTE WHERE CLIENTE_ID_PK = " + cliente.getID();
	}
	
	/** Monta a query de inserção de um cliente na base de dados.
	 *  @param cliente - cliente a ser inserido na base de dados.
	 *  @return Query de inserção do cliente no BD. */
	protected static String getInsertQuery(final Cliente cliente) {
		
		String insert = "INSERT INTO CLIENTE(CLIENTE_NOME, CLIENTE_CPF, CLIENTE_FIXO, CLIENTE_CEL, CLIENTE_EMAIL, CLIENTE_DTNASC, CLIENTE_LOGRADOURO, CLIENTE_NUM_CASA, CLIENTE_BAIRRO, CLIENTE_CIDADE, CLIENTE_UF, CLIENTE_COMPLEMENTO, CLIENTE_CEP, CLIENTE_OBS) "
				      + "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')";
		
		String query = String.format(insert, cliente.getInsertFields());
		
		return StringUtils.blankToNull(query);
		
	}
	
	/** Monta a query de listagem de todos os clientes da base de dados,
	 *  usada em conjunto com o método <code>ClienteDAO::dump</code>.
	 *  @return Query de listagem de todos os clientes do BD. */
	protected static String getListQuery() {
		
		String select = "SELECT CLIENTE_ID_PK, CLIENTE_NOME, CLIENTE_CPF, CLIENTE_DTNASC, CLIENTE_LOGRADOURO, CLIENTE_COMPLEMENTO, CLIENTE_NUM_CASA, CLIENTE_BAIRRO, "
					  + "CLIENTE_CIDADE, CLIENTE_UF, CLIENTE_CEP, CLIENTE_FIXO, CLIENTE_CEL, CLIENTE_EMAIL, CLIENTE_OBS, CLIENTE_DTINSERT, CLIENTE_DTUPDATE FROM CLIENTE "
					  + "ORDER BY CLIENTE_ID_PK ASC";
		
		return select;
	}
	
	/** Monta a query de busca de clientes que possuam nome, logradouro, bairro, cidade ou alguma observação que satisfaça o texto descrito em <code>field</code>.
	 *  @param field - campo de busca
	 *  @return Query de busca no BD, filtrada com <code>field</code>. */
	protected static String getListQuery(final String field) {
		
		String select = "SELECT CLIENTE_ID_PK, CLIENTE_NOME, CLIENTE_LOGRADOURO, CLIENTE_NUM_CASA, CLIENTE_BAIRRO, CLIENTE_CIDADE, CLIENTE_FIXO, CLIENTE_CEL "
				      + "FROM CLIENTE "
				      + "WHERE CLIENTE_NOME LIKE '%s%%' OR CLIENTE_LOGRADOURO LIKE '%s%%' OR CLIENTE_BAIRRO LIKE '%s%%' OR CLIENTE_CIDADE LIKE '%s%%' OR CLIENTE_OBS LIKE '%s%%' "
				      + "ORDER BY CLIENTE_NOME ASC";
		
		String query = String.format(select, field, field, field, field, field); 
		
		return query;
	}
	
	/** Monta a string de completude de dados de um cliente.
	 *  @param cliente - cliente recuperado pelo método <code>getListQuery</code>
	 *  @return Query de completude de dados do cliente. */
	protected static String getQuery(final Cliente cliente) {
		
		String query = "SELECT CLIENTE_CPF, CLIENTE_DTNASC, CLIENTE_COMPLEMENTO, CLIENTE_UF, CLIENTE_CEP, CLIENTE_EMAIL, CLIENTE_OBS, CLIENTE_DTINSERT, CLIENTE_DTUPDATE "
					 + "FROM CLIENTE "
					 + "WHERE CLIENTE_ID_PK = " + cliente.getID();
		
		return query;
	}
	
	/** Monta a string de recuperação de um cliente do arquivo de backup.
	 *  @param cliente - cliente recuperado do arquivo de backup
	 *  @return Query de inserção de todos os dados do cliente no BD. */
	protected static String getRestoreQuery(final Cliente cliente) {
		
		String insert = "INSERT INTO CLIENTE(CLIENTE_NOME, CLIENTE_CPF, CLIENTE_FIXO, CLIENTE_CEL, CLIENTE_EMAIL, CLIENTE_DTNASC, CLIENTE_LOGRADOURO, CLIENTE_NUM_CASA, CLIENTE_BAIRRO, CLIENTE_CIDADE, CLIENTE_UF, CLIENTE_COMPLEMENTO, CLIENTE_CEP, CLIENTE_OBS, CLIENTE_ID_PK, CLIENTE_DTINSERT, CLIENTE_DTUPDATE) "
				      + "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',%d,'%s','%s')";
		
		String query = String.format(insert, cliente.getRestoreFields());
		
		return StringUtils.blankToNull(query);
		
	}
	
	/** Monta a string de atualização de dados de um cliente na base de dados.
	 *  @param cliente - cliente do sistema
	 *  @return Query de atualização de dados no BD. */
	protected static String getUpdateQuery(final Cliente cliente) {
		
		String update = "UPDATE CLIENTE SET CLIENTE_NOME = '%s', "
				                         + "CLIENTE_CPF = '%s', "
				                         + "CLIENTE_FIXO = '%s', "
				                         + "CLIENTE_CEL = '%s', "
				                         + "CLIENTE_EMAIL = '%s', "
				                         + "CLIENTE_DTNASC = '%s', "
				                         + "CLIENTE_LOGRADOURO = '%s', "
				                         + "CLIENTE_NUM_CASA = '%s', "
				                         + "CLIENTE_BAIRRO = '%s', "
				                         + "CLIENTE_CIDADE = '%s', "
				                         + "CLIENTE_UF = '%s', "
										 + "CLIENTE_COMPLEMENTO = '%s', "
										 + "CLIENTE_CEP = '%s', "
										 + "CLIENTE_OBS = '%s'  "
						+ "WHERE CLIENTE_ID_PK = %d";
		
		String query = String.format(update, cliente.getUpdateFields());
		
		return StringUtils.blankToNull(query);
		
	}

}
