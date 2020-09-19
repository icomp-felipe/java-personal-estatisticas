package eleicao.dados.parser;

import com.phill.libs.StringUtils;
import eleicao.dados.model.Dado;

public class DadoParser {
	
	public static String getInsertQuery(Dado dado) {
		
		String insert = "INSERT INTO DADOS2(DADO_NOME, DADO_CPF, DADO_FIXO, DADO_CEL, DADO_EMAIL, DADO_DTNASC, DADO_LOGRADOURO, DADO_NUM_CASA, DADO_BAIRRO, DADO_CIDADE, DADO_UF, DADO_COMPLEMENTO, DADO_CEP, DADO_OBS) "
				      + "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')";
		
		String query = String.format(insert, dado.getInsertFields());
		
		return StringUtils.blank_to_null(query);
		
	}
	
	public static String getUpdateQuery(Dado dado) {
		
		String update = "UPDATE DADOS2 SET DADO_NOME  = '%s',"
				                        + "DADO_CPF   = '%s',"
				                        + "DADO_FIXO  = '%s',"
				                        + "DADO_CEL   = '%s',"
				                        + "DADO_EMAIL = '%s',"
				                        + "DADO_DTNASC = '%s',"
				                        + "DADO_LOGRADOURO  = '%s',"
				                        + "DADO_NUM_CASA = '%s',"
				                        + "DADO_BAIRRO,"
				                        + "DADO_CIDADE,"
				                        + "DADO_UF,"
										+ "DADO_COMPLEMENTO = '%s',"
										+ "DADO_CEP,"
										+ "DADO_OBS = '%s' "
						+ "WHERE DADO_ID_PK = %d";
		
		String query = String.format(update, dado.getUpdateFields());
		
		return StringUtils.blank_to_null(query);
		
	}

}
