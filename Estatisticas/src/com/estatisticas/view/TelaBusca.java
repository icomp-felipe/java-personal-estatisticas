package com.estatisticas.view;

import java.sql.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import com.estatisticas.dao.*;
import com.estatisticas.model.*;

import com.phill.libs.*;
import com.phill.libs.ui.*;
import com.phill.libs.table.*;

/** Implementa uma interface de busca de informações no banco de dados.
 *  @author Felipe André - felipeandresouza@hotmail.com
 *  @version 2.5, 21/09/2020 */
public class TelaBusca extends JFrame {

	// Serial
	private static final long serialVersionUID = -1394541735684711242L;
	
	// Atributos gráficos
	private final JTextField textBusca;
	private final JTable tableResultado;
	private final LockedTableModel modelo;
	
	// Atributos dinâmicos
	private ArrayList<Cliente> listaClientes;
	private JLabel textQTD;

	/** Inicia a tela de consulta de informações. */
	public TelaBusca() {
		super("Consulta Registros");
		
		// Inicializando atributos gráficos
		GraphicsHelper instance = GraphicsHelper.getInstance();
		GraphicsHelper.setFrameIcon(this,"img/logo.png");
		
		Dimension dimension = new Dimension(1044,495);
		JPanel    mainPanel = new JPaintedPanel("img/background.png",dimension);
		
		// Recuperando fontes e cores
		Font fonte  = instance.getFont();
		Color color = instance.getColor();
		
		// Recuperando ícones
		Icon addIcon   = ResourceManager.getIcon("icon/round_plus.png",20,20);
		Icon clearIcon = ResourceManager.getIcon("icon/clear.png",20,20);
		Icon exitIcon  = ResourceManager.getIcon("icon/shutdown.png",20,20);
		
		mainPanel.setLayout(null);
		setContentPane(mainPanel);
		
		// Painel Busca
		JPanel painelBusca = new JPanel();
		painelBusca.setOpaque(false);
		painelBusca.setBorder(instance.getTitledBorder("Busca (Nome | Logradouro | Bairro | Cidade | Observações)"));
		painelBusca.setBounds(12, 12, 1000, 70);
		painelBusca.setLayout(null);
		mainPanel.add(painelBusca);
		
		textBusca = new JTextField();
		textBusca.setFont(fonte);
		textBusca.setForeground(color);
		textBusca.setBounds(12, 30, 892, 25);
		textBusca.getDocument().addDocumentListener((DocumentChangeListener) (event) -> action_update_table());
		painelBusca.add(textBusca);
		
		JButton buttonBuscaAdd = new JButton(addIcon);
		buttonBuscaAdd.addActionListener((event) -> new TelaCadastroEdicao());
		buttonBuscaAdd.setToolTipText("Cria um novo registro");
		buttonBuscaAdd.setBounds(916, 30, 30, 25);
		painelBusca.add(buttonBuscaAdd);
		
		JButton buttonBuscaClear = new JButton(clearIcon);
		buttonBuscaClear.addActionListener((event) -> action_field_clear());
		buttonBuscaClear.setToolTipText("Limpa o campo de pesquisa");
		buttonBuscaClear.setBounds(958, 30, 30, 25);
		painelBusca.add(buttonBuscaClear);
		
		// Painel Resultado
		JPanel painelResultado = new JPanel();
		painelResultado.setOpaque(false);
		painelResultado.setBorder(instance.getTitledBorder("Resultados"));
		painelResultado.setBounds(12, 85, 1000, 321);
		painelResultado.setLayout(null);
		mainPanel.add(painelResultado);
		
		JScrollPane scrollResultado = new JScrollPane();
		scrollResultado.setBounds(12, 26, 976, 283);
		painelResultado.add(scrollResultado);
		
		String[] header = new String []{"Nome","Endereço","Tel. Celular","Tel. Fixo"};
		
		modelo = new LockedTableModel(header);
		
		tableResultado = new JTable(modelo);
		tableResultado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableResultado.addMouseListener(new JTableMouseListener(tableResultado));
		
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		tableResultado.getColumnModel().getColumn(0).setPreferredWidth(250);
		tableResultado.getColumnModel().getColumn(1).setPreferredWidth(280);
		
		tableResultado.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tableResultado.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		
		scrollResultado.setViewportView(tableResultado);
		
		JButton botaoSair = new JButton(exitIcon);
		botaoSair.setToolTipText("Fechar a tela de busca");
		botaoSair.addActionListener((event) -> dispose());
		botaoSair.setBounds(982, 418, 30, 25);
		mainPanel.add(botaoSair);
		
		JLabel labelQTD = new JLabel("Registros encontrados:");
		labelQTD.setFont(fonte);
		labelQTD.setBounds(12, 418, 170, 20);
		mainPanel.add(labelQTD);
		
		textQTD = new JLabel("0");
		textQTD.setFont(fonte);
		textQTD.setForeground(color);
		textQTD.setBounds(190, 418, 88, 20);
		mainPanel.add(textQTD);
		
		onCreateOptionsPopupMenu();
		
		setSize(dimension);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		action_update_table();
		
		textBusca.requestFocus();
		
	}
	
	/** Cria as opções de menu da tabela. */
	private void onCreateOptionsPopupMenu() {
		
		JPopupMenu popupMenu = new JPopupMenu();
		
        JMenuItem itemExpandir = new JMenuItem("Expandir");
        itemExpandir.addActionListener((event) -> action_expand());
        popupMenu.add(itemExpandir);
        
        JMenuItem itemExcluir = new JMenuItem("Excluir");
        itemExcluir.addActionListener((event) -> action_delete());
        popupMenu.add(itemExcluir);
        
        tableResultado.setComponentPopupMenu(popupMenu);
        
	}
	
	/********************* Bloco de Funcionalidades da Interface Gráfica *************************/
	
	/** Exclui da base de dados o cliente selecionado na tabela. */
	private void action_delete() {
		
		final String title = "Excluindo registro";
		
		try {
			
			Cliente selecionado = TableUtils.getSelected(tableResultado, listaClientes);
			
			int option = AlertDialog.dialog(title, "Você tem certeza que deseja excluir a linha selecionada?");
			
			if (option == AlertDialog.OK_OPTION) {
				
				ClienteDAO.remove(selecionado);
				
				AlertDialog.info(title, "Linha excluída com sucesso!");
				action_update_table();
				
			}
			
		}
		catch (ArrayIndexOutOfBoundsException exception) {
			AlertDialog.error(title, "Selecione uma linha da tabela!");
		}
		catch (SQLException exception) {
			exception.printStackTrace();
			AlertDialog.error(title, "Falha ao excluir registro.");
		}
		
	}
	
	/** Exibe mais informações de uma entrada da tabela em uma janela dedicada. */
	private void action_expand() {
		
		final String title = "Expandindo registro";
		
		try {
			
			Cliente selecionado = TableUtils.getSelected(tableResultado, listaClientes);
			ClienteDAO.complete(selecionado);
			
			new TelaCadastroEdicao(selecionado);
			
		}
		catch (ArrayIndexOutOfBoundsException exception) {
			AlertDialog.error(title, "Selecione uma linha da tabela!");
		}
		catch (SQLException exception) {
			exception.printStackTrace();
			AlertDialog.error(title, "Falha ao expandir registro.");
		}
		
	}
	
	/** Limpa os dados do campo de busca. */
	private void action_field_clear() {
		
		textBusca.setText(null);
		textBusca.requestFocus();
		
		action_update_table();
		
	}
	
	/** Atualiza a tabela de acordo com os dados do campo 'busca'. */
	private synchronized void action_update_table() {
		
		final String field  = textBusca.getText().trim();
		
		try {
			
			this.listaClientes = ClienteDAO.list(field);
			
			TableUtils.load(modelo, listaClientes, textQTD);
			
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		
	}
}
