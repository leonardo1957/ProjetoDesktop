package model.vo;

public class Laboratorio {

	private int idLaboratorio;
	private String nomeLaboratorio;

	public int getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(int idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	public String getNomeLaboratorio() {
		return nomeLaboratorio;
	}

	public void setNomeLaboratorio(String nomeLaboratorio) {
		this.nomeLaboratorio = nomeLaboratorio;
	}

	public Laboratorio(int idLaboratorio, String nomeLaboratorio) {
		super();
		this.idLaboratorio = idLaboratorio;
		this.nomeLaboratorio = nomeLaboratorio;
	}

	public Laboratorio() {
		super();
	}

	@Override
	public String toString() {
		return idLaboratorio + " - " + nomeLaboratorio;
	}

}
