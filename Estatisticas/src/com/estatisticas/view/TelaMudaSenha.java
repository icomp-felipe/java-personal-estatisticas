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
 *  @version 2.0, 21/09/2020 */
public class TelaMudaSenha extends JFrame {

	// Serial
	private static final long serialVersionUID = 3483645244458314752L;
	
	// Constantes
	private final Usuario usuario;
	
	// Atributos gráficos
	private final JPasswordField textSenhaOnce, textSenhaTwice;
	
	/** Constrói a janela gráfica. */
	public TelaMudaSenha(final Usuario usuario) {
		super("Atualização de Senha");
		
		// Registrando usuário
		this.usuario = usuario;
		
		// Inicializando atributos gráficos
		GraphicsHelper helper = GraphicsHelper.getInstance();
		GraphicsHelper.setFrameIcon(this,"img/logo.png");
						
		Dimension dimension = new Dimension(300,225);
		JPanel    mainPanel = new JPaintedPanel("img/background.png",dimension);
						
		mainPanel.setLayout(null);
		setContentPane(mainPanel);
						
		// Recuperando fontes e cores
		Font  fonte = helper.getFont ();
		Color color = helper.getColor();
		
		// Recuperando ícones
		Icon exitIcon  = ResourceManager.getResizedIcon("icon/shutdown.png",20,20);
		Icon saveIcon  = ResourceManager.getResizedIcon("icon/save.png",20,20);
		
		// Declaração da janela gráfica
		JPanel painelSenha = new JPanel();
		painelSenha.setBorder(helper.getTitledBorder("Digite sua senha"));
		painelSenha.setBounds(12, 12, 272, 61);
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
		painelConfirmaSenha.setBorder(helper.getTitledBorder("Confirme sua senha"));
		painelConfirmaSenha.setBounds(12, 85, 272, 61);
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
		botaoSalvar.setBounds(254, 158, 30, 25);
		mainPanel.add(botaoSalvar);
		
		JButton botaoSair = new JButton(exitIcon);
		botaoSair.setToolTipText("Sai desta tela");
		botaoSair.addActionListener((event) -> dispose());
		botaoSair.setBounds(212, 158, 30, 25);
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
		
		final String firstKey  = new String(textSenhaOnce .getPassword());
		final String secondKey = new String(textSenhaTwice.getPassword());
		
		if (firstKey.equals(secondKey)) {
			
			try {
				
				UsuarioDAO.changePassword(secondKey);	this.usuario.setSenha(secondKey);
				AlertDialog.info(title, "Senha de acesso atualizada com sucesso!");
				dispose();
				
			}
			catch (SQLException exception) {
				exception.printStackTrace();
				AlertDialog.error(title, "Falha ao alterar senha de acesso");
			}
			
		}
		else
			AlertDialog.error(title,"Senhas não conferem!");
		
	}

}
