package model.vo;

public class FormaPagamento {

	private int idFP;
	private String Descricao;

	public int getIdFP() {
		return idFP;
	}

	public void setIdFP(int idFP) {
		this.idFP = idFP;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	public FormaPagamento(int idFP, String descricao) {
		super();
		this.idFP = idFP;
		Descricao = descricao;
	}

	public FormaPagamento() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return Descricao;
	}

}
