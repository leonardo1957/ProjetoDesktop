package model.seletor;

import java.util.Date;

public class VendaSeletor {
	private int id;
	private Double valorMenor;
	private Double valorMaior;
	private Date dataMenor;
	private Date dataMaior;

	// Pagina��o
	private int limite;
	private int pagina;

	public VendaSeletor() {
		this.limite = 0;
		this.pagina = -1;
	}

	public boolean temFiltro() {
		return (this.id > 0) || ((this.valorMenor != null) && (this.valorMenor > 0))
				|| ((this.valorMaior != null) && (this.valorMaior > 0)) || (this.dataMenor != null)
				|| (this.dataMaior != null);
	}

	/**
	 * Verifica se os campos de paginacao estao preenchidos
	 *
	 * @return verdadeiro se os campos limite e pagina estao preenchidos
	 */
	public boolean temPaginacao() {
		return ((this.limite > 0) && (this.pagina > -1));
	}

	/**
	 * Calcula deslocamento (offset) a partir da pagina e do limite
	 *
	 * @return offset
	 */
	public int getOffset() {
		return (this.limite * (this.pagina - 1));
	}

	// Getters & setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getValorMenor() {
		return valorMenor;
	}

	public void setValorMenor(Double valorMenor) {
		this.valorMenor = valorMenor;
	}

	public Double getValorMaior() {
		return valorMaior;
	}

	public void setValorMaior(Double valorMaior) {
		this.valorMaior = valorMaior;
	}

	public Date getDataMenor() {
		return dataMenor;
	}

	public void setDataMenor(Date dataMenor) {
		this.dataMenor = dataMenor;
	}

	public Date getDataMaior() {
		return dataMaior;
	}

	public void setDataMaior(Date dataMaior) {
		this.dataMaior = dataMaior;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

}
