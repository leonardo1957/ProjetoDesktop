package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.dao.ItemProdutoDAO;
import model.dao.ItemRemedioDAO;
import model.dao.VendaDAO;
import model.dto.VendaDTO;
import model.seletor.MercadoriaSeletor;
import model.seletor.VendaSeletor;
import model.vo.FormaPagamento;
import model.vo.ItemProduto;
import model.vo.ItemRemedio;
import model.vo.Mercadoria;
import model.vo.Usuario;
import model.vo.Venda;

public class VendaBO {

	VendaDTO vendaDTO = new VendaDTO();
	VendaDAO vendaDAO = new VendaDAO();

	public List<VendaDTO> listarVendaDTO(MercadoriaSeletor seletor) {
		return vendaDAO.listarVendaDTO(seletor);
	}

	public String salvar(double valorTotal, List<ItemProduto> itensProdutos, List<ItemRemedio> itensRemedios,
			FormaPagamento formaPgto, Usuario usuario) {
		Venda novaVenda = new Venda();
		String mensagem = "";
		if (vendaDAO.inserirVenda(valorTotal, formaPgto, usuario)) {
			novaVenda = vendaDAO.pegarUltimaVenda();
		} else {
			mensagem = "N�o foi possivel criar a venda (VendaBO30)";
		}

		if (novaVenda != null) {
			for (ItemProduto itemProduto : itensProdutos) {
				itemProduto.setVenda(novaVenda);
				ItemProdutoDAO itemProdutoDAO = new ItemProdutoDAO();
				itemProdutoDAO.inserirItemProduto(itemProduto);
			}
			for (ItemRemedio itemRemedio : itensRemedios) {
				itemRemedio.setVenda(novaVenda);
				ItemRemedioDAO itemRemedioDAO = new ItemRemedioDAO();
				itemRemedioDAO.inserirItemRemedio(itemRemedio);
			}
		}
		return mensagem;
	}

	public List<Mercadoria> listarMercadorias(MercadoriaSeletor seletor) {
		return vendaDAO.listarMercadorias(seletor);
	}

	public List<Venda> listarVendas(VendaSeletor seletor) {
		return vendaDAO.listarVenda(seletor);
	}

	public void gerarPlanilha(List<Venda> vendasConsultadas, String caminhoEscolhido) {
		GeradorDePlanilha gerador = new GeradorDePlanilha();
		gerador.gerarPlanilhaVenda(vendasConsultadas, caminhoEscolhido);

	}

	public ArrayList<FormaPagamento> consultarFormaPagamento() {
		return vendaDAO.consultarFormaPagamento();
	}

	public String cancelarVenda(Venda venda) {
		return vendaDAO.cancelarVenda(venda);
	}
}
