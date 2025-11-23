package dao;

import model.AcessoLoja;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcessoLojaDAO {

    // Tabela Acesso_Loja:
    // id_trainer, id_loja, data_acesso

    public void inserir(AcessoLoja a) {
        String sql = "INSERT INTO Acesso_Loja (id_trainer, id_loja) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getIdTreinador());
            stmt.setInt(2, a.getIdLoja());

            stmt.executeUpdate();
            System.out.println("Acesso registrado!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir acesso: " + e.getMessage());
        }
    }

    public void deletar(int idTrainer, int idLoja) {
        String sql = "DELETE FROM Acesso_Loja WHERE id_trainer = ? AND id_loja = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTrainer);
            stmt.setInt(2, idLoja);

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Acesso removido!" : "Acesso não encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar acesso: " + e.getMessage());
        }
    }

    public List<AcessoLoja> listarTodos() {
        String sql = "SELECT * FROM Acesso_Loja";
        List<AcessoLoja> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AcessoLoja a = new AcessoLoja();
                a.setIdTreinador(rs.getInt("id_trainer"));
                a.setIdLoja(rs.getInt("id_loja"));
                // se você adicionar data_acesso no model, pode setar aqui
                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar acessos: " + e.getMessage());
        }

        return lista;
    }

    // JOIN com tabela intermediária: Acesso_Loja + Treinador + Loja
    public void listarAcessosComNomes() {
        String sql = """
                SELECT al.data_acesso,
                       t.nome  AS treinador,
                       l.nome  AS loja,
                       l.cidade
                FROM Acesso_Loja al
                JOIN Treinador t ON al.id_trainer = t.id_trainer
                JOIN Loja l      ON al.id_loja    = l.id_loja
                ORDER BY al.data_acesso DESC
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "[%s] Treinador: %s | Loja: %s (%s)%n",
                        rs.getTimestamp("data_acesso").toLocalDateTime(),
                        rs.getString("treinador"),
                        rs.getString("loja"),
                        rs.getString("cidade")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN Acesso_Loja+Treinador+Loja: " + e.getMessage());
        }
    }
}
