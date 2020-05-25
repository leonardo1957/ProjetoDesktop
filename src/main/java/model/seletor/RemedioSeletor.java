package model.seletor;

public class RemedioSeletor {

	private String codBar;
	private String nomeRemedio;
	private String composicaoRemedio;
	private String tipoRemedio;
	private boolean generico;

	// Paginação
	private int limite;
	private int pagina;

	public RemedioSeletor() {
		this.limite = 0;
		this.pagina = -1;
	}

	public boolean temFiltro() {

		if ((this.codBar != null) && (this.codBar.trim().length() > 0)) {
			return true;
		}
		if ((this.nomeRemedio != null) && (this.nomeRemedio.trim().length() > 0)) {
			return true;
		}
		if ((this.composicaoRemedio != null) && (this.composicaoRemedio.trim().length() > 0)) {
			return true;
		}
		if ((this.tipoRemedio != null) && (this.tipoRemedio.trim().length() > 0)) {
			return true;
		}
		if (this.generico) {
			return true;
		}
		return false;
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
	public String getCodBar() {
		return codBar;
	}

	public void setCodBar(String codBarRemedio) {
		this.codBar = codBarRemedio;
	}

	public String getNomeRemedio() {
		return nomeRemedio;
	}

	public void setNomeRemedio(String nomeRemedio) {
		this.nomeRemedio = nomeRemedio;
	}

	public String getComposicaoRemedio() {
		return composicaoRemedio;
	}

	public void setComposicaoRemedio(String composicaoRemedio) {
		this.composicaoRemedio = composicaoRemedio;
	}

	public String getTipoRemedio() {
		return tipoRemedio;
	}

	public void setTipoRemedio(String tipoRemedio) {
		this.tipoRemedio = tipoRemedio;
	}

	public boolean isGenerico() {
		return generico;
	}

	public void setGenerico(boolean generico) {
		this.generico = generico;
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
