package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.seletor.ProdutoSeletor;
import model.vo.Categoria;
import model.vo.Produto;

public class ProdutoDAO {

	public String inserir(Produto p) {
		String mensagem = "";
		String sql = "INSERT INTO PRODUTO(COD_BARRA, NM_PRODUTO, DT_CADASTRO, PRECO, PRECO_CUSTO, ESTOQUE,ID_CATEGORIA)"
				+ " VALUES (?,?,NOW(),?,?,?,?)";

		Connection conn = Banco.getConnection();
		PreparedStatement prepStmt = Banco.getPreparedStatement(conn, sql, PreparedStatement.RETURN_GENERATED_KEYS);
		try {
			prepStmt.setString(1, p.getCodBarra());
			prepStmt.setString(2, p.getNome());
			prepStmt.setDouble(3, p.getPrecoVenda());
			prepStmt.setDouble(4, p.getPrecoCusto());
			prepStmt.setInt(5, p.getEstoque());
			prepStmt.setInt(6, p.getCategoria().getIdCategoria());

			prepStmt.execute();

		} catch (SQLException e) {
			System.out.println("Erro ao inserir produto. Causa: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(prepStmt);
			Banco.closeConnection(conn);
		}
		return mensagem;
	}

	public String atualizar(Produto p) {
		String mensagem = "";
		String sql = "UPDATE PRODUTO R SET NM_PRODUTO = ?, DT_CADASTRO = ?, PRECO = ?, PRECO_CUSTO = ?, ESTOQUE = ?, ID_CATEGORIA = ?"
				+ " WHERE R.COD_BARRA = ?";

		Connection conn = Banco.getConnection();
		PreparedStatement prepStmt = Banco.getPreparedStatement(conn, sql, Statement.RETURN_GENERATED_KEYS);

		try {
			prepStmt.setString(1, p.getNome());
			prepStmt.setDate(2, (Date) p.getDataCadastro());
			prepStmt.setDouble(3, p.getPrecoVenda());
			prepStmt.setDouble(4, p.getPrecoCusto());
			prepStmt.setInt(5, consultarEstoque(p) + p.getEstoque());
			prepStmt.setInt(6, p.getCategoria().getIdCategoria());
			prepStmt.setString(7, p.getCodBarra());

			prepStmt.execute();

			int codigoRetorno = prepStmt.executeUpdate();
			if (codigoRetorno == 0) {
				mensagem = "Erro ao executar query de atualiza��o de Produto!";
			}
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar produto. Causa: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(prepStmt);
			Banco.closeConnection(conn);
		}
		return mensagem;
	}

	private int consultarEstoque(Produto p) {

		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		int estoque = 0;

		String query = "SELECT ESTOQUE FROM PRODUTO P WHERE P.COD_BARRA = " + p.getCodBarra();

		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				estoque = resultado.getInt("ESTOQUE");
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a Query de Consulta de Prato. Causa:" + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return estoque;
	}

	public List<Produto> listarComSeletor(ProdutoSeletor seletor) {
		String sql = " SELECT P.COD_BARRA, P.NM_PRODUTO, P.DT_CADASTRO, P.PRECO, P.PRECO_CUSTO, P.ESTOQUE, C.ID_CATEGORIA, C.NM_CATEGORIA "
				+ " FROM PRODUTO P JOIN CATEGORIA C ON P.ID_CATEGORIA = C.ID_CATEGORIA";

		if (seletor.temFiltro()) {
			sql = criarFiltros(seletor, sql);
		}

		if (seletor.temPaginacao()) {
			sql += " LIMIT " + seletor.getLimite() + " OFFSET " + seletor.getOffset();
		}
		Connection conexao = Banco.getConnection();
		PreparedStatement prepStmt = Banco.getPreparedStatement(conexao, sql);
		ArrayList<Produto> produtos = new ArrayList<Produto>();

		try {
			// System.out.println(sql);
			ResultSet result = prepStmt.executeQuery();

			while (result.next()) {
				Produto p = construirProdutoDoResultSet(result);
				produtos.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produtos;
	}

	private String criarFiltros(ProdutoSeletor seletor, String sql) {
		sql += " WHERE ";
		boolean primeiro = true;

		if ((seletor.getCodBar() != null) && (seletor.getCodBar().trim().length() > 0)) {
			if (!primeiro) {
				sql += " AND ";
			}
			sql += "P.COD_BARRA LIKE '%" + seletor.getCodBar().trim() + "%'";
			primeiro = false;
		}

		if ((seletor.getNomeProduto() != null) && (seletor.getNomeProduto().trim().length() > 0)) {
			if (!primeiro) {
				sql += " AND ";
			}
			sql += "P.NM_PRODUTO LIKE '%" + seletor.getNomeProduto().trim() + "%'";
			primeiro = false;
		}

		if ((seletor.getCategoria() != null) && (seletor.getCategoria().trim().length() > 0)) {
			if (!primeiro) {
				sql += " AND ";
			}
			sql += "C.NM_CATEGORIA LIKE '%" + seletor.getCategoria().trim() + "%'";
			primeiro = false;

		}
		// Verificando o que retorna nos filtros
		// System.out.println(sql);
		return sql;
	}

	private Produto construirProdutoDoResultSet(ResultSet result) {
		Produto p = new Produto();
		Categoria c = new Categoria();

		try {
			p.setCodBarra(result.getString("COD_BARRA"));
			p.setNome(result.getString("NM_PRODUTO"));
			p.setDataCadastro(result.getDate("DT_CADASTRO"));
			p.setPrecoVenda(result.getDouble("PRECO"));
			p.setPrecoCusto(result.getDouble("PRECO_CUSTO"));
			p.setEstoque(result.getInt("ESTOQUE"));
			c.setIdCategoria(result.getInt("ID_CATEGORIA"));
			c.setNomeCategoria(result.getString("NM_CATEGORIA"));
			p.setCategoria(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	public boolean existeCodBar(String codBar) {
		boolean codigoRetorno = false;
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;

		String sql = "SELECT COD_BARRA FROM PRODUTO WHERE COD_BARRA = '" + codBar + "'";

		try {
			resultado = stmt.executeQuery(sql);
			if (resultado.next()) {
				codigoRetorno = true;
			}
		} catch (SQLException e) {
			System.out.println(
					"Erro ao executar Query que verifica exist�ncia de C�digo de Barras. Causa :" + e.getMessage());
			codigoRetorno = false;
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return codigoRetorno;
	}

	public boolean excluir(String produtoSelecionado) {
		boolean result = false;
		String sql = " DELETE FROM PRODUTO " + " WHERE COD_BARRA = ?";

		Connection conexao = Banco.getConnection();
		PreparedStatement prepStmt = Banco.getPreparedStatement(conexao, sql);

		try {
			prepStmt.setString(1, produtoSelecionado);
			result = true;
			int codigoRetorno = prepStmt.executeUpdate();
			if (codigoRetorno == 0) {
				result = false;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao remover produto. Causa: " + e.getMessage());
			result = false;
		} finally {
			Banco.closePreparedStatement(prepStmt);
			Banco.closeConnection(conexao);
		}
		return result;
	}

	public ArrayList<Categoria> consultarCategoria() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;

		ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>();

		String query = "SELECT * FROM CATEGORIA";
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				Categoria c = new Categoria();
				c.setIdCategoria(Integer.parseInt(resultado.getString(1)));
				c.setNomeCategoria(resultado.getString(2));
				listaCategorias.add(c);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar os Categorias!!");
			e.printStackTrace();
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return listaCategorias;
	}

}
