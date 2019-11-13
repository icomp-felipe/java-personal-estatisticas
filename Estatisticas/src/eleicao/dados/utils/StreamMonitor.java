package eleicao.dados.utils;

import java.io.*;
import java.util.*;
import java.text.*;

/** Classe que redireciona uma stream para um arquivo.
 *  @author Felipe André
 *  @version 2.0, 30/08/2015 */
public class StreamMonitor extends OutputStream {

	/** Arquivo de saída dos dados */
	private File stackTraceFile;
	private FileOutputStream outputStream;
	
	/** Cria um arquivo de rastreamento com a data atual do sistema */
	public StreamMonitor() {
		try { this.outputStream = getStackTraceStream(); }
		catch (IOException exception) { }
	}
	
	@Override
	public void write(int args) throws IOException {
		write(new byte[] {(byte) args}, 0, 1);
	}
	
	@Override
	public synchronized void write(byte[] charSequence, int offset, int length) throws IOException {
		registerStackTrace(charSequence,offset,length);
	}
	
	/** Escreve o stack trace em arquivo */
	private void registerStackTrace(byte[] charSequence, int offset, int length) {
		try {
			outputStream.write(charSequence,offset,length);
			outputStream.flush();
		}
		catch (IOException exception) {
		}
	}
	
	@Override
	public void close() throws IOException {
		super.close();
		outputStream.flush();
		outputStream.close();
		deleteBlankFile();
	}
	
	/** Remove um arquivo se este for vazio */
	private void deleteBlankFile() {
		if (stackTraceFile.length() == 0)
			stackTraceFile.delete();
	}
	
	/** Retonra a data atual do sistema */
	private String getSystemDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/** Recupera a string de separação de arquivos do S.O. */
	private String getFileSeparator() {
		return System.getProperty("file.separator");
	}
	
	/** Cria o arquivo de debug */
	private File getStackTraceFile() {
		String filename = String.format("stackTrace_%s.txt",getSystemDate());
		String stackFolder = ResourceManager.getResource("tracing" + getFileSeparator());
		return new File(stackFolder+filename);
	}
	
	/** Cria uma stream com o arquivo de debug */
	private FileOutputStream getStackTraceStream() throws IOException {
		this.stackTraceFile = getStackTraceFile();
		return new FileOutputStream(stackTraceFile);
	}
	
}