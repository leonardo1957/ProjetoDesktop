package model.vo;

public class ItemRemedio {

	private int idItemRemedio;
	private Remedio remedio;
	private Venda venda;
	private int quantidade;

	public int getIdItemRemedio() {
		return idItemRemedio;
	}

	public void setIdItemRemedio(int idItemRemedio) {
		this.idItemRemedio = idItemRemedio;
	}

	public Remedio getRemedio() {
		return remedio;
	}

	public void setRemedio(Remedio remedio) {
		this.remedio = remedio;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public ItemRemedio(int idItemRemedio, Remedio remedio, Venda venda, int quantidade) {
		super();
		this.idItemRemedio = idItemRemedio;
		this.remedio = remedio;
		this.venda = venda;
		this.quantidade = quantidade;
	}

	public ItemRemedio() {
		super();
		// TODO Auto-generated constructor stub
	}

}
