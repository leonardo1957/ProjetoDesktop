package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import controller.ControllerUsuario;
import model.seletor.UsuarioSeletor;
import model.vo.Usuario;
import net.miginfocom.swing.MigLayout;

public class ListagemUsuario extends JInternalFrame {
	private JTextField txtNome;
	private JTable table;
	private List<Usuario> usuariosConsultados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListagemUsuario frame = new ListagemUsuario();
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
	public ListagemUsuario() {
		setFrameIcon(new ImageIcon(ListagemUsuario.class.getResource("/icons/network.png")));
		setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		setClosable(true);
		getContentPane().setBackground(Color.WHITE);
		setTitle("Listagem usuário");
		setBounds(100, 100, 800, 619);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][grow]"));

		JLabel lblNome = new JLabel("Nome:");
		getContentPane().add(lblNome, "cell 0 0,alignx left");

		table = new JTable();
		table.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		getContentPane().add(table, "cell 1 0 1 4,grow");

		txtNome = new JTextField();
		getContentPane().add(txtNome, "cell 0 1,alignx left");
		txtNome.setColumns(10);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setIcon(new ImageIcon(ListagemUsuario.class.getResource("/icons/search.png")));
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarUsuarios();
			}
		});
		getContentPane().add(btnPesquisar, "cell 0 2,alignx left");

	}

	private void pesquisarUsuarios() {

		ControllerUsuario controlador = new ControllerUsuario();
		UsuarioSeletor seletor = new UsuarioSeletor();

		List<Usuario> usuarios = controlador.listarUsuarios(seletor);

		usuarios = controlador.listarUsuarios(seletor);
		atualizarTabelaUsuarios(usuarios);
	}

	private void atualizarTabelaUsuarios(List<Usuario> usuarios) {
		// atualiza o atributo remediosConsultados
		usuariosConsultados = usuarios;

		// Limpa a tabela
		table.setModel(new DefaultTableModel(new String[][] { { "Id Usuario", "Nome", "Data de cadastro", "Nivel" }, },
				new String[] { "Id Usuario", "Nome", "Data de cadastro", "Nivel" }));

		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		DecimalFormat format = new DecimalFormat("0.00");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (Usuario usuario : usuariosConsultados) {
			String[] novaLinha = new String[] { usuario.getId() + "", usuario.getNome(),
					usuario.getDt_cadastro().toString(), usuario.getNivel().getDescricao() };
			modelo.addRow(novaLinha);
		}
		;

	}
}
