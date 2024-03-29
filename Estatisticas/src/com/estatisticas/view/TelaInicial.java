package com.estatisticas.view;

import java.io.*;
import java.sql.*;
import java.awt.Desktop;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;

import com.phill.libs.ResourceManager;
import com.phill.libs.ui.*;
import com.phill.libs.sys.*;
import com.phill.libs.files.*;
import com.estatisticas.bd.*;
import com.estatisticas.dao.*;
import com.estatisticas.model.*;

/** Contém a interface principal de acesso às funcionalidades do sistema.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 3.1, 21/09/2020 */
public class TelaInicial extends JFrame {
	
	// Serial
	private static final long serialVersionUID = 8827494103161618301L;

	// Constantes
	private final Usuario usuario;
	private final FileNameExtensionFilter EBP = new FileNameExtensionFilter("Arquivo de Backup (.ebpx)","ebpx");
	
	// Atributos dinâmicos
	private File lastSelectedDir;
	
	/** Constrói a janela gráfica. */
	public TelaInicial(final Usuario usuario) {
		super("Sistema de Manipulação de Dados");
		
		// Registrando usuário
		this.usuario = usuario;
		
		// Inicializando atributos gráficos
		GraphicsHelper.setFrameIcon(this,"img/logo.png");
		
		onCreateOptionsMenu();
		
		// Redirecionando evento de fechamento de janela para o método 'dispose()' garantindo
		// assim, que o banco de dados seja desconectado sempre ao sair do sistema.
		addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent event) {
		       dispose();
		}});
		
		setSize(HostUtils.getScreenWidth(),70);
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
        JMenu menuAjuda   = new JMenu("Ajuda"  );
        
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
        JMenuItem itemManual = new JMenuItem("Visualizar Manual");
        itemManual.addActionListener((event) -> action_manual());
        menuAjuda.add(itemManual);
        
        JMenuItem itemAjudaSobre = new JMenuItem("Sobre");
        itemAjudaSobre.addActionListener((event) -> sobre());
        menuAjuda.add(itemAjudaSobre);
        
        /** Adicionando os menus na barra de menu */
        menuBar.add(menuArquivo);
        menuBar.add(menuBackups);
        menuBar.add(menuAjuda  );
        
        /** Setando o menu na JFrame */
        setJMenuBar(menuBar);
	}

	/********************* Bloco de Funcionalidades da Interface Gráfica *************************/
	
	/** Implementação da tela de troca de login de usuário. */
	private void action_login_change() {
		
		// Título das janelas de diálogo
		final String title = "Trocando login";
		
		// Solicitando senha atual do usuário
		String currentPWD = AlertDialog.password(title, "Informe a senha atual");
		
		// Se alguma senha foi informada...
		if (currentPWD != null) {
			
			try {
				
				// ...valido as credenciais e, ...
				boolean status = UsuarioDAO.tryLogin(new Usuario(this.usuario.getLogin(), currentPWD));
				
				// ...se as credenciais são válidas...
				if (status) {
					
					// ...solicito o novo login, ...
					String newLogin = AlertDialog.input(title, "Informe o novo login");
					
					// ...e, por fim, se o login for válido (não nulo), o atualizo na base de dados
					if (newLogin != null) {
						
						// aparando as pontas do novo login
						newLogin = newLogin.trim();
						
						if (newLogin.isEmpty())
							AlertDialog.error(title, "Por favor, informe um login não vazio");
						else {
							UsuarioDAO.changeLogin(newLogin);	this.usuario.setLogin(newLogin);
							AlertDialog.info(title, "Login alterado com sucesso!");
						}
						
					}
					
				}
				
				// Credenciais inválidas
				else
					AlertDialog.error(title, "Senha informada não confere com a cadastrada");
				
			}
			catch (SQLException exception) {
				exception.printStackTrace();
				AlertDialog.error(title, "Falha na comunicação com a base de dados");
			}
			
		}
		
	}
	
	/** Cria um backup com todos os dados do banco. */
	private void action_backup_create() {
		
		// Título das janelas de diálogo
		final String title = "Criando backup";
		
		// Recuperando arquivo de backup
		final File bkp = PhillFileUtils.loadFile("Selecione o arquivo de saída", this.EBP, PhillFileUtils.SAVE_DIALOG, lastSelectedDir);
		
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
		final File bkp = PhillFileUtils.loadFile("Selecione o arquivo de backup", this.EBP, PhillFileUtils.OPEN_DIALOG, lastSelectedDir);
		
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
	
	/** Abre o manual em PDF */
	private void action_manual() {
		
		try {
			
			File manual = ResourceManager.getResourceAsFile("doc/Sistema de Estatísticas.pdf");
			Desktop.getDesktop().open(manual);
			
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		
	}
	
	/** Exibe informações legais do software */
	private void sobre() {
		AlertDialog.info("Sobre", "Sistema de Gerenciamento de Dados\nversão 3.0 - build 20201001");
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
			TelaEstatisticasLogin.logger.dumpToFile();
			super.dispose();
		}
		
	}
	
}
