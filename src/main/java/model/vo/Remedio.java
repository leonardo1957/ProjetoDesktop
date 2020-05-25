package model.vo;

import java.util.Date;

public class Remedio extends Mercadoria {

	private String dosagem;
	private String composicao;
	private FormaUso formaUso;
	private boolean generico;
	private Laboratorio laboratorio;

	public String getDosagem() {
		return dosagem;
	}

	public void setDosagem(String dosagem) {
		this.dosagem = dosagem;
	}

	public String getComposicao() {
		return composicao;
	}

	public void setComposicao(String composicao) {
		this.composicao = composicao;
	}

	public FormaUso getFormaUso() {
		return formaUso;
	}

	public void setFormaUso(FormaUso formaUso) {
		this.formaUso = formaUso;
	}

	public boolean isGenerico() {
		return generico;
	}

	public void setGenerico(boolean generico) {
		this.generico = generico;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public Remedio(String codBarra, String nome, Date dataCadastro, double preco, int estoque, String dosagem,
			String composicao, FormaUso tipo, boolean generico, Laboratorio laboratorio) {
		super(codBarra, nome, dataCadastro, preco, estoque);
		this.dosagem = dosagem;
		this.composicao = composicao;
		this.formaUso = tipo;
		this.generico = generico;
		this.laboratorio = laboratorio;
	}

	public Remedio() {
		super();
	}

	public Remedio(String codBarra, String nome, Date dataCadastro, double preco, int estoque) {
		super(codBarra, nome, dataCadastro, preco, estoque);
	}

	@Override
	public String toString() {
		return "Remedio [dosagem=" + dosagem + ", composicao=" + composicao + ", formaUso=" + formaUso + ", generico="
				+ generico + ", laboratorio=" + laboratorio + "]" + super.toString();
	}

}
