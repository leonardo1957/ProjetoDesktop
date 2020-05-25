package model.seletor;

public class MercadoriaSeletor {
	private String codBar;
	private String nome;

	// Paginação
	private int limite;
	private int pagina;

	public MercadoriaSeletor() {
		this.limite = 0;
		this.pagina = -1;
	}

	public boolean temFiltro() {

		if ((this.codBar != null) && (this.codBar.trim().length() > 0)) {
			return true;
		}
		if ((this.nome != null) && (this.nome.trim().length() > 0)) {
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

	public void setCodBar(String i) {
		this.codBar = i;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
