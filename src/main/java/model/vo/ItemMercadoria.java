package model.vo;

public class ItemMercadoria {

	private Mercadoria mercadoria;
	private int qtd;

	public Mercadoria getMercadoria() {
		return mercadoria;
	}

	public void setMercadoria(Mercadoria mercadoria) {
		this.mercadoria = mercadoria;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public ItemMercadoria(Mercadoria mercadoria, int qtd) {
		super();
		this.mercadoria = mercadoria;
		this.qtd = qtd;
	}

	public ItemMercadoria() {
		super();
		// TODO Auto-generated constructor stub
	}

}
