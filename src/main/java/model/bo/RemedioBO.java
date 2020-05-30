package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.dao.RemedioDAO;
import model.seletor.RemedioSeletor;
import model.vo.FormaUso;
import model.vo.Laboratorio;
import model.vo.Remedio;

public class RemedioBO {

	RemedioDAO remedioDAO = new RemedioDAO();

	public String inserir(Remedio remedio) {

		String mensagem = "";

		mensagem = remedioDAO.inserir(remedio);

		return mensagem;

	}

	public String atualizar(Remedio remedio) {

		String mensagem = "";

		mensagem = remedioDAO.atualizar(remedio);

		return mensagem;
	}

	public List<Remedio> listarRemedios(RemedioSeletor seletor) {

		return remedioDAO.listarComSeletor(seletor);

	}

	public boolean existeCodBar(String CodBar) {
		return remedioDAO.existeCodBar(CodBar);
	}

	public String excluir(String remedioSelecionado) {
		String mensagem = "";
		if (remedioDAO.excluir(remedioSelecionado)) {
			mensagem = "Remédio excluído com sucesso.";
		} else {
			mensagem = "Erro: Remédios que já foram vendidos não podem ser excluídos!";
		}
		return mensagem;
	}

	public ArrayList<FormaUso> consultarFormaUso() {
		return remedioDAO.consultarFormaUso();
	}

	public ArrayList<Laboratorio> consultarLaboratorio() {
		return remedioDAO.consultarLaboratorio();
	}

	public void gerarPlanilha(List<Remedio> remedios, String caminhoEscolhido) {
		GeradorDePlanilha gerador = new GeradorDePlanilha();
		gerador.gerarPlanilhaRemedio(remedios, caminhoEscolhido);
	}

}
