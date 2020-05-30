package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.ControllerProduto;
import model.vo.Categoria;
import model.vo.Produto;
import net.miginfocom.swing.MigLayout;
import util.JNumberFormatField;
import util.JTextFieldLimit;

public class CadastroProduto extends JInternalFrame {
	private JTextField txtNome;
	private JNumberFormatField txtPrecoVenda;
	private JTextField txtEstoque;
	private JTextField txtCodBar;
	private JComboBox<Categoria> cmbCategoria;
	private ArrayList<Categoria> listaCategorias;
	private Produto produto;
	private JTextField txtPrecoCusto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroProduto frame = new CadastroProduto(null);
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
	public CadastroProduto(Produto produtoSelecionado) {
		setBorder(new LineBorder(new Color(192, 192, 192), 3));
		setFrameIcon(new ImageIcon(CadastroProduto.class.getResource("/icons/product.png")));
		getContentPane().setBackground(Color.WHITE);
		setTitle("Cadastro de produto");
		setClosable(true);
		setBounds(100, 100, 380, 195);
		getContentPane().setLayout(new MigLayout("", "[grow][][]", "[][][][][][][]"));

		JLabel lblNome = new JLabel("Nome:");
		getContentPane().add(lblNome, "cell 0 0");

		JLabel lblEspaco = new JLabel("    ");
		lblEspaco.setEnabled(false);
		getContentPane().add(lblEspaco, "cell 1 0");

		JLabel lblCodbarras = new JLabel("C\u00F3d.barras:");
		getContentPane().add(lblCodbarras, "cell 2 0");

		JLabel lblAlteraoGeraSoma = new JLabel("Alteração gera soma do mesmo");
		lblAlteraoGeraSoma.setForeground(Color.RED);
		lblAlteraoGeraSoma.setFont(new Font("Tahoma", Font.PLAIN, 9));
		getContentPane().add(lblAlteraoGeraSoma, "cell 2 3");

		// Pre�o

		txtPrecoVenda = new JNumberFormatField(2);

		getContentPane().add(txtPrecoVenda, "flowx,cell 0 6,alignx left");
		txtPrecoVenda.setColumns(10);

		// Nome

		txtNome = new JTextField();

		txtNome.setDocument(new JTextFieldLimit(150));

		getContentPane().add(txtNome, "cell 0 1,growx");
		txtNome.setColumns(10);

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

		getContentPane().add(txtCodBar, "cell 2 1,growx,aligny top");
		txtCodBar.setColumns(10);

		JLabel lblCategoria = new JLabel("Categoria:");
		getContentPane().add(lblCategoria, "cell 0 2,alignx left");

		JLabel lblEstoque = new JLabel("Estoque:");
		getContentPane().add(lblEstoque, "cell 2 2");

		// Categoria

		consultarCategoria();

		cmbCategoria = new JComboBox();
		cmbCategoria.setBackground(Color.WHITE);
		cmbCategoria.setModel(new DefaultComboBoxModel(listaCategorias.toArray()));
		cmbCategoria.setSelectedIndex(-1);
		getContentPane().add(cmbCategoria, "cell 0 4,growx");

		// Estoque

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE))
					arg0.consume();
			}
		});

		getContentPane().add(txtEstoque, "cell 2 4,alignx left");
		txtEstoque.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(CadastroProduto.class.getResource("/icons/check.png")));
		btnSalvar.setPreferredSize(new Dimension(80, 30));
		btnSalvar.setBorder(new LineBorder(Color.gray, 2, true));
		btnSalvar.setBackground(Color.WHITE);
		btnSalvar.setOpaque(true);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					Produto produto = new Produto();
					produto.setCodBarra(txtCodBar.getText());
					produto.setNome(txtNome.getText());
					produto.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText().replace(",", ".")));
					produto.setPrecoCusto(Double.parseDouble(txtPrecoCusto.getText().replace(",", ".")));
					try {
						produto.setEstoque(Integer.parseInt(txtEstoque.getText().trim()));
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Verificar se o estoque foi preenchido");
					}

					if (cmbCategoria.getSelectedIndex() > -1) {
						Categoria cat = new Categoria();
						cat.setIdCategoria(listaCategorias.get(cmbCategoria.getSelectedIndex()).getIdCategoria());
						cat.setNomeCategoria(listaCategorias.get(cmbCategoria.getSelectedIndex()).getNomeCategoria());
						produto.setCategoria(cat);
					} else {
						JOptionPane.showMessageDialog(null, "Verificar se o Laboratório foi selecionado.");
					}

					ControllerProduto produtoController = new ControllerProduto();
					String mensagem = "";
					mensagem = produtoController.salvar(produto);
					if (mensagem.contains("sucesso")) {
						limparCampos();
					}
					JOptionPane.showMessageDialog(null, mensagem);
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Verificar se todas as caixas foram preenchidas");
				}
			}
		});

		getContentPane().add(btnSalvar, "cell 2 6,alignx right");

		JLabel lblPreco = new JLabel("Pre\u00E7o:");
		getContentPane().add(lblPreco, "flowx,cell 0 5");

		JLabel label = new JLabel("                        ");
		getContentPane().add(label, "cell 0 5");

		JLabel lblNewLabel = new JLabel("Preço de custo:");
		getContentPane().add(lblNewLabel, "cell 0 5");

		JLabel label_1 = new JLabel("            ");
		getContentPane().add(label_1, "cell 0 6");

		txtPrecoCusto = new JNumberFormatField(2);
		getContentPane().add(txtPrecoCusto, "cell 0 6");
		txtPrecoCusto.setColumns(10);

		if (produtoSelecionado != null) {
			this.produto = produtoSelecionado;
			this.preencherCampos();
			this.bloquearCamposEdicao();
			this.setTitle("Alterar Produto");
		}
	}

	private void bloquearCamposEdicao() {
		this.txtCodBar.setEnabled(false);
	}

	private void consultarCategoria() {
		ControllerProduto controllerProduto = new ControllerProduto();
		listaCategorias = controllerProduto.consultarCategoria();
	}

	public void limparCampos() {
		txtNome.setText("");
		txtCodBar.setText("");
		txtPrecoVenda.setText("");
		txtEstoque.setText("");
		cmbCategoria.setSelectedIndex(-1);
	}

	public void preencherCampos() {
		txtNome.setText(produto.getNome());
		txtCodBar.setText(produto.getCodBarra());
		DecimalFormat format = new DecimalFormat("0.00");
		txtPrecoVenda.setText(format.format(produto.getPrecoVenda()) + "");
		txtEstoque.setText(produto.getEstoque() + "");

		Optional<Categoria> categoriaSelecionado = listaCategorias.stream()
				.filter(cat -> cat.getIdCategoria() == produto.getCategoria().getIdCategoria()).findFirst();

		cmbCategoria.setSelectedItem(categoriaSelecionado.get());
	}

}
