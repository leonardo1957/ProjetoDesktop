package model.vo;

import java.util.Date;

public class Produto extends Mercadoria {

	private Categoria categoria;

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Produto(String codBarra, String nome, Date dataCadastro, double preco, int estoque, Categoria categoria) {
		super(codBarra, nome, dataCadastro, preco, estoque);
		this.categoria = categoria;
	}

	public Produto() {
		super();
	}

	public Produto(String codBarra, String nome, Date dataCadastro, double preco, int estoque) {
		super(codBarra, nome, dataCadastro, preco, estoque);
	}

}
