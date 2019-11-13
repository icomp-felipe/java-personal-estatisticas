package eleicao.dados.utils;

import java.util.InputMismatchException;

/** Implementa o algoritmo de verificação de CPF.
 *  @author  Felipe André Souza
 *  @version 1.5, 30/08/2015 */
public class CPFParser {

	/* Constantes de Controle ASCII */
	private static final int ASCII_OFFSET = 0x30;
	private static final int NINTH 	  = 0x9;
	private static final int TENTH 	  = 0xA;
	private static final int ELEVENTH = 0xB;
	
	/** Verifica os absurdos de CPF inválidoss */
	private static boolean verificaAbsurdos(String CPF) {
		if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
		    CPF.equals("22222222222") || CPF.equals("33333333333") ||
		    CPF.equals("44444444444") || CPF.equals("55555555555") ||
		    CPF.equals("66666666666") || CPF.equals("77777777777") ||
		    CPF.equals("88888888888") || CPF.equals("99999999999") ||
		    CPF.length() != 11)
			return true;
		return false;
	}
	
	/** Verifica se um número de CPF é válido */
	public static boolean parse(String CPF) {
		
		if (verificaAbsurdos(CPF))
			return false;
		
		char dig10, dig11;
	    int soma, indice, result, numero, peso;

	    try {
	    	soma = 0;	peso = TENTH;
	    	
	    	for (indice=0; indice<NINTH; indice++) {              
	    		numero = (int) (CPF.charAt(indice) - ASCII_OFFSET); 
	    		soma += (numero * peso);
	    		peso--;
	    	}

	    	result = ELEVENTH - (soma % ELEVENTH);
	    	
	    	if ((result == TENTH) || (result == ELEVENTH))
	    		dig10 = '0';
	    	else
	    		dig10 = (char) (result + ASCII_OFFSET);
	    	
	    	soma = 0;	peso = ELEVENTH;
	    	
	    	for (indice=0; indice<TENTH; indice++) {
	    		numero = (int) (CPF.charAt(indice) - ASCII_OFFSET);
	    		soma += (numero * peso);
	    		peso--;
	    	}

	    	result = ELEVENTH - (soma % ELEVENTH);
	    	
	    	if ((result == TENTH) || (result == ELEVENTH))
	    		dig11 = '0';
	    	else
	    		dig11 = (char) (result + ASCII_OFFSET);
	    	
	    	if ((dig10 == CPF.charAt(NINTH)) && (dig11 == CPF.charAt(TENTH)))
	    		return true;
	    	else
	    		return false;
	    	}
	    catch (InputMismatchException exception) {
	    	return false;
	    }
	}
	
}
