package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.ControllerUsuario;
import model.vo.Nivel;
import model.vo.Usuario;
import net.miginfocom.swing.MigLayout;

public class CadastroUsuario extends JInternalFrame {
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JComboBox<Nivel> cmbNivel;
	private ArrayList<Nivel> listaNiveis;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroUsuario frame = new CadastroUsuario();
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
	public CadastroUsuario() {
		setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		setClosable(true);
		getContentPane().setBackground(Color.WHITE);
		setFrameIcon(new ImageIcon(CadastroUsuario.class.getResource("/icons/network.png")));
		setTitle("Cadastro Usuário");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[grow][][grow]", "[10px:n,center][][][][][][][][][]"));

		JLabel lblNome = new JLabel("Nome:");
		getContentPane().add(lblNome, "cell 0 1,alignx left");

		JLabel lblLogin = new JLabel("Login:");
		getContentPane().add(lblLogin, "cell 2 1,alignx left");

		txtNome = new JTextField();
		getContentPane().add(txtNome, "cell 0 2,growx");
		txtNome.setColumns(10);

		txtLogin = new JTextField();
		getContentPane().add(txtLogin, "cell 2 2,growx");
		txtLogin.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		getContentPane().add(lblSenha, "cell 0 4,alignx left");

		JLabel lblNivel = new JLabel("Nível:");
		getContentPane().add(lblNivel, "cell 2 4,alignx left");

		txtSenha = new JPasswordField();
		getContentPane().add(txtSenha, "cell 0 5,growx");
		txtSenha.setColumns(10);

		consultarNiveis();

		cmbNivel = new JComboBox<Nivel>();
		cmbNivel.setBackground(Color.WHITE);
		cmbNivel.setModel(new DefaultComboBoxModel(listaNiveis.toArray()));
		cmbNivel.setSelectedIndex(-1);
		getContentPane().add(cmbNivel, "cell 2 5,growx");

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(CadastroMedicamento.class.getResource("/icons/check.png")));
		btnSalvar.setBackground(Color.WHITE);
		btnSalvar.setOpaque(true);
		btnSalvar.setPreferredSize(new Dimension(80, 30));
		btnSalvar.setBorder(new LineBorder(Color.gray, 2, true));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					Usuario usuario = new Usuario();
					usuario.setNome(txtNome.getText());
					usuario.setLogin(txtLogin.getText());
					usuario.setSenha(txtSenha.getText());
					if (cmbNivel.getSelectedIndex() > -1) {
						Nivel nivel = new Nivel();
						nivel.setId(listaNiveis.get(cmbNivel.getSelectedIndex()).getId());
						nivel.setDescricao(listaNiveis.get(cmbNivel.getSelectedIndex()).getDescricao());
						usuario.setNivel(nivel);
					} else {
						JOptionPane.showMessageDialog(null, "Verificar se o Nivel foi selecionado.");
					}
					ControllerUsuario controllerUsuario = new ControllerUsuario();
					String mensagem = "";

					mensagem = controllerUsuario.salvarUsuario(usuario);
					if (mensagem.contains("sucesso")) {
						limparCampos();
					}
					JOptionPane.showMessageDialog(null, mensagem);
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Verificar se todas as caixas foram preenchidas");
				}
			}
		});
		getContentPane().add(btnSalvar, "cell 2 9,alignx right");

	}

	private void consultarNiveis() {
		ControllerUsuario controllerUsuario = new ControllerUsuario();
		listaNiveis = controllerUsuario.consultarNiveis();
	}

	public void limparCampos() {
		txtNome.setText("");
		txtLogin.setText("");
		txtSenha.setText("");
		cmbNivel.setSelectedIndex(-1);
	}
}
