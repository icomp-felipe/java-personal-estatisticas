package eleicao.dados.model;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

import eleicao.dados.utils.*;

/** Classe ObjetoDAO - faz a ponte entre as classes de objetos Java e os objetos do banco de dados
 *  @author Felipe André Souza da Silva 
 *  @version 1.00, 10/09/2014 */
public class ObjetoDAO extends BancoSQL {
	
	/************************* Bloco de Interfaces de Comunicação ********************************/
	
	/** Adiciona um objeto no banco de dados */
	public static boolean adicionaObjeto(Objeto objeto) {
		boolean status = false;
		conecta();
		
		try {
			Statement st = getStatement();
			st.executeUpdate(objeto.getInsertString());
			status = true;
		}
		catch (SQLIntegrityConstraintViolationException exception) {
			AlertDialog.erro("Cadastro de Informações","CPF informado já existe na base de dados!");
		}
		catch (SQLException exception) {
			AlertDialog.erro("Cadastro de Informações",exception.getMessage());
		}
		finally {
			desconecta();
		}
		
		return status;
	}
	
	/** Atualiza um objeto no banco de dados */
	public static boolean atualizaObjeto(Objeto velho, Objeto novo) {
		boolean status = false;
		conecta();
		
		try {
			Statement st = getStatement();
			st.executeUpdate(velho.getRemoveString());
			st.executeUpdate(novo .getInsertString());
			status = true;
		}
		catch (SQLException exception) {
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}
		finally {
			desconecta();
		}
		
		return status;
	}
	
	/** Recupera um objeto do banco de dados a partir do CPF */
	public static Objeto getObjeto(String cpf) {
		Objeto entrada = null;
		conecta();
		
		try {
	    	Statement st = getStatement();
	    	ResultSet rs = st.executeQuery("SELECT * FROM DADOS WHERE DADO_CPF_PK='"+cpf+"'");
	    	if (rs.next())
	    		entrada = new Objeto(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14));
	    }
	    catch (SQLException exception) {
	    	exception.printStackTrace();
	    }
		finally {
			desconecta();
		}
		
		return entrada;
	}
	
	/** Recupera todos os objetos do banco de dados */
	public static ArrayList<Objeto> getObjetos() {
		ArrayList<Objeto> entradas = new ArrayList<Objeto>();
		conecta();
		
		try {
	    	Statement st = getStatement();
	    	ResultSet rs = st.executeQuery("SELECT * FROM DADOS");
	    	while (rs.next()) {
	    		Objeto entrada = new Objeto(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14));
	    		entradas.add(entrada);
	    		entrada = null;
	    	}	
	    }
	    catch (SQLException exception) {
	    	exception.printStackTrace();
	    }
		finally {
			desconecta();
		}
		
		return entradas;
	}
	
	/** Remove um objeto do banco de dados */
	public static boolean removeObjeto(Objeto objeto) {
		boolean status = false;
		conecta();
		
		try {
			Statement st = getStatement();
			st.executeUpdate(objeto.getRemoveString());
			status = true;
		}
		catch (SQLException exception) {
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}
		finally {
			desconecta();
		}
		
		return status;
	}
	
	/** Restaura o banco de dados a partir de um backup */
	public static boolean restauraBackup(ArrayList<Objeto> objetos) {
		boolean status = false;
		conecta();
		
		try {
			Statement st = getStatement();
			st.executeUpdate("DELETE FROM DADOS");
			for (Objeto objeto: objetos)
				st.executeUpdate(objeto.getInsertString());
			status = true;
		}
		catch (SQLException exception) {
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}
		finally {
			desconecta();
		}
		
		return status;
	}
	
	/** Cria um backup do banco de dados em um arquivo */
	public static void criaBackup(File arquivo) throws IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(arquivo)));
		
		for (Objeto objeto: getObjetos())
			outputStream.writeObject(objeto);
		
		outputStream.close();
	}
	
	/** Restaura o backup de arquivo para o banco de dados */
	public static void restauraBackup(File arquivo) throws IOException {
		ArrayList<Objeto> listaObjetos = new ArrayList<Objeto>();
		ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(arquivo)));
		
		while (true) {
	    	try {
	    		Objeto objeto = (Objeto) inputStream.readObject();
	    		listaObjetos.add(objeto);
	    	}
	    	catch (Exception exception) {
	    		break;
	    	}
	    }
		
		inputStream.close();
		restauraBackup(listaObjetos);
	}
	
	/************************** Bloco de Métodos Auxiliares às Interfaces ************************/
	
	/** Retorna a lista de objetos do sistema de acordo com um determinado filtro */
	public static ArrayList<Objeto> getObjetos(String query, Filtro filter) {
		ArrayList<Objeto> entradas = new ArrayList<Objeto>();
		conecta();
		
		try {
	    	Statement st = getStatement();
	    	ResultSet rs = st.executeQuery(getSQLQuery(query, filter));
	    	while (rs.next()) {
	    		Objeto entrada = new Objeto(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
	    		entradas.add(entrada);
	    	}	
	    }
	    catch (SQLException exception) {
	    	exception.printStackTrace();
	    }
		finally {
			desconecta();
		}
		
		return entradas;
	}
	
	/** Retorna a striing persolanizada de busca de acordo com o filtro selecionado na interface gráfica */
	private static String getSQLQuery(String field, Filtro filter) {
		String format = "SELECT DADO_CPF_PK, DADO_NOME, DADO_LOGRADOURO, DADO_NUM_CASA, "
					  + "DADO_BAIRRO, DADO_TEL01, DADO_TEL02 FROM DADOS WHERE %s LIKE \"%%%s%%\";";
		
		String query = String.format(format,filter.getField(),field);
		
		return query;
	}
	
}
