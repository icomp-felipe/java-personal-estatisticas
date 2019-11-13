package eleicao.dados.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import eleicao.dados.model.*;
import eleicao.dados.utils.*;

/** Classe TelaLogin - cria um ambiente gráfico para o usuário fazer login no sistema
 *  @author Felipe André Souza da Silva 
 *  @version 2.00, 12/09/2014 */
public class TelaLogin extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textLogin;	private JLabel labelSenha;
	private JButton botaoEntrar, botaoLimpar, botaoSair;
	private JPasswordField textSenha;
	
	/** Construtor da classe TelaLogin - constrói a janela gráfica */
	public TelaLogin() {
		super("Tela de Login");
		setResizable(false);
		
		Font  dialg = GraphicsHelper.getFont();
		Color color = GraphicsHelper.getColor();
		
		Container container = getContentPane();
		container.setLayout(null);		
		
		Font fonte = new Font("Dialog", Font.BOLD, 20);
	
		ImageIcon icon = new ImageIcon(ResourceManager.getResource("img/login.png"));
		JLabel labelImagem = new JLabel(icon);
		labelImagem.setBounds(12, 12, 232, 235);
		
		JLabel labelLogin = new JLabel("Login:");
		labelLogin.setFont(fonte);
		labelLogin.setBounds(256, 75, 85, 24);
		
		textLogin = new JTextField();
		textLogin.setFont(dialg);
		textLogin.setForeground(color);
		textLogin.setToolTipText("Digite aqui seu login");
		textLogin.setBounds(341, 75, 193, 24);
		textLogin.setColumns(10);
		
		labelSenha = new JLabel("Senha:");
		labelSenha.setFont(fonte);
		labelSenha.setBounds(256, 134, 85, 24);
		
		textSenha = new JPasswordField();
		textSenha.setFont(dialg);
		textSenha.setForeground(color);
		textSenha.setToolTipText("Digite aqui sua senha");
		textSenha.setBounds(341, 134, 193, 24);
		textSenha.addKeyListener(new KeyAdapter() {    /** Adiciona uma ação quando o ENTER é pressionado */
		      public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER)
		        	botaoEntrar.doClick();
		      }
		});
				
		container.add(labelImagem);
		container.add(labelLogin);
		container.add(textLogin);
		container.add(labelSenha);
		container.add(textSenha);
		
		botaoEntrar = new JButton("Entrar");
		botaoEntrar.addActionListener(this);
		botaoEntrar.setBounds(449, 203, 85, 25);
		container.add(botaoEntrar);
		
		botaoLimpar = new JButton("Limpar");
		botaoLimpar.addActionListener(this);
		botaoLimpar.setBounds(352, 203, 85, 25);
		container.add(botaoLimpar);
		
		botaoSair = new JButton("Sair");
		botaoSair.addActionListener(this);
		botaoSair.setBounds(256, 203, 85, 25);
		getContentPane().add(botaoSair);
		
	    setSize(550,300);
	    this.setLocationRelativeTo(null); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/** Função que realiza o login no sistema */
	private void tryLogin() {
		String login = textLogin.getText();
		String senha = new String(textSenha.getPassword());
		Usuario usuario = new Usuario(login, senha);
		
		String user  = UsuarioDAO.tryLogin(usuario);
		
		if (user == null)
			AlertDialog.erro("Usuário e/ou senha inválidos!");
		else {
			parseCurrentKey(login,senha);
			new TelaInicial(user);
			dispose();
		}
	}
	
	/** Solicita a troca de senha caso esta ainda seja a padrão */
	private void parseCurrentKey(String login, String key) {
		if (login.equals("admin") && key.equals("admin")) {
			if (AlertDialog.dialog("É altamente recomendável que você troque sua senha\nDeseja fazer isso agora?") == 0)
				new TelaMudaSenha();
		}
	}
	
	/** Método para limpar os campos de texto da janela */
	private void limpaCampos() {
		textLogin.setText(null);
		textSenha.setText(null);
		textLogin.requestFocus();
	}
	
	/** Construtor da classe TelaLogin - Cria a janela */
	public static void main(String[] args) {
		new TelaLogin();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == botaoLimpar)
			limpaCampos();
		else if (source == botaoSair)
			dispose();
		else if (source == botaoEntrar)
			tryLogin();
	}
}
