package dao;

import model.Treinador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreinadorDAO {

    // Tabela Treinador:
    // id_trainer (PK), nome, pokeyens, insignias

    public void inserir(Treinador t) {
        String sql = "INSERT INTO Treinador (nome, pokeyens, insignias) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getNome());
            stmt.setDouble(2, t.getPokeYens());
            stmt.setInt(3, t.getInsignias());

            stmt.executeUpdate();
            System.out.println("Treinador inserido!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir treinador: " + e.getMessage());
        }
    }

    public void atualizar(Treinador t) {
        String sql = "UPDATE Treinador SET nome = ?, pokeyens = ?, insignias = ? " +
                "WHERE id_trainer = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getNome());
            stmt.setDouble(2, t.getPokeYens());
            stmt.setInt(3, t.getInsignias());
            stmt.setInt(4, t.getId());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Treinador atualizado!" : "Treinador não encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar treinador: " + e.getMessage());
        }
    }

    public void deletar(int idTrainer) {
        String sql = "DELETE FROM Treinador WHERE id_trainer = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTrainer);
            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Treinador deletado!" : "Treinador não encontrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar treinador: " + e.getMessage());
        }
    }

    public Treinador buscarPorId(int idTrainer) {
        String sql = "SELECT * FROM Treinador WHERE id_trainer = ?";
        Treinador t = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTrainer);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                t = new Treinador();
                t.setId(rs.getInt("id_trainer"));
                t.setNome(rs.getString("nome"));
                t.setPokeYens(rs.getDouble("pokeyens"));
                t.setInsignias(rs.getInt("insignias"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar treinador: " + e.getMessage());
        }

        return t;
    }

    public List<Treinador> listarTodos() {
        String sql = "SELECT * FROM Treinador";
        List<Treinador> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Treinador t = new Treinador();
                t.setId(rs.getInt("id_trainer"));
                t.setNome(rs.getString("nome"));
                t.setPokeYens(rs.getDouble("pokeyens"));
                t.setInsignias(rs.getInt("insignias"));
                lista.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar treinadores: " + e.getMessage());
        }

        return lista;
    }
}
