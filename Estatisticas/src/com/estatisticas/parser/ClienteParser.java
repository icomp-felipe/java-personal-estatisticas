package com.estatisticas.parser;

import com.estatisticas.model.Cliente;
import com.phill.libs.StringUtils;

public class ClienteParser {
	
	public static String getInsertQuery(final Cliente cliente) {
		
		String insert = "INSERT INTO CLIENTE(CLIENTE_NOME, CLIENTE_CPF, CLIENTE_FIXO, CLIENTE_CEL, CLIENTE_EMAIL, CLIENTE_DTNASC, CLIENTE_LOGRADOURO, CLIENTE_NUM_CASA, CLIENTE_BAIRRO, CLIENTE_CIDADE, CLIENTE_UF, CLIENTE_COMPLEMENTO, CLIENTE_CEP, CLIENTE_OBS) "
				      + "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')";
		
		String query = String.format(insert, cliente.getInsertFields());
		
		System.out.println(query);
		
		return StringUtils.blankToNull(query);
		
	}
	
	public static String getRestoreQuery(final Cliente cliente) {
		
		String insert = "INSERT INTO CLIENTE(CLIENTE_NOME, CLIENTE_CPF, CLIENTE_FIXO, CLIENTE_CEL, CLIENTE_EMAIL, CLIENTE_DTNASC, CLIENTE_LOGRADOURO, CLIENTE_NUM_CASA, CLIENTE_BAIRRO, CLIENTE_CIDADE, CLIENTE_UF, CLIENTE_COMPLEMENTO, CLIENTE_CEP, CLIENTE_OBS, CLIENTE_ID_PK, CLIENTE_DTINSERT, CLIENTE_DTUPDATE) "
				      + "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',%d,'%s','%s')";
		
		String query = String.format(insert, cliente.getRestoreFields());
		
		return StringUtils.blankToNull(query);
		
	}
	
	public static String getUpdateQuery(final Cliente cliente) {
		
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
		
		System.out.println(query);
		
		return StringUtils.blankToNull(query);
		
	}
	
	public static String getListQuery(final String field) {
		
		String select = "SELECT CLIENTE_ID_PK, CLIENTE_NOME, CLIENTE_LOGRADOURO, CLIENTE_NUM_CASA, CLIENTE_BAIRRO, CLIENTE_CIDADE, CLIENTE_FIXO, CLIENTE_CEL "
				      + "FROM CLIENTE "
				      + "WHERE CLIENTE_NOME LIKE '%s%%' OR CLIENTE_LOGRADOURO LIKE '%s%%' OR CLIENTE_BAIRRO LIKE '%s%%' OR CLIENTE_CIDADE LIKE '%s%%' OR CLIENTE_OBS LIKE '%s%%' "
				      + "ORDER BY CLIENTE_NOME ASC";
		
		String query = String.format(select, field, field, field, field, field); 
		
		return query;
	}
	
	public static String getListQuery() {
		
		String select = "SELECT CLIENTE_ID_PK, CLIENTE_NOME, CLIENTE_CPF, CLIENTE_DTNASC, CLIENTE_LOGRADOURO, CLIENTE_COMPLEMENTO, CLIENTE_NUM_CASA, CLIENTE_BAIRRO, "
					  + "CLIENTE_CIDADE, CLIENTE_UF, CLIENTE_CEP, CLIENTE_FIXO, CLIENTE_CEL, CLIENTE_EMAIL, CLIENTE_OBS, CLIENTE_DTINSERT, CLIENTE_DTUPDATE FROM CLIENTE";
		
		return select;
	}

	public static String getQuery(final Cliente cliente) {
		
		String query = "SELECT CLIENTE_CPF, CLIENTE_DTNASC, CLIENTE_COMPLEMENTO, CLIENTE_UF, CLIENTE_CEP, CLIENTE_EMAIL, CLIENTE_OBS, CLIENTE_DTINSERT, CLIENTE_DTUPDATE "
					 + "FROM CLIENTE "
					 + "WHERE CLIENTE_ID_PK = " + cliente.getID();
		
		return query;
	}

	public static String getDeleteQuery(final Cliente cliente) {
		return "DELETE FROM CLIENTE WHERE CLIENTE_ID_PK = " + cliente.getID();
	}

}
