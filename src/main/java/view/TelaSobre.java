package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class TelaSobre extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaSobre frame = new TelaSobre();
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
	public TelaSobre() {
		setTitle("Sobre");
		setClosable(true);
		setBackground(Color.WHITE);
		setFrameIcon(new ImageIcon(TelaSobre.class.getResource("/icons/network.png")));
		setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		setBounds(500, 100, 1282, 496);
		getContentPane().setLayout(new MigLayout("", "[grow,fill]", "[][][][][][][]"));

		JLabel lblDesenvolvedores = new JLabel("Desenvolvedores e seus cargos (fiquem em casa nesse momento dificil):");
		lblDesenvolvedores.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesenvolvedores.setFont(new Font("Tahoma", Font.BOLD, 35));
		getContentPane().add(lblDesenvolvedores, "cell 0 0,alignx center");

		JLabel lblLeo = new JLabel("Leonardo Vieira - Qualirede Dev ");
		lblLeo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblLeo.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblLeo, "cell 0 1,alignx center");

		JLabel lblKalleo = new JLabel("Kalleo Agostinho ");
		lblKalleo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblKalleo.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblKalleo, "cell 0 2,alignx center");

		JLabel lblLucas = new JLabel("Lucas Hermann - Orsegups Analista");
		lblLucas.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblLucas.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblLucas, "cell 0 3,alignx center");

		
		JLabel lblV = new JLabel("V.1.0 - Desenvolvimento Desktop 2020");
		getContentPane().add(lblV, "flowx,cell 0 6");

		JLabel label = new JLabel("\u00A9 2020");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(label, "cell 0 6");

				

	}

}
