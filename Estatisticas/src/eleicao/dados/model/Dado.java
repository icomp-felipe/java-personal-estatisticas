package eleicao.dados.model;

import java.io.Serializable;
import java.util.Arrays;

import org.joda.time.DateTime;

import com.phill.libs.time.TimeFormatter;
import com.phill.libs.time.TimeParser;

public class Dado implements Serializable {

	private static final long serialVersionUID = 4716814927846252980L;
	
	private int id;
	private String nome, cpf, fixo, celular, email;
	private DateTime nascimento;
	private String logradouro, numero, bairro, cidade, uf, complemento, cep;
	private String obs;
	
	/************************ Bloco de Setters ****************************/
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCPF(String cpf) {
		this.cpf = cpf;
	}
	
	public void setFixo(String fixo) {
		this.fixo = fixo;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setNascimento(String nascimento) {
		this.nascimento = TimeParser.createDate(nascimento);
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public void setUF(String uf) {
		this.uf = uf;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public void setCEP(String cep) {
		this.cep = cep;
	}
	
	public void setObs(String obs) {
		this.obs = obs;
	}
	
	/************************ Bloco de Getters ****************************/
	
	public String getNome() {
		return nome;
	}
	public String getCPF() {
		return cpf;
	}
	public String getFixo() {
		return fixo;
	}
	public String getCelular() {
		return celular;
	}
	public String getEmail() {
		return email;
	}
	public String getNascimento(TimeFormatter format) {
		return TimeParser.retrieveDate(format,this.nascimento);
	}
	public String getLogradouro() {
		return logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public String getBairro() {
		return bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public String getUF() {
		return uf;
	}
	public String getComplemento() {
		return complemento;
	}
	public String getCEP() {
		return cep;
	}
	public String getObs() {
		return obs;
	}
	
	
	
	
	
	
	
	public boolean isEmpty() {
		return this.id == 0;
	}

	public Object[] getInsertFields() {
		return Arrays.copyOfRange(getUpdateFields(), 0, 14);
	}
	
	public Object[] getUpdateFields() {
		
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
								this.id
							};
		
	}

}
