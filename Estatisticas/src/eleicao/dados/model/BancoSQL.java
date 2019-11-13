package eleicao.dados.model;

import java.sql.*;

/** Classe BancoSQL - faz a interface de conex�o com o banco de dados
 *  @author Felipe Andr� Souza da Silva 
 *  @version 1.00, 10/09/2014 */
public class BancoSQL {
	
	/* Atributos de Conex�o */
	private static String url  = "jdbc:mysql://localhost/ESTATISTICAS";
	private static String user = "java";
	private static String pass = "javaApplet";
    private static Connection conexao = null;

    /** Faz a conex�o com o banco de dados */
    public static boolean conecta() {
    	try {
    		conexao = DriverManager.getConnection(url, user, pass);
    		return true;
    	}
    	catch (SQLException exception) {
    		return false;
    	}
    }
    
    /** Retorna o objeto da conex�o 
     *  @throws SQLException Quando a conex�o n�o foi estabelecida */
    public static Statement getStatement() throws SQLException {
    	return conexao.createStatement();
    }
    
    /** Faz a desconex�o com o banco de dados */
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
