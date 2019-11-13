package eleicao.dados.utils;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/** Encapsula as operações de abertura e fechamento de arquivo do Swing
 *  @author Felipe André
 *  @version 1.0, 02/08/2015 */
public class FileChooserHelper {
	
	/* Constantes úteis */
	private static final String EXTENSION = "ebp";
	private static final String DOT_EXTENSION = "." + EXTENSION;
	private static final File DIRECTORY = getUserHome();
	
	/** Retorna o diretório do usuário */
	private static File getUserHome() {
		return new File(System.getProperty("user.home"));
	}
	
	/** Exibe um diálogo de escolha de arquivos */
	public static File dialog(Component context, boolean salvar) {
		JFileChooser chooser = new JFileChooser();
		
		chooser.setDialogTitle("Selecione a planilha");
		chooser.setCurrentDirectory(DIRECTORY);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("Planilha do Excel (*" + DOT_EXTENSION + ")", EXTENSION)); // Define o filtro de seleção.
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setMultiSelectionEnabled(false);	// Impede seleções múltiplas
		
		int resultado;
		
		if (salvar)
			resultado = chooser.showSaveDialog(context);
		else
			resultado = chooser.showOpenDialog(context);
		
	    if (resultado != JFileChooser.APPROVE_OPTION)
	        return null;
	  
	    String filename = chooser.getSelectedFile().getAbsolutePath();
	    
	    if (!filename.endsWith(DOT_EXTENSION))
	    	filename += DOT_EXTENSION;
	    
	    File novo = new File(filename);
	    
	    if (novo.exists() && salvar) {
	    	int res = JOptionPane.showConfirmDialog(context,"O arquivo \"" + novo.getName() + "\" já existe!\nDeseja sobrescrevê-lo?");
	    	
	    	return (res == JOptionPane.OK_OPTION) ? novo : null;
	    }
	    
	    return novo;
	}

}
