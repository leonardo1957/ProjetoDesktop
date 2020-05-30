package controller;

import java.util.List;

import model.bo.VendaBO;
import model.dto.VendaDTO;
import model.seletor.MercadoriaSeletor;
import model.seletor.VendaSeletor;
import model.vo.FormaPagamento;
import model.vo.ItemProduto;
import model.vo.ItemRemedio;
import model.vo.Mercadoria;
import model.vo.Usuario;
import model.vo.Venda;

public class ControllerVenda {

	private VendaBO vendaBO = new VendaBO();

	public List<VendaDTO> listarVendaDTO(MercadoriaSeletor seletor) {
		return vendaBO.listarVendaDTO(seletor);
	}

	public String salvarVenda(double valorTotal, List<ItemProduto> itensProdutos, List<ItemRemedio> itensRemedios,
			FormaPagamento formaPgto, Usuario usuario) {
		return vendaBO.salvar(valorTotal, itensProdutos, itensRemedios, formaPgto, usuario);
	}

	public List<Mercadoria> listarMercadorias(MercadoriaSeletor seletor) {
		return vendaBO.listarMercadorias(seletor);
	}

	public List<Venda> listarVendas(VendaSeletor seletor) {
		return vendaBO.listarVendas(seletor);
	}

	public void gerarRelatorio(List<Venda> vendasConsultadas, String caminhoEscolhido, String tipoRelatorioXls) {
		vendaBO.gerarPlanilha(vendasConsultadas, caminhoEscolhido);

	}

	public List<FormaPagamento> consultarFormaPagamento() {
		return vendaBO.consultarFormaPagamento();
	}

	public String cancelarVenda(Venda venda) {
		return vendaBO.cancelarVenda(venda);
	}
}
