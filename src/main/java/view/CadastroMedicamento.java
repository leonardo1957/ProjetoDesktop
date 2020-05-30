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
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.ControllerRemedio;
import model.vo.FormaUso;
import model.vo.Laboratorio;
import model.vo.Remedio;
import net.miginfocom.swing.MigLayout;
import util.JNumberFormatField;
import util.JTextFieldLimit;

public class CadastroMedicamento extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNome;
	private JTextField txtCodBar;
	private JNumberFormatField txtPrecoVenda;
	private JTextField txtComposicao;
	private JTextField txtDosagem;
	private JTextField txtEstoque;
	private JComboBox<Laboratorio> cmbLaboratorio;
	private JComboBox<FormaUso> cmbFormaUso;
	private JCheckBox chckbxGenerico;
	private ArrayList<Laboratorio> listaLaboratorios;
	private ArrayList<FormaUso> listaFormasUso;
	protected Scanner teclado = new Scanner(System.in);
	private Remedio remedio;
	private JTextField txtPrecoCusto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroMedicamento frame = new CadastroMedicamento(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. INSERT
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CadastroMedicamento(Remedio remedioSelecionado) {
		setFrameIcon(new ImageIcon(CadastroMedicamento.class.getResource("/icons/med3x.png")));
		getContentPane().setBackground(Color.WHITE);
		setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		setTitle("Cadastro de medicamentos");
		setClosable(true);

		setBounds(100, 100, 420, 315);
		getContentPane().setLayout(new MigLayout("", "[][][grow]", "[10px:n][][][][][][][][][][][][]"));

		JLabel lblNome = new JLabel("Nome:");
		getContentPane().add(lblNome, "cell 0 1");

		JLabel lblEspaco = new JLabel("    ");
		getContentPane().add(lblEspaco, "cell 1 1");

		JLabel lblCodbarras = new JLabel("C\u00F3d.barras:");
		getContentPane().add(lblCodbarras, "cell 2 1");

		txtNome = new JTextField();
		txtNome.setDocument(new JTextFieldLimit(150));

		getContentPane().add(txtNome, "cell 0 2,growx");
		txtNome.setColumns(10);

		JLabel lblValidacaoTxtCodBar = new JLabel(" ");
		lblValidacaoTxtCodBar.setForeground(Color.RED);

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

		getContentPane().add(txtCodBar, "cell 2 2,growx");
		txtCodBar.setColumns(10);

		getContentPane().add(lblValidacaoTxtCodBar, "cell 2 3,alignx left");

		JLabel lblDosagem = new JLabel("Dosagem:");
		getContentPane().add(lblDosagem, "flowx,cell 0 4");

		JLabel lblPreoDeCusto = new JLabel("Preço de custo:");
		getContentPane().add(lblPreoDeCusto, "cell 2 4");

		txtDosagem = new JTextField();
		txtDosagem.setDocument(new JTextFieldLimit(15));

		getContentPane().add(txtDosagem, "flowx,cell 0 5,alignx left");
		txtDosagem.setColumns(10);

		txtPrecoCusto = new JNumberFormatField(2);
		getContentPane().add(txtPrecoCusto, "cell 2 5,growx");
		txtPrecoCusto.setColumns(10);

		JLabel lblValidacaoTxtDosagem = new JLabel(" ");
		lblValidacaoTxtDosagem.setForeground(Color.RED);
		getContentPane().add(lblValidacaoTxtDosagem, "cell 2 6");

		consultarLaboratorio();

		JLabel lblComposicao = new JLabel("Composicão:");
		getContentPane().add(lblComposicao, "cell 0 7");

		JLabel lblLaboratorio = new JLabel("Laboratório:");
		getContentPane().add(lblLaboratorio, "cell 2 7");

		txtComposicao = new JTextField();
		txtComposicao.setDocument(new JTextFieldLimit(100));

		getContentPane().add(txtComposicao, "cell 0 8,growx");
		txtComposicao.setColumns(10);

		cmbLaboratorio = new JComboBox<Laboratorio>();
		cmbLaboratorio.setBackground(Color.WHITE);
		cmbLaboratorio.setModel(new DefaultComboBoxModel(listaLaboratorios.toArray()));
		cmbLaboratorio.setSelectedIndex(-1);
		getContentPane().add(cmbLaboratorio, "cell 2 8,growx");

		consultarFormaUso();

		JLabel lblEspaco4 = new JLabel(" ");
		lblEspaco4.setForeground(Color.RED);
		getContentPane().add(lblEspaco4, "cell 2 9");

		JLabel lblFormaUso = new JLabel("Forma de uso:");
		getContentPane().add(lblFormaUso, "flowx,cell 0 10,alignx left");

		cmbFormaUso = new JComboBox();
		cmbFormaUso.setBackground(Color.WHITE);
		cmbFormaUso.setModel(new DefaultComboBoxModel(listaFormasUso.toArray()));
		cmbFormaUso.setSelectedIndex(-1);
		getContentPane().add(cmbFormaUso, "flowx,cell 0 11,growx");

		JLabel labelEspaco2 = new JLabel("    ");
		getContentPane().add(labelEspaco2, "cell 0 11");

		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char vchar = arg0.getKeyChar();
				if (!(Character.isDigit(vchar)) || (vchar == KeyEvent.VK_BACK_SPACE) || (vchar == KeyEvent.VK_DELETE))
					arg0.consume();
			}
		});

		getContentPane().add(txtEstoque, "cell 0 11,growx");
		txtEstoque.setColumns(10);

		JLabel lblEstoque = new JLabel("    Estoque:");
		getContentPane().add(lblEstoque, "cell 0 10,alignx right");

		chckbxGenerico = new JCheckBox("Gen\u00E9rico");
		chckbxGenerico.setBackground(Color.WHITE);
		getContentPane().add(chckbxGenerico, "cell 0 11");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(CadastroMedicamento.class.getResource("/icons/check.png")));
		btnSalvar.setBackground(Color.WHITE);
		btnSalvar.setOpaque(true);
		btnSalvar.setPreferredSize(new Dimension(80, 30));
		btnSalvar.setBorder(new LineBorder(Color.gray, 2, true));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					Remedio remedio = new Remedio();
					remedio.setNome(txtNome.getText());
					remedio.setCodBarra(txtCodBar.getText());
					remedio.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText().replace(",", ".")));
					remedio.setPrecoCusto(Double.parseDouble(txtPrecoCusto.getText().replace(",", ".")));
					remedio.setComposicao(txtComposicao.getText());
					remedio.setDosagem(txtDosagem.getText());

					if (cmbLaboratorio.getSelectedIndex() > -1) {
						Laboratorio lab = new Laboratorio();
						lab.setIdLaboratorio(
								listaLaboratorios.get(cmbLaboratorio.getSelectedIndex()).getIdLaboratorio());
						lab.setNomeLaboratorio(
								listaLaboratorios.get(cmbLaboratorio.getSelectedIndex()).getNomeLaboratorio());
						remedio.setLaboratorio(lab);
					} else {
						JOptionPane.showMessageDialog(null, "Verificar se o Laboratório foi selecionado.");
					}

					try {
						remedio.setEstoque(Integer.parseInt(txtEstoque.getText().trim()));
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Verificar se o estoque foi preenchido");
					}
					if (cmbFormaUso.getSelectedIndex() > -1) {
						FormaUso formaUso = new FormaUso();
						formaUso.setIdFormaUso(listaFormasUso.get(cmbLaboratorio.getSelectedIndex()).getIdFormaUso());
						formaUso.setDescricao(listaFormasUso.get(cmbLaboratorio.getSelectedIndex()).getDescricao());
						remedio.setFormaUso(formaUso);

					} else {
						JOptionPane.showMessageDialog(null, "Verificar se a forma de uso foi selecionada.");
					}
					remedio.setGenerico(chckbxGenerico.isSelected());

					ControllerRemedio controllerRemedio = new ControllerRemedio();
					String mensagem = "";

					mensagem = controllerRemedio.salvar(remedio);
					if (mensagem.contains("sucesso")) {
						limparCampos();
					}
					JOptionPane.showMessageDialog(null, mensagem);
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Verificar se todas as caixas foram preenchidas");
				}
			}
		});
		getContentPane().add(btnSalvar, "cell 2 11,alignx right");

		JLabel lblAlteraoGeraSoma = new JLabel("Alteração gera soma do mesmo");
		lblAlteraoGeraSoma.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblAlteraoGeraSoma.setForeground(Color.RED);
		getContentPane().add(lblAlteraoGeraSoma, "cell 0 10");

		JLabel lblNewLabel = new JLabel("");
		getContentPane().add(lblNewLabel, "cell 0 5,growx");

		txtPrecoVenda = new JNumberFormatField(2);
		getContentPane().add(txtPrecoVenda, "cell 0 5,alignx right");
		txtPrecoVenda.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("");
		getContentPane().add(lblNewLabel_1, "cell 0 4,growx");

		JLabel lblPreco = new JLabel("Preço:                          ");
		getContentPane().add(lblPreco, "cell 0 4");

		if (remedioSelecionado != null) {
			this.remedio = remedioSelecionado;
			this.preencherCampos();
			this.bloquearCamposEdicao();
			this.setTitle("Alterar Medicamento");
		}
	}

	private void bloquearCamposEdicao() {
		this.txtCodBar.setEnabled(false);
	}

	private void consultarLaboratorio() {
		ControllerRemedio controllerRemedio = new ControllerRemedio();
		listaLaboratorios = controllerRemedio.consultarLaboratorio();
	}

	private void consultarFormaUso() {
		ControllerRemedio controllerRemedio = new ControllerRemedio();
		listaFormasUso = controllerRemedio.consultarFormaUso();
	}

	public void limparCampos() {
		txtNome.setText("");
		txtCodBar.setText("");
		txtPrecoVenda.setText("");
		txtPrecoCusto.setText("");
		txtComposicao.setText("");
		txtDosagem.setText("");
		txtEstoque.setText("");
		cmbLaboratorio.setSelectedIndex(-1);
		cmbFormaUso.setSelectedIndex(-1);
		chckbxGenerico.setSelected(isClosed);
	}

	public void preencherCampos() {
		txtCodBar.setText(remedio.getCodBarra());
		txtNome.setText(remedio.getNome());
		txtDosagem.setText(remedio.getDosagem());
		DecimalFormat format = new DecimalFormat("0.00");
		txtPrecoVenda.setText(format.format(remedio.getPrecoVenda()) + "");
		txtPrecoCusto.setText(format.format(remedio.getPrecoCusto()) + "");
		txtComposicao.setText(remedio.getComposicao());

		// Usando calculo Lambda ;)
		Optional<Laboratorio> laboratorioSelecionado = listaLaboratorios.stream()
				.filter(lab -> lab.getIdLaboratorio() == remedio.getLaboratorio().getIdLaboratorio()).findFirst();

		cmbLaboratorio.setSelectedItem(laboratorioSelecionado.get());

		Optional<FormaUso> formaUsoSelecionado = listaFormasUso.stream()
				.filter(fu -> fu.getIdFormaUso() == remedio.getFormaUso().getIdFormaUso()).findFirst();

		cmbFormaUso.setSelectedItem(formaUsoSelecionado.get());
		txtEstoque.setText(remedio.getEstoque() + "");
		chckbxGenerico.setSelected(remedio.isGenerico());
	}
}