package com.estatisticas.model;

import java.io.Serializable;
import java.util.Arrays;

import org.joda.time.DateTime;

import com.phill.libs.StringUtils;
import com.phill.libs.br.PhoneNumberUtils;
import com.phill.libs.table.JTableRowData;
import com.phill.libs.time.TimeFormatter;
import com.phill.libs.time.TimeParser;

/** Modelagem da entidade Cliente.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 1.5, 20/09/2020 */
public class Cliente implements JTableRowData, Serializable {

	// Serial, não esquecer de mudar caso haja alterações na modelagem
	private static final long serialVersionUID = 4716814927846252980L;
	
	// Atributos
	private int id;
	private String nome, cpf, fixo, celular, email;
	private DateTime nascimento, insert, update;
	private String logradouro, numero, bairro, cidade, uf, complemento, cep;
	private String obs;
	
	/************************ Bloco de Setters ****************************/
	
	/** Setter do ID.
	 *  @param id - ID do cliente no banco de dados */
	public void setID(final int id) {
		this.id = id;
	}
	
	/** Setter do nome (já normalizado).
	 *  @param nome - nome do cliente */
	public void setNome(final String nome) {
		this.nome = StringUtils.BR.normaliza(nome);
	}
	
	/** Setter do número de CPF. Aqui apenas os números são guardados.
	 *  @param cpf - número de CPF do cliente */
	public void setCPF(final String cpf) {
		this.cpf = StringUtils.extractNumbers(cpf);
	}
	
	/** Setter do telefone fixo. Aqui apenas os números são guardados.
	 *  @param fixo - número de telefone fixo do cliente (com DDD) */
	public void setFixo(final String fixo) {
		this.fixo = StringUtils.extractNumbers(fixo);
	}
	
	/** Setter do celular. Aqui apenas os números são guardados.
	 *  @param celular - número de celular do cliente (com DDD) */
	public void setCelular(final String celular) {
		this.celular = StringUtils.extractNumbers(celular);
	}
	
	/** Setter do e-mail.
	 *  @param email - e-mail do cliente */
	public void setEmail(final String email) {
		this.email = StringUtils.trim(email);
	}
	
	/** Setter da data de nascimento. Aqui ela é convertida para um {@link DateTime}.
	 *  @param nascimento - data de nascimento do cliente, neste programa é geralmente com o formato SQL ou data humana
	 *  @see TimeParser#createDate(String) */
	public void setNascimento(final String nascimento) {
		this.nascimento = TimeParser.createDate(nascimento);
	}
	
	/** Setter do logradouro.
	 *  @param logradouro - logradouro do cliente */
	public void setLogradouro(final String logradouro) {
		this.logradouro = StringUtils.trim(logradouro);
	}
	
	/** Setter do número referente ao logradouro.
	 *  @param numero - número do logradouro do cliente */
	public void setNumero(final String numero) {
		this.numero = StringUtils.trim(numero);
	}
	
	/** Setter do bairro.
	 *  @param bairro - bairro do cliente */
	public void setBairro(final String bairro) {
		this.bairro = StringUtils.trim(bairro);
	}
	
	/** Setter da cidade.
	 *  @param cidade - cidade ou município do cliente */
	public void setCidade(final String cidade) {
		this.cidade = StringUtils.trim(cidade);
	}
	
	/** Setter da Unidade de Federação.
	 *  @param uf - unidade de federação do cliente */
	public void setUF(final String uf) {
		this.uf = StringUtils.trim(uf);
		this.uf = (this.uf == null) ? null : this.uf.toUpperCase();
	}
	
	/** Setter do complemento de endereço.
	 *  @param complemento - complemento de endereço do cliente */
	public void setComplemento(final String complemento) {
		this.complemento = StringUtils.trim(complemento);
	}
	
	/** Setter do CEP.
	 *  @param cep - CEP do cliente. Aqui apenas os números são guardados. */
	public void setCEP(final String cep) {
		this.cep = StringUtils.extractNumbers(cep);
	}
	
	/** Setter das observações.
	 *  @param obs - observações de cadastro do cliente */
	public void setObservacoes(final String obs) {
		this.obs = StringUtils.trim(obs);
	}
	
	/** Setter da data de criação do cliente na base de dados.
	 *  @param datetime - data da criação do cliente no BD. Aqui ela é convertida para um {@link DateTime}.
	 *  @see TimeParser#createDate(String) */
	public void setDataInsert(final String datetime) {
		this.insert = TimeParser.createDate(datetime);
	}
	
	/** Setter da data da última alteração de dados do cliente na base de dados.
	 *  @param datetime - data da última alteração de dados do cliente no BD. Aqui ela é convertida para um {@link DateTime}.
	 *  @see TimeParser#createDate(String) */
	public void setDataUpdate(final String datetime) {
		this.update = TimeParser.createDate(datetime);
	}
	
	/************************ Bloco de Getters ****************************/
	
	/** Getter do ID.
	 *  @return ID do cliente no banco de dados. */
	public int getID() {
		return this.id;
	}
	
	/** Getter do nome (já normalizado).
	 *  @return Nome do cliente. */
	public String getNome() {
		return this.nome;
	}
	
	/** Getter do número de CPF.
	 *  @return Número de CPF do cliente (apenas números). */
	public String getCPF() {
		return this.cpf;
	}
	
	/** Getter do telefone fixo.
	 *  @return Número de telefone fixo do cliente (com DDD, apenas números). */
	public String getFixo() {
		return this.fixo;
	}
	
	/** Getter do celular.
	 *  @return Número de celular do cliente (com DDD, apenas números). */
	public String getCelular() {
		return this.celular;
	}
	
	/** Getter do e-mail.
	 *  @return e-mail do cliente. */
	public String getEmail() {
		return this.email;
	}
	
	/** Getter da data de nascimento.
	 *  @param format - formatação da data de nascimento, consultar tipos de formato.
	 *  @return Data de nascimento do cliente.
	 *  @see TimeParser#retrieveDate(TimeFormatter, DateTime) */
	public String getNascimento(TimeFormatter format) {
		return TimeParser.retrieveDate(format,this.nascimento);
	}
	
	/** Getter do logradouro.
	 *  @return Logradouro do cliente. */
	public String getLogradouro() {
		return this.logradouro;
	}
	
	/** Getter do número referente ao logradouro.
	 *  @return Número do logradouro do cliente. */
	public String getNumero() {
		return this.numero;
	}
	
	/** Getter do bairro.
	 *  @return Bairro do cliente. */
	public String getBairro() {
		return this.bairro;
	}
	
	/** Getter da cidade.
	 *  @return Cidade ou município do cliente. */
	public String getCidade() {
		return this.cidade;
	}
	
	/** Getter da Unidade de Federação.
	 *  @return Unidade de federação do cliente. */
	public String getUF() {
		return this.uf;
	}
	
	/** Getter do complemento de endereço.
	 *  @return Complemento de endereço do cliente. */
	public String getComplemento() {
		return this.complemento;
	}
	
	/** Getter do CEP.
	 *  @return CEP do cliente (apenas números). */
	public String getCEP() {
		return this.cep;
	}
	
	/** Getter das observações.
	 *  @return Observações de cadastro do cliente. */
	public String getObservacoes() {
		return this.obs;
	}
	
	/** Getter da data de criação do cliente na base de dados.
	 *  @param format - formatação da data de nascimento, consultar tipos de formato.
	 *  @return Data da criação do cliente no BD.
	 *  @see TimeParser#retrieveDate(TimeFormatter, DateTime) */
	public String getDataInsert(TimeFormatter format) {
		return TimeParser.retrieveDate(format,this.insert);
	}
	
	/** Getter da data da última alteração de dados do cliente na base de dados.
	 *  @param format - formatação da data de nascimento, consultar tipos de formato.
	 *  @return Data da última alteração de dados do cliente no BD.
	 *  @see TimeParser#retrieveDate(TimeFormatter, DateTime) */
	public String getDataUpdate(TimeFormatter format) {
		return TimeParser.retrieveDate(format,this.update);
	}
	
	/****************** Bloco de Getters Personalizados *******************/
	
	/** Recupera o endereço do cliente no formato 'Logradouro, número, bairro (cidade)'.
	 *  Útil para exibição dos dados na tela de busca.
	 *  @return Endereço do cliente. */
	private String getEndereco() {
		
		if ( blank(this.logradouro) && blank(this.numero) && blank(this.bairro) && blank(this.cidade) )
			return "";
		
		return String.format("%s, %s, %s (%s)", blank(this.logradouro) ? "<sem logradouro>" : this.logradouro,
												blank(this.numero    ) ? "<sem numero>"     : this.numero    ,
												blank(this.bairro    ) ? "<sem bairro>"     : this.bairro    ,
												blank(this.cidade    ) ? "<sem cidade>"     : this.cidade   );
	}
	
	/** Determina se o cliente atual é novo ou já existente, com base no seu ID.
	 *  @return 'true', caso esta instância contenha um id válido (maior que 0) ou 'false', caso contrário. */
	public boolean isEmpty() {
		return this.id == 0;
	}

	/** Recupera os dados para montagem da string de inserção de um cliente na base de dados.
	 *  @return Array com os dados para montagem da string de inserção. */
	public Object[] getInsertFields() {
		return Arrays.copyOfRange(getRestoreFields(), 0, 14);
	}
	
	/** Recupera os dados para montagem da string de atualização de dados de um cliente na base de dados.
	 *  @return Array com os dados para montagem da string de atualização de dados. */
	public Object[] getUpdateFields() {
		return Arrays.copyOfRange(getRestoreFields(), 0, 15);
	}
	
	/** Recupera os dados para montagem da string de restauração de um cliente (backup) para a base de dados.
	 *  @return Array com os dados para montagem da string de restauração. */
	public Object[] getRestoreFields() {
		
		// Aqui os dados foram dispostos de forma a servir para os métodos 'getInsertFields' e 'getUpdateFields'
		return new Object[] {
				this.nome,
				this.cpf,
				this.fixo,
				this.celular,
				this.email,
				getNascimento(TimeFormatter.SQL_DATE),
				this.logradouro,
				this.numero,
				this.bairro,
				this.cidade,
				this.uf,
				this.complemento,
				this.cep,
				this.obs,
				this.id,
				getDataInsert(TimeFormatter.SQL_DATE_TIME),
				getDataUpdate(TimeFormatter.SQL_DATE_TIME)
			};
		
	}

	@Override
	public Object[] getRowData() {
		return new Object[] { blank(this.nome) ? "" : this.nome,
							  getEndereco(),
							  blank(this.celular) ? "" : PhoneNumberUtils.format(this.celular,true),
							  blank(this.fixo   ) ? "" : PhoneNumberUtils.format(this.fixo   ,true)
							};
	}
	
	/** Verifica se uma string é vazia ou nula.
	 *  @param string - string
	 *  @return 'true' se a string é vazia ou nula ou 'false' caso contrário. */
	private boolean blank(final String string) {
		return (string == null) || (string.isEmpty());
	}

}
