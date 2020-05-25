package model.vo;

public class FormaUso {

	private int IdFormaUso;
	private String descricao;

	public int getIdFormaUso() {
		return IdFormaUso;
	}

	public void setIdFormaUso(int idFormaUso) {
		IdFormaUso = idFormaUso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public FormaUso(int idFormaUso, String descricao) {
		super();
		IdFormaUso = idFormaUso;
		this.descricao = descricao;
	}

	public FormaUso() {
		super();
	}

	@Override
	public String toString() {
		return descricao;
	}

}
