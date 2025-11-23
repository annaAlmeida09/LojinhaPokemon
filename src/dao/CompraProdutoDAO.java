package dao;

import model.CompraProduto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraProdutoDAO {

    // Tabela Compra_Produto:
    // id_compra, id_produto, quantidade, preco_unit

    public void inserir(CompraProduto cp) {
        String sql = "INSERT INTO Compra_Produto (id_compra, id_produto, quantidade, preco_unit) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cp.getIdCompra());
            stmt.setInt(2, cp.getIdProduto());
            stmt.setInt(3, cp.getQuantidade());
            stmt.setDouble(4, cp.getPrecoUnit());

            stmt.executeUpdate();
            System.out.println("Item de compra inserido!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir item de compra: " + e.getMessage());
        }
    }

    public void atualizar(CompraProduto cp) {
        String sql = "UPDATE Compra_Produto SET quantidade = ?, preco_unit = ? " +
                "WHERE id_compra = ? AND id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cp.getQuantidade());
            stmt.setDouble(2, cp.getPrecoUnit());
            stmt.setInt(3, cp.getIdCompra());
            stmt.setInt(4, cp.getIdProduto());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Item atualizado!" : "Item não encontrado.");
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
            System.out.println(linhas > 0 ? "Item removido!" : "Item não encontrado.");
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
                cp.setPrecoUnit(rs.getDouble("preco_unit"));
                lista.add(cp);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar itens da compra: " + e.getMessage());
        }

        return lista;
    }
}
