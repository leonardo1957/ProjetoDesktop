package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.bo.ProdutoBO;
import model.seletor.ProdutoSeletor;
import model.vo.Categoria;
import model.vo.Produto;

public class ControllerProduto {

	public static final String TIPO_RELATORIO_XLS = null;
	private ProdutoBO produtoBO = new ProdutoBO();

	public String salvar(Produto produto) {
		String mensagem = "";
		String validacao = validarProduto(produto);

		if (validacao == "") {
			if (produtoBO.existeCodBar(produto.getCodBarra())) {
				int opcao = JOptionPane.showConfirmDialog(null,
						"Produto j� cadastrado, deseja fazer altera��o com os dados inseridos?", "",
						JOptionPane.OK_CANCEL_OPTION);
			
				
				
				// UPDATE
				if (opcao == 0) {
					if (produtoBO.atualizar(produto) == "") {
						mensagem = "Produto atualizado com sucesso";
					} else {
						mensagem = "ERRO ao atualizar produto";
					}
				}
			} else {
				// INSERT
				if (produtoBO.inserir(produto) == "") {
					mensagem = "Produto cadastrado com sucesso";
				} else {
					mensagem = "ERRO ao cadastrar produto";
				}
			}
		} else {
			return validacao;
		}
		return mensagem;
	}

	private String validarProduto(Produto produto) {
		String validacao = "";

		if (produto == null) {
			validacao = "Produto esta vazio";
		} else {
			// valida��es de campo vazio e nulo
			if (produto.getCodBarra().trim().equals("")) {
				validacao = "Produto deve possuir um codigo";
			}
			if (produto.getNome().trim().equals("") || produto.getNome() == null) {
				validacao = "Nome do produto � obrigatorio";
			}
			if (produto.getCategoria().getNomeCategoria() == "") {
				validacao = "Categoria deve ser selecionado";
			}
			if (produto.getPrecoVenda() <= 0.0) {
				validacao = "Pre�o do produto deve ser maior que zero";
			}
		}
		return validacao;
	}

	public List<Produto> listarProdutos(ProdutoSeletor seletor) {
		return produtoBO.listarProdutos(seletor);
	}

	public boolean existeProdutoCodBar(String produtoSelecionado) {
		return produtoBO.existeCodBar(produtoSelecionado);
	}

	public String excluir(String produtoSelecionado) {
		return produtoBO.excluir(produtoSelecionado);
	}

	public ArrayList<Categoria> consultarCategoria() {
		return produtoBO.consultarCategoria();
	}

	public void gerarRelatorio(List<Produto> produtos, String caminhoEscolhido, String tipoRelatorioXls) {
		produtoBO.gerarPlanilha(produtos, caminhoEscolhido);
	}

}
