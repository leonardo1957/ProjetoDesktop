package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import model.vo.Usuario;
import net.miginfocom.swing.MigLayout;

public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDesktopPane desktopPane;
	CadastroMedicamento cadastroMedicamento = null;
	ListagemMedicamento pesquisaMedicamento = null;
	CadastroProduto cadastroProduto = null;
	ListagemProduto pesquisaProduto = null;
	Usuario usuarioTela;
	TelaVenda telaVenda = null;
	ListagemVenda listagemVenda = null;
	TelaSobre telaSobre = null;
	CadastroUsuario cadastroUsuario = null;
	ListagemUsuario pesquisaUsuario = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu menu = new Menu(null);
					menu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @param usuario
	 */
	public Menu(Usuario usuario) {
		usuarioTela = usuario;
		setTitle("NOAH PHARMACY");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/icons/logo.png")));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		UIManager.put("PopupMenu.border", new LineBorder(Color.LIGHT_GRAY));
		this.setBounds(0, 0, screenSize.width, screenSize.height - 35);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new MigLayout("", "[grow][]", "[grow][]"));

		ImageIcon icon = new ImageIcon(this.getClass().getResource("/icons/pharmacy_Sketch.png"));
		Image img = icon.getImage();

		desktopPane = new JDesktopPane() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6974299825891675087L;

			public void paintComponent(Graphics g) {
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			}
		};
		desktopPane.setBackground(Color.WHITE);
		this.getContentPane().add(desktopPane, "cell 0 0 2 2,grow");

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(245, 245, 245));
		this.setJMenuBar(menuBar);

		JMenu mnMedicamento = new JMenu("Medicamentos");
		mnMedicamento.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		mnMedicamento.setBackground(Color.WHITE);
		mnMedicamento.setIcon(new ImageIcon(Menu.class.getResource("/icons/med3x.png")));
		menuBar.add(mnMedicamento);

		JMenuItem mntmCadastrarRemedio = new JMenuItem("Cadastrar");
		mntmCadastrarRemedio.setIcon(new ImageIcon(Menu.class.getResource("/icons/espaco.png")));
		mntmCadastrarRemedio.setBorder(new LineBorder(Color.GRAY, 1));
		mntmCadastrarRemedio.setBackground(Color.WHITE);
		mntmCadastrarRemedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!temComponenteNaTela(cadastroMedicamento)) {
					cadastroMedicamento = new CadastroMedicamento(null);
				}

				desktopPane.add(cadastroMedicamento);
				// desktopPane.getDesktopManager().maximizeFrame(cadastroMedicamento);
				cadastroMedicamento.show();

				cadastroMedicamento.addInternalFrameListener(new InternalFrameAdapter() {
					@Override
					public void internalFrameClosing(InternalFrameEvent evt) {
						Menu pai = (Menu) SwingUtilities.getWindowAncestor(cadastroMedicamento);
						pai.chamarPai(cadastroMedicamento);
					}
				});
			}
		});
		mnMedicamento.add(mntmCadastrarRemedio);

		JMenuItem mntmPesquisarRemedio = new JMenuItem("Pesquisar");
		mntmPesquisarRemedio.setIcon(new ImageIcon(Menu.class.getResource("/icons/espaco.png")));
		mntmPesquisarRemedio.setBorder(new LineBorder(Color.GRAY, 1));
		mntmPesquisarRemedio.setBackground(Color.WHITE);
		mntmPesquisarRemedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!temComponenteNaTela(pesquisaMedicamento)) {
					pesquisaMedicamento = new ListagemMedicamento();
					desktopPane.add(pesquisaMedicamento);
					// desktopPane.getDesktopManager().maximizeFrame(pesquisaMedicamento);
					pesquisaMedicamento.show();

					pesquisaMedicamento.addInternalFrameListener(new InternalFrameAdapter() {
						@Override
						public void internalFrameClosing(InternalFrameEvent evt) {
							Menu pai = (Menu) SwingUtilities.getWindowAncestor(pesquisaMedicamento);
							pai.chamarPai(pesquisaMedicamento);
						}
					});
				}
			}
		});
		mnMedicamento.add(mntmPesquisarRemedio);

		// espaco entre os botoes
		JMenu menuSpace = new JMenu("  ");
		menuSpace.setEnabled(false);
		menuBar.add(menuSpace);

		JMenu mnProdutos = new JMenu("Produtos");
		mnProdutos.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		mnProdutos.setBackground(Color.LIGHT_GRAY);
		mnProdutos.setIcon(new ImageIcon(Menu.class.getResource("/icons/product.png")));
		menuBar.add(mnProdutos);

		JMenuItem mntmCadastrarProduto = new JMenuItem("Cadastrar");
		mntmCadastrarProduto.setIcon(new ImageIcon(Menu.class.getResource("/icons/espaco.png")));
		mntmCadastrarProduto.setBorder(new LineBorder(Color.GRAY, 1));
		mntmCadastrarProduto.setBackground(Color.WHITE);
		mntmCadastrarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!temComponenteNaTela(pesquisaMedicamento)) {
					cadastroProduto = new CadastroProduto(null);
					desktopPane.add(cadastroProduto);
					// desktopPane.getDesktopManager().maximizeFrame(pesquisaMedicamento);
					cadastroProduto.show();

					cadastroProduto.addInternalFrameListener(new InternalFrameAdapter() {
						@Override
						public void internalFrameClosing(InternalFrameEvent evt) {
							Menu pai = (Menu) SwingUtilities.getWindowAncestor(cadastroProduto);
							pai.chamarPai(cadastroProduto);
						}
					});
				}
			}
		});
		mnProdutos.add(mntmCadastrarProduto);

		JMenuItem mntmPesquisarProduto = new JMenuItem("Pesquisar");
		mntmPesquisarProduto.setIcon(new ImageIcon(Menu.class.getResource("/icons/espaco.png")));
		mntmPesquisarProduto.setBorder(new LineBorder(Color.GRAY, 1));
		mntmPesquisarProduto.setBackground(Color.WHITE);
		mntmPesquisarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!temComponenteNaTela(pesquisaProduto)) {
					pesquisaProduto = new ListagemProduto();
					desktopPane.add(pesquisaProduto);
					// desktopPane.getDesktopManager().maximizeFrame(pesquisaMedicamento);
					pesquisaProduto.show();

					pesquisaProduto.addInternalFrameListener(new InternalFrameAdapter() {
						@Override
						public void internalFrameClosing(InternalFrameEvent evt) {
							Menu pai = (Menu) SwingUtilities.getWindowAncestor(pesquisaProduto);
							pai.chamarPai(pesquisaProduto);
						}
					});
				}
			}
		});
		mnProdutos.add(mntmPesquisarProduto);

		JMenu mnVendas = new JMenu("Vendas");
		mnVendas.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		mnVendas.setBackground(Color.LIGHT_GRAY);
		mnVendas.setIcon(new ImageIcon(Menu.class.getResource("/icons/cart (2).png")));
		/*
		 * mnVendas.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent evt) { if
		 * (!temComponenteNaTela(telaVenda)) { telaVenda = new TelaVenda();
		 * desktopPane.add(telaVenda); //
		 * desktopPane.getDesktopManager().maximizeFrame(telaVenda); telaVenda.show();
		 * 
		 * telaVenda.addInternalFrameListener(new InternalFrameAdapter() {
		 * 
		 * @Override public void internalFrameClosing(InternalFrameEvent evt) { Menu pai
		 * = (Menu) SwingUtilities.getWindowAncestor(telaVenda);
		 * pai.chamarPai(telaVenda); } }); } } });
		 */

		// espaco entre os botoes
		JMenu menuSpace2 = new JMenu("  ");
		menuSpace2.setEnabled(false);
		menuBar.add(menuSpace2);

		menuBar.add(mnVendas);

		JMenuItem mntmNovaVenda = new JMenuItem("Nova Venda");
		mntmNovaVenda.setBorder(new LineBorder(Color.GRAY, 1));
		mntmNovaVenda.setBackground(Color.WHITE);
		mntmNovaVenda.setIcon(new ImageIcon(Menu.class.getResource("/icons/espaco.png")));
		mntmNovaVenda.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!temComponenteNaTela(telaVenda)) {
					telaVenda = new TelaVenda(usuarioTela);
					desktopPane.add(telaVenda);
					// desktopPane.getDesktopManager().maximizeFrame(telaVenda);
					telaVenda.show();

					telaVenda.addInternalFrameListener(new InternalFrameAdapter() {
						@Override
						public void internalFrameClosing(InternalFrameEvent evt) {
							Menu pai = (Menu) SwingUtilities.getWindowAncestor(telaVenda);
							pai.chamarPai(telaVenda);
						}
					});
				}
			}
		});
		mnVendas.add(mntmNovaVenda);

		JMenuItem mntmRelatorio = new JMenuItem("Relatório");
		mntmRelatorio.setBorder(new LineBorder(Color.GRAY, 1));
		mntmRelatorio.setBackground(Color.WHITE);
		mntmRelatorio.setIcon(new ImageIcon(Menu.class.getResource("/icons/espaco.png")));
		mntmRelatorio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!temComponenteNaTela(listagemVenda)) {
					listagemVenda = new ListagemVenda();
					desktopPane.add(listagemVenda);
					// desktopPane.getDesktopManager().maximizeFrame(telaVenda);
					listagemVenda.show();

					listagemVenda.addInternalFrameListener(new InternalFrameAdapter() {
						@Override
						public void internalFrameClosing(InternalFrameEvent evt) {
							Menu pai = (Menu) SwingUtilities.getWindowAncestor(listagemVenda);
							pai.chamarPai(listagemVenda);
						}
					});
				}
			}
		});
		mnVendas.add(mntmRelatorio);

		JMenu mnSobre = new JMenu("Sobre");
		mnSobre.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		mnSobre.setBackground(Color.LIGHT_GRAY);
		mnSobre.setIcon(new ImageIcon(Menu.class.getResource("/icons/info.png")));
		mnSobre.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (!temComponenteNaTela(telaSobre)) {
					telaSobre = new TelaSobre();
					desktopPane.add(telaSobre);
					// desktopPane.getDesktopManager().maximizeFrame(pesquisaMedicamento);
					telaSobre.show();

					telaSobre.addInternalFrameListener(new InternalFrameAdapter() {
						@Override
						public void internalFrameClosing(InternalFrameEvent evt) {
							Menu pai = (Menu) SwingUtilities.getWindowAncestor(telaSobre);
							pai.chamarPai(telaSobre);
						}
					});
				}
			}
		});

		// espaco entre os botoes
		JMenu menuSpace3 = new JMenu("  ");
		menuSpace3.setEnabled(false);
		menuBar.add(menuSpace3);

		JMenu mnUsuario = new JMenu("Usuário");
		mnUsuario.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		mnUsuario.setBackground(Color.LIGHT_GRAY);
		mnUsuario.setIcon(new ImageIcon(Menu.class.getResource("/icons/network.png")));
		menuBar.add(mnUsuario);

		JMenuItem mntmCadastrar = new JMenuItem("Cadastrar");
		mntmCadastrar.setBorder(new LineBorder(Color.GRAY, 1));
		mntmCadastrar.setBackground(Color.WHITE);
		mntmCadastrar.setIcon(new ImageIcon(Menu.class.getResource("/icons/espaco.png")));
		mntmCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!temComponenteNaTela(cadastroUsuario)) {
					cadastroUsuario = new CadastroUsuario();
					desktopPane.add(cadastroUsuario);
					// desktopPane.getDesktopManager().maximizeFrame(telaVenda);
					cadastroUsuario.show();

					cadastroUsuario.addInternalFrameListener(new InternalFrameAdapter() {
						@Override
						public void internalFrameClosing(InternalFrameEvent evt) {
							Menu pai = (Menu) SwingUtilities.getWindowAncestor(cadastroUsuario);
							pai.chamarPai(cadastroUsuario);
						}
					});
				}
			}
		});
		mnUsuario.add(mntmCadastrar);

		JMenuItem mntmListar = new JMenuItem("Listar");
		mntmListar.setBorder(new LineBorder(Color.GRAY, 1));
		mntmListar.setBackground(Color.WHITE);
		mntmListar.setIcon(new ImageIcon(Menu.class.getResource("/icons/espaco.png")));
		mntmListar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!temComponenteNaTela(pesquisaUsuario)) {
					pesquisaUsuario = new ListagemUsuario();
					desktopPane.add(pesquisaUsuario);
					// desktopPane.getDesktopManager().maximizeFrame(telaVenda);
					pesquisaUsuario.show();

					pesquisaUsuario.addInternalFrameListener(new InternalFrameAdapter() {
						@Override
						public void internalFrameClosing(InternalFrameEvent evt) {
							Menu pai = (Menu) SwingUtilities.getWindowAncestor(pesquisaUsuario);
							pai.chamarPai(pesquisaUsuario);
						}
					});
				}
			}
		});
		mnUsuario.add(mntmListar);

		JMenu menu = new JMenu("  ");
		menu.setEnabled(false);
		menuBar.add(menu);

		menuBar.add(mnSobre);

		if (usuarioTela != null) {
			switch (usuarioTela.getNivel().getId()) {
			case 1:

			case 2:

			case 3:

			}
		}
	}

	public boolean temComponenteNaTela(Object frame) {
		ArrayList<Component> componentes = new ArrayList<Component>(Arrays.asList(desktopPane.getComponents()));
		return (componentes.contains(frame));
	}

	public void chamarPai(JInternalFrame telaFilho) {
		telaFilho = null;
	}

	public void mostrarTelaFilho() {

	}

	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public void setDesktopPane(JDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}
}
