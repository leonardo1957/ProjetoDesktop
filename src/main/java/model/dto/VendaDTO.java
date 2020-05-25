package model.dto;

public class VendaDTO {

	private String id;
	private String nome;
	private Double preco;
	private int estoque;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public VendaDTO(String id, String nome, Double preco, int estoque) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.estoque = estoque;
	}

	public VendaDTO() {
		super();
	}

}