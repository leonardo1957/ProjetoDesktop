package model.vo;

public class Nivel {

	private int id;
	private String descricao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Nivel(int id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public Nivel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return descricao;
	}

}
