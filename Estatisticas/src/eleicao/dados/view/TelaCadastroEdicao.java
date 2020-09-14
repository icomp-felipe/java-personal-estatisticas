package eleicao.dados.view;

import java.awt.*;
import org.jsoup.*;
import javax.swing.*;
import java.awt.event.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.phill.libs.GraphicsHelper;
import com.phill.libs.JPaintedPanel;
import com.phill.libs.ResourceManager;

import eleicao.dados.model.*;
import eleicao.dados.utils.*;

/** Classe TelaCadastro - contém a interface de cadastro de informações no sistema
 *  @author Felipe André Souza da Silva 
 *  @version 2.00, 16/09/2014 */
public class TelaCadastroEdicao extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final JPanel painelDados, painelObs, painelEndereco;
	
	private JTextField textNome, textEmail, textLogradouro, textNumero, textBairro;
	private JFormattedTextField textTelRes, textTelCel, textCEP, textCPF, textNasc;
	private JTextField textComplemento, textUF, textCidade;
	private JButton botaoSalvar, botaoSair, botaoLimpar, botaoConsultar;
	private JTextArea textObs;
	private Objeto velho;
	private boolean frameConsulta = false;
	
	
	private final ImageIcon loading = new ImageIcon(ResourceManager.getResource("img/loading.gif"));

	public static void main(String[] args) {
		new TelaCadastroEdicao();
	}
	
	/** Construtor da tela de edição - carrega os dados de um objeto para a interface
	 *  @wbp.parser.constructor */
	public TelaCadastroEdicao(Objeto objeto) {
		super(objeto == null ? "Cadastro de Dados" : "Edição de Dados");
		
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
		
		textCPF = new JFormattedTextField(helper.getMascara("###.###.###-##"));
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
		
		textTelRes = new JFormattedTextField(helper.getMascara("(##) ####-####"));
		textTelRes.setHorizontalAlignment(JFormattedTextField.CENTER);
		textTelRes.setFont(fonte);
		textTelRes.setForeground(color);
		textTelRes.setBounds(330, 60, 125, 25);
		painelDados.add(textTelRes);
		
		JLabel labelTelCel = new JLabel("Tel. Cel.:");
		labelTelCel.setHorizontalAlignment(JLabel.RIGHT);
		labelTelCel.setFont(fonte);
		labelTelCel.setBounds(465, 60, 65, 20);
		painelDados.add(labelTelCel);
		
		textTelCel = new JFormattedTextField(helper.getMascara("(##) #####-####"));
		textTelCel.setHorizontalAlignment(JFormattedTextField.CENTER);
		textTelCel.setForeground(color);
		textTelCel.setFont(fonte);
		textTelCel.setBounds(540, 60, 140, 25);
		painelDados.add(textTelCel);
		
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
		
		textNasc = new JFormattedTextField(helper.getMascara("##/##/####"));
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
		
		textCEP = new JFormattedTextField(helper.getMascara("##.###-###"));
		textCEP.setHorizontalAlignment(JFormattedTextField.CENTER);
		textCEP.setForeground(color);
		textCEP.setFont(fonte);
		textCEP.setBounds(550, 90, 90, 25);
		painelEndereco.add(textCEP);
		
		botaoConsultar = new JButton(selectIcon);
		botaoConsultar.addActionListener(this);
		botaoConsultar.setToolTipText("Buscar CEP online");
		botaoConsultar.setBounds(648, 90, 30, 25);
		painelEndereco.add(botaoConsultar);
		
		// Painel de Observações
		painelObs = new JPanel();
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
		botaoLimpar.addActionListener(this);
		botaoLimpar.setBounds(632, 418, 30, 25);
		mainPanel.add(botaoLimpar);
		
		botaoSalvar = new JButton(saveIcon);
		botaoSalvar.setToolTipText("Salvar dados");
		botaoSalvar.addActionListener(this);
		botaoSalvar.setBounds(674, 418, 30, 25);
		mainPanel.add(botaoSalvar);
		
		JLabel textOBS = new JLabel();
		textOBS.setFont(fonte);
		textOBS.setBounds(12, 418, 560, 25);
		mainPanel.add(textOBS);
		
		setSize(dimension);
		setResizable(false);
	    setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setVisible(true);
		
		
		
		/*
		
		velho = objeto;
		frameConsulta = true;
		
		textNome.setText(objeto.getNome());
		textEmail.setText(objeto.getEmail());
		textLogradouro.setText(objeto.getLogradouro());
		textNumero.setText(objeto.getNumCasa());
		textBairro.setText(objeto.getBairro());
		
		textTelRes.setValue(objeto.getContato01());
		textTelCel.setValue(objeto.getContato02());
		textCEP.setValue(objeto.getCEP());
		textCPF.setValue(objeto.getCPF());
		textNasc.setText(objeto.getNascimento());
		
		textComplemento.setText(objeto.getComplemento());
		textUF.setText(objeto.getUF());
		textCidade.setText(objeto.getCidade());
		
		textObs.setText(objeto.getDescricao());
		
		textCPF.requestFocus();*/
	}
	
	public TelaCadastroEdicao() {
		this(null);
	}

	/********************* Bloco de Funcionalidades da Interface Gráfica *************************/
	
	/** Inicia a consulta de CEP */
	private void consultar() {
		@SuppressWarnings("rawtypes")
		SwingWorker worker = new SwingWorker() {
			   protected Void doInBackground() throws Exception {
				   start();
				   return null;
			   }
		};
		worker.execute();
	}
	
	/** Limpa os dados da tela */
	private void limpar() {
		limpaCampos(painelDados);
		limpaCampos(painelEndereco);
		textObs.setText(null);
		textNome.requestFocus();
	}
	
	/** Salva as informações da interface no banco de dados */
	private void salvar() {
		try {
			boolean status;
			String middle;
			Objeto novo = getScreenData();
			if (novo == null)
				return;
			
			if (frameConsulta) {
				status = ObjetoDAO.atualizaObjeto(velho, novo);
				middle = " atualizadas ";
			}
			else {
				status = ObjetoDAO.adicionaObjeto(novo);
				middle = " cadastradas ";
				limpar();
			}
			
			if (status)
				AlertDialog.informativo("Informações"+middle+"com sucesso!");
			else
				AlertDialog.erro("Falha ao cadastrar informações!");
		}
		catch (Exception exception) {
			AlertDialog.erro("Falha ao salvar dados!\nVerifique se todos os campos estão preenchidos corretamente!");
		}
	}
	
	/******************** Métodos Auxiliares ao Controle das Funções *****************************/
	
	/** Recupera as informações da interface para o objeto de dados do sistema */
	private Objeto getScreenData() throws Exception {
		String cpf = "", tel1 = "", tel2 = "", nasc = "", cep = "";
		
		String nome   = textNome.getText();
		String logra  = textLogradouro.getText();
		String numer  = textNumero.getText();
		String bairro = textBairro.getText();
		String cidade = textCidade.getText();
		String uf	  = textUF.getText();
		String compl  = textComplemento.getText();
		String email  = textEmail.getText();
		String obsvs  = textObs.getText();
		
		try { cpf = textCPF.getValue().toString(); }
		catch (Exception exception) { }
		
		try { tel1 = textTelRes.getValue().toString(); }
		catch (Exception exception) { }
		
		try { tel2   = textTelCel.getValue().toString(); }
		catch (Exception exception) { }
		
		try { cep = textCEP.getValue().toString(); }
		catch (Exception exception) { }
		
		try { nasc = textNasc.getText(); }
		catch (Exception exception) { }
		
		if (!CPFParser.parse(cpf)) { AlertDialog.erro("Digite um CPF válido!"); return null; }
		
		Objeto objeto = new Objeto(cpf, nome, nasc, logra, compl, numer, bairro, cidade, uf, cep, tel1, tel2, email, obsvs);
		return objeto;
	}
	
	/** Limpa os campos de texto de determinado painel de componentes gráficos */
	private void limpaCampos(JPanel painel) {
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
	
	/** Prepara a consulta de CEP */
	private void start() {
		String CEP = "";
		try { CEP = textCEP.getValue().toString(); }
		catch (Exception exception) { AlertDialog.erro("Digite um CEP válido!"); }
		if (CEP.equals("")) return;
		getEnderecoCompleto(CEP);
	}
	
	/** Recupera da internet o endereço completo com base em determinado CEP */
	private void getEnderecoCompleto(String CEP) {
		try {
			Document doc = Jsoup.connect("http://www.qualocep.com/busca-cep/" + CEP).timeout(120000).get();
			String logradouro = doc.select("span[itemprop=streetAddress]").text();
			String bairro 	  = getBairro(doc.select("td:gt(1)"));
			String cidade	  = doc.select("span[itemprop=addressLocality]").text();
			String estado	  = doc.select("span[itemprop=addressRegion]").text();
			
			if (logradouro.equals(""))	return;
			
			textLogradouro.setText(logradouro);
			textBairro.setText(bairro);
			textCidade.setText(cidade);
			textUF.setText(estado);
		}
		catch (Exception exception) {
			AlertDialog.erro("Não foi possível recuperar o endereço!\nVerifique sua conexão com a Internet");
		}
	}
	
	/** Recupera um bairro do site */
	private String getBairro(Elements urlPesquisa) {
    	for (Element urlBairro: urlPesquisa)
            return urlBairro.text();
        return null;
    }
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == botaoSalvar)
			salvar();
		else if (source == botaoLimpar)
			limpar();
		else if (source == botaoConsultar)
			consultar();
	}
}
