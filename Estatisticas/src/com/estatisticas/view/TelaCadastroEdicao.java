package com.estatisticas.view;

import java.io.*;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import com.phill.libs.*;
import com.phill.libs.br.*;
import com.phill.libs.ui.*;
import com.phill.libs.time.*;

import com.estatisticas.dao.*;
import com.estatisticas.model.*;

/** Contém a interface de cadastro de informações no sistema.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 2.6, 21/09/2020 */
public class TelaCadastroEdicao extends JFrame {

	// Serial
	private static final long serialVersionUID = -8936733268274359724L;
	
	// Atributos gráficos
	private final JLabel labelBuscaCEP;
	private final JPanel painelDados, painelEndereco;
	private final CPFTextField textCPF;
	private final JTextField textNome, textEmail, textLogradouro, textNumero, textBairro;
	private final JFormattedTextField textFixo, textCel, textCEP, textNasc;
	private final JTextField textComplemento, textUF, textCidade;
	private final JButton botaoSalvar, botaoSair, botaoLimpar, botaoConsultar;
	private final JTextArea textObs;
	private final ImageIcon loading = new ImageIcon(ResourceManager.getResource("img/loading.gif"));
	private final Color yl_dk = new Color(0xE9EF84);

	// Atributos dinâmicos
	private Cliente cliente;
	
	/** Construtor utilizado para a criação de um novo cliente do sistema. */
	public TelaCadastroEdicao() {
		this(new Cliente());
	}
	
	/** Construtor utilizado para edição de um cliente já existente.
	 *  @param cliente - cliente do sistema
	 *  @wbp.parser.constructor */
	public TelaCadastroEdicao(final Cliente cliente) {
		super(cliente.isEmpty() ? "Cadastro de Dados" : "Edição de Dados");
		
		this.cliente = cliente;
		
		// Inicializando atributos gráficos
		GraphicsHelper helper = GraphicsHelper.getInstance();
		GraphicsHelper.setFrameIcon(this,"img/logo.png");
		
		Dimension dimension = new Dimension(740,495);
		JPanel    mainPanel = new JPaintedPanel("img/background.png",dimension);
		
		// Recuperando ícones
		Icon selectIcon = ResourceManager.getIcon("icon/zoom.png",20,20);
		Icon exitIcon  = ResourceManager.getIcon("icon/shutdown.png",20,20);
		Icon clearIcon = ResourceManager.getIcon("icon/clear.png",20,20);
		Icon saveIcon  = ResourceManager.getIcon("icon/save.png",20,20);
				
		mainPanel.setLayout(null);
		setContentPane(mainPanel);
		
		// Recuperando fontes e cores
		Font  fonte = helper.getFont ();
		Color color = helper.getColor();
		
		// Painel de Dados Pessoais
		painelDados = new JPanel();
		painelDados.setOpaque(false);
		painelDados.setBorder(helper.getTitledBorder("Dados Pessoais"));
		painelDados.setBounds(12, 12, 692, 125);
		painelDados.setLayout(null);
		mainPanel.add(painelDados);
		
		JLabel labelNome = new JLabel("Nome:");
		labelNome.setHorizontalAlignment(JLabel.RIGHT);
		labelNome.setFont(fonte);
		labelNome.setBounds(12, 30, 50, 20);
		painelDados.add(labelNome);
		
		textNome = new JTextFieldBounded(100);
		textNome.getDocument().addDocumentListener((DocumentChangeListener) event -> listener_name());
		textNome.setFont(fonte);
		textNome.setForeground(color);
		textNome.setBounds(70, 30, 610, 25);
		painelDados.add(textNome);
		textNome.setColumns(10);
		
		JLabel labelCPF = new JLabel("CPF:");
		labelCPF.setHorizontalAlignment(JLabel.RIGHT);
		labelCPF.setFont(fonte);
		labelCPF.setBounds(12, 60, 50, 20);
		painelDados.add(labelCPF);
		
		textCPF = new CPFTextField();
		textCPF.setHorizontalAlignment(JFormattedTextField.CENTER);
		textCPF.setFocusLostBehavior(JFormattedTextField.PERSIST);
		textCPF.setFont(fonte);
		textCPF.setForeground(color);
		textCPF.setBounds(70, 60, 120, 25);
		painelDados.add(textCPF);
		
		JLabel labelTelRes = new JLabel("Tel. Fixo:");
		labelTelRes.setHorizontalAlignment(JLabel.RIGHT);
		labelTelRes.setFont(fonte);
		labelTelRes.setBounds(250, 60, 70, 20);
		painelDados.add(labelTelRes);
		
		textFixo = new JFormattedTextField(GraphicsHelper.getMask("(##) ####-####"));
		textFixo.setHorizontalAlignment(JFormattedTextField.CENTER);
		textFixo.setFocusLostBehavior(JFormattedTextField.PERSIST);
		textFixo.setFont(fonte);
		textFixo.setForeground(color);
		textFixo.setBounds(330, 60, 125, 25);
		painelDados.add(textFixo);
		
		JLabel labelTelCel = new JLabel("Tel. Cel.:");
		labelTelCel.setHorizontalAlignment(JLabel.RIGHT);
		labelTelCel.setFont(fonte);
		labelTelCel.setBounds(465, 60, 65, 20);
		painelDados.add(labelTelCel);
		
		textCel = new JFormattedTextField(GraphicsHelper.getMask("(##) #####-####"));
		textCel.setHorizontalAlignment(JFormattedTextField.CENTER);
		textCel.setFocusLostBehavior(JFormattedTextField.PERSIST);
		textCel.setForeground(color);
		textCel.setFont(fonte);
		textCel.setBounds(540, 60, 140, 25);
		painelDados.add(textCel);
		
		JLabel labelEmail = new JLabel("e-mail:");
		labelEmail.setHorizontalAlignment(JLabel.RIGHT);
		labelEmail.setFont(fonte);
		labelEmail.setBounds(12, 90, 50, 20);
		painelDados.add(labelEmail);
		
		textEmail = new JTextFieldBounded(50);
		textEmail.setFont(fonte);
		textEmail.setForeground(color);
		textEmail.setBounds(70, 90, 385, 25);
		painelDados.add(textEmail);
		textEmail.setColumns(10);
		
		JLabel labelNasc = new JLabel("Nascim.:");
		labelNasc.setHorizontalAlignment(JLabel.RIGHT);
		labelNasc.setFont(fonte);
		labelNasc.setBounds(465, 90, 65, 20);
		painelDados.add(labelNasc);
		
		textNasc = new JFormattedTextField(GraphicsHelper.getMask("##/##/####"));
		textNasc.setHorizontalAlignment(JFormattedTextField.CENTER);
		textNasc.setFocusLostBehavior(JFormattedTextField.PERSIST);
		textNasc.setForeground(color);
		textNasc.setFont(fonte);
		textNasc.setBounds(540, 90, 140, 25);
		painelDados.add(textNasc);
		
		// Painel de Endereço
		painelEndereco = new JPanel();
		painelEndereco.setOpaque(false);
		painelEndereco.setBorder(helper.getTitledBorder("Endereço"));
		painelEndereco.setBounds(12, 140, 692, 125);
		painelEndereco.setLayout(null);
		mainPanel.add(painelEndereco);
		
		JLabel labelLogradouro = new JLabel("Lograd.:");
		labelLogradouro.setFont(fonte);
		labelLogradouro.setBounds(12, 30, 65, 20);
		painelEndereco.add(labelLogradouro);
		
		textLogradouro = new JTextFieldBounded(100);
		textLogradouro.setFont(fonte);
		textLogradouro.setForeground(color);
		textLogradouro.setBounds(75, 30, 475, 25);
		textLogradouro.setColumns(10);
		painelEndereco.add(textLogradouro);
		
		JLabel labelNumero = new JLabel("Núm.:");
		labelNumero.setHorizontalAlignment(JLabel.RIGHT);
		labelNumero.setFont(fonte);
		labelNumero.setBounds(565, 30, 45, 20);
		painelEndereco.add(labelNumero);
		
		textNumero = new JTextFieldBounded(10);
		textNumero.setHorizontalAlignment(JTextField.CENTER);
		textNumero.setFont(fonte);
		textNumero.setForeground(color);
		textNumero.setBounds(618, 30, 60, 25);
		textNumero.setColumns(10);
		painelEndereco.add(textNumero);
		
		JLabel labelBairro = new JLabel("Bairro:");
		labelBairro.setFont(fonte);
		labelBairro.setBounds(12, 60, 65, 20);
		painelEndereco.add(labelBairro);
		
		textBairro = new JTextFieldBounded(100);
		textBairro.setFont(fonte);
		textBairro.setForeground(color);
		textBairro.setBounds(75, 60, 225, 25);
		textBairro.setColumns(10);
		painelEndereco.add(textBairro);
		
		JLabel labelCidade = new JLabel("Cidade:");
		labelCidade.setFont(fonte);
		labelCidade.setBounds(310, 60, 70, 20);
		painelEndereco.add(labelCidade);
		
		textCidade = new JTextFieldBounded(70);
		textCidade.setFont(fonte);
		textCidade.setForeground(color);
		textCidade.setColumns(10);
		textCidade.setBounds(380, 60, 170, 25);
		painelEndereco.add(textCidade);
		
		JLabel labelComplemento = new JLabel("Compl.:");
		labelComplemento.setFont(fonte);
		labelComplemento.setBounds(12, 90, 65, 20);
		painelEndereco.add(labelComplemento);
		
		textComplemento = new JTextFieldBounded(100);
		textComplemento.setFont(fonte);
		textComplemento.setForeground(color);
		textComplemento.setBounds(75, 90, 420, 25);
		textComplemento.setColumns(10);
		painelEndereco.add(textComplemento);
		
		JLabel labelUF = new JLabel("UF:");
		labelUF.setHorizontalAlignment(JLabel.RIGHT);
		labelUF.setFont(fonte);
		labelUF.setBounds(565, 60, 45, 20);
		painelEndereco.add(labelUF);
		
		textUF = new JTextFieldBounded(2);
		textUF.setHorizontalAlignment(JTextField.CENTER);
		textUF.setFont(fonte);
		textUF.setForeground(color);
		textUF.setBounds(618, 60, 60, 25);
		textUF.setColumns(10);
		painelEndereco.add(textUF);
		
		JLabel labelCEP = new JLabel("CEP:");
		labelCEP.setFont(fonte);
		labelCEP.setBounds(505, 90, 40, 20);
		painelEndereco.add(labelCEP);
		
		textCEP = new JFormattedTextField(GraphicsHelper.getMask("##.###-###"));
		textCEP.setHorizontalAlignment(JFormattedTextField.CENTER);
		textCel.setFocusLostBehavior(JFormattedTextField.PERSIST);
		textCEP.setForeground(color);
		textCEP.setFont(fonte);
		textCEP.setBounds(550, 90, 90, 25);
		painelEndereco.add(textCEP);
		
		botaoConsultar = new JButton(selectIcon);
		botaoConsultar.addActionListener((event) -> action_busca_cep(event));
		botaoConsultar.setToolTipText("Buscar CEP online");
		botaoConsultar.setBounds(648, 90, 30, 25);
		painelEndereco.add(botaoConsultar);
		
		// Painel de Observações
		JPanel painelObs = new JPanel();
		painelObs.setOpaque(false);
		painelObs.setBorder(helper.getTitledBorder("Observações"));
		painelObs.setBounds(12, 270, 692, 140);
		painelObs.setLayout(null);
		mainPanel.add(painelObs);
		
		textObs = new JTextArea();
		textObs.setLineWrap(true);
		textObs.setFont(fonte);
		textObs.setForeground(color);
		
		JScrollPane scrollObs = new JScrollPane(textObs);
		scrollObs.setBounds(12, 24, 668, 104);
		painelObs.add(scrollObs);
		
		botaoSair = new JButton(exitIcon);
		botaoSair.setToolTipText("Sair do sistema");
		botaoSair.addActionListener((event) -> dispose());
		botaoSair.setBounds(590, 418, 30, 25);
		mainPanel.add(botaoSair);
		
		botaoLimpar = new JButton(clearIcon);
		botaoLimpar.setToolTipText("Limpar dados da tela");
		botaoLimpar.addActionListener((event) -> action_clear(false));
		botaoLimpar.setBounds(632, 418, 30, 25);
		mainPanel.add(botaoLimpar);
		
		botaoSalvar = new JButton(saveIcon);
		botaoSalvar.setToolTipText("Salvar dados");
		botaoSalvar.addActionListener((event) -> action_save());
		botaoSalvar.setBounds(674, 418, 30, 25);
		mainPanel.add(botaoSalvar);
		
		labelBuscaCEP = new JLabel("Buscando CEP online...", loading, JLabel.LEADING);
		labelBuscaCEP.setFont(fonte);
		labelBuscaCEP.setBounds(12, 418, 560, 25);
		labelBuscaCEP.setVisible(false);
		mainPanel.add(labelBuscaCEP);
		
		setSize(dimension);
		setResizable(false);
	    setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		util_load_data();
		
		setVisible(true);
		
	}
	
	/********************* Bloco de Funcionalidades da Interface Gráfica *************************/
	
	/** Busca na internet o endereço referente ao CEP informado e o preenche nos campos da tela.
	 *  @param event - botão que gerou o evento (este é ocultado quando o método está em execução para evitar redundâncias de thread) */
	private void action_busca_cep(ActionEvent event) {
		
		// Recuperando o botão gerador do evento
		JButton button = (JButton) event.getSource();
		
		try {
			
			// Recuperando o CEP. Caso seja um CEP inválido, é lançado um NullPointerException aqui.
			String cep = textCEP.getValue().toString();
			
			// Como o método 'EnderecoDAO::get' é bloqueante, o coloco dentro de uma thread para não travar a tela
			Runnable job = () -> {
				
				try {
					
					// Bloqueando o botão de busca e exibindo o label
					SwingUtilities.invokeLater(() -> { labelBuscaCEP.setVisible(true); button.setEnabled(false); });
					
					// Recuperando o CEP online
					Endereco endereco = EnderecoDAO.get(cep);
					
					// Preenchendo os dados na view
					textLogradouro.setText(endereco.getLogradouro());
					textBairro    .setText(endereco.getBairro    ());
					textCidade    .setText(endereco.getCidade    ());
					textUF        .setText(endereco.getUF        ());
					
				}
				
				// NullPointerException pode ser gerado quando o endereço não foi encontrado via 'EnderecoDAO::get'
				// IOException ocorre quando há algum erro de conexão à internet
				catch (NullPointerException | IOException exception) { }
				finally { SwingUtilities.invokeLater(() -> { labelBuscaCEP.setVisible(false); button.setEnabled(true); }); }
				
			};
			
			// Iniciando a thread
			Thread buscaCEP = new Thread(job);
			buscaCEP.setName("Thread do busca CEP online");
			buscaCEP.start();
			
		}
		
		// Um NullPointerException é gerado quando o cep não contém todos os dígitos
		catch (NullPointerException exception) { }
		
	}
	
	/** Limpa os dados da tela.
	 *  @param skipDialog - ignora ou não o diálogo de confirmação */
	private void action_clear(final boolean skipDialog) {
		
		if (!skipDialog) {
			
			int choice = AlertDialog.dialog("Deseja mesmo limpar\ntodos os dados da tela?");
			if (choice != AlertDialog.OK_OPTION)
				return;
			
		}
			
		util_clear_panel(painelDados);
		util_clear_panel(painelEndereco);
			
		textObs .setText(null);
		textNome.requestFocus();
			
		this.cliente = new Cliente();
			
	}
	
	/** Salva as alterações de dados na base. */
	private void action_save() {
		
		final String title = "Salvando alterações";
		
		// Recuperando os dados da tela
		cliente.setNome      (textNome.getText().trim());
		cliente.setCPF       (textCPF .getCPF(true));
		cliente.setFixo      (StringUtils.extractNumbers(textFixo.getText()));
		cliente.setCelular   (StringUtils.extractNumbers(textCel.getText()));
		cliente.setEmail     (textEmail.getText().trim());
		cliente.setNascimento(textNasc .getText());
		
		cliente.setLogradouro (textLogradouro .getText().trim());
		cliente.setNumero     (textNumero     .getText().trim());
		cliente.setBairro     (textBairro     .getText().trim());
		cliente.setCidade     (textCidade     .getText().trim());
		cliente.setUF         (textUF         .getText().trim());
		cliente.setComplemento(textComplemento.getText().trim());
		cliente.setCEP        (StringUtils.extractNumbers(textCEP.getText()));
		
		cliente.setObservacoes(textObs.getText().trim());
		
		int choice = AlertDialog.dialog(title,"Deseja salvar as alterações?");
		
		if (choice == AlertDialog.OK_OPTION) {
			
			try {
				
				ClienteDAO.commit(cliente);
				AlertDialog.info(title, "Dados salvos com sucesso");
				
				// Se a tela era de criação, limpo os dados para uma nova inserção
				if (cliente.isEmpty())
					action_clear(true);
				
			}
			catch (SQLException exception) {
				exception.printStackTrace();
				AlertDialog.error(title, "Falha ao salvar alterações");
			}
			
		}
		
	}
	
	/*********************************** Bloco de Listeners **************************************/
	
	/** Verifica se o nome digitado já existe no sistema. Apenas na tela de cadastro. */
	private void listener_name() {
		
		// Se é uma tela de cadastro...
		if (this.cliente.isEmpty()) {
			
			// ...recupero o nome digitado, ...
			final String nome = textNome.getText().trim();
			
			// ...verifico duplicidade no sistema e...
			boolean duplicado = ClienteDAO.exists(nome);
			
			// ...atualizo a tela
			textNome.setBackground(duplicado ? this.yl_dk : Color.WHITE);
			
		}
		
	}
	
	/******************** Métodos Auxiliares ao Controle das Funções *****************************/
	
	/** Limpa os campos de texto de determinado painel de componentes gráficos.
	 *  @param painel - painel de componentes gráficos */
	private void util_clear_panel(final JPanel painel) {
		
		for (int i=0; i< painel.getComponentCount(); i++) {
			
			if (painel.getComponent(i) instanceof JTextField) {
				JTextField component = (JTextField) painel.getComponent(i);
				component.setText(null);
			}
			else if (painel.getComponent(i) instanceof JFormattedTextField) {
				JFormattedTextField component = (JFormattedTextField) painel.getComponent(i);
				component.setText(null);
			}
			
		}
		
	}
	
	/** Carrega os dados do cliente para os campos da janela. */
	private void util_load_data() {
		
		textNome .setText (this.cliente.getNome());
		textCPF  .setValue(this.cliente.getCPF ());
		textFixo .setValue(this.cliente.getFixo());
		textCel  .setValue(this.cliente.getCelular());
		textEmail.setText (this.cliente.getEmail  ());
		textNasc .setText (this.cliente.getNascimento(PhillsDateFormatter.AWT_DATE));
		
		textLogradouro .setText (this.cliente.getLogradouro ());
		textNumero     .setText (this.cliente.getNumero     ());
		textBairro     .setText (this.cliente.getBairro     ());
		textCidade     .setText (this.cliente.getCidade     ());
		textUF         .setText (this.cliente.getUF         ());
		textComplemento.setText (this.cliente.getComplemento());
		textCEP        .setValue(this.cliente.getCEP        ());
		
		textObs.setText(this.cliente.getObservacoes());
		
		textNome.requestFocus();
		
	}
	
}
