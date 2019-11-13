package eleicao.dados.utils;

import java.awt.*;
import java.io.*;
import javax.swing.border.*;
import javax.swing.text.*;

/** Classe que cont�m m�todos �teis de manipula��o de GUI
 *  @author Felipe Andr�
 *  @version 2.5, 19/01/2015 */
public class GraphicsHelper {
	
	/** Atributos de cria��o de fonte */
	private static final File ttfFont  = new File(ResourceManager.getResource("fonts/swiss.ttf"));
	private static final Font baseFont = createFont(ttfFont);
	private static final Font normFont = deriveFont(baseFont, Font.PLAIN, 15);
	
	/** Recursos gr�ficos */
	private static final Color color = new Color(31,96,203);
	
	/** Retorna a fonte atual */
	public static Font getFont() {
		return normFont;
	}
	
	/** Retorna uma nova fonte com o tamanho especificado */
	public static Font getFont(int size) {
		return deriveFont(baseFont, Font.PLAIN, size);
	}
	
	/** Retorna a cor azul */
	public static Color getColor() {
		return color;
	}
	
	/** Cria uma Borda Personalizada para os pain�is do Java Swing e AWT */
	public static TitledBorder getTitledBorder(String title) {
		return new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, getFont(), getColor());
	}
	
	/** Cria uma M�scara Personalizada para os text fields do Java Swing */
	public static MaskFormatter getMascara(String mascara) {
		return getMascara(mascara,' ');
	}
	
	/** Cria uma M�scara Personalizada com preenchimento personalizado para os text fields do Java Swing */
	public static MaskFormatter getMascara(String mascara, char preenchimento) {
		MaskFormatter formato = new MaskFormatter();
		formato.setValueContainsLiteralCharacters(false);
		try {
			formato.setMask(mascara);
			formato.setPlaceholderCharacter(preenchimento);
		}
		catch (Exception exception) {  
			exception.printStackTrace();
	    }
		return formato;  
	}
	
	/** Cria uma nova fonte TTF a partir de um arquivo especificado */
	public static Font createFont(File arquivoFonte) {
		try {  return Font.createFont(Font.TRUETYPE_FONT, arquivoFonte); }
		catch (Exception exception) { return null; }
	}
	
	/** Cria uma fonte personalizada a partir de uma j� existente */
	public static Font deriveFont(Font arquivoFonte, int tipoFonte, int tamanho) {
		try { return arquivoFonte.deriveFont(tipoFonte, (float) tamanho); }
		catch (Exception exception) { return null; }
	}
	
}
