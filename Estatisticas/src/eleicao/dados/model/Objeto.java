package eleicao.dados.model;

import java.io.*;
import java.text.*;
import java.util.*;

/** Classe Objeto - representa o objeto principal do sistema
 *  @author Felipe André Souza da Silva 
 *  @version 1.00, 10/09/2014 */
@Deprecated
public class Objeto implements Serializable {
	
	/* Atributos da classe */
	private static final transient long serialVersionUID = 1L;
	private String cpf, nome, logradouro, numCasa, uf, cidade;
	private String bairro, cep, tel01, tel02,email, descricao;
	private String dtnasc, dtinsert, complemento;

	/****************************** Bloco de Construtores ****************************************/
	
	/** Construtor principal da classe objeto, este inicializa todos os atributos */
	public Objeto(String cpf, String nome, String dtnasc, String logradouro, String compl,
			  String numCasa, String bairro, String cidade, String uf, String cep, String tel01,
			  String tel02, String email, String observacao) {
		this.uf = uf;
		this.cep = cep;
		this.cpf = cpf;
		this.nome = nome;
		this.tel01 = tel01;
		this.tel02 = tel02;
		this.email = email;
		this.dtnasc = dtnasc;
		this.bairro = bairro;
		this.cidade = cidade;
		this.numCasa = numCasa;
		this.complemento = compl;
		this.descricao = observacao;
		this.logradouro = logradouro;
	}
	
	/** Construtor Utilizado para remover este objeto do banco de dados */
	public Objeto(String cpf) {
		this(cpf,null,null,null,null,null,null,null,null,null,null,null,null,null);
	}
	
	/** Construtor utilizado para recuperar apenas as informações pertinentes à tabela de visualização */
	public Objeto(String cpf, String nome, String logradouro, String numCasa, String bairro, String tel01, String tel02) {
		this(cpf,nome,null,logradouro,null,numCasa,bairro,null,null,null,tel01,tel02,null,null);
	}
	
	/************************* Bloco de Getters Personalizados ***********************************/

	/** Recupera o endereço completo */
	public String getEndereco() {
		return String.format("%s, %s, %s",logradouro,numCasa,bairro);
	}
	
	/** Recupera o CPF com a máscara convencional */
	private String getFormattedCPF() {
		try {
			return String.format("%s.%s.%s-%s",cpf.substring(0, 3),cpf.substring(3, 6),cpf.substring(6, 9),cpf.substring(9, 11));
		}
		catch (StringIndexOutOfBoundsException exception) {
			return cpf;
		}
	}
	
	/** Recupera o número de telefone com as máscaras convencionais
	 *  para residenciais (10 dígitos) e celulares (11 dígitos) */
	private String getFormattedFone(String fone) {
		
		final String format = "(%s) %s-%s";
		
		if (fone.length() == 10)
			return String.format(format, fone.substring(0,2),fone.substring(2,6),fone.substring(6));
		
		if (fone.length() == 11)
			return String.format(format, fone.substring(0,2),fone.substring(2,7),fone.substring(7));
		
		return fone;
	}
	
	/** Recupera apenas as informações pertinentes à tabela de visualizações (busca de objeto) */
	public String[] getRowResume() {
		return resume(nome,getFormattedCPF(),getEndereco(),getFormattedFone(tel01),getFormattedFone(tel02));
	}
	
	/************************* Bloco de Métodos Auxiliares à Classe ******************************/
	
	/** Agrupa os argumentos do tipo String em um vetor */
	private String[] resume(String... args) {
		return args;
	}
	
	/** Converte uma data do formato humano para o formato SQL */
	private String parseMySQLDate(String data) {
		String formattedDate = null;
		
		if (data.matches(".*-.*-.*"))
			return data;
		
		try {
			Date raw = new SimpleDateFormat("dd/MM/yyyy").parse(data);
			formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(raw);
		}
		catch (ParseException exception) {
			formattedDate = "0000-00-00";
		}
		return formattedDate;
	}
	
	/** Converte uma data do formato SQL para o formato humano */
	public String recoverMySQLDate(String data) {
		String formattedDate = null;
		try {
			if (data.equals("0000-00-00"))
				return "00/00/0000";
			Date raw = new SimpleDateFormat("yyyy-MM-dd").parse(data);
			formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(raw);
		}
		catch (ParseException exception) {
			formattedDate = "00/00/0000";
		}
		return formattedDate;
	}
	
	/** Converte uma data expressa em millisegundos para uma data completa em SQL */
	private String milisegundoPraData(long millis) {
	    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date resultdate = new Date(millis);  
        return formato.format(resultdate);
	}
	
	/** Recupera o horário atual do sistema para esta classe */
	private void setCurrentDate() {
		this.dtinsert = milisegundoPraData(System.currentTimeMillis());
	}
	
	/**************************** Bloco de Interfaces SQL ****************************************/
	
	/** Monta e retorna uma string de inserção de dados no banco */
	public String getInsertString() {

		final String dataNascimento = parseMySQLDate(dtnasc);
		final String format = "INSERT INTO DADOS VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');";
		
		setCurrentDate();
		
		return String.format(format,cpf,nome,dataNascimento,logradouro,complemento,numCasa,  bairro,cidade,uf,cep,tel01,tel02,email,descricao,dtinsert);
	}
	
	/** Monta uma string de remoção de dados no banco */
	public String getRemoveString() {
		return String.format("DELETE FROM DADOS WHERE DADO_CPF_PK='%s';",cpf);
	}
	
	/******************************* Bloco de Getters ********************************************/
	
	public String getCPF() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}
	
	public String getContato01() {
		return tel01;
	}

	public String getContato02() {
		return tel02;
	}
	
	public String getLogradouro() {
		return logradouro;
	}

	public String getNumCasa() {
		return numCasa;
	}

	public String getUF() {
		return uf;
	}

	public String getCidade() {
		return cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCEP() {
		return cep;
	}

	public String getEmail() {
		return email;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getNascimento() {
		return recoverMySQLDate(dtnasc);
	}

	public String getComplemento() {
		return complemento;
	}
	
}
