package eleicao.dados.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

import com.phill.libs.*;
import com.phill.libs.br.*;
import com.phill.libs.ui.*;
import com.phill.libs.time.*;

import eleicao.dados.dao.ClienteDAO;
import eleicao.dados.model.*;

/** Classe TelaCadastro - contém a interface de cadastro de informações no sistema
 *  @author Felipe André Souza da Silva 
 *  @version 2.00, 16/09/2014 */
public class TelaCadastroEdicao extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final JLabel labelBuscaCEP;
	private final JPanel painelDados, painelEndereco;
	private final CPFTextField textCPF;
	
	private JTextField textNome, textEmail, textLogradouro, textNumero, textBairro;
	private JFormattedTextField textFixo, textCel, textCEP, textNasc;
	private JTextField textComplemento, textUF, textCidade;
	private JButton botaoSalvar, botaoSair, botaoLimpar, botaoConsultar;
	private JTextArea textObs;
	
	private final Cliente dado;
	
	private final ImageIcon loading = new ImageIcon(ResourceManager.getResource("img/loading.gif"));

	public static void main(String[] args) {
		new TelaCadastroEdicao();
	}
	
	/** Construtor da tela de edição - carrega os dados de um objeto para a interface
	 *  @wbp.parser.constructor */
	public TelaCadastroEdicao(Cliente dado) {
		super(dado.isEmpty() ? "Cadastro de Dados" : "Edição de Dados");
		
		this.dado = dado;
		
		// Inicializando atributos gráficos
		GraphicsHelper helper = GraphicsHelper.getInstance();
		GraphicsHelper.setFrameIcon(this,"img/logo.png");
		
		Dimension dimension = new Dimension(720,480);
		JPanel    mainPanel = new JPaintedPanel("img/background.png",dimension);
		
		Icon selectIcon = ResourceManager.getResizedIcon("icon/zoom.png",20,20);
		Icon exitIcon  = ResourceManager.getResizedIcon("icon/shutdown.png",20,20);
		Icon clearIcon = ResourceManager.getResizedIcon("icon/clear.png",20,20);
		Icon saveIcon  = ResourceManager.getResizedIcon("icon/save.png",20,20);
				
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
		
		textNome = new JTextField();
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
		textCPF.setFont(fonte);
		textCPF.setForeground(color);
		textCPF.setBounds(70, 60, 120, 25);
		painelDados.add(textCPF);
		
		JLabel labelTelRes = new JLabel("Tel. Fixo:");
		labelTelRes.setHorizontalAlignment(JLabel.RIGHT);
		labelTelRes.setFont(fonte);
		labelTelRes.setBounds(250, 60, 70, 20);
		painelDados.add(labelTelRes);
		
		textFixo = new JFormattedTextField(helper.getMask("(##) ####-####"));
		textFixo.setHorizontalAlignment(JFormattedTextField.CENTER);
		textFixo.setFont(fonte);
		textFixo.setForeground(color);
		textFixo.setBounds(330, 60, 125, 25);
		painelDados.add(textFixo);
		
		JLabel labelTelCel = new JLabel("Tel. Cel.:");
		labelTelCel.setHorizontalAlignment(JLabel.RIGHT);
		labelTelCel.setFont(fonte);
		labelTelCel.setBounds(465, 60, 65, 20);
		painelDados.add(labelTelCel);
		
		textCel = new JFormattedTextField(helper.getMask("(##) #####-####"));
		textCel.setHorizontalAlignment(JFormattedTextField.CENTER);
		textCel.setForeground(color);
		textCel.setFont(fonte);
		textCel.setBounds(540, 60, 140, 25);
		painelDados.add(textCel);
		
		JLabel labelEmail = new JLabel("e-mail:");
		labelEmail.setHorizontalAlignment(JLabel.RIGHT);
		labelEmail.setFont(fonte);
		labelEmail.setBounds(12, 90, 50, 20);
		painelDados.add(labelEmail);
		
		textEmail = new JTextField();
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
		
		textNasc = new JFormattedTextField(helper.getMask("##/##/####"));
		textNasc.setHorizontalAlignment(JFormattedTextField.CENTER);
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
		
		textLogradouro = new JTextField();
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
		
		textNumero = new JTextField();
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
		
		textBairro = new JTextField();
		textBairro.setFont(fonte);
		textBairro.setForeground(color);
		textBairro.setBounds(75, 60, 225, 25);
		textBairro.setColumns(10);
		painelEndereco.add(textBairro);
		
		JLabel labelCidade = new JLabel("Cidade:");
		labelCidade.setFont(fonte);
		labelCidade.setBounds(310, 60, 70, 20);
		painelEndereco.add(labelCidade);
		
		textCidade = new JTextField();
		textCidade.setFont(fonte);
		textCidade.setForeground(color);
		textCidade.setColumns(10);
		textCidade.setBounds(380, 60, 170, 25);
		painelEndereco.add(textCidade);
		
		JLabel labelComplemento = new JLabel("Compl.:");
		labelComplemento.setFont(fonte);
		labelComplemento.setBounds(12, 90, 65, 20);
		painelEndereco.add(labelComplemento);
		
		textComplemento = new JTextField();
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
		
		textUF = new JTextField();
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
		
		textCEP = new JFormattedTextField(helper.getMask("##.###-###"));
		textCEP.setHorizontalAlignment(JFormattedTextField.CENTER);
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
		botaoLimpar.addActionListener((event) -> action_clear());
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
	

	public TelaCadastroEdicao() {
		this(new Cliente());
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
	
	/** Limpa os dados da tela. */
	private void action_clear() {
		
		int choice = AlertDialog.dialog("Deseja mesmo limpar\ntodos os dados da tela?");
		
		if (choice == AlertDialog.OK_OPTION) {
			
			util_clear_panel(painelDados);
			util_clear_panel(painelEndereco);
			
			textObs .setText(null);
			textNome.requestFocus();
			
		}
		
	}
	
	private void action_save() {
		
		final String title = "Salvando alterações";
		
		// Recuperando os dados da tela
		dado.setNome      (textNome.getText().trim());
		dado.setCPF       (textCPF .getCPF(true));
		dado.setFixo      (StringUtils.extractNumbers(textFixo.getText()));
		dado.setCelular   (StringUtils.extractNumbers(textCel.getText()));
		dado.setEmail     (textEmail.getText().trim());
		dado.setNascimento(textNasc .getText());
		
		dado.setLogradouro (textLogradouro .getText().trim());
		dado.setNumero     (textNumero     .getText().trim());
		dado.setBairro     (textBairro     .getText().trim());
		dado.setCidade     (textCidade     .getText().trim());
		dado.setUF         (textUF         .getText().trim());
		dado.setComplemento(textComplemento.getText().trim());
		dado.setCEP        (StringUtils.extractNumbers(textCEP.getText()));
		
		dado.setObs(textObs.getText().trim());
		
		int choice = AlertDialog.dialog(title,"Deseja salvar as alterações?");
		
		if (choice == AlertDialog.OK_OPTION) {
			
			try {
				
				ClienteDAO.commit(dado);
				AlertDialog.info(title, "Dados salvos com sucesso");
				
			}
			catch (SQLException exception) {
				exception.printStackTrace();
				AlertDialog.error(title, "Falha ao salvar alterações");
			}
			
		}
		
	}
	
	/******************** Métodos Auxiliares ao Controle das Funções *****************************/
	
	/** Limpa os campos de texto de determinado painel de componentes gráficos. */
	private void util_clear_panel(JPanel painel) {
		
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
	
	private void util_load_data() {
		
		textNome .setText (this.dado.getNome());
		textCPF  .setValue(this.dado.getCPF ());
		textFixo .setValue(this.dado.getFixo());
		textCel  .setValue(this.dado.getCelular());
		textEmail.setText (this.dado.getEmail  ());
		textNasc .setText (this.dado.getNascimento(TimeFormatter.AWT_DATE));
		
		textLogradouro .setText (this.dado.getLogradouro());
		textNumero     .setText (this.dado.getNumero());
		textBairro     .setText (this.dado.getBairro());
		textCidade     .setText (this.dado.getCidade());
		textUF         .setText (this.dado.getUF ());
		textComplemento.setText (this.dado.getComplemento());
		textCEP        .setValue(this.dado.getCEP());
		
		textObs.setText(this.dado.getObs());
		
		textNome.requestFocus();
		
	}
	
}
