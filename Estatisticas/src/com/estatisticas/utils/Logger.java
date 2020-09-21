package com.estatisticas.utils;

import java.io.*;
import java.text.*;
import java.util.*;

import org.apache.commons.io.FileUtils;

import com.phill.libs.ResourceManager;
import com.phill.libs.ui.AlertDialog;

/** Implementa um mini sistema de logging para as exceções do programa.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 1.0, 21/09/2020 */
public class Logger extends ByteArrayOutputStream {
	
	/** Escreve toda a saída de texto desta classe em um novo arquivo de tracing. */
	public void dumpToFile() {
		
		final String tracing = toString().trim  ();
		final File traceFile = getStackTraceFile();
		
		if (!tracing.isEmpty()) {
			
			try {
				flush();
				FileUtils.writeStringToFile(traceFile, tracing, "UTF-8");
			} catch (IOException exception) {
				AlertDialog.error("Log do sistema", "Falha ao gravar arquivo de log");
			}
			
		}
		
	}
	
	/** Retorna a data e horário atual do sistema.
	 *  @return String contendo a data e horário atual do sistema. */
	private String getSystemDate() {
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");
		Date date = new Date();
		
		return dateFormat.format(date);
	}
	
	/** Monta o arquivo de tracing.
	 *  @return Arquivo de tracing. */
	private File getStackTraceFile() {
		
		String filename = String.format("stackTrace_%s.txt",getSystemDate());
		String stackFolder = ResourceManager.getResource("tracing/");
		
		return new File(stackFolder + filename);
	}
	
}
