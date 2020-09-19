package eleicao.dados.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.phill.libs.ui.*;
import eleicao.dados.model.*;

/** Classe TelaMudaSenha - contém um diálogo de troca de senha do sistema
 *  @author Felipe André Souza da Silva 
 *  @version 2.6, 19/09/2020 */
public class TelaMudaSenha extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPasswordField textSenhaOnce, textSenhaTwice;
	
	public static void main(String[] args) {
		new TelaMudaSenha();
	}
	
	/** Constrói a janela gráfica */
	public TelaMudaSenha() {
		super("Atualização de Senha");
		
		// Inicializando atributos gráficos
		GraphicsHelper helper = GraphicsHelper.getInstance();
		//GraphicsHelper.setFrameIcon(this,"img/logo.png");
						
		Dimension dimension = new Dimension(300,225);
		JPanel    mainPanel = new JPaintedPanel("img/background.png",dimension);
						
		mainPanel.setLayout(null);
		setContentPane(mainPanel);
						
		// Recuperando fontes e cores
		Font  fonte = helper.getFont ();
		Color color = helper.getColor();
		
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
		
		JButton botaoSalvar = new JButton("Salvar");
		botaoSalvar.addActionListener((event) -> action_update_password());
		botaoSalvar.setBounds(152, 158, 90, 25);
		mainPanel.add(botaoSalvar);
		
		JButton botaoSair = new JButton("Sair");
		botaoSair.addActionListener((event) -> dispose());
		botaoSair.setBounds(56, 158, 90, 25);
		mainPanel.add(botaoSair);
		
		KeyListener listener = (KeyReleasedListener) (event) -> { if (event.getKeyCode() == KeyEvent.VK_ENTER) botaoSalvar.doClick(); };
		textSenhaTwice.addKeyListener(listener);
		
		setSize(dimension);
		setResizable(false);
	    setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setVisible(true);
	}
	
	/** Verifica as senhas e as altera no banco de dados */
	private void action_update_password() {
		
		String firstKey  = new String(textSenhaOnce .getPassword());
		String secondKey = new String(textSenhaTwice.getPassword());
		
		if (firstKey.equals(secondKey)) {
			UsuarioDAO.changePassword(secondKey);
			dispose();
		}
		else
			AlertDialog.error("Atualizando senha","Senhas não conferem!");
		
	}

}
