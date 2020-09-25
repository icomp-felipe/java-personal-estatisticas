package com.estatisticas.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

import com.phill.libs.ResourceManager;
import com.phill.libs.ui.*;
import com.estatisticas.dao.UsuarioDAO;
import com.estatisticas.model.*;

/** Contém um diálogo de troca de senha do sistema.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 2.5, 21/09/2020 */
public class TelaMudaSenha extends JFrame {

	// Serial
	private static final long serialVersionUID = 3483645244458314752L;
	
	// Constantes
	private final Usuario usuario;
	
	// Atributos gráficos
	private final JPasswordField textSenhaOnce, textSenhaTwice;
	private JPasswordField textSenhaAtual;
	
	/** Constrói a janela gráfica. */
	public TelaMudaSenha(final Usuario usuario) {
		super("Atualização de Senha");
		
		// Registrando usuário
		this.usuario = usuario;
		
		// Inicializando atributos gráficos
		GraphicsHelper helper = GraphicsHelper.getInstance();
		GraphicsHelper.setFrameIcon(this,"img/logo.png");
						
		Dimension dimension = new Dimension(320,300);
		JPanel    mainPanel = new JPaintedPanel("img/background.png",dimension);
						
		mainPanel.setLayout(null);
		setContentPane(mainPanel);
						
		// Recuperando fontes e cores
		Font  fonte = helper.getFont ();
		Color color = helper.getColor();
		
		// Recuperando ícones
		Icon exitIcon  = ResourceManager.getIcon("icon/shutdown.png",20,20);
		Icon saveIcon  = ResourceManager.getIcon("icon/save.png",20,20);
		
		// Declaração da janela gráfica
		JPanel painelSenhaAtual = new JPanel();
		painelSenhaAtual.setBorder(helper.getTitledBorder("Digite a senha atual"));
		painelSenhaAtual.setLayout(null);
		painelSenhaAtual.setOpaque(false);
		painelSenhaAtual.setBounds(12, 12, 272, 60);
		mainPanel.add(painelSenhaAtual);
		
		textSenhaAtual = new JPasswordField();
		textSenhaAtual.setFont(fonte);
		textSenhaAtual.setForeground(color);
		textSenhaAtual.setBounds(12, 30, 248, 20);
		painelSenhaAtual.add(textSenhaAtual);
		
		JPanel painelSenha = new JPanel();
		painelSenha.setBorder(helper.getTitledBorder("Digite a nova senha"));
		painelSenha.setBounds(12, 75, 272, 60);
		painelSenha.setLayout(null);
		painelSenha.setOpaque(false);
		mainPanel.add(painelSenha);
		
		textSenhaOnce = new JPasswordField();
		textSenhaOnce.setFont(fonte);
		textSenhaOnce.setForeground(color);
		textSenhaOnce.setBounds(12, 30, 248, 20);
		painelSenha.add(textSenhaOnce);
		
		JPanel painelConfirmaSenha = new JPanel();
		painelConfirmaSenha.setLayout(null);
		painelConfirmaSenha.setBorder(helper.getTitledBorder("Confirme a nova senha"));
		painelConfirmaSenha.setBounds(12, 138, 272, 60);
		painelConfirmaSenha.setOpaque(false);
		mainPanel.add(painelConfirmaSenha);
		
		textSenhaTwice = new JPasswordField();
		textSenhaTwice.setFont(fonte);
		textSenhaTwice.setForeground(color);
		textSenhaTwice.setBounds(12, 30, 248, 20);
		painelConfirmaSenha.add(textSenhaTwice);
		
		JButton botaoSalvar = new JButton(saveIcon);
		botaoSalvar.setToolTipText("Salva alterações");
		botaoSalvar.addActionListener((event) -> action_update_password());
		botaoSalvar.setBounds(254, 210, 30, 25);
		mainPanel.add(botaoSalvar);
		
		JButton botaoSair = new JButton(exitIcon);
		botaoSair.setToolTipText("Sai desta tela");
		botaoSair.addActionListener((event) -> dispose());
		botaoSair.setBounds(212, 210, 30, 25);
		mainPanel.add(botaoSair);
		
		KeyListener listener = (KeyReleasedListener) (event) -> { if (event.getKeyCode() == KeyEvent.VK_ENTER) botaoSalvar.doClick(); };
		textSenhaTwice.addKeyListener(listener);
		
		setSize(dimension);
		setResizable(false);
	    setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setVisible(true);
	}
	
	/** Verifica as senhas e as altera no banco de dados. */
	private void action_update_password() {
		
		final String title = "Atualizando senha";
		
		final String previousKey = new String(textSenhaAtual.getPassword());
		final String firstKey    = new String(textSenhaOnce .getPassword());
		final String secondKey   = new String(textSenhaTwice.getPassword());
		
		try {
			
			// Validando credenciais
			boolean status = UsuarioDAO.tryLogin(new Usuario(this.usuario.getLogin(), previousKey));
			
			// Se as credenciais forem válidas...
			if (status) {
				
				// ...verifico a nova senha
				if (firstKey.equals(secondKey)) {
							
					UsuarioDAO.changePassword(secondKey);	this.usuario.setSenha(secondKey);
					AlertDialog.info(title, "Senha de acesso atualizada com sucesso!");
					dispose();
							
				}
				else
					AlertDialog.error(title,"Senhas não conferem!");
				
			}
			
			// Credenciais inválidas
			else
				AlertDialog.error(title,"Senha informada não confere com a cadastrada");
				
				
		}
		catch (SQLException exception) {
			exception.printStackTrace();
			AlertDialog.error(title, "Falha ao alterar senha de acesso");
		}
		
	}
	
}
