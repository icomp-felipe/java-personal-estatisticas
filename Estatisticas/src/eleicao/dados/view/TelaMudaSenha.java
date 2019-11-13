package eleicao.dados.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import eleicao.dados.model.*;
import eleicao.dados.utils.*;

/** Classe TelaMudaSenha - contém um diálogo de troca de senha do sistema
 *  @author Felipe André Souza da Silva 
 *  @version 2.00, 12/09/2014 */
public class TelaMudaSenha extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton botaoSalvar, botaoSair;
	private JPasswordField textSenhaOnce, textSenhaTwice;
	
	/** Constrói a janela gráfica */
	public TelaMudaSenha() {
		super("Atualização de Senha");
		
		Font  fonte = GraphicsHelper.getFont();
		Color color = GraphicsHelper.getColor();
		
		setSize(300,225);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JPanel painelSenha = new JPanel();
		painelSenha.setBorder(GraphicsHelper.getTitledBorder("Digite sua senha"));
		painelSenha.setBounds(12, 12, 272, 61);
		getContentPane().add(painelSenha);
		painelSenha.setLayout(null);
		
		textSenhaOnce = new JPasswordField();
		textSenhaOnce.setFont(fonte);
		textSenhaOnce.setForeground(color);
		textSenhaOnce.setBounds(12, 30, 248, 20);
		painelSenha.add(textSenhaOnce);
		
		JPanel painelConfirmaSenha = new JPanel();
		painelConfirmaSenha.setLayout(null);
		painelConfirmaSenha.setBorder(GraphicsHelper.getTitledBorder("Confirme sua senha"));
		painelConfirmaSenha.setBounds(12, 85, 272, 61);
		getContentPane().add(painelConfirmaSenha);
		
		textSenhaTwice = new JPasswordField();
		textSenhaTwice.setFont(fonte);
		textSenhaTwice.setForeground(color);
		textSenhaTwice.setBounds(12, 30, 248, 20);
		textSenhaTwice.addKeyListener(new KeyAdapter() {    /** Adiciona uma ação quando o ENTER é pressionado */
		      public void keyPressed(KeyEvent e) {
			        if (e.getKeyCode() == KeyEvent.VK_ENTER)
			        	botaoSalvar.doClick();
			      }
		});
		painelConfirmaSenha.add(textSenhaTwice);
		
		botaoSalvar = new JButton("Salvar");
		botaoSalvar.addActionListener(this);
		botaoSalvar.setBounds(152, 158, 90, 25);
		getContentPane().add(botaoSalvar);
		
		botaoSair = new JButton("Sair");
		botaoSair.addActionListener(this);
		botaoSair.setBounds(56, 158, 90, 25);
		getContentPane().add(botaoSair);
		setResizable(false);
		setVisible(true);
	}
	
	/** Verifica as senhas e as altera no banco de dados */
	private void salvar() {
		String keyOnce  = new String(textSenhaOnce.getPassword());
		String keyTwice = new String(textSenhaTwice.getPassword());
		
		if (keyOnce.equals(keyTwice)) {
			UsuarioDAO.changePassword(keyTwice);
			dispose();
		}
		else
			AlertDialog.erro("Senhas não conferem!");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == botaoSalvar)
			salvar();
		else if (source == botaoSair)
			dispose();
	}
}
