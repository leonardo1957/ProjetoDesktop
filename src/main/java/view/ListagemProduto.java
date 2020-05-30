package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controller.ControllerProduto;
import model.seletor.ProdutoSeletor;
import model.vo.Categoria;
import model.vo.Produto;
import net.miginfocom.swing.MigLayout;
import util.JTextFieldLimit;

public class ListagemProduto extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4465473469800344264L;

	CadastroProduto cadastroProduto = null;

	private JTextField txtCodBar;
	private JTextField txtNome;
	private JTable tblProdutos;
	private JComboBox<String> cmbCategoria;

	private List<Produto> produtosConsultados = null;
	private JLabel lblPaginaAtual;
	private JLabel lbMax;
	private JButton btnProximo;
	private int totalPaginas = 1;
	private int paginaAtual = 1;
	private int paginaTotal = 1;

	private ArrayList<Categoria> listaCategoria;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListagemProduto frame = new ListagemProduto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. a
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ListagemProduto() {
		setResizable(true);
		getContentPane().setBackground(Color.WHITE);
		setFrameIcon(new ImageIcon(ListagemProduto.class.getResource("/icons/product.png")));
		setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		setTitle("Pesquisa de produtos");
		setClosable(true);
		setBounds(100, 100, 900, 540);
		getContentPane()
				.setLayout(new MigLayout("", "[211.00][][grow]", "[18.00][][][][][][8px:n][][10px:n][][][grow][]"));

		JLabel lblCodbarras = new JLabel("Cód.barras:");
		getContentPane().add(lblCodbarras, "cell 0 0");

		JLabel lblEspaco = new JLabel("      ");
		getContentPane().add(lblEspaco, "cell 1 0");

		tblProdutos = new JTable();
		tblProdutos.setColumnSelectionAllowed(true);
		tblProdutos.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		tblProdutos.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Código", "Nome", "Preço venda", "Preço custo", "Categoria", "Estoque" }));
		getContentPane().add(tblProdutos, "cell 2 0 1 12,grow");

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

		getContentPane().add(txtCodBar, "cell 0 1,growx");
		txtCodBar.setColumns(10);

		JLabel lblNome = new JLabel("Nome:");
		getContentPane().add(lblNome, "cell 0 2");

		txtNome = new JTextField();

		txtNome.setDocument(new JTextFieldLimit(150));

		getContentPane().add(txtNome, "cell 0 3,growx");
		txtNome.setColumns(10);

		JLabel lblCategoria = new JLabel("Categoria:");
		getContentPane().add(lblCategoria, "cell 0 4");

		this.consultarCategoria();
		cmbCategoria = new JComboBox(listaCategoria.toArray());
		cmbCategoria.setBackground(Color.WHITE);
		cmbCategoria.addItem("");
		getContentPane().add(cmbCategoria, "cell 0 5,growx");
		cmbCategoria.setSelectedIndex(listaCategoria.toArray().length);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setIcon(new ImageIcon(ListagemProduto.class.getResource("/icons/search.png")));
		btnPesquisar.setBackground(Color.WHITE);
		btnPesquisar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPesquisar.setPreferredSize(new Dimension(80, 30));
		btnPesquisar.setBorder(new LineBorder(Color.gray, 2, true));

		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarProdutos();
				if (paginaAtual != totalPaginas) {
					btnProximo.setEnabled(true);
				}
			}
		});
		getContentPane().add(btnPesquisar, "cell 0 7,growx");

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setIcon(new ImageIcon(ListagemProduto.class.getResource("/icons/garbage.png")));
		btnExcluir.setForeground(Color.RED);
		btnExcluir.setBackground(Color.WHITE);
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExcluir.setPreferredSize(new Dimension(80, 30));
		btnExcluir.setBorder(new LineBorder(Color.gray, 2, true));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tblProdutos.getSelectedRow();

				if (linhaSelecionada > 0) {
					String mensagem = "";
					String produtoSelecionado = produtosConsultados.get(linhaSelecionada - 1).getCodBarra();
					ControllerProduto controllerProduto = new ControllerProduto();

					if (controllerProduto.existeProdutoCodBar(produtoSelecionado)) {
						mensagem = controllerProduto.excluir(produtoSelecionado);
						atualizarTabelaProdutos(produtosConsultados);
					} else {
						mensagem = "Produto não foi cadastrado";
					}
					JOptionPane.showMessageDialog(null, mensagem);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um produto para ser excluído!!");
				}
			}

		});
		getContentPane().add(btnExcluir, "flowx,cell 0 9,growx");

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.setBackground(Color.WHITE);
		btnAlterar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAlterar.setPreferredSize(new Dimension(80, 30));
		btnAlterar.setBorder(new LineBorder(Color.gray, 2, true));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tblProdutos.getSelectedRow();

				if (linhaSelecionada > 0) {
					Produto produtoSelecionado = produtosConsultados.get(linhaSelecionada - 1);
					cadastroProduto = new CadastroProduto(produtoSelecionado);
					Menu pai = (Menu) SwingUtilities.getWindowAncestor((Component) e.getSource());

					pai.cadastroProduto = cadastroProduto;
					pai.getDesktopPane().add(cadastroProduto);
					pai.cadastroProduto.show();

					atualizarTabelaProdutos(produtosConsultados);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um produto para ser Alterado!!");
				}
			}
		});

		getContentPane().add(btnAlterar, "cell 0 9,growx");

		JButton btnGerarXls = new JButton("Relatório");
		btnGerarXls.setBackground(Color.WHITE);
		btnGerarXls.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGerarXls.setPreferredSize(new Dimension(80, 30));
		btnGerarXls.setBorder(new LineBorder(Color.gray, 2, true));
		btnGerarXls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Salvar...");

				int resultado = jfc.showSaveDialog(null);
				if (resultado == JFileChooser.APPROVE_OPTION) {
					String caminhoEscolhido = jfc.getSelectedFile().getAbsolutePath();

					ControllerProduto produtoController = new ControllerProduto();
					produtoController.gerarRelatorio(produtosConsultados, caminhoEscolhido,
							ControllerProduto.TIPO_RELATORIO_XLS);
				}
			}
		});
		getContentPane().add(btnGerarXls, "cell 0 10,alignx center");

		JButton btnProximo = new JButton("Próximo>");
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
				pesquisarProdutos();
			}
		});
		getContentPane().add(btnAnterior, "flowx,cell 2 12,alignx center");

		lblPaginaAtual = new JLabel("");
		lblPaginaAtual.setText(paginaAtual + "");
		getContentPane().add(lblPaginaAtual, "cell 2 12,alignx center");

		JLabel label = new JLabel("/");
		getContentPane().add(label, "cell 2 12,alignx center");

		lbMax = new JLabel("1");
		getContentPane().add(lbMax, "cell 2 12,alignx center");

		btnProximo.setBorder(new LineBorder(Color.gray, 2, true));
		btnProximo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnProximo.setBackground(Color.WHITE);
		btnProximo.setPreferredSize(new Dimension(80, 30));
		btnProximo.setEnabled(false);
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual++;
				if (paginaAtual == totalPaginas) {
					btnProximo.setEnabled(false);
				}
				btnAnterior.setEnabled(true);
				pesquisarProdutos();
			}
		});
		getContentPane().add(btnProximo, "cell 2 12,alignx center");

	}

	private void consultarCategoria() {
		ControllerProduto controllerProduto = new ControllerProduto();
		listaCategoria = controllerProduto.consultarCategoria();
	}

	private void pesquisarProdutos() {
		lblPaginaAtual.setText(paginaAtual + "");

		ControllerProduto controlador = new ControllerProduto();
		ProdutoSeletor seletor = new ProdutoSeletor();

		List<Produto> produtos = controlador.listarProdutos(seletor);

		seletor.setLimite(10);

		int quociente = produtos.size() / seletor.getLimite();
		int resto = produtos.size() % seletor.getLimite();

		if (resto == 0) {
			totalPaginas = quociente;
		} else {
			totalPaginas = quociente + 1;
		}
		lbMax.setText(totalPaginas + "");

		seletor.setPagina(paginaAtual);

		if (!txtCodBar.getText().trim().equals("")) {
			seletor.setCodBar(txtCodBar.getText());
		}

		if (!txtNome.getText().trim().equals("")) {
			seletor.setNomeProduto(txtNome.getText());
		}

		if (cmbCategoria.getSelectedIndex() > -1) {
			seletor.setCategoria(cmbCategoria.getSelectedItem().toString());
		} else {
			seletor.setCategoria("");
		}

		produtos = controlador.listarProdutos(seletor);
		atualizarTabelaProdutos(produtos);
	}

	private void atualizarTabelaProdutos(List<Produto> produtos) {
		produtosConsultados = produtos;

		tblProdutos.setModel(new DefaultTableModel(
				new String[][] { { "Código", "Nome", "Preço venda", "Preço custo", "Lucro", "Categoria", "Estoque" }, },
				new String[] { "Código", "Nome", "Preço venda", "Preço custo", "Lucro", "Categoria", "Estoque" }));

		DefaultTableModel modelo = (DefaultTableModel) tblProdutos.getModel();
		DecimalFormat format = new DecimalFormat("0.00");
		for (Produto produto : produtos) {

			String[] novaLinha = new String[] { produto.getCodBarra() + "", produto.getNome(),
					"R$" + format.format(produto.getPrecoVenda()), "R$" + format.format(produto.getPrecoCusto()),
					"R$" + format.format(produto.getPrecoVenda() - produto.getPrecoCusto()),
					produto.getCategoria().getNomeCategoria(), produto.getEstoque() + "" };
			modelo.addRow(novaLinha);
		}
	}

}
