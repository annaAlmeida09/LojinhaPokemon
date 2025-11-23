package dao;

import model.Compra;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    // Tabela Compra:
    // id_compra, id_trainer, id_loja, data_compra, valor_total

    public void inserir(Compra c) {
        // Se quiser deixar o banco calcular valor_total via trigger, pode usar 0 aqui
        String sql = "INSERT INTO Compra (id_trainer, id_loja, valor_total) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, c.getIdTreinador());
            stmt.setInt(2, c.getIdLoja());
            stmt.setDouble(3, c.getValorTotal());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                c.setIdCompra(rs.getInt(1));
            }

            System.out.println("Compra inserida!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir compra: " + e.getMessage());
        }
    }

    public void atualizar(Compra c) {
        String sql = "UPDATE Compra SET id_trainer = ?, id_loja = ?, valor_total = ? " +
                "WHERE id_compra = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getIdTreinador());
            stmt.setInt(2, c.getIdLoja());
            stmt.setDouble(3, c.getValorTotal());
            stmt.setInt(4, c.getIdCompra());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Compra atualizada!" : "Compra não encontrada.");
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
            System.out.println(linhas > 0 ? "Compra deletada!" : "Compra não encontrada.");
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
                c.setIdTreinador(rs.getInt("id_trainer"));
                c.setIdLoja(rs.getInt("id_loja"));
                c.setValorTotal(rs.getDouble("valor_total"));
                c.setDataCompra(rs.getTimestamp("data_compra").toLocalDateTime());
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
                c.setIdTreinador(rs.getInt("id_trainer"));
                c.setIdLoja(rs.getInt("id_loja"));
                c.setValorTotal(rs.getDouble("valor_total"));
                c.setDataCompra(rs.getTimestamp("data_compra").toLocalDateTime());
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar compras: " + e.getMessage());
        }

        return lista;
    }

    // JOIN simples: Compra + Treinador + Loja
    public void listarComprasComTreinadorELoja() {
        String sql = """
                SELECT c.id_compra,
                       c.data_compra,
                       c.valor_total,
                       t.nome AS treinador,
                       l.nome AS loja,
                       l.cidade
                FROM Compra c
                JOIN Treinador t ON c.id_trainer = t.id_trainer
                JOIN Loja l ON c.id_loja = l.id_loja
                ORDER BY c.id_compra
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "Compra %d | Data: %s | Treinador: %s | Loja: %s (%s) | Total: R$ %.2f%n",
                        rs.getInt("id_compra"),
                        rs.getTimestamp("data_compra").toLocalDateTime(),
                        rs.getString("treinador"),
                        rs.getString("loja"),
                        rs.getString("cidade"),
                        rs.getDouble("valor_total")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN Compra+Treinador+Loja: " + e.getMessage());
        }
    }

    // JOIN com tabela intermediária: Compra + Compra_Produto + Produto
    public void listarComprasComProdutos() {
        String sql = """
                SELECT c.id_compra,
                       t.nome AS treinador,
                       l.nome AS loja,
                       p.nome AS produto,
                       cp.quantidade,
                       cp.preco_unit,
                       (cp.quantidade * cp.preco_unit) AS subtotal
                FROM Compra c
                JOIN Treinador t ON c.id_trainer = t.id_trainer
                JOIN Loja l ON c.id_loja = l.id_loja
                JOIN Compra_Produto cp ON cp.id_compra = c.id_compra
                JOIN Produto p ON cp.id_produto = p.id_produto
                ORDER BY c.id_compra
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "Compra %d | Treinador: %s | Loja: %s | Produto: %s | Qtd: %d | Unit: R$ %.2f | Subtotal: R$ %.2f%n",
                        rs.getInt("id_compra"),
                        rs.getString("treinador"),
                        rs.getString("loja"),
                        rs.getString("produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("preco_unit"),
                        rs.getDouble("subtotal")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN de compras com produtos: " + e.getMessage());
        }
    }
}
