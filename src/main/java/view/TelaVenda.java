package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controller.ControllerVenda;
import model.seletor.MercadoriaSeletor;
import model.vo.FormaPagamento;
import model.vo.ItemMercadoria;
import model.vo.ItemProduto;
import model.vo.ItemRemedio;
import model.vo.Mercadoria;
import model.vo.Produto;
import model.vo.Remedio;
import model.vo.Usuario;
import net.miginfocom.swing.MigLayout;
import util.JNumberFormatField;
import util.JTextFieldLimit;

public class TelaVenda extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtCodBar;
	private JTextField txtNome;
	private JTable tblPesquisa;
	private JTable tblVenda;

	private List<Mercadoria> mercadoriasConsultadas = new ArrayList<Mercadoria>();
	private List<ItemMercadoria> mercadoriasParaVenda = new ArrayList<ItemMercadoria>();

	private List<ItemProduto> itensProdutos = new ArrayList<ItemProduto>();
	private List<ItemRemedio> itensRemedios = new ArrayList<ItemRemedio>();
	private List<Produto> produtos = new ArrayList<Produto>();

	private double valorTotal = 0.0;
	private double valorDesconto = 0.0;
	private double valorComDesconto = 0.0;
	private JLabel lblValor;
	private JSpinner spiQuantidade;
	private int totalPaginas = 1;
	private int paginaAtual = 1;
	private JLabel lblPaginaAtual;
	private JLabel lbMax;
	private int paginaTotal = 1;
	private JButton btnProximo;
	private JComboBox cmbFormaPagamento;
	private List<FormaPagamento> listaFormaPagamento;
	private JTextField txtDesconto;

	private Usuario usuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaVenda frame = new TelaVenda(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param usuario
	 */
	public TelaVenda(Usuario usuario) {
		getContentPane().setBackground(Color.WHITE);
		setResizable(true);
		setFrameIcon(new ImageIcon(TelaVenda.class.getResource("/icons/cart (2).png")));
		setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		setBackground(Color.WHITE);
		setTitle("Vendas");
		setClosable(true);
		setBounds(100, 100, 660, 530);
		getContentPane().setLayout(new MigLayout("", "[grow][][grow]", "[21.00][][][25.00][][][][][][grow][][][]"));

		JLabel lblCodbarra = new JLabel("Cód.barra:");
		getContentPane().add(lblCodbarra, "flowx,cell 0 0,growx");

		JLabel lblEspaco = new JLabel("         ");
		getContentPane().add(lblEspaco, "cell 1 0,alignx left");

		tblVenda = new JTable();
		tblVenda.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		tblVenda.setFillsViewportHeight(true);
		tblVenda.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nome", "Quantidade", "Preço" }));
		getContentPane().add(tblVenda, "cell 2 0 1 10,grow");

		txtCodBar = new JTextField();
		txtCodBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE))
					arg0.consume();
			}
		});

		txtCodBar.setDocument(new JTextFieldLimit(13));
		getContentPane().add(txtCodBar, "flowx,cell 0 1,growx");
		txtCodBar.setColumns(10);

		tblPesquisa = new JTable();
		tblPesquisa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int estoque = mercadoriasConsultadas.get(tblPesquisa.getSelectedRow() - 1).getEstoque();
				spiQuantidade.setModel(new SpinnerNumberModel(1, 1, estoque, 1));
			}
		});
		tblPesquisa.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		tblPesquisa.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Código", "Nome", "Preco", "Estoque" }));
		getContentPane().add(tblPesquisa, "cell 0 3 1 7,grow");

		btnProximo = new JButton("Próximo>");
		btnProximo.setBorder(new LineBorder(Color.gray, 2, true));
		btnProximo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnProximo.setBackground(Color.WHITE);
		btnProximo.setPreferredSize(new Dimension(80, 30));

		JButton btnAnterior = new JButton("<Anterior");
		btnAnterior.setBorder(new LineBorder(Color.gray, 2, true));
		btnAnterior.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnterior.setBackground(Color.WHITE);
		btnAnterior.setPreferredSize(new Dimension(80, 30));
		btnAnterior.setEnabled(false);
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (paginaAtual > 1) {
					paginaAtual--;
				}
				if (paginaAtual == 1) {
					btnAnterior.setEnabled(false);

				}
				btnProximo.setEnabled(true);
				pesquisarMercadorias();
			}
		});
		getContentPane().add(btnAnterior, "flowx,cell 0 10,alignx center");

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setIcon(new ImageIcon(TelaVenda.class.getResource("/icons/search.png")));
		btnPesquisar.setBackground(Color.WHITE);
		btnPesquisar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPesquisar.setPreferredSize(new Dimension(200, 30));
		btnPesquisar.setBorder(new LineBorder(Color.gray, 2, true));
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarMercadorias();
				if (paginaAtual != totalPaginas) {
					btnProximo.setEnabled(false);
				}
			}
		});
		getContentPane().add(btnPesquisar, "cell 0 2,alignx right");

		JLabel lblNome = new JLabel("Nome:");
		getContentPane().add(lblNome, "cell 0 0,growx");

		txtNome = new JTextField();
		txtNome.setDocument(new JTextFieldLimit(150));
		getContentPane().add(txtNome, "cell 0 1,growx");
		txtNome.setColumns(10);

		lblPaginaAtual = new JLabel("");
		lblPaginaAtual.setText(paginaAtual + "");
		getContentPane().add(lblPaginaAtual, "cell 0 10,alignx center");

		JLabel lbLabel = new JLabel("/");
		getContentPane().add(lbLabel, "cell 0 10,alignx center");

		lbMax = new JLabel("1");
		getContentPane().add(lbMax, "cell 0 10,alignx center");

		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual++;
				if (paginaAtual == paginaTotal) {
					btnProximo.setEnabled(false);
				}
				btnAnterior.setEnabled(true);
				pesquisarMercadorias();
			}
		});
		getContentPane().add(btnProximo, "cell 0 10,alignx center");
		btnProximo.setEnabled(false);

		JLabel lblDesconto = new JLabel("Desconto:");
		lblDesconto.setVerticalAlignment(SwingConstants.BOTTOM);
		getContentPane().add(lblDesconto, "flowx,cell 2 10");

		txtDesconto = new JNumberFormatField(2);
		txtDesconto.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent arg0) {
				valorDesconto = Double.parseDouble(txtDesconto.getText().toString().replace(",", "."));
				obterValorDesconto();
			}

		});
		txtDesconto.setForeground(Color.GREEN);
		txtDesconto.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(txtDesconto, "cell 2 10");
		txtDesconto.setColumns(10);

		JLabel lblEspacovs = new JLabel("                                  ");
		getContentPane().add(lblEspacovs, "cell 2 10,aligny bottom");

		JLabel lblSelecionarFormaDe = new JLabel("Selecionar Forma de Pagamento:");
		getContentPane().add(lblSelecionarFormaDe, "cell 2 10,alignx left,aligny bottom");

		JButton btnConcluirVenda = new JButton("Concluir Venda");
		btnConcluirVenda.setIcon(new ImageIcon(TelaVenda.class.getResource("/icons/check.png")));
		btnConcluirVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				obterValorDesconto();
				if (valorComDesconto > 0.0) {
					valorTotal = valorComDesconto;
				}
				String mensagem = "";
				if (!mercadoriasParaVenda.isEmpty()) {
					ControllerVenda controllerVenda = new ControllerVenda();
					FormaPagamento formaPgto = new FormaPagamento();
					formaPgto = listaFormaPagamento.get(cmbFormaPagamento.getSelectedIndex());
					mensagem = controllerVenda.salvarVenda(valorTotal, itensProdutos, itensRemedios, formaPgto,
							usuario);
					if (mensagem == "") {
						DecimalFormat df = new DecimalFormat("0.#####");
						String dx = df.format(valorTotal);
						mensagem = "Venda concluída com sucesso! Valor total de: R$" + dx;
						removerMercadorias(mercadoriasParaVenda);
						atualizarTblVenda(mercadoriasParaVenda);
						valorTotal = 0;
						lblValor.setText("R$" + valorTotal);
						txtDesconto.setText("0,00");
					}
				} else {
					mensagem = "Selecione algum produto para realizar uma venda";
				}
				JOptionPane.showMessageDialog(null, mensagem);
			}

		});

		JButton btnRemover = new JButton("Remover");
		btnRemover.setIcon(new ImageIcon(TelaVenda.class.getResource("/icons/garbage.png")));
		btnRemover.setForeground(Color.RED);
		btnRemover.setBackground(Color.WHITE);
		btnRemover.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRemover.setPreferredSize(new Dimension(100, 30));
		btnRemover.setBorder(new LineBorder(Color.gray, 2, true));
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblVenda.getSelectedRow() > 0) {
					ItemMercadoria mercadoriaSelecionada = mercadoriasParaVenda.get(tblVenda.getSelectedRow() - 1);
					mercadoriasParaVenda.remove(mercadoriaSelecionada);

					int qtd = Integer.parseInt(tblVenda.getValueAt(tblVenda.getSelectedRow(), 1).toString());

					removerMercadoria(mercadoriaSelecionada);
					atualizarTblVenda(mercadoriasParaVenda);
					atualizarValorTotal(mercadoriaSelecionada.getMercadoria().getPrecoVenda() * qtd, 0);
					obterValorDesconto();
					addEstoque(mercadoriaSelecionada, qtd);
					atualizarTabelaMercadorias(mercadoriasConsultadas);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um item para excluir");
				}
			}
		});

		JButton btnAddItem = new JButton("Adicionar item");
		btnAddItem.setBackground(Color.WHITE);
		btnAddItem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAddItem.setPreferredSize(new Dimension(90, 30));
		btnAddItem.setBorder(new LineBorder(Color.gray, 2, true));
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblPesquisa.getSelectedRow() > 0) {
					ItemMercadoria mercadoriaSelecionada = new ItemMercadoria();
					int qtd = (int) spiQuantidade.getValue();
					mercadoriaSelecionada.setMercadoria(mercadoriasConsultadas.get(tblPesquisa.getSelectedRow() - 1));
					mercadoriaSelecionada.setQtd(qtd);
					boolean validacao = adicionarItem(mercadoriaSelecionada);
					if (validacao) {
						mercadoriasParaVenda.add(mercadoriaSelecionada);
						atualizarTblVenda(mercadoriasParaVenda);
						atualizarValorTotal(mercadoriaSelecionada.getMercadoria().getPrecoVenda() * qtd, 1);
						obterValorDesconto();
						diminuirEstoque(mercadoriaSelecionada, qtd);
						atualizarTabelaMercadorias(mercadoriasConsultadas);
					}

					spiQuantidade.setValue(1);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um item para adicionar a venda");
				}
			}
		});

		JLabel lblQuantidade = new JLabel("Quantidade:");
		getContentPane().add(lblQuantidade, "flowx,cell 0 12,aligny center");

		spiQuantidade = new JSpinner();
		spiQuantidade.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		getContentPane().add(spiQuantidade, "cell 0 12,aligny center");
		getContentPane().add(btnAddItem, "cell 0 12,alignx center,aligny bottom");
		getContentPane().add(btnRemover, "flowx,cell 2 12,alignx center,aligny bottom");
		btnConcluirVenda.setForeground(new Color(0, 128, 0));
		btnConcluirVenda.setBackground(Color.WHITE);
		btnConcluirVenda.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnConcluirVenda.setPreferredSize(new Dimension(30, 30));
		btnConcluirVenda.setBorder(new LineBorder(Color.gray, 2, true));
		getContentPane().add(btnConcluirVenda, "cell 2 12,alignx center");

		this.consultarFormaPagamento();

		JLabel lblTotal = new JLabel("Total:");
		getContentPane().add(lblTotal, "flowx,cell 2 11,alignx left,aligny bottom");

		lblValor = new JLabel("R$0.00");
		lblValor.setForeground(Color.BLUE);
		lblValor.setBackground(Color.WHITE);
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().add(lblValor, "cell 2 11,aligny bottom");

		JLabel label = new JLabel("                                                                                ");
		getContentPane().add(label, "cell 2 11");
		cmbFormaPagamento = new JComboBox(listaFormaPagamento.toArray());
		cmbFormaPagamento.setSelectedIndex(0);
		getContentPane().add(cmbFormaPagamento, "cell 2 11,alignx right");

	}

	private void atualizarValorTotal(double valor, int tipo) {
		if (tipo == 0) {
			valorTotal -= valor;
		} else {
			valorTotal += valor;
		}

		DecimalFormat df = new DecimalFormat("0.#####");
		String dx = df.format(valorTotal);
		lblValor.setText("R$" + dx);
	}

	private void obterValorDesconto() {
		valorComDesconto = valorTotal - valorDesconto;

		DecimalFormat df = new DecimalFormat("0.#####");
		String dx = df.format(valorComDesconto);
		lblValor.setText("R$" + dx);
	}

	private void consultarFormaPagamento() {
		ControllerVenda vendaController = new ControllerVenda();
		listaFormaPagamento = vendaController.consultarFormaPagamento();
	}

	protected void addEstoque(ItemMercadoria mercadoriaSelecionada, int qtd) {

		int posicao = 0;

		for (int i = 0; i < mercadoriasConsultadas.size(); i++) {
			if (mercadoriasConsultadas.get(i).getCodBarra()
					.equals(mercadoriaSelecionada.getMercadoria().getCodBarra())) {
				posicao = i;
			}
		}

		int estoque = mercadoriasConsultadas.get(posicao).getEstoque();
		mercadoriasConsultadas.get(posicao).setEstoque(estoque + qtd);

	}

	protected void diminuirEstoque(ItemMercadoria mercadoriaSelecionada, int qtd) {

		int posicao = 0;

		for (int i = 0; i < mercadoriasConsultadas.size(); i++) {
			if (mercadoriasConsultadas.get(i).getCodBarra()
					.equals(mercadoriaSelecionada.getMercadoria().getCodBarra())) {
				posicao = i;
			}
		}

		int estoque = mercadoriasConsultadas.get(posicao).getEstoque();
		mercadoriasConsultadas.get(posicao).setEstoque(estoque - qtd);

	}

	private void pesquisarMercadorias() {
		lblPaginaAtual.setText(paginaAtual + "");

		ControllerVenda controlador = new ControllerVenda();
		MercadoriaSeletor seletor = new MercadoriaSeletor();

		List<Mercadoria> mercadorias = controlador.listarMercadorias(seletor);

		seletor.setLimite(5);

		int quociente = mercadorias.size() / seletor.getLimite();
		int resto = mercadorias.size() % seletor.getLimite();

		if (resto == 0) {
			totalPaginas = quociente;
		} else {
			totalPaginas = quociente + 1;
		}
		lbMax.setText(totalPaginas + "");
		seletor.setPagina(paginaAtual);
		// Preenche os campos de filtro da tela no seletor

		if (txtCodBar != null) {
			seletor.setCodBar(txtCodBar.getText());
		}

		if (!txtNome.getText().trim().equals("")) {
			seletor.setNome(txtNome.getText());
		}

		// mercadorias = controlador.listarVendaDTO(seletor);
		mercadorias = controlador.listarMercadorias(seletor);
		atualizarTabelaMercadorias(mercadorias);
	}

	private void atualizarTabelaMercadorias(List<Mercadoria> mercadorias) {
		// atualiza o atributo merdacoriasConsultadas
		mercadoriasConsultadas = mercadorias;

		// btnGerarXls.setEnabled(remedios != null && remedios.size() > 0);

		// Limpa a tabela
		tblPesquisa.setModel(new DefaultTableModel(new String[][] { { "Codigo", "Nome", "Preco", "Estoque" }, },
				new String[] { "Codigo", "Nome", "Preco", "Estoque" }));

		DefaultTableModel modelo = (DefaultTableModel) tblPesquisa.getModel();

		for (Mercadoria mercadoria : mercadorias) {
			// Crio uma nova linha na tabela
			// Preencher a linha com os atributos do remedio
			// na ORDEM do cabe�alho da tabela

			String[] novaLinha = new String[] { mercadoria.getCodBarra() + "", mercadoria.getNome(),
					"R$" + mercadoria.getPrecoVenda(), "" + mercadoria.getEstoque(), };
			modelo.addRow(novaLinha);
		}
	}

	private void removerMercadoria(ItemMercadoria itemMercadoria) {
		Mercadoria mercadoriaSelecionada = itemMercadoria.getMercadoria();
		if (mercadoriaSelecionada instanceof Produto) {
			ItemProduto itemProduto = obterItemProduto((Produto) mercadoriaSelecionada);
			itensProdutos.remove(itemProduto);
		}

		if (mercadoriaSelecionada instanceof Remedio) {
			ItemRemedio itemRemedio = obterItemRemedio((Remedio) mercadoriaSelecionada);
			itensRemedios.remove(itemRemedio);
		}
	}

	private void removerMercadorias(List<ItemMercadoria> mercadoriasParaVenda) {
		mercadoriasParaVenda.clear();
	}

	private boolean adicionarItem(ItemMercadoria itemMercadoria) {
		Mercadoria mercadoriaSelecionada = itemMercadoria.getMercadoria();
		int quantidade = (int) spiQuantidade.getValue();
		if (quantidade <= mercadoriaSelecionada.getEstoque()) {
			if (mercadoriaSelecionada instanceof Produto) {
				if (produtos.contains(mercadoriaSelecionada)) {
					ItemProduto item = obterItemProduto((Produto) mercadoriaSelecionada);
					item.setQuantidade(item.getQuantidade() + quantidade);
				} else {
					ItemProduto novoItem = new ItemProduto();
					novoItem.setProduto((Produto) mercadoriaSelecionada);
					novoItem.setQuantidade(quantidade);

					itensProdutos.add(novoItem);
				}
			}

			if (mercadoriaSelecionada instanceof Remedio) {
				if (produtos.contains(mercadoriaSelecionada)) {
					ItemRemedio item = obterItemRemedio((Remedio) mercadoriaSelecionada);
					item.setQuantidade(item.getQuantidade() + quantidade);
				} else {
					ItemRemedio novoItem = new ItemRemedio();
					novoItem.setRemedio((Remedio) mercadoriaSelecionada);
					novoItem.setQuantidade(quantidade);

					itensRemedios.add(novoItem);
				}
			}
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Não é possivel vender mais do que tem no estoque");
			return false;
		}
	}

	private ItemRemedio obterItemRemedio(Remedio remedioSelecionado) {
		ItemRemedio item = null;

		for (ItemRemedio i : itensRemedios) {
			if (i.getRemedio().getCodBarra().equals(remedioSelecionado.getCodBarra())) {
				item = i;
				break;
			}
		}
		return item;
	}

	private ItemProduto obterItemProduto(Produto produtoSelecionado) {
		ItemProduto item = null;

		for (ItemProduto i : itensProdutos) {
			if (i.getProduto().getCodBarra().equals(produtoSelecionado.getCodBarra())) {
				item = i;
				break;
			}
		}
		return item;
	}

	private void atualizarTblVenda(List<ItemMercadoria> mercadoriasParaVenda) {
		tblVenda.setModel(new DefaultTableModel(new String[][] { { "Nome", "Quantidade", "Preço" } },
				new String[] { "Nome", "Quantidade", "Preço" }));

		DefaultTableModel modelo = (DefaultTableModel) tblVenda.getModel();

		for (ItemMercadoria mercadoria : mercadoriasParaVenda) {
			String[] novaLinha = new String[] { mercadoria.getMercadoria().getNome(), mercadoria.getQtd() + "",
					"R$" + mercadoria.getMercadoria().getPrecoVenda() };
			modelo.addRow(novaLinha);
		}
	}
}
