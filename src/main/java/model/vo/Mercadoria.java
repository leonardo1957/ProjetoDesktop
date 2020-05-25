package model.vo;

import java.util.Date;

public abstract class Mercadoria {

	private String codBarra;
	private String nome;
	private Date dataCadastro;
	private double precoVenda;
	private double precoCusto;
	private int estoque;

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public double getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(double precoCusto) {
		this.precoCusto = precoCusto;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public Mercadoria(String codBarra, String nome, Date dataCadastro, double preco, int estoque) {
		super();
		this.codBarra = codBarra;
		this.nome = nome;
		this.dataCadastro = dataCadastro;
		this.precoVenda = preco;
		this.estoque = estoque;
	}

	public Mercadoria() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		return "C�d: " + codBarra + " - " + "\nNome: " + nome + "\nData Cad.: " + dataCadastro + "\nPre�o: "
				+ precoVenda + "\nEstoque: " + estoque;
	}
}
