package dao;

import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // TABELA: Produtos
    // COLUNAS:
    // id, nome, descricao, tipo, preco, id_loja

    public void inserir(Produto p) {
        String sql = "INSERT INTO Produtos (nome, descricao, tipo, preco, id_loja) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDescricao());
            stmt.setString(3, p.getTipo());
            stmt.setDouble(4, p.getPreco());
            stmt.setInt(5, p.getIdLoja());

            stmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public void atualizar(Produto p) {
        String sql = "UPDATE Produtos SET nome = ?, descricao = ?, tipo = ?, preco = ?, id_loja = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDescricao());
            stmt.setString(3, p.getTipo());
            stmt.setDouble(4, p.getPreco());
            stmt.setInt(5, p.getIdLoja());
            stmt.setInt(6, p.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Produtos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Produto deletado com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM Produtos WHERE id = ?";
        Produto p = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setTipo(rs.getString("tipo"));
                p.setPreco(rs.getDouble("preco"));
                p.setIdLoja(rs.getInt("id_loja"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }
        return p;
    }

    public List<Produto> listarTodos() {
        String sql = "SELECT * FROM Produtos";
        List<Produto> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setTipo(rs.getString("tipo"));
                p.setPreco(rs.getDouble("preco"));
                p.setIdLoja(rs.getInt("id_loja"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        return lista;
    }

    // JOIN simples: Produtos + Loja
    public void listarProdutosComLoja() {
        String sql = """
                SELECT p.id, p.nome AS produto, p.preco,
                       l.nome AS loja, l.cidade
                FROM Produtos p
                JOIN Loja l ON p.id_loja = l.id
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("Produto %d - %s (R$ %.2f) | Loja: %s (%s)%n",
                        rs.getInt("id"),
                        rs.getString("produto"),
                        rs.getDouble("preco"),
                        rs.getString("loja"),
                        rs.getString("cidade"));
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN Produtos+Loja: " + e.getMessage());
        }
    }
}
