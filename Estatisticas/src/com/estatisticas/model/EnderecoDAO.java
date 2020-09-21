package com.estatisticas.model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EnderecoDAO {
	
	/** Recupera um endereço de um determinado CEP. 
	 * @throws IOException */
	public static Endereco get(String cep) throws IOException {
		
		// Recupera a página de resposta do webservice, com timeout de 10s
		Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/" + cep).timeout(10000).get();
		
		// Recupera os elementos do endereço
		String logradouro = doc.select("span[itemprop=streetAddress]").text();
		String bairro 	  = getBairro(doc.select("td:gt(1)"));
		String cidade	  = doc.select("span[itemprop=addressLocality]").text();
		String estado	  = doc.select("span[itemprop=addressRegion]").text();
		
		// Encapsulando dados no objeto Endereco (nulo se não foi possível recuperar o endereço)
		Endereco endereco = logradouro.isEmpty() ? null : new Endereco(logradouro,bairro,cidade,estado);
		
		return endereco;
	}
	
	/** Recupera um bairro do site. */
	private static String getBairro(Elements urlPesquisa) {
		
    	for (Element urlBairro: urlPesquisa)
            return urlBairro.text();
    	
        return null;
        
    }

}
