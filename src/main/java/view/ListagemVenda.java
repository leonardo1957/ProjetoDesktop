package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.ControllerProduto;
import controller.ControllerVenda;
import model.seletor.VendaSeletor;
import model.vo.Venda;
import net.miginfocom.swing.MigLayout;
import util.JNumberFormatField;
import util.JTextFieldLimit;

public class ListagemVenda extends JInternalFrame {
	private JTable tblVendas;
	private JLabel lblPaginaAtual;
	private JLabel lbMax;
	private JTextField txtId;
	private JTextField txtValorMin;
	private JTextField txtValorMax;
	// private JTextField txtDataMin;
	// private JTextField txtDataMax;
	private JButton btnProximo;
	private JButton btnAnterior;
	private JFormattedTextField txtDataMin;
	private JFormattedTextField txtDataMax;
	private List<Venda> vendasConsultadas;

	private int paginaTotal = 1;
	private int paginaAtual = 1;
	private int totalPaginas = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListagemVenda frame = new ListagemVenda();
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
	public ListagemVenda() {
		setTitle("Relatório de venda");
		setFrameIcon(new ImageIcon(ListagemVenda.class.getResource("/icons/cart (2).png")));
		setResizable(true);
		setBorder(new LineBorder(new Color(192, 192, 192), 3));
		setClosable(true);
		setBounds(100, 100, 600, 470);
		getContentPane().setLayout(new MigLayout("", "[grow][][grow]", "[][][][][][][][][][][][grow][]"));

		JLabel lblId = new JLabel("Id");
		getContentPane().add(lblId, "flowx,cell 0 0,growx");

		tblVendas = new JTable();
		tblVendas.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { " ", "Codigo", "Valor", "Data da venda" }));
		tblVendas.getColumnModel().getColumn(0).setPreferredWidth(15);
		tblVendas.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		getContentPane().add(tblVendas, "flowx,cell 2 0 1 12,grow");

		txtId = new JTextField();
		getContentPane().add(txtId, "flowx,cell 0 1");
		txtId.setColumns(10);

		JLabel lblDataMinima = new JLabel("Data min. (DD/MM/AAAA)");
		getContentPane().add(lblDataMinima, "cell 0 2");

		JLabel lblEspaco = new JLabel(" ");
		getContentPane().add(lblEspaco, "cell 1 2");
		try {
			txtDataMin = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		txtDataMin.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent arg0) {
//				char vchar = arg0.getKeyChar();
//				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE))
//					arg0.consume();
//			}
		});

		txtDataMin.setDocument(new JTextFieldLimit(10));
		getContentPane().add(txtDataMin, "cell 0 3,growx");

		JLabel lblDataMax = new JLabel("Data max. (DD/MM/AAAA)");
		getContentPane().add(lblDataMax, "cell 0 4");

		JLabel lblEspaco_1 = new JLabel(" ");
		getContentPane().add(lblEspaco_1, "cell 2 2");

		try {
			txtDataMax = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtDataMax.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent arg0) {
//				char vchar = arg0.getKeyChar();
//				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE))
//					arg0.consume();
//			}
		});
		// dataMax.setDate(Calendar.getInstance().getTime());
		txtDataMax.setDocument(new JTextFieldLimit(10));
		getContentPane().add(txtDataMax, "cell 0 5,growx");

		JLabel lblValorMin = new JLabel("Valor min.");
		getContentPane().add(lblValorMin, "cell 0 6");

		txtValorMin = new JNumberFormatField(2);
		getContentPane().add(txtValorMin, "cell 0 7,growx");
		txtValorMin.setColumns(10);

		JLabel lblValorMax = new JLabel("Valor max.");
		getContentPane().add(lblValorMax, "cell 0 8");

		txtValorMax = new JNumberFormatField(2);
		getContentPane().add(txtValorMax, "cell 0 9,growx");
		txtValorMax.setColumns(10);

		JButton btnGerarLista = new JButton("Gerar lista");
		btnGerarLista.setIcon(new ImageIcon(ListagemVenda.class.getResource("/icons/search.png")));
		btnGerarLista.setBorder(new LineBorder(Color.gray, 2, true));
		btnGerarLista.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGerarLista.setBackground(Color.WHITE);
		btnGerarLista.setPreferredSize(new Dimension(80, 30));
		btnGerarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarVendas();
				if (paginaAtual != totalPaginas) {
					btnProximo.setEnabled(true);
				}
			}
		});
		getContentPane().add(btnGerarLista, "flowx,cell 0 10,alignx center");

		JButton btnGerarXls = new JButton("Relatório");
		btnGerarXls.setBorder(new LineBorder(Color.gray, 2, true));
		btnGerarXls.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGerarXls.setBackground(Color.WHITE);
		btnGerarXls.setPreferredSize(new Dimension(80, 30));
		btnGerarXls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Salvar...");

				int resultado = jfc.showSaveDialog(null);
				if (resultado == JFileChooser.APPROVE_OPTION) {
					String caminhoEscolhido = jfc.getSelectedFile().getAbsolutePath();

					ControllerVenda vendaController = new ControllerVenda();
					vendaController.gerarRelatorio(vendasConsultadas, caminhoEscolhido,
							ControllerProduto.TIPO_RELATORIO_XLS);
				}
			}
		});
		getContentPane().add(btnGerarXls, "cell 0 10,alignx center");

		btnProximo = new JButton("Proximo>");
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
				listarVendas();
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
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual++;
				if (paginaAtual == totalPaginas) {
					btnProximo.setEnabled(false);
				}
				btnAnterior.setEnabled(true);
				listarVendas();
			}
		});
		getContentPane().add(btnProximo, "cell 2 12,alignx center");

		JButton btnCancelarVenda = new JButton("Cancelar Venda");
		btnCancelarVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ControllerVenda vendaController = new ControllerVenda();
				String mensagem = vendaController.cancelarVenda(vendasConsultadas.get(tblVendas.getSelectedRow() - 1));
				JOptionPane.showMessageDialog(null, mensagem);
			}
		});
		btnCancelarVenda.setForeground(Color.RED);
		btnCancelarVenda.setBorder(new LineBorder(Color.gray, 2, true));
		btnCancelarVenda.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelarVenda.setBackground(Color.WHITE);
		btnCancelarVenda.setPreferredSize(new Dimension(80, 30));
		getContentPane().add(btnCancelarVenda, "cell 0 10");

	}

	private void listarVendas() {
		lblPaginaAtual.setText(paginaAtual + "");

		ControllerVenda controlador = new ControllerVenda();
		VendaSeletor seletor = new VendaSeletor();

		List<Venda> vendas = controlador.listarVendas(seletor);

		seletor.setLimite(10);

		int quociente = vendas.size() / seletor.getLimite();
		int resto = vendas.size() % seletor.getLimite();
		DecimalFormat format = new DecimalFormat("0.00");

		if (resto == 0) {
			totalPaginas = quociente;
		} else {
			totalPaginas = quociente + 1;
		}
		lbMax.setText(totalPaginas + "");

		if (!txtId.getText().trim().equals("")) {
			seletor.setId(Integer.parseInt(txtId.getText()));
		}

		if (!txtValorMin.getText().equals("") || txtValorMin.getText().equals("0.00")) {
			seletor.setValorMenor(Double.parseDouble(txtValorMin.getText().replace(",", ".")));
		}

		if (!txtValorMax.getText().equals("") || txtValorMax.getText().equals("0.00")) {
			seletor.setValorMaior(Double.parseDouble(txtValorMax.getText().replace(",", ".")));
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (!txtDataMin.getText().isEmpty()) {
			Date dataMenor;
			try {
				dataMenor = sdf.parse(txtDataMin.getText());
				seletor.setDataMenor(dataMenor);
			} catch (ParseException e) {
				// TODO tratar a mensagem
				e.printStackTrace();
			}
		}

		if (!txtDataMax.getText().isEmpty()) {
			Date dataMaior;
			try {
				dataMaior = sdf.parse(txtDataMax.getText());
				seletor.setDataMaior(dataMaior);
			} catch (ParseException e) {
				// TODO tratar a mensagem
				e.printStackTrace();
			}
		}

		seletor.setPagina(paginaAtual);

		vendas = controlador.listarVendas(seletor);
		atualizarTabelaVendas(vendas);
	}

	private void atualizarTabelaVendas(List<Venda> vendas) {
		vendasConsultadas = vendas;

		// btnGerarXls.setEnabled(vendas != null && vendas.size() > 0);

		tblVendas.setModel(new DefaultTableModel(new String[][] { { "Codigo", "Valor", "Data da venda" }, },
				new String[] { "Codigo", "Valor", "Data da venda" }));

		DefaultTableModel modelo = (DefaultTableModel) tblVendas.getModel();

		for (Venda venda : vendas) {

			DecimalFormat format = new DecimalFormat("0.00");
			String[] novaLinha = new String[] { venda.getIdVenda() + "", "R$" + format.format(venda.getValor()),
					String.valueOf(venda.getDataVenda()) };
			modelo.addRow(novaLinha);
		}
	}
}
