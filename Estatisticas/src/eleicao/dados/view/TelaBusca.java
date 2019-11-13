package eleicao.dados.view;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import eleicao.dados.model.*;
import eleicao.dados.utils.*;

/** Classe TelaBusca - implementa uma interface de busca de informações no banco de dados
 *  @author Felipe André Souza da Silva 
 *  @version 2.00, 20/09/2014 */
public class TelaBusca extends JFrame implements DocumentListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField textPesquisa;
	private JButton botaoAtualizar, botaoSair;
	private JRadioButton radioBairro, radioCPF, radioRua, radioNome;
	private JMenuItem expandItem, deleteItem;
	private JTable tableResultado;
	private JPanel painelFiltro;
	private String[][] dados;
    private String[] colunas = new String []{"Nome","CPF","Endereço","Contato 1","Contato 2"};
	private DefaultTableModel modelo = new DefaultTableModel(dados,colunas);

	/** Inicia a tela de consulta de informações */
	public TelaBusca() {
		super("Tela de Consultas");
		
		Font fonte  = GraphicsHelper.getFont();
		Color color = GraphicsHelper.getColor();
		
		tableResultado = new JTable(modelo);
		tableResultado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableResultado.addMouseListener(new TableMouseListener());
		
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		tableResultado.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableResultado.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableResultado.getColumnModel().getColumn(2).setPreferredWidth(250);
		
		tableResultado.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tableResultado.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tableResultado.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		
		setSize(1024, 480);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		painelFiltro = new JPanel();
		painelFiltro.setBorder(GraphicsHelper.getTitledBorder("Filtros de Busca"));
		painelFiltro.setBounds(12, 12, 1000, 105);
		getContentPane().add(painelFiltro);
		painelFiltro.setLayout(null);
		
		radioNome = new JRadioButton(Filtro.Nome.name());
		radioNome.addActionListener(this);
		radioNome.setSelected(true);
		radioNome.setFont(fonte);
		radioNome.setBounds(8, 28, 83, 23);
		painelFiltro.add(radioNome);
		
		radioCPF = new JRadioButton(Filtro.CPF.name());
		radioCPF.addActionListener(this);
		radioCPF.setFont(fonte);
		radioCPF.setBounds(131, 28, 83, 23);
		painelFiltro.add(radioCPF);
		
		radioRua = new JRadioButton(Filtro.Logradouro.name());
		radioRua.addActionListener(this);
		radioRua.setFont(fonte);
		radioRua.setBounds(252, 28, 125, 23);
		painelFiltro.add(radioRua);
		
		radioBairro = new JRadioButton(Filtro.Bairro.name());
		radioBairro.addActionListener(this);
		radioBairro.setFont(fonte);
		radioBairro.setBounds(431, 28, 74, 23);
		painelFiltro.add(radioBairro);
		
		textPesquisa = new JTextField();
		textPesquisa.setFont(fonte);
		textPesquisa.setForeground(color);
		textPesquisa.setBounds(12, 68, 976, 25);
		painelFiltro.add(textPesquisa);
		textPesquisa.setColumns(10);
		textPesquisa.getDocument().addDocumentListener(this);
		
		ButtonGroup botoes = new ButtonGroup();
		botoes.add(radioBairro);
		botoes.add(radioCPF);
		botoes.add(radioNome);
		botoes.add(radioRua);
		
		JPanel painelResultado = new JPanel();
		painelResultado.setBorder(GraphicsHelper.getTitledBorder("Resultados"));
		painelResultado.setBounds(12, 129, 1000, 273);
		getContentPane().add(painelResultado);
		painelResultado.setLayout(null);
		
		JScrollPane scrollResultado = new JScrollPane(tableResultado);
		scrollResultado.setBounds(12, 26, 976, 235);
		painelResultado.add(scrollResultado);
		
		botaoSair = new JButton("Sair");
		botaoSair.addActionListener(this);
		botaoSair.setBounds(911, 406, 101, 25);
		getContentPane().add(botaoSair);
		
		botaoAtualizar = new JButton("Atualizar");
		botaoAtualizar.addActionListener(this);
		botaoAtualizar.setBounds(798, 406, 101, 25);
		getContentPane().add(botaoAtualizar);
		
		onCreateOptionsPopupMenu();
		setVisible(true);
		
		textPesquisa.requestFocus();
		atualizaTabela();
	}
	
	/** Cria as opções de menu */
	private void onCreateOptionsPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		
        expandItem = new JMenuItem("Expandir");
        deleteItem = new JMenuItem("Excluir");
        
        expandItem.addActionListener(this);
        deleteItem.addActionListener(this);
        
        popupMenu.add(expandItem);
        popupMenu.add(deleteItem);
        
        tableResultado.setComponentPopupMenu(popupMenu);
	}
	
	/********************* Bloco de Funcionalidades da Interface Gráfica *************************/
	
	/** Método adiciona novas linhas à JTable */
	public void adicionaLinha(Objeto objeto) {
		 modelo.addRow(objeto.getRowResume());  
	}
	
	/** Método remove uma linha da JTable */
	public void removeLinhas() {
		int rows = modelo.getRowCount();
		for (int i=0; i<rows; i++) {
			modelo.removeRow(0);
		}
	}

	/** Atualiza a tabela de acordo com o filtro selecionado */
	private void atualizaTabela() {
		String field  = textPesquisa.getText();
		Filtro filter = getSelectedFilter();
		
		removeLinhas();
		ArrayList<Objeto> objetos = ObjetoDAO.getObjetos(field,filter);
		
		for (Objeto novo: objetos) 
			adicionaLinha(novo);
	}
	
	/** Retorna o botão atualmente selecionado na interface gráfica */
	private JRadioButton getSelectedButton() {
		for (Component component: painelFiltro.getComponents())
			if (component instanceof JRadioButton)
				if (((JRadioButton) component).isSelected())
					return (JRadioButton) component;
		return null;
	}
	
	/** Retorna o filtro atualmente selecionado */
	private Filtro getSelectedFilter() {
		JRadioButton button = getSelectedButton();
		return Filtro.valueOf(button.getText());
	}
	
	/** Atualiza a tabela de informações */
	private synchronized void textFieldEvent() {
		atualizaTabela();
	}
	
	/** Retorna o CPF da linha selecionada na tabela */
	private String getSelectedCPF() {
		return modelo.getValueAt(tableResultado.getSelectedRow(), 1).toString().replace(".","").replace("-","");
	}
	
	/** Exclui uma entrada da tabela a partir de seu CPF */
	private void excluir() {
		try {
			String cpf = getSelectedCPF();
			int option = AlertDialog.dialog("Você tem certeza que deseja excluir a linha selecionada?");
			if (option != JFileChooser.APPROVE_OPTION)
				return;
			ObjetoDAO.removeObjeto(new Objeto(cpf));
			AlertDialog.informativo("Linha excluída com sucesso!");
			atualizaTabela();
		}
		catch (ArrayIndexOutOfBoundsException exception) {
			AlertDialog.erro("Seleção de Itens", "Selecione uma linha da tabela!");
		}
	}

	/** Exibe mais informações de uma entrada da tabela em uma janela dedicada */
	private void expandir() {
		try {
			String cpf = getSelectedCPF();
			Objeto objeto = ObjetoDAO.getObjeto(cpf);
			if (objeto != null)
				new TelaCadastroEdicao(objeto);
		}
		catch (ArrayIndexOutOfBoundsException exception) {
			AlertDialog.erro("Seleção de Itens", "Selecione uma linha da tabela!");
		}
	}

	/************************** Bloco de Tratamento de Eventos ***********************************/
	
	@Override
	public void insertUpdate(DocumentEvent event) {
		textFieldEvent();
	}

	@Override
	public void removeUpdate(DocumentEvent event) {
		textFieldEvent();
	}
	
	@Override
	public void changedUpdate(DocumentEvent event) { }

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if ((source == radioBairro) || (source == radioCPF) || (source == radioNome) || (source == radioRua))
			atualizaTabela();
		else if (source == expandItem)
			expandir();
		else if (source == deleteItem)
			excluir();
		else if (source == botaoSair)
			dispose();
		else if (source == botaoAtualizar)
			atualizaTabela();
	}
	
	/************************* Bloco de Classes Auxiliares ***************************************/
	
	/** Adiciona a funcionalidade de abrir o menu de popup de
	 *  uma linha da JTable com o botão direito do mouse */
	private class TableMouseListener extends MouseAdapter {
	     
	    @Override
	    public void mouseReleased(MouseEvent event) {
	        Point point = event.getPoint();
	        int currentRow = tableResultado.rowAtPoint(point);
	        tableResultado.setRowSelectionInterval(currentRow, currentRow);
	    }
	}

}
