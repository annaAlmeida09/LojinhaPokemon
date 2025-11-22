package dao;

import model.CompraProduto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraProdutoDAO {

    // TABELA: Compra_Produto
    // COLUNAS:
    // id_compra, id_produto, quantidade

    public void inserir(CompraProduto cp) {
        String sql = "INSERT INTO Compra_Produto (id_compra, id_produto, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cp.getIdCompra());
            stmt.setInt(2, cp.getIdProduto());
            stmt.setInt(3, cp.getQuantidade());

            stmt.executeUpdate();
            System.out.println("Item de compra inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir item de compra: " + e.getMessage());
        }
    }

    public void atualizar(CompraProduto cp) {
        String sql = "UPDATE Compra_Produto SET quantidade = ? WHERE id_compra = ? AND id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cp.getQuantidade());
            stmt.setInt(2, cp.getIdCompra());
            stmt.setInt(3, cp.getIdProduto());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Item de compra atualizado com sucesso!");
            } else {
                System.out.println("Item de compra não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar item de compra: " + e.getMessage());
        }
    }

    public void deletar(int idCompra, int idProduto) {
        String sql = "DELETE FROM Compra_Produto WHERE id_compra = ? AND id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCompra);
            stmt.setInt(2, idProduto);

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Item de compra deletado com sucesso!");
            } else {
                System.out.println("Item de compra não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar item de compra: " + e.getMessage());
        }
    }

    public List<CompraProduto> listarPorCompra(int idCompra) {
        String sql = "SELECT * FROM Compra_Produto WHERE id_compra = ?";
        List<CompraProduto> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCompra);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CompraProduto cp = new CompraProduto();
                cp.setIdCompra(rs.getInt("id_compra"));
                cp.setIdProduto(rs.getInt("id_produto"));
                cp.setQuantidade(rs.getInt("quantidade"));
                lista.add(cp);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar itens da compra: " + e.getMessage());
        }

        return lista;
    }
}
