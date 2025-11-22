package dao;

import model.Loja;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LojaDAO {

    // TABELA: Loja
    // COLUNAS:
    // id, nome, cidade, tipo, id_trainer_responsavel

    public void inserir(Loja l) {
        String sql = "INSERT INTO Loja (nome, cidade, tipo, id_trainer_responsavel) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getNome());
            stmt.setString(2, l.getCidade());
            stmt.setString(3, l.getTipo());
            stmt.setInt(4, l.getIdTrainerResponsavel());

            stmt.executeUpdate();
            System.out.println("Loja inserida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir loja: " + e.getMessage());
        }
    }

    public void atualizar(Loja l) {
        String sql = "UPDATE Loja SET nome = ?, cidade = ?, tipo = ?, id_trainer_responsavel = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getNome());
            stmt.setString(2, l.getCidade());
            stmt.setString(3, l.getTipo());
            stmt.setInt(4, l.getIdTrainerResponsavel());
            stmt.setInt(5, l.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Loja atualizada com sucesso!");
            } else {
                System.out.println("Loja não encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar loja: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Loja WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Loja deletada com sucesso!");
            } else {
                System.out.println("Loja não encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar loja: " + e.getMessage());
        }
    }

    public Loja buscarPorId(int id) {
        String sql = "SELECT * FROM Loja WHERE id = ?";
        Loja l = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                l = new Loja();
                l.setId(rs.getInt("id"));
                l.setNome(rs.getString("nome"));
                l.setCidade(rs.getString("cidade"));
                l.setTipo(rs.getString("tipo"));
                l.setIdTrainerResponsavel(rs.getInt("id_trainer_responsavel"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar loja: " + e.getMessage());
        }
        return l;
    }

    public List<Loja> listarTodas() {
        String sql = "SELECT * FROM Loja";
        List<Loja> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Loja l = new Loja();
                l.setId(rs.getInt("id"));
                l.setNome(rs.getString("nome"));
                l.setCidade(rs.getString("cidade"));
                l.setTipo(rs.getString("tipo"));
                l.setIdTrainerResponsavel(rs.getInt("id_trainer_responsavel"));
                lista.add(l);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar lojas: " + e.getMessage());
        }
        return lista;
    }

    // JOIN simples: Loja + Treinador (responsável)
    public void listarLojasComTreinadorResponsavel() {
        String sql = """
                SELECT l.id, l.nome AS loja, l.cidade, l.tipo,
                       t.nome AS treinador
                FROM Loja l
                JOIN Treinador t ON l.id_trainer_responsavel = t.id
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("Loja %d - %s (%s, %s) | Treinador: %s%n",
                        rs.getInt("id"),
                        rs.getString("loja"),
                        rs.getString("cidade"),
                        rs.getString("tipo"),
                        rs.getString("treinador"));
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN Loja+Treinador: " + e.getMessage());
        }
    }
}
