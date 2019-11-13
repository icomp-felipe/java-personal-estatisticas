package eleicao.dados.model;

/** Enum Filtro - contém as constantes de definição dos filtros e campos de dados do sistema
 *  @author Felipe André Souza da Silva 
 *  @version 1.00, 10/09/2014 */
public enum Filtro {

	/* Bloco de constantes */
	Nome("DADO_NOME"), Bairro("DADO_BAIRRO"), Logradouro("DADO_LOGRADOURO"), CPF("DADO_CPF_PK");
	
	/** Nome do campo do banco de dados
	 *  associado a cada filtro do sistema */
	private String field;
	
	/** Construtor inicializando os campos do BD */
	Filtro(String field) {
		this.field = field;
	}
	
	/** Recupera o campo do banco de dados associado ao enum */
	public String getField() {
		return field;
	}
	
}
