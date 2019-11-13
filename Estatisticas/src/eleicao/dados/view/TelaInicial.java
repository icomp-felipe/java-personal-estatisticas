package eleicao.dados.view;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import eleicao.dados.model.*;
import eleicao.dados.utils.*;

/** Classe TelaInicial - contém a interface principal de acesso às funcionalidades do sistema
 *  @author Felipe André Souza da Silva 
 *  @version 2.00, 15/09/2014 */
public class TelaInicial extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JMenuItem arquivoConsulta, arquivoCadastro, arquivoSair, arquivoNome, arquivoSenha;
	private JMenuItem backupCria, backupRestaura;
	private JMenuItem ajudaSobre;
	private PrintStream stderr;
	private String username;

	/** Constrói a janela gráfica */
	public TelaInicial(String username) {
		super("Sistema de Manipulação de Dados");
		
		this.username = username;
		onCreateOptionsMenu();
		
		stderr = new PrintStream(new StreamMonitor()); 
		System.setErr(stderr);
		
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
        
        /** Instanciação dos itens de menu */
        arquivoCadastro = new JMenuItem("Cadastro de Dados");
        arquivoConsulta = new JMenuItem("Consulta de Dados");
        arquivoNome	    = new JMenuItem("Mudar login");
        arquivoSenha	= new JMenuItem("Mudar senha do sistema");
        arquivoSair 	= new JMenuItem("Sair do Sistema");
        
        backupCria 	   = new JMenuItem("Criar Backup");
        backupRestaura = new JMenuItem("Restaurar Backup");
        
        ajudaSobre   = new JMenuItem("Sobre");
        
        /** Adicionando itens ao Menu Arquivo */
        menuArquivo.add(arquivoCadastro);
        menuArquivo.add(arquivoConsulta);
        menuArquivo.add(new JSeparator());
        menuArquivo.add(arquivoNome);
        menuArquivo.add(arquivoSenha);
        menuArquivo.add(new JSeparator());
        menuArquivo.add(arquivoSair);
        
        menuBackups.add(backupCria);
        menuBackups.add(backupRestaura);
        
        menuAjudas.add(ajudaSobre);
        
        /** Adicionando eventos aos itens de menu */
        arquivoCadastro.addActionListener(this);
        arquivoConsulta.addActionListener(this);
        arquivoNome.addActionListener(this);
        arquivoSenha.addActionListener(this);
        arquivoSair.addActionListener(this);
        
        backupCria.addActionListener(this);
        backupRestaura.addActionListener(this);
        
        ajudaSobre.addActionListener(this);
        
        /** Adicionando os menus na barra de menu */
        menuBar.add(menuArquivo);
        menuBar.add(menuBackups);
        menuBar.add(menuAjudas);
        
        /** Setando o menu na JFrame */
        setJMenuBar(menuBar);
	}

	/********************* Bloco de Funcionalidades da Interface Gráfica *************************/
	
	/** Abre um diálogo de troca de nome de usuário */
	private void changeName() {
		UsuarioDAO.changeLogin();
	}
	
	/** Cria um backup com todos os dados do banco */
	private void criaBackup() throws IOException {
		File arquivo = FileChooserHelper.dialog(this,true);
		if (arquivo != null) {
			ObjetoDAO.criaBackup(arquivo);
			AlertDialog.informativo("Backup criado com sucesso!");
		}
	}
	
	/** Restaura o backup para o banco de dados */
	private void restauraBackup() throws IOException {
		File arquivo = FileChooserHelper.dialog(this,false);
		
		if (arquivo != null) {
			int status = AlertDialog.dialog("Você tem certeza que deseja restaurar a base de dados?\nTodos os dados serão sobrescritos!");
		
			if (status == JFileChooser.APPROVE_OPTION) {
				ObjetoDAO.restauraBackup(arquivo);
				AlertDialog.informativo("Backup restaurado com sucesso!");
			}
		}
	}
	
	/** Exibe informações legais do software */
	private void sobre() {
		AlertDialog.informativo("Sistema de Gerenciamento de Dados\nversão 1.0");
	}
	
	/******************** Métodos Auxiliares ao Controle das Funções *****************************/
	
	/** Retorna a largura atual da tela em pixels */
	private int getScreenWidth() {
	    return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		stderr.close();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == arquivoCadastro)
			new TelaCadastroEdicao(username);
		else if (source == arquivoConsulta)
			new TelaBusca();
		else if (source == arquivoNome)
			changeName();
		else if (source == arquivoSenha)
			new TelaMudaSenha();
		else if (source == arquivoSair)
			dispose();
		else if (source == backupCria)
			try { criaBackup();	}
			catch (IOException e) { e.printStackTrace(); }
		else if (source == backupRestaura)
			try { restauraBackup(); }
			catch (Exception e) { e.printStackTrace(); }
		else if (source == ajudaSobre)
			sobre();
	}

}
