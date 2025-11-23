package dao;

import model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // Tabela Produto:
    // id_produto, id_loja, nome, descricao, preco, tipo

    public void inserir(Produto p) {
        String sql = "INSERT INTO Produto (id_loja, nome, descricao, preco, tipo) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getIdLoja());
            stmt.setString(2, p.getNome());
            stmt.setString(3, p.getDescricao());
            stmt.setDouble(4, p.getPreco());
            stmt.setString(5, p.getTipo());

            stmt.executeUpdate();
            System.out.println("Produto inserido!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public void atualizar(Produto p) {
        String sql = "UPDATE Produto SET id_loja = ?, nome = ?, descricao = ?, preco = ?, tipo = ? " +
                "WHERE id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getIdLoja());
            stmt.setString(2, p.getNome());
            stmt.setString(3, p.getDescricao());
            stmt.setDouble(4, p.getPreco());
            stmt.setString(5, p.getTipo());
            stmt.setInt(6, p.getId());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Produto atualizado!" : "Produto não encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public void deletar(int idProduto) {
        String sql = "DELETE FROM Produto WHERE id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);
            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Produto deletado!" : "Produto não encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
    }

    public Produto buscarPorId(int idProduto) {
        String sql = "SELECT * FROM Produto WHERE id_produto = ?";
        Produto p = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p = new Produto();
                p.setId(rs.getInt("id_produto"));
                p.setIdLoja(rs.getInt("id_loja"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setTipo(rs.getString("tipo"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }

        return p;
    }

    public List<Produto> listarTodos() {
        String sql = "SELECT * FROM Produto";
        List<Produto> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id_produto"));
                p.setIdLoja(rs.getInt("id_loja"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setTipo(rs.getString("tipo"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        return lista;
    }

    // JOIN Produto + Loja
    public void listarProdutosComLoja() {
        String sql = """
                SELECT p.id_produto, p.nome AS produto, p.preco,
                       l.nome AS loja, l.cidade
                FROM Produto p
                JOIN Loja l ON p.id_loja = l.id_loja
                ORDER BY p.id_produto
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "Produto %d - %s (R$ %.2f) | Loja: %s (%s)%n",
                        rs.getInt("id_produto"),
                        rs.getString("produto"),
                        rs.getDouble("preco"),
                        rs.getString("loja"),
                        rs.getString("cidade")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN Produto+Loja: " + e.getMessage());
        }
    }
}
