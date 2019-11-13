package eleicao.dados.model;

import java.sql.*;

/** Classe BancoSQL - faz a interface de conexão com o banco de dados
 *  @author Felipe André Souza da Silva 
 *  @version 1.00, 10/09/2014 */
public class BancoSQL {
	
	/* Atributos de Conexão */
	private static String url  = "jdbc:mysql://localhost/ESTATISTICAS";
	private static String user = "java";
	private static String pass = "javaApplet";
    private static Connection conexao = null;

    /** Faz a conexão com o banco de dados */
    public static boolean conecta() {
    	try {
    		conexao = DriverManager.getConnection(url, user, pass);
    		return true;
    	}
    	catch (SQLException exception) {
    		return false;
    	}
    }
    
    /** Retorna o objeto da conexão 
     *  @throws SQLException Quando a conexão não foi estabelecida */
    public static Statement getStatement() throws SQLException {
    	return conexao.createStatement();
    }
    
    /** Faz a desconexão com o banco de dados */
    public static boolean desconecta() {
    	try {
    		if (conexao != null)
    			conexao.close();
    		return true;
    	}
    	catch (SQLException exception) { 
    		return false;
    	}
    }
    
}
