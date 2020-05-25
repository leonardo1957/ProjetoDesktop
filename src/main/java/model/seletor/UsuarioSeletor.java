package model.seletor;

public class UsuarioSeletor {

	private String nome;
	private String login;
	private String senha;

	public boolean temFiltro() {

		if ((this.nome != null) && (this.nome.trim().length() > 0)) {
			return true;
		}
		if ((this.login != null) && (this.login.trim().length() > 0)) {
			return true;
		}
		if ((this.senha != null) && (this.senha.trim().length() > 0)) {
			return true;
		}
		return false;

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
