package model.vo;

public class ItemProduto {

	private int idItemProduto;
	private Produto produto;
	private Venda venda;
	private int quantidade;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public int getIdItemProduto() {
		return idItemProduto;
	}

	public void setIdItemProduto(int idItemProduto) {
		this.idItemProduto = idItemProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public ItemProduto(int idItemProduto, Produto produto, Venda venda, int quantidade) {
		super();
		this.idItemProduto = idItemProduto;
		this.produto = produto;
		this.venda = venda;
		this.quantidade = quantidade;
	}

	public ItemProduto() {
		super();
		// TODO Auto-generated constructor stub
	}
}
