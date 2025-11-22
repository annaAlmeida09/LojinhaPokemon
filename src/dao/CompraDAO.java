package dao;

import model.Compra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    // TABELA: Compra
    // COLUNAS:
    // id_compra, id_treinador, valor_total, preco_insignias

    public void inserir(Compra c) {
        String sql = "INSERT INTO Compra (id_treinador, valor_total, preco_insignias) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getIdTreinador());
            stmt.setDouble(2, c.getValorTotal());
            stmt.setDouble(3, c.getPrecoInsignias());

            stmt.executeUpdate();
            System.out.println("Compra inserida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir compra: " + e.getMessage());
        }
    }

    public void atualizar(Compra c) {
        String sql = "UPDATE Compra SET id_treinador = ?, valor_total = ?, preco_insignias = ? WHERE id_compra = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getIdTreinador());
            stmt.setDouble(2, c.getValorTotal());
            stmt.setDouble(3, c.getPrecoInsignias());
            stmt.setInt(4, c.getIdCompra());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Compra atualizada com sucesso!");
            } else {
                System.out.println("Compra não encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar compra: " + e.getMessage());
        }
    }

    public void deletar(int idCompra) {
        String sql = "DELETE FROM Compra WHERE id_compra = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCompra);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Compra deletada com sucesso!");
            } else {
                System.out.println("Compra não encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar compra: " + e.getMessage());
        }
    }

    public Compra buscarPorId(int idCompra) {
        String sql = "SELECT * FROM Compra WHERE id_compra = ?";
        Compra c = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCompra);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                c = new Compra();
                c.setIdCompra(rs.getInt("id_compra"));
                c.setIdTreinador(rs.getInt("id_treinador"));
                c.setValorTotal(rs.getDouble("valor_total"));
                c.setPrecoInsignias(rs.getDouble("preco_insignias"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar compra: " + e.getMessage());
        }

        return c;
    }

    public List<Compra> listarTodas() {
        String sql = "SELECT * FROM Compra";
        List<Compra> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Compra c = new Compra();
                c.setIdCompra(rs.getInt("id_compra"));
                c.setIdTreinador(rs.getInt("id_treinador"));
                c.setValorTotal(rs.getDouble("valor_total"));
                c.setPrecoInsignias(rs.getDouble("preco_insignias"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar compras: " + e.getMessage());
        }

        return lista;
    }

    // JOIN simples: Compra + Treinador
    public void listarComprasComTreinador() {
        String sql = """
                SELECT c.id_compra,
                       t.nome AS treinador,
                       c.valor_total,
                       c.preco_insignias
                FROM Compra c
                JOIN Treinador t ON c.id_treinador = t.id
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("Compra %d | Treinador: %s | Valor: R$ %.2f | Insígnias: R$ %.2f%n",
                        rs.getInt("id_compra"),
                        rs.getString("treinador"),
                        rs.getDouble("valor_total"),
                        rs.getDouble("preco_insignias"));
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN Compra+Treinador: " + e.getMessage());
        }
    }

    // JOIN com tabela intermediária: Compra + CompraProduto + Produtos
    public void listarComprasComProdutos() {
        String sql = """
                SELECT c.id_compra,
                       t.nome AS treinador,
                       p.nome AS produto,
                       cp.quantidade,
                       p.preco,
                       (cp.quantidade * p.preco) AS subtotal
                FROM Compra c
                JOIN Treinador t ON c.id_treinador = t.id
                JOIN Compra_Produto cp ON cp.id_compra = c.id_compra
                JOIN Produtos p ON cp.id_produto = p.id
                ORDER BY c.id_compra
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("Compra %d | Treinador: %s | Produto: %s | Qtd: %d | Subtotal: R$ %.2f%n",
                        rs.getInt("id_compra"),
                        rs.getString("treinador"),
                        rs.getString("produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("subtotal"));
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN de compras com produtos: " + e.getMessage());
        }
    }
}
