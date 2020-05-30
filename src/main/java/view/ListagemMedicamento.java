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
import java.text.SimpleDateFormat;
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

import controller.ControllerRemedio;
import model.seletor.RemedioSeletor;
import model.vo.FormaUso;
import model.vo.Remedio;
import net.miginfocom.swing.MigLayout;
import util.JTextFieldLimit;

public class ListagemMedicamento extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6012516348434889292L;
	private CadastroMedicamento cadastroMedicamento = null;
	private JTextField txtCodBar = null;
	private JTextField txtNome;
	private JTextField txtComposicao;
	private JTable tblRemedios;
	private JComboBox cmbFormaUso;
	private JComboBox cmbGenerico;
	private JButton btnGerarXls;

	private List<Remedio> remediosConsultados;
	private int totalPaginas = 1;
	private int paginaAtual = 1;
	private JLabel lblPaginaAtual;
	private JLabel lbMax;
	private int paginaTotal = 1;
	private JButton btnProximo;
	private JButton btnAnterior;

	private ArrayList<FormaUso> listaFormasUso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListagemMedicamento frame = new ListagemMedicamento();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ListagemMedicamento() {
		setResizable(true);
		setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		setFrameIcon(new ImageIcon(ListagemMedicamento.class.getResource("/icons/med3x.png")));
		getContentPane().setBackground(Color.WHITE);
		setTitle("Pesquisa de medicamentos");
		setClosable(true);
		setBounds(100, 100, 1400, 540);
		getContentPane()
				.setLayout(new MigLayout("", "[211.00][][grow]", "[][][][][][][][][][][10px:n][][10px:n][][][grow][]"));

		JLabel lblCodbarras = new JLabel("Cód.barras:");
		getContentPane().add(lblCodbarras, "cell 0 0");

		JLabel lblespaco2 = new JLabel("      ");
		getContentPane().add(lblespaco2, "cell 1 0");

		tblRemedios = new JTable();
		tblRemedios.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		tblRemedios.setColumnSelectionAllowed(true);
		tblRemedios.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Código de Barras", "Dosagem", "Composição", "Genérico", "Nome", "Data Cad.",
						"Preço venda", "Preço custo", "Estoque", "Forma Uso", "Laboratório" }));
		tblRemedios.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblRemedios.getColumnModel().getColumn(1).setPreferredWidth(58);
		tblRemedios.getColumnModel().getColumn(2).setPreferredWidth(69);
		tblRemedios.getColumnModel().getColumn(3).setPreferredWidth(56);
		tblRemedios.getColumnModel().getColumn(4).setPreferredWidth(88);
		tblRemedios.getColumnModel().getColumn(5).setPreferredWidth(61);
		tblRemedios.getColumnModel().getColumn(6).setPreferredWidth(40);
		tblRemedios.getColumnModel().getColumn(7).setPreferredWidth(50);
		tblRemedios.getColumnModel().getColumn(8).setPreferredWidth(62);
		tblRemedios.getColumnModel().getColumn(9).setPreferredWidth(68);
		getContentPane().add(tblRemedios, "cell 2 0 1 16,grow");

		// C�digo de barras

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

		getContentPane().add(txtCodBar, "cell 0 1,growx,aligny center");
		txtCodBar.setColumns(10);

		JLabel lblNome = new JLabel("Nome:");
		getContentPane().add(lblNome, "cell 0 2");

		// Nomes

		txtNome = new JTextField();

		getContentPane().add(txtNome, "cell 0 3,growx");
		txtNome.setColumns(10);

		txtNome.setDocument(new JTextFieldLimit(150));

		JLabel lblComposicao = new JLabel("Composição:");
		getContentPane().add(lblComposicao, "cell 0 4");

		// Composi��o

		txtComposicao = new JTextField();

		getContentPane().add(txtComposicao, "cell 0 5,growx");
		txtComposicao.setColumns(10);

		JLabel lblFormaUso = new JLabel("Forma de uso:");
		getContentPane().add(lblFormaUso, "cell 0 6");

		// Forma de uso

		this.consultarFormaUso();
		cmbFormaUso = new JComboBox(listaFormasUso.toArray());
		cmbFormaUso.setBackground(Color.WHITE);
		cmbFormaUso.addItem("");
		getContentPane().add(cmbFormaUso, "cell 0 7,growx");
		cmbFormaUso.setSelectedIndex(listaFormasUso.toArray().length);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setIcon(new ImageIcon(ListagemMedicamento.class.getResource("/icons/search.png")));
		btnPesquisar.setPreferredSize(new Dimension(80, 30));
		btnPesquisar.setBorder(new LineBorder(Color.gray, 2, true));
		btnPesquisar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPesquisar.setBackground(Color.WHITE);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarMedicamentos();
				if (paginaAtual != totalPaginas) {
					btnProximo.setEnabled(true);
				}
			}
		});

		JLabel lblGenerico = new JLabel("Genérico:");
		getContentPane().add(lblGenerico, "cell 0 8");
		getContentPane().add(btnPesquisar, "cell 0 11,growx");

		btnGerarXls = new JButton("Relatório");
		btnGerarXls.setPreferredSize(new Dimension(100, 30));
		btnGerarXls.setBorder(new LineBorder(Color.gray, 2, true));
		btnGerarXls.setBackground(Color.WHITE);
		btnGerarXls.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGerarXls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Salvar...");

				int resultado = jfc.showSaveDialog(null);
				if (resultado == JFileChooser.APPROVE_OPTION) {
					String caminhoEscolhido = jfc.getSelectedFile().getAbsolutePath();

					ControllerRemedio remedioController = new ControllerRemedio();
					remedioController.gerarRelatorio(remediosConsultados, caminhoEscolhido,
							ControllerRemedio.TIPO_RELATORIO_XLS);

				}
			}
		});

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.setPreferredSize(new Dimension(30, 30));
		btnAlterar.setBorder(new LineBorder(Color.gray, 2, true));
		btnAlterar.setBackground(Color.WHITE);
		btnAlterar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tblRemedios.getSelectedRow();

				if (linhaSelecionada > 0) {
					Remedio remedioSelecionado = remediosConsultados.get(linhaSelecionada - 1);

					cadastroMedicamento = new CadastroMedicamento(remedioSelecionado);
					Menu pai = (Menu) SwingUtilities.getWindowAncestor((Component) e.getSource());

					pai.cadastroMedicamento = cadastroMedicamento;
					pai.getDesktopPane().add(cadastroMedicamento);
					pai.cadastroMedicamento.show();

					atualizarTabelaMedicamentos(remediosConsultados);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um remédio para ser Alterado!!");
				}

			}
		});

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setIcon(new ImageIcon(ListagemMedicamento.class.getResource("/icons/garbage.png")));
		btnExcluir.setForeground(Color.RED);
		btnExcluir.setPreferredSize(new Dimension(30, 30));
		btnExcluir.setBorder(new LineBorder(Color.gray, 2, true));
		btnExcluir.setBackground(Color.WHITE);
		btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int linhaSelecionada = tblRemedios.getSelectedRow();

				if (linhaSelecionada > 0) {
					String mensagem = "";
					String remedioSelecionado = remediosConsultados.get(linhaSelecionada - 1).getCodBarra();

					ControllerRemedio controllerRemedio = new ControllerRemedio();

					if (controllerRemedio.existeRemedioPorCodBar(remedioSelecionado)) {
						mensagem = controllerRemedio.excluir(remedioSelecionado);
						atualizarTabelaMedicamentos(remediosConsultados);
					} else {
						mensagem = "Remédio não foi cadastrado";
					}
					JOptionPane.showMessageDialog(null, mensagem);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um remédio para ser Excluído!!");
				}
			}
		});

		// espaco entre os botoes (pesquisa e excluir)
		JLabel lblespaco = new JLabel("    ");
		lblespaco.setEnabled(false);
		getContentPane().add(lblespaco, "cell 0 12");
		getContentPane().add(btnExcluir, "flowx,cell 0 13,growx");
		getContentPane().add(btnAlterar, "cell 0 13,growx");
		getContentPane().add(btnGerarXls, "cell 0 14,alignx center");

		String[] pGenerico = { "Sim", "Não", "" };
		cmbGenerico = new JComboBox(pGenerico);
		cmbGenerico.setBackground(Color.WHITE);
		cmbGenerico.setSelectedIndex(2);
		getContentPane().add(cmbGenerico, "flowx,cell 0 9,growx");

		btnProximo = new JButton("Próximo>");
		btnProximo.setBorder(new LineBorder(Color.gray, 2, true));
		btnProximo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnProximo.setBackground(Color.WHITE);
		btnProximo.setPreferredSize(new Dimension(80, 30));
		btnProximo.setEnabled(false);
		btnAnterior = new JButton("<Anterior");
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
				pesquisarMedicamentos();
			}
		});
		getContentPane().add(btnAnterior, "flowx,cell 2 16,alignx center");

		lblPaginaAtual = new JLabel("");
		lblPaginaAtual.setText(paginaAtual + "");
		getContentPane().add(lblPaginaAtual, "cell 2 16,alignx center");

		JLabel label = new JLabel("/");
		getContentPane().add(label, "cell 2 16,alignx center");

		lbMax = new JLabel("1");
		getContentPane().add(lbMax, "cell 2 16,alignx center");

		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual++;
				if (paginaAtual == totalPaginas) {
					btnProximo.setEnabled(false);
				}
				btnAnterior.setEnabled(true);
				pesquisarMedicamentos();
			}
		});
		getContentPane().add(btnProximo, "cell 2 16,alignx center");

	}

	private void pesquisarMedicamentos() {
		lblPaginaAtual.setText(paginaAtual + "");

		ControllerRemedio controlador = new ControllerRemedio();
		RemedioSeletor seletor = new RemedioSeletor();

		List<Remedio> remedios = controlador.listarRemedios(seletor);

		seletor.setLimite(10);

		int quociente = remedios.size() / seletor.getLimite();
		int resto = remedios.size() % seletor.getLimite();

		if (resto == 0) {
			totalPaginas = quociente;
		} else {
			totalPaginas = quociente + 1;
		}
		lbMax.setText(totalPaginas + "");

		seletor.setPagina(paginaAtual);

		// Preenche os campos de filtro da tela no seletor

		if (!txtCodBar.getText().trim().equals("")) {
			seletor.setCodBar(txtCodBar.getText());
		}

		if (!txtNome.getText().trim().equals("")) {
			seletor.setNomeRemedio(txtNome.getText());
		}

		if (!txtComposicao.getText().trim().equals("")) {
			seletor.setComposicaoRemedio(txtComposicao.getText());
		}

		if (cmbFormaUso.getSelectedIndex() > -1) {
			seletor.setTipoRemedio(cmbFormaUso.getSelectedItem().toString());
		} else {
			seletor.setTipoRemedio("");
		}

		if (cmbGenerico.getSelectedIndex() == 0) {
			seletor.setGenerico(true);
		}

		if (cmbGenerico.getSelectedIndex() == 1) {
			seletor.setGenerico(false);
		}

		remedios = controlador.listarRemedios(seletor);
		atualizarTabelaMedicamentos(remedios);
	}

	private void consultarFormaUso() {
		ControllerRemedio controllerRemedio = new ControllerRemedio();
		listaFormasUso = controllerRemedio.consultarFormaUso();
	}

	private void atualizarTabelaMedicamentos(List<Remedio> remedios) {
		// atualiza o atributo remediosConsultados
		remediosConsultados = remedios;

		btnGerarXls.setEnabled(remedios != null && remedios.size() > 0);

		// Limpa a tabela
		tblRemedios.setModel(new DefaultTableModel(
				new String[][] { { "Código de Barras", "Dosagem", "Composição", "Genérico", "Nome", "Data Cad.",
						"Preço venda", "Preço custo", "Lucro", "Estoque", "Forma Uso", "Laboratório" }, },
				new String[] { "Código de Barras", "Dosagem", "Composição", "Genérico", "Nome", "Data Cad.",
						"Preço venda", "Preço custo", "Lucro", "Estoque", "Forma Uso", "Laboratório" }));

		DefaultTableModel modelo = (DefaultTableModel) tblRemedios.getModel();

		String generico = "";
		for (Remedio remedio : remedios) {
			// Crio uma nova linha na tabela
			// Preencher a linha com os atributos do remedio
			// na ORDEM do cabe�alho da tabela
			if (remedio.isGenerico()) {
				generico = "Sim";
			} else {
				generico = "Não";
			}
			DecimalFormat format = new DecimalFormat("0.00");

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			String[] novaLinha = new String[] { remedio.getCodBarra() + "", remedio.getDosagem(),
					remedio.getComposicao(), generico, remedio.getNome(), sdf.format(remedio.getDataCadastro()),
					"R$" + format.format(remedio.getPrecoVenda()), "R$" + format.format(remedio.getPrecoCusto()),
					"R$" + format.format(remedio.getPrecoVenda() - remedio.getPrecoCusto()), "" + remedio.getEstoque(),
					remedio.getFormaUso().getDescricao(), remedio.getLaboratorio().getNomeLaboratorio() };
			modelo.addRow(novaLinha);
		}
	}
}
