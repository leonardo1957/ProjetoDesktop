package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.vo.ItemProduto;

public class ItemProdutoDAO {

	public void inserirItemProduto(ItemProduto itemProduto) {
		String sql = "INSERT INTO ITEM_PRODUTO(ID_VENDA, COD_BARRA, QT_PRODUTO) VALUES (?, ?, ?)";

		Connection conn = Banco.getConnection();
		PreparedStatement prepStmt = Banco.getPreparedStatement(conn, sql, PreparedStatement.RETURN_GENERATED_KEYS);
		try {
			prepStmt.setInt(1, itemProduto.getVenda().getIdVenda());
			prepStmt.setString(2, itemProduto.getProduto().getCodBarra());
			prepStmt.setInt(3, itemProduto.getQuantidade());
			prepStmt.execute();

		} catch (SQLException e) {
			System.out.println("Erro ao inserir um item (Produto) na venda. Causa: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(prepStmt);
			Banco.closeConnection(conn);
		}
		String sql2 = "UPDATE PRODUTO SET ESTOQUE = ESTOQUE - ? WHERE COD_BARRA = ? ";

		Connection conn2 = Banco.getConnection();
		PreparedStatement prepStmt2 = Banco.getPreparedStatement(conn2, sql2, PreparedStatement.RETURN_GENERATED_KEYS);
		try {
			prepStmt2.setInt(1, itemProduto.getQuantidade());
			prepStmt2.setString(2, itemProduto.getProduto().getCodBarra());
			prepStmt2.execute();

		} catch (SQLException e2) {
			System.out.println("Erro ao atualizar estoque do produto. Causa: " + e2.getMessage());
		} finally {
			Banco.closePreparedStatement(prepStmt2);
			Banco.closeConnection(conn2);
		}

	}
}
