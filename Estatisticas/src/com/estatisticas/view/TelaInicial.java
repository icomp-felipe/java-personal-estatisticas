package com.estatisticas.view;

import java.io.*;
import java.sql.SQLException;
import java.awt.*;
import javax.swing.*;

import com.estatisticas.bd.Database;
import com.estatisticas.dao.ClienteDAO;
import com.estatisticas.model.*;
import com.estatisticas.utils.StreamMonitor;
import com.phill.libs.*;
import com.phill.libs.ui.*;

/** Classe TelaInicial - contém a interface principal de acesso às funcionalidades do sistema
 *  @author Felipe André Souza da Silva 
 *  @version 2.00, 15/09/2014 */
public class TelaInicial extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private PrintStream stderr;
	
	private String login;
	
	private File lastSelectedDir = FileChooserHelper.HOME_DIRECTORY;

	/** Constrói a janela gráfica */
	public TelaInicial(String login) {
		super("Sistema de Manipulação de Dados");
		
		this.login = login;
		onCreateOptionsMenu();
		
		//stderr = new PrintStream(new StreamMonitor()); 
		//System.setErr(stderr);
		
		setSize(getScreenWidth(),60);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
        itemArquivoSenha.addActionListener((event) -> new TelaMudaSenha());
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
			if (currentLogin.equals(this.login)) {
				
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
							
							UsuarioDAO.changeLogin(newLogin);	this.login = newLogin;
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
		final File bkp = FileChooserHelper.loadFile(this,Constants.FileTypes.EBP,"Selecione o arquivo de saída",true,lastSelectedDir);
		
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
		final File bkp = FileChooserHelper.loadFile(this,Constants.FileTypes.EBP,"Selecione o arquivo de backup",false,lastSelectedDir);
		
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
		AlertDialog.info("Sobre", "Sistema de Gerenciamento de Dados\nversão 2.0");
	}
	
	/******************** Métodos Auxiliares ao Controle das Funções *****************************/
	
	/** Retorna a largura atual da tela em pixels */
	private int getScreenWidth() {
	    return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	
	@Override
	public void dispose() {
		
		try { Database.INSTANCE.disconnect(); }
		catch (SQLException exception) { }
		finally { /*stderr.close();*/ super.dispose(); }
		
	}
	
}
