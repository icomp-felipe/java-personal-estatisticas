package com.estatisticas.view;

import java.io.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.filechooser.*;

import com.phill.libs.*;
import com.phill.libs.sys.HostUtils;
import com.phill.libs.ui.*;

import com.estatisticas.bd.*;
import com.estatisticas.dao.*;
import com.estatisticas.model.*;
import com.estatisticas.utils.*;

/** Contém a interface principal de acesso às funcionalidades do sistema.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 3.0, 21/09/2020 */
public class TelaInicial extends JFrame {
	
	// Serial
	private static final long serialVersionUID = 8827494103161618301L;

	// Constantes
	private final Usuario usuario;
	private final FileNameExtensionFilter EBP = new FileNameExtensionFilter("Arquivo de Backup (.ebp)","ebp");
	
	// Atributos dinâmicos
	private File lastSelectedDir = FileChooserHelper.HOME_DIRECTORY;
	private PrintStream stderr;
	
	/** Constrói a janela gráfica. */
	public TelaInicial(final Usuario usuario) {
		super("Sistema de Manipulação de Dados");
		
		// Registrando usuário
		this.usuario = usuario;
		
		// Inicializando atributos gráficos
		GraphicsHelper.setFrameIcon(this,"img/logo.png");
		
		onCreateOptionsMenu();
		
		// Iniciando monitor de exceções
		/*stderr = new PrintStream(new StreamMonitor()); 
		System.setErr(stderr);*/
		
		// Redirecionando evento de fechamento de janela para o método 'dispose()' garantindo
		// assim, que o banco de dados seja desconectado sempre ao sair do sistema.
		addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent event) {
		       dispose();
		}});
		
		setSize(HostUtils.getScreenWidth(),60);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setVisible(true);
	}
	
	/** Cria a barra de menu com todas as suas opções */
	private void onCreateOptionsMenu() {
		
		JMenuBar menuBar = new JMenuBar();
		  
	 	/** Instanciação dos menus */
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenu menuBackups = new JMenu("Backups");
        JMenu menuAjudas  = new JMenu("Ajuda");
        
        // Menu Arquivo
        JMenuItem itemArquivoCadastro = new JMenuItem("Cadastro de Dados");
        itemArquivoCadastro.addActionListener((event) -> new TelaCadastroEdicao());
        menuArquivo.add(itemArquivoCadastro);
        
        JMenuItem itemArquivoConsulta = new JMenuItem("Consulta de Dados");
        itemArquivoConsulta.addActionListener((event) -> new TelaBusca());
        menuArquivo.add(itemArquivoConsulta);
        
        menuArquivo.add(new JSeparator());
        
        JMenuItem  itemArquivoLogin = new JMenuItem("Mudar login");
        itemArquivoLogin.addActionListener((event) -> action_login_change());
        menuArquivo.add(itemArquivoLogin);
        
        JMenuItem itemArquivoSenha = new JMenuItem("Mudar senha do sistema");
        itemArquivoSenha.addActionListener((event) -> new TelaMudaSenha(this.usuario));
        menuArquivo.add(itemArquivoSenha);
        
        menuArquivo.add(new JSeparator());
        
        JMenuItem itemArquivoSair = new JMenuItem("Sair do Sistema");
        itemArquivoSair.addActionListener((event) -> dispose());
        menuArquivo.add(itemArquivoSair);
        
        // Menu Backups
        JMenuItem itemBackupCria = new JMenuItem("Criar Backup");
        itemBackupCria.addActionListener((event) -> action_backup_create());
        menuBackups.add(itemBackupCria);
        
        JMenuItem itemBackupRestaura = new JMenuItem("Restaurar Backup");
        itemBackupRestaura.addActionListener((event) -> action_backup_restore());
        menuBackups.add(itemBackupRestaura);
        
        // Menu Ajuda
        JMenuItem itemAjudaSobre = new JMenuItem("Sobre");
        itemAjudaSobre.addActionListener((event) -> sobre());
        menuAjudas.add(itemAjudaSobre);
        
        /** Adicionando os menus na barra de menu */
        menuBar.add(menuArquivo);
        menuBar.add(menuBackups);
        menuBar.add(menuAjudas);
        
        /** Setando o menu na JFrame */
        setJMenuBar(menuBar);
	}

	/********************* Bloco de Funcionalidades da Interface Gráfica *************************/
	
	/** Implementação da tela de troca de login de usuário */
	private void action_login_change() {
		
		// Título das janelas de diálogo
		final String title = "Trocando login";
		
		// Solicitando login atual do usuário
		String currentLogin = AlertDialog.input(title, "Informe o login atual");
		
		// Se algum login foi informado...
		if (currentLogin != null) {
			
			// ...aparo as pontas para evitar possíveis erros de entrada, ...
			currentLogin = currentLogin.trim();
			
			// ...verifico o login informado com a cadastrado, ...
			if (currentLogin.equals(this.usuario.getLogin())) {
				
				// ...solicito o novo login, ...
				String newLogin = AlertDialog.input(title, "Informe o novo login");
				
				// ...e, por fim, se o login for válido (não nulo), o atualizo na base de dados
				if (newLogin != null) {
					
					// aparando as pontas do novo login
					newLogin = newLogin.trim();
					
					if (newLogin.isEmpty())
						AlertDialog.error(title, "Por favor, informe um login não vazio");
					
					else {
						
						try {
							
							UsuarioDAO.changeLogin(newLogin);	this.usuario.setLogin(newLogin);
							AlertDialog.info(title, "Login alterado com sucesso!");
							
						}
						catch (SQLException exception) {
							exception.printStackTrace();
							AlertDialog.error(title, "Falha ao alterar login na base de dados");
						}
						
					}
					
				}
				
			}
			else
				AlertDialog.error(title, "Login informado não confere com o cadastrado");
			
		}
		
	}
	
	/** Cria um backup com todos os dados do banco. */
	private void action_backup_create() {
		
		// Título das janelas de diálogo
		final String title = "Criando backup";
		
		// Recuperando arquivo de backup
		final File bkp = FileChooserHelper.loadFile(this,this.EBP,"Selecione o arquivo de saída",true,lastSelectedDir);
		
		// Se algum arquivo foi escolhido...
		if (bkp != null) {
			
			// ...guardo o endereço deste arquivo, para ser usado como sugestão em próximas seleções...
			this.lastSelectedDir = bkp.getParentFile();
			
			// ...verifico se este arquivo pode ser escrito...
			if (!bkp.getParentFile().canWrite()) {
				
				AlertDialog.error(title, "O arquivo que você escolheu está numa pasta que não pode ser escrita!\nFavor escolher outra pasta.");
				return;
				
			}
			
			// ...e, por fim, crio o backup em arquivo
			try {
				
				ClienteDAO.dump(bkp);
				AlertDialog.info(title, "Backup criado com sucesso!");
			
			}
			catch (Exception exception) {
				
				exception.printStackTrace();
				AlertDialog.error(title, "Falha ao criar backup");
			
			}
			
		}
		
	}
	
	/** Restaura os dados do arquivo de backup para o banco de dados. */
	private void action_backup_restore() {
		
		// Título das janelas de diálogo
		final String title = "Restaurando backup";
				
		// Recuperando arquivo de backup
		final File bkp = FileChooserHelper.loadFile(this,this.EBP,"Selecione o arquivo de backup",false,lastSelectedDir);
		
		// Se algum arquivo foi escolhido...
		if (bkp != null) {
					
			// ...guardo o endereço deste arquivo, para ser usado como sugestão em próximas seleções...
			this.lastSelectedDir = bkp.getParentFile();
			
			// ...exibo o diálogo de confirmação ao usuário e...
			int choice = AlertDialog.dialog("Você tem certeza que deseja restaurar a base de dados?\nTodos os dados serão sobrescritos!");
		
			// ...se ele deseja prosseguir, o faço
			if (choice == AlertDialog.OK_OPTION) {
				
				try {
					
					ClienteDAO.restore(bkp);
					AlertDialog.info(title, "Backup restaurado com sucesso!");
					
				} catch (Exception exception) {
					
					exception.printStackTrace();
					AlertDialog.error(title, "Falha ao restaurar backup");
					
				}
				
			}
			
		}
		
	}
	
	/** Exibe informações legais do software */
	private void sobre() {
		AlertDialog.info("Sobre", "Sistema de Gerenciamento de Dados\nversão 3.0 - build 20200921");
	}
	
	/******************** Métodos Auxiliares ao Controle das Funções *****************************/
	
	@Override
	public void dispose() {
		
		try {
			Database.INSTANCE.disconnect();
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		finally {
			/*stderr.close();*/
			super.dispose();
		}
		
	}
	
}
