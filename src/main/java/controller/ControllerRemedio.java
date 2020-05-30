package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.bo.RemedioBO;
import model.seletor.RemedioSeletor;
import model.vo.FormaUso;
import model.vo.Laboratorio;
import model.vo.Remedio;

public class ControllerRemedio {

	public static final String TIPO_RELATORIO_XLS = "xls";

	private RemedioBO remedioBO = new RemedioBO();

	public String salvar(Remedio remedio) {
		String mensagem = "";
		String validacao = validarRemedio(remedio);

		if (validacao == "") {
			if (remedioBO.existeCodBar(remedio.getCodBarra())) {
				// UPDATE
				int opcao = JOptionPane.showConfirmDialog(null,
						"Remedio já cadastrado, deseja fazer altera��o com os dados inseridos?", "",
						JOptionPane.OK_CANCEL_OPTION);
				if (opcao == 0) {
					if (remedioBO.atualizar(remedio) == "") {
						mensagem = "Remédio atualizado com sucesso.";
					} else {
						mensagem = "Erro ao atualizar Remédio.";
					}
				} else {
					mensagem = "Nenhuma alteração feita.";
				}
			} else {
				// INSERT
				if (remedioBO.inserir(remedio) == "") {
					mensagem = "Rem�dio cadastrado com sucesso!";
				} else {
					mensagem = "Erro ao inserir Rem�dio.";
				}
			}
		} else {
			return validacao;
		}
		return mensagem;
	}

	private String validarRemedio(Remedio remedio) {
		String validacao = "";

		if (remedio == null) {
			validacao = "Rem�dio esta vazio";
		} else {
		
			
			// valida��es de campo vazio e nulo
			if (remedio.getCodBarra().trim().equals("")) {
				validacao = "Remedio deve possuir um c�digo de barras.";
			}
			if (remedio.getNome() == null || remedio.getNome().trim().equals("")) {
				validacao = "Nome do rem�dio � obrigat�rio.";
			}
			if (remedio.getComposicao() == null || remedio.getComposicao().trim().equals("")) {
				validacao = "Composi��o do remedio � obrigat�rio.";
			}
			if (remedio.getDosagem() == null || remedio.getDosagem().trim().equals("")) {
				validacao = "Dosagem do rem�dio � obrigat�ria.";
			}
			if (remedio.getFormaUso().getDescricao() == "") {
				validacao = "Forma de uso deve ser selecionado.";
			}
			if (remedio.getLaboratorio() == null || remedio.getLaboratorio().getNomeLaboratorio().trim().equals("")) {
				validacao = "Laborat�rio deve ser selecionado.";
			}
			if (remedio.getPrecoVenda() <= 0.0) {
				validacao = "Pre�o do rem�dio deve ser maior que zero.";
			}

		}
		return validacao;
	}

	public List<Remedio> listarRemedios(RemedioSeletor seletor) {
		return remedioBO.listarRemedios(seletor);
	}

	public String excluir(String remedioSelecionado) {
		return remedioBO.excluir(remedioSelecionado);
	}

	public boolean existeRemedioPorCodBar(String remedioSelecionado) {
		return remedioBO.existeCodBar(remedioSelecionado);
	}

	public ArrayList<FormaUso> consultarFormaUso() {
		return remedioBO.consultarFormaUso();
	}

	public ArrayList<Laboratorio> consultarLaboratorio() {
		return remedioBO.consultarLaboratorio();
	}

	public String atualizar(Remedio remedioSelecionado) {
		return remedioBO.atualizar(remedioSelecionado);
	}

	public void gerarRelatorio(List<Remedio> remedios, String caminhoEscolhido, String tipoRelatorio) {
		remedioBO.gerarPlanilha(remedios, caminhoEscolhido);
	}

}
