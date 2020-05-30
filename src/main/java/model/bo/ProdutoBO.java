
package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.dao.ProdutoDAO;
import model.seletor.ProdutoSeletor;
import model.vo.Categoria;
import model.vo.Produto;

public class ProdutoBO {

	ProdutoDAO produtoDAO = new ProdutoDAO();

	public String inserir(Produto produto) {
		String mensagem = "";

		mensagem = produtoDAO.inserir(produto);

		return mensagem;
	}

	public String atualizar(Produto produto) {

		String mensagem = "";

		mensagem = produtoDAO.atualizar(produto);

		return mensagem;
	}

	public List<Produto> listarProdutos(ProdutoSeletor seletor) {
		return produtoDAO.listarComSeletor(seletor);
	}

	public boolean existeCodBar(String produtoSelecionado) {
		return produtoDAO.existeCodBar(produtoSelecionado);
	}

	public String excluir(String produtoSelecionado) {
		String mensagem = "";
		if (produtoDAO.excluir(produtoSelecionado)) {
			mensagem = "Produto excluído com sucesso.";
		} else {
			mensagem = "Erro: Produtos que já foram vendidos não podem ser excluídos!";
		}
		return mensagem;
	}

	public ArrayList<Categoria> consultarCategoria() {
		return produtoDAO.consultarCategoria();
	}

	public void gerarPlanilha(List<Produto> produtos, String caminhoEscolhido) {
		GeradorDePlanilha gerador = new GeradorDePlanilha();
		gerador.gerarPlanilhaProduto(produtos, caminhoEscolhido);
	}

}
