package eleicao.dados.view;

import java.awt.*;
import org.jsoup.*;
import javax.swing.*;
import java.awt.event.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import eleicao.dados.model.*;
import eleicao.dados.utils.*;

/** Classe TelaCadastro - cont�m a interface de cadastro de informa��es no sistema
 *  @author Felipe Andr� Souza da Silva 
 *  @version 2.00, 16/09/2014 */
public class TelaCadastroEdicao extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textNome, textEmail, textLogradouro, textNumero, textBairro;
	private JFormattedTextField textTelRes, textTelCel, textCEP, textCPF, textNasc;
	private JTextField textComplemento, textUF, textCidade;
	private JButton botaoSalvar, botaoSair, botaoLimpar, botaoConsultar;
	private JTextArea textObs;
	private Objeto velho;
	private boolean frameConsulta = false;
	private JPanel painelDados, painelObs, painelEndereco;

	/** Construtor da tela de edi��o - carrega os dados de um objeto para a interface */
	public TelaCadastroEdicao(Objeto objeto) {
		this("Consulta/Edi��o");
		
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
		
		textCPF.requestFocus();
	}
	
	/** Construtor da tela de cadastro - cria uma interface de cadastro de informa��es
	 *  @wbp.parser.constructor */
	public TelaCadastroEdicao(String usuario) {
		super("Tela de Cadastro: " + usuario);
		
		Font fonte  = GraphicsHelper.getFont();
		Color color = GraphicsHelper.getColor();
		
		setSize(720,480);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		textObs = new JTextArea();
		textObs.setLineWrap(true);
		textObs.setFont(fonte);
		textObs.setForeground(color);
		
		painelDados = new JPanel();
		painelDados.setBorder(GraphicsHelper.getTitledBorder("Dados Pessoais"));
		painelDados.setBounds(12, 12, 692, 121);
		getContentPane().add(painelDados);
		painelDados.setLayout(null);
		
		JLabel labelNome = new JLabel("Nome:");
		labelNome.setFont(fonte);
		labelNome.setBounds(12, 30, 70, 15);
		painelDados.add(labelNome);
		
		textNome = new JTextField();
		textNome.setFont(fonte);
		textNome.setForeground(color);
		textNome.setBounds(67, 29, 613, 20);
		painelDados.add(textNome);
		textNome.setColumns(10);
		
		JLabel labelCPF = new JLabel("CPF:");
		labelCPF.setFont(fonte);
		labelCPF.setBounds(12, 61, 70, 15);
		painelDados.add(labelCPF);
		
		textCPF = new JFormattedTextField(GraphicsHelper.getMascara("###.###.###-##"));
		textCPF.setFont(fonte);
		textCPF.setForeground(color);
		textCPF.setBounds(67, 58, 118, 20);
		painelDados.add(textCPF);
		
		JLabel labelTelRes = new JLabel("Tel. Res.:");
		labelTelRes.setFont(fonte);
		labelTelRes.setBounds(229, 61, 70, 15);
		painelDados.add(labelTelRes);
		
		textTelRes = new JFormattedTextField(GraphicsHelper.getMascara("(##) ####-####"));
		textTelRes.setFont(fonte);
		textTelRes.setForeground(color);
		textTelRes.setBounds(309, 58, 124, 20);
		painelDados.add(textTelRes);
		
		JLabel labelTelCel = new JLabel("Tel. Cel.:");
		labelTelCel.setFont(fonte);
		labelTelCel.setBounds(464, 61, 70, 15);
		painelDados.add(labelTelCel);
		
		textTelCel = new JFormattedTextField(GraphicsHelper.getMascara("(##) #####-####"));
		textTelCel.setForeground(color);
		textTelCel.setFont(fonte);
		textTelCel.setBounds(539, 58, 141, 20);
		painelDados.add(textTelCel);
		
		JLabel labelEmail = new JLabel("Email:");
		labelEmail.setFont(fonte);
		labelEmail.setBounds(12, 90, 70, 15);
		painelDados.add(labelEmail);
		
		textEmail = new JTextField();
		textEmail.setFont(fonte);
		textEmail.setForeground(color);
		textEmail.setBounds(67, 89, 366, 20);
		painelDados.add(textEmail);
		textEmail.setColumns(10);
		
		JLabel labelNasc = new JLabel("Data de Nascim.:");
		labelNasc.setFont(fonte);
		labelNasc.setBounds(464, 90, 124, 15);
		painelDados.add(labelNasc);
		
		textNasc = new JFormattedTextField(GraphicsHelper.getMascara("##/##/####"));
		textNasc.setForeground(color);
		textNasc.setFont(fonte);
		textNasc.setBounds(590, 88, 90, 20);
		painelDados.add(textNasc);
		
		painelEndereco = new JPanel();
		painelEndereco.setBorder(GraphicsHelper.getTitledBorder("Endere�o"));
		painelEndereco.setBounds(12, 145, 692, 121);
		getContentPane().add(painelEndereco);
		painelEndereco.setLayout(null);
		
		JLabel labelLogradouro = new JLabel("Logradouro:");
		labelLogradouro.setFont(fonte);
		labelLogradouro.setBounds(12, 25, 107, 19);
		painelEndereco.add(labelLogradouro);
		
		textLogradouro = new JTextField();
		textLogradouro.setFont(fonte);
		textLogradouro.setForeground(color);
		textLogradouro.setBounds(130, 26, 395, 20);
		painelEndereco.add(textLogradouro);
		textLogradouro.setColumns(10);
		
		JLabel labelNumero = new JLabel("N�mero:");
		labelNumero.setFont(fonte);
		labelNumero.setBounds(543, 25, 91, 19);
		painelEndereco.add(labelNumero);
		
		textNumero = new JTextField();
		textNumero.setFont(fonte);
		textNumero.setForeground(color);
		textNumero.setBounds(618, 26, 62, 20);
		painelEndereco.add(textNumero);
		textNumero.setColumns(10);
		
		JLabel labelBairro = new JLabel("Bairro:");
		labelBairro.setFont(fonte);
		labelBairro.setBounds(12, 56, 62, 16);
		painelEndereco.add(labelBairro);
		
		textBairro = new JTextField();
		textBairro.setFont(fonte);
		textBairro.setForeground(color);
		textBairro.setBounds(130, 56, 251, 20);
		painelEndereco.add(textBairro);
		textBairro.setColumns(10);
		
		JLabel labelCidade = new JLabel("Cidade:");
		labelCidade.setFont(fonte);
		labelCidade.setBounds(399, 57, 70, 15);
		painelEndereco.add(labelCidade);
		
		textCidade = new JTextField();
		textCidade.setFont(fonte);
		textCidade.setForeground(color);
		textCidade.setColumns(10);
		textCidade.setBounds(462, 56, 218, 20);
		painelEndereco.add(textCidade);
		
		JLabel labelComplemento = new JLabel("Complemento:");
		labelComplemento.setFont(fonte);
		labelComplemento.setBounds(12, 87, 107, 15);
		painelEndereco.add(labelComplemento);
		
		textComplemento = new JTextField();
		textComplemento.setFont(fonte);
		textComplemento.setForeground(color);
		textComplemento.setBounds(130, 86, 177, 20);
		painelEndereco.add(textComplemento);
		textComplemento.setColumns(10);
		
		JLabel labelUF = new JLabel("UF:");
		labelUF.setFont(fonte);
		labelUF.setBounds(320, 87, 34, 15);
		painelEndereco.add(labelUF);
		
		textUF = new JTextField();
		textUF.setFont(fonte);
		textUF.setForeground(color);
		textUF.setBounds(351, 86, 30, 19);
		painelEndereco.add(textUF);
		textUF.setColumns(10);
		
		JLabel labelCEP = new JLabel("CEP:");
		labelCEP.setFont(fonte);
		labelCEP.setBounds(417, 87, 43, 15);
		painelEndereco.add(labelCEP);
		
		textCEP = new JFormattedTextField(GraphicsHelper.getMascara("##.###-###"));
		textCEP.setForeground(color);
		textCEP.setFont(fonte);
		textCEP.setBounds(462, 86, 90, 20);
		painelEndereco.add(textCEP);
		
		botaoConsultar = new JButton("Consultar");
		botaoConsultar.addActionListener(this);
		botaoConsultar.setBounds(563, 86, 117, 19);
		painelEndereco.add(botaoConsultar);
		
		painelObs = new JPanel();
		painelObs.setBorder(GraphicsHelper.getTitledBorder("Observa��es"));
		painelObs.setBounds(12, 278, 692, 127);
		getContentPane().add(painelObs);
		painelObs.setLayout(null);
		
		JScrollPane scrollObs = new JScrollPane(textObs);
		scrollObs.setBounds(12, 24, 668, 91);
		painelObs.add(scrollObs);
		
		botaoSair = new JButton("Sair");
		botaoSair.addActionListener(this);
		botaoSair.setBounds(426, 417, 85, 25);
		getContentPane().add(botaoSair);
		
		botaoLimpar = new JButton("Limpar");
		botaoLimpar.addActionListener(this);
		botaoLimpar.setBounds(522, 417, 85, 25);
		getContentPane().add(botaoLimpar);
		
		botaoSalvar = new JButton("Salvar");
		botaoSalvar.addActionListener(this);
		botaoSalvar.setBounds(619, 417, 85, 25);
		getContentPane().add(botaoSalvar);
		setVisible(true);
	}

	/********************* Bloco de Funcionalidades da Interface Gr�fica *************************/
	
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
	
	/** Salva as informa��es da interface no banco de dados */
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
				AlertDialog.informativo("Informa��es"+middle+"com sucesso!");
			else
				AlertDialog.erro("Falha ao cadastrar informa��es!");
		}
		catch (Exception exception) {
			AlertDialog.erro("Falha ao salvar dados!\nVerifique se todos os campos est�o preenchidos corretamente!");
		}
	}
	
	/******************** M�todos Auxiliares ao Controle das Fun��es *****************************/
	
	/** Recupera as informa��es da interface para o objeto de dados do sistema */
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
		
		if (!CPFParser.parse(cpf)) { AlertDialog.erro("Digite um CPF v�lido!"); return null; }
		
		Objeto objeto = new Objeto(cpf, nome, nasc, logra, compl, numer, bairro, cidade, uf, cep, tel1, tel2, email, obsvs);
		return objeto;
	}
	
	/** Limpa os campos de texto de determinado painel de componentes gr�ficos */
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
		catch (Exception exception) { AlertDialog.erro("Digite um CEP v�lido!"); }
		if (CEP.equals("")) return;
		getEnderecoCompleto(CEP);
	}
	
	/** Recupera da internet o endere�o completo com base em determinado CEP */
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
			AlertDialog.erro("N�o foi poss�vel recuperar o endere�o!\nVerifique sua conex�o com a Internet");
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
		else if ((source == botaoSair))
			dispose();
		else if (source == botaoLimpar)
			limpar();
		else if (source == botaoConsultar)
			consultar();
	}
	
}
