package dao;

import model.Bolsa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BolsaDAO {

    // Tabela Bolsa:
    // id_trainer (PK/FK), espaco

    public void inserir(Bolsa b) {
        String sql = "INSERT INTO Bolsa (id_trainer, espaco) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, b.getIdTreinador());
            stmt.setInt(2, b.getEspaco());

            stmt.executeUpdate();
            System.out.println("Bolsa criada!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir bolsa: " + e.getMessage());
        }
    }

    public void atualizar(Bolsa b) {
        String sql = "UPDATE Bolsa SET espaco = ? WHERE id_trainer = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, b.getEspaco());
            stmt.setInt(2, b.getIdTreinador());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Bolsa atualizada!" : "Bolsa não encontrada.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar bolsa: " + e.getMessage());
        }
    }

    public void deletar(int idTrainer) {
        String sql = "DELETE FROM Bolsa WHERE id_trainer = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTrainer);
            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Bolsa deletada!" : "Bolsa não encontrada.");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar bolsa: " + e.getMessage());
        }
    }

    public Bolsa buscarPorId(int idTrainer) {
        String sql = "SELECT * FROM Bolsa WHERE id_trainer = ?";
        Bolsa b = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTrainer);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                b = new Bolsa();
                b.setIdTreinador(rs.getInt("id_trainer"));
                b.setEspaco(rs.getInt("espaco"));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar bolsa: " + e.getMessage());
        }

        return b;
    }

    public List<Bolsa> listarTodas() {
        String sql = "SELECT * FROM Bolsa";
        List<Bolsa> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bolsa b = new Bolsa();
                b.setIdTreinador(rs.getInt("id_trainer"));
                b.setEspaco(rs.getInt("espaco"));
                lista.add(b);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar bolsas: " + e.getMessage());
        }

        return lista;
    }

    // JOIN simples: Bolsa + Treinador
    public void listarBolsasComTreinador() {
        String sql = """
                SELECT b.id_trainer, b.espaco, t.nome
                FROM Bolsa b
                JOIN Treinador t ON b.id_trainer = t.id_trainer
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("Treinador: %s | Espaço bolsa: %d%n",
                        rs.getString("nome"),
                        rs.getInt("espaco"));
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN Bolsa+Treinador: " + e.getMessage());
        }
    }
}
