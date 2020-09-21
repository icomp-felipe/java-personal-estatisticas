package com.estatisticas.view;

import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.estatisticas.bd.*;
import com.estatisticas.model.*;
import com.phill.libs.*;
import com.phill.libs.ui.*;

/** Classe TelaLogin - cria um ambiente gráfico para o usuário fazer login no sistema.
 *  @author Felipe André Souza da Silva 
 *  @version 2.6, 19/09/2020 */
public class TelaLogin extends JFrame {

	// Serial
	private static final long serialVersionUID = 6192035759251528292L;
	
	private final JTextField textLogin;
	private final JPasswordField textSenha;
	private final JLabel labelConexaoStatus;
	private final JButton botaoLimpar, botaoEntrar;
	private final ImageIcon loading = new ImageIcon(ResourceManager.getResource("img/loading.gif"));
	
	/** Construtor da classe TelaLogin - Cria a janela */
	public static void main(String[] args) {
		new TelaLogin();
	}
	
	/** Construtor da classe TelaLogin - constrói a janela gráfica */
	public TelaLogin() {
		super("Tela de Login");
		
		// Inicializando atributos gráficos
		GraphicsHelper helper = GraphicsHelper.getInstance();
		GraphicsHelper.setFrameIcon(this,"img/logo.png");
				
		Dimension dimension = new Dimension(550,300);
		JPanel    mainPanel = new JPaintedPanel("img/background.png",dimension);
				
		mainPanel.setLayout(null);
		setContentPane(mainPanel);
				
		// Recuperando fontes e cores
		Font dialog = helper.getFont (  );
		Font  fonte = helper.getFont (18);
		Color color = helper.getColor(  );
				
		// Recuperando ícones
		ImageIcon loginIcon = new ImageIcon(ResourceManager.getResource("img/login.png"));
		
		// Declaração da janela gráfica
		JLabel labelImagem = new JLabel(loginIcon);
		labelImagem.setBounds(12, 12, 232, 235);
		mainPanel.add(labelImagem);
		
		JLabel labelLogin = new JLabel("Login:");
		labelLogin.setFont(fonte);
		labelLogin.setBounds(256, 75, 85, 24);
		mainPanel.add(labelLogin);
		
		textLogin = new JTextField();
		textLogin.setFont(dialog);
		textLogin.setForeground(color);
		textLogin.setToolTipText("Digite aqui seu login");
		textLogin.setBounds(341, 75, 193, 24);
		textLogin.setColumns(10);
		mainPanel.add(textLogin);
		
		JLabel labelSenha = new JLabel("Senha:");
		labelSenha.setFont(fonte);
		labelSenha.setBounds(256, 134, 85, 24);
		mainPanel.add(labelSenha);
		
		textSenha = new JPasswordField();
		textSenha.setFont(dialog);
		textSenha.setForeground(color);
		textSenha.setToolTipText("Digite aqui sua senha");
		textSenha.setBounds(341, 134, 193, 24);
		mainPanel.add(textSenha);
		
		botaoEntrar = new JButton("Entrar");
		botaoEntrar.addActionListener((event) -> action_login());
		botaoEntrar.setBounds(449, 203, 85, 25);
		mainPanel.add(botaoEntrar);
		
		botaoLimpar = new JButton("Limpar");
		botaoLimpar.addActionListener((event) -> action_clear());
		botaoLimpar.setBounds(352, 203, 85, 25);
		mainPanel.add(botaoLimpar);
		
		JButton botaoSair = new JButton("Sair");
		botaoSair.addActionListener((event) -> dispose());
		botaoSair.setBounds(256, 203, 85, 25);
		getContentPane().add(botaoSair);
		
		labelConexaoStatus = new JLabel();
		labelConexaoStatus.setHorizontalAlignment(JLabel.LEFT);
		labelConexaoStatus.setBounds(12, 245, 220, 20);
		labelConexaoStatus.setVisible(false);
		mainPanel.add(labelConexaoStatus);
		
		KeyListener listener = (KeyReleasedListener) (event) -> { if (event.getKeyCode() == KeyEvent.VK_ENTER) botaoEntrar.doClick(); };
		textSenha.addKeyListener(listener);
		
		setSize(dimension);
		setResizable(false);
	    setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
	}
	
	/** Método para limpar os campos de texto da janela */
	private void action_clear() {
		textLogin.setText(null);
		textSenha.setText(null);
		textLogin.requestFocus();
	}
	
	/** Implementa a tentativa de login no sistema */
	private void action_login() {
		
		new Thread(() -> {
		
			// Recuperando login e senha da view
			final String login  = textLogin.getText();
			final String senha  = new String(textSenha.getPassword());
			final Usuario usuario = new Usuario(login, senha);
			
			util_control_label(false);
		
			// Aqui tento conectar ao banco e validar o usuário
			try {
				
				// Estabelece a conexão ao banco de acordo com a seleção na área de opções
				Database.INSTANCE.connect();
				
				// Verificando as credenciais no BD
				String user  = UsuarioDAO.tryLogin(usuario);
				
				// Se as credenciais forem inválidas, indico ao usuário...
				if (user == null) {
					SwingUtilities.invokeLater(() -> { labelConexaoStatus.setVisible(false); botaoEntrar.setEnabled(true); botaoLimpar.setEnabled(true);} );
					AlertDialog.error("Login","Usuário e/ou senha inválidos!");
				}
				
				// ...caso contrário, prossigo pra tela inicial, após verificar a senha de admin
				else {
					util_parse_key(login,senha);
					new TelaInicial(user);
					dispose();
				}
				
			}
			catch (SQLException exception) {
				exception.printStackTrace();
				util_control_label(true);
			}
			
		
		}).start();
		
	}
	
	/** Controla o label de conexão */
	private void util_control_label(boolean falha) {
		
		SwingUtilities.invokeLater(() -> {
			
			labelConexaoStatus.setVisible(true);
			
			if (falha) {
				labelConexaoStatus.setText("Falha na conexão ao banco");
				labelConexaoStatus.setIcon(null);
				labelConexaoStatus.setForeground(Color.RED);
				
				botaoEntrar.setEnabled(true);
				botaoLimpar.setEnabled(true);
			}
			else {
				labelConexaoStatus.setText("Validando credenciais...");
				labelConexaoStatus.setIcon(loading);
				labelConexaoStatus.setForeground(Color.BLACK);
				
				botaoEntrar.setEnabled(false);
				botaoLimpar.setEnabled(false);
			}
			
		});
		
	}
	
	/** Solicita a troca de senha caso esta ainda seja a padrão */
	private void util_parse_key(String login, String key) {
		if (login.equals("admin") && key.equals("admin")) {
			if (AlertDialog.dialog("É altamente recomendável que você troque sua senha\nDeseja fazer isso agora?") == 0)
				new TelaMudaSenha();
		}
	}
	
}
