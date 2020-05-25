package model.vo;

import java.sql.Date;

public class Usuario {

	private int id;
	private String nome;
	private String login;
	private String senha;
	private Date dt_cadastro;
	private Nivel nivel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getDt_cadastro() {
		return dt_cadastro;
	}

	public void setDt_cadastro(Date dt_cadastro) {
		this.dt_cadastro = dt_cadastro;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public Usuario(int id, String nome, String login, String senha, Date dt_cadastro, Nivel nivel) {
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.dt_cadastro = dt_cadastro;
		this.nivel = nivel;
	}

	public Usuario() {
		super();
	}
}
