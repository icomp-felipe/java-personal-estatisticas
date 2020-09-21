package com.estatisticas.model;

import java.io.Serializable;
import java.util.Arrays;

import org.joda.time.DateTime;

import com.phill.libs.StringUtils;
import com.phill.libs.br.PhoneNumberUtils;
import com.phill.libs.table.JTableRowData;
import com.phill.libs.time.TimeFormatter;
import com.phill.libs.time.TimeParser;

public class Cliente implements JTableRowData, Serializable {

	private static final long serialVersionUID = 4716814927846252980L;
	
	private int id;
	private String nome, cpf, fixo, celular, email;
	private DateTime nascimento, insert, update;
	private String logradouro, numero, bairro, cidade, uf, complemento, cep;
	private String obs;
	
	/************************ Bloco de Setters ****************************/
	
	public void setID(final int id) {
		this.id = id;
	}
	
	public void setNome(final String nome) {
		this.nome = StringUtils.BR.normaliza(nome);
	}
	
	public void setCPF(final String cpf) {
		this.cpf = StringUtils.extractNumbers(cpf);
	}
	
	public void setFixo(final String fixo) {
		this.fixo = StringUtils.extractNumbers(fixo);
	}
	
	public void setCelular(final String celular) {
		this.celular = StringUtils.extractNumbers(celular);
	}
	
	public void setEmail(final String email) {
		this.email = email;
	}
	
	public void setNascimento(final String nascimento) {
		this.nascimento = TimeParser.createDate(nascimento);
	}
	
	public void setLogradouro(final String logradouro) {
		this.logradouro = logradouro;
	}
	
	public void setNumero(final String numero) {
		this.numero = numero;
	}
	
	public void setBairro(final String bairro) {
		this.bairro = bairro;
	}
	
	public void setCidade(final String cidade) {
		this.cidade = cidade;
	}
	
	public void setUF(final String uf) {
		this.uf = uf;
	}
	
	public void setComplemento(final String complemento) {
		this.complemento = complemento;
	}
	
	public void setCEP(final String cep) {
		this.cep = StringUtils.extractNumbers(cep);
	}
	
	public void setObs(final String obs) {
		this.obs = obs;
	}
	
	public void setDataInsert(final String datetime) {
		this.insert = TimeParser.createDate(datetime);
	}
	
	public void setDataUpdate(final String datetime) {
		this.update = TimeParser.createDate(datetime);
	}
	
	/************************ Bloco de Getters ****************************/
	
	public int getID() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCPF() {
		return this.cpf;
	}
	
	public String getFixo() {
		return this.fixo;
	}
	
	public String getCelular() {
		return this.celular;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getNascimento(TimeFormatter format) {
		return TimeParser.retrieveDate(format,this.nascimento);
	}
	
	public String getLogradouro() {
		return this.logradouro;
	}
	
	public String getNumero() {
		return this.numero;
	}
	
	public String getBairro() {
		return this.bairro;
	}
	
	public String getCidade() {
		return this.cidade;
	}
	
	public String getUF() {
		return this.uf;
	}
	
	public String getComplemento() {
		return this.complemento;
	}
	
	public String getCEP() {
		return this.cep;
	}
	
	public String getObs() {
		return this.obs;
	}
	
	public String getDataInsert(TimeFormatter format) {
		return TimeParser.retrieveDate(format,this.insert);
	}
	
	public String getDataUpdate(TimeFormatter format) {
		return TimeParser.retrieveDate(format,this.update);
	}
	
	
	public String getEndereco() {
		return String.format("%s, %s, %s (%s)", this.logradouro, this.numero, this.bairro, this.cidade);
	}
	
	
	
	
	public boolean isEmpty() {
		return this.id == 0;
	}

	public Object[] getInsertFields() {
		return Arrays.copyOfRange(getRestoreFields(), 0, 14);
	}
	
	public Object[] getUpdateFields() {
		return Arrays.copyOfRange(getRestoreFields(), 0, 15);
	}
	
	public Object[] getRestoreFields() {
		
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
				this.id, // -----------------------------
				getDataInsert(TimeFormatter.SQL_DATE_TIME),
				getDataUpdate(TimeFormatter.SQL_DATE_TIME)
			};
		
	}

	@Override
	public Object[] getRowData() {
		return new Object[] { this.nome, getEndereco(), PhoneNumberUtils.format(this.celular,true), PhoneNumberUtils.format(this.fixo,true) };
	}

}
