package eleicao.dados.model;

public class Endereco {
	
	private String logradouro, bairro, cidade, uf;
	
	public Endereco(final String logradouro, final String bairro, final String cidade, final String uf) {
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
	}

	public String getLogradouro() {
		return logradouro;
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

}
