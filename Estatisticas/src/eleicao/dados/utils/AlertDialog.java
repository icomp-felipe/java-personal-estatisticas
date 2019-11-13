package eleicao.dados.utils;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import javax.swing.*;

/** Classe que contém métodos úteis de manipulação de telas de diálogo
 *  @author Felipe André
 *  @version 2.5, 19/01/2015 */
public class AlertDialog {
	
	/** Mostra uma mensagem de informação padrão */
	public static void informativo(String mensagem) {
		informativo("Informação", mensagem);
	}
	
	/** Mostra uma mensagem de informação personalizada */
	public static void informativo(String titulo, String mensagem) {
		JOptionPane.showMessageDialog(null,mensagem,titulo,JOptionPane.INFORMATION_MESSAGE);
	}
	
	/** Mostra uma mensagem de erro padrão */
	public static void erro(String mensagem) {
		erro("Tela de Erro",mensagem);
	}
	
	/** Mostra uma mensagem de erro personalizada */
	public static void erro(String titulo, String mensagem) {
		JOptionPane.showMessageDialog(null,mensagem,titulo,JOptionPane.ERROR_MESSAGE);
	}
	
	/** Mostra uma janela de diálogo */
	public static int dialog(String mensagem) {
		return JOptionPane.showConfirmDialog(null,mensagem);
	}
	
	/** Cola um texto na área de transferência */
	public static void pasteToClibpoard(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }
	
	public static void showMessageForAWhile(String message) {
		System.out.print(message);
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println();
		}
	}
	
	/** Copia um texto da área de transferência */
	public static String copyFromClipboard() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
		   	try { result = (String)contents.getTransferData(DataFlavor.stringFlavor); }
		   	catch (UnsupportedFlavorException | IOException ex) { ex.printStackTrace(); }
		}
	    return result;
	}
	
}
