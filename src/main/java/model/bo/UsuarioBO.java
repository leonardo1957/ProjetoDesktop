package model.bo;

import java.util.ArrayList;
import java.util.List;

import model.dao.UsuarioDAO;
import model.seletor.UsuarioSeletor;
import model.vo.Nivel;
import model.vo.Usuario;

public class UsuarioBO {

	UsuarioDAO usuarioDAO = new UsuarioDAO();

	public String salvarUsuario(Usuario usuario) {
		String mensagem = "";
		mensagem = usuarioDAO.salvarUsuario(usuario);

		return mensagem;
	}

	public Usuario validarUsuario(String login, String senha) {
		return usuarioDAO.validarUsuario(login, senha);
	}

	public ArrayList<Nivel> consultarNivel() {
		return usuarioDAO.consultarNivel();
	}

	public List<Usuario> listarUsuarios(UsuarioSeletor seletor) {

		return usuarioDAO.listarComSeletor(seletor);

	}
}
