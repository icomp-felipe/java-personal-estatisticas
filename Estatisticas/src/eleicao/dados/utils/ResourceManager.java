package eleicao.dados.utils;

import java.nio.file.*;

/** Faz a interface do programa com arquivos externos
 *  @author Felipe Andr�
 *  @version 1.0, 23/08/2015 */
public class ResourceManager {
	
	/** Retorna o diret�rio de trabalho atual */
	public static String getCurrentPath() {
		Path currentRelativePath = Paths.get("");
		return currentRelativePath.toAbsolutePath().toString();
	}
	
	/** Retorna o diret�rio de trabalho atual */
	public static String getResource(String resource) {
		String baseDirectory = getCurrentPath();
		return (baseDirectory + "/res/" + resource);
	}

}
