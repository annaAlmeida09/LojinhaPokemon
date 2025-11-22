package dao;

import model.Bolsa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BolsaDAO {

    // TABELA: Bolsa
    // COLUNAS:
    // id (PK), id_treinador (FK), espaco

    public void inserir(Bolsa b) {
        String sql = "INSERT INTO Bolsa (id, id_treinador, espaco) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, b.getId());
            stmt.setInt(2, b.getIdTreinador());
            stmt.setInt(3, b.getEspaco());

            stmt.executeUpdate();
            System.out.println("Bolsa inserida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir bolsa: " + e.getMessage());
        }
    }

    public void atualizar(Bolsa b) {
        String sql = "UPDATE Bolsa SET id_treinador = ?, espaco = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, b.getIdTreinador());
            stmt.setInt(2, b.getEspaco());
            stmt.setInt(3, b.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Bolsa atualizada com sucesso!");
            } else {
                System.out.println("Bolsa não encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar bolsa: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Bolsa WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Bolsa deletada com sucesso!");
            } else {
                System.out.println("Bolsa não encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar bolsa: " + e.getMessage());
        }
    }

    public Bolsa buscarPorId(int id) {
        String sql = "SELECT * FROM Bolsa WHERE id = ?";
        Bolsa b = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                b = new Bolsa();
                b.setId(rs.getInt("id"));
                b.setIdTreinador(rs.getInt("id_treinador"));
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
                b.setId(rs.getInt("id"));
                b.setIdTreinador(rs.getInt("id_treinador"));
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
                SELECT b.id, b.espaco,
                       t.nome AS treinador
                FROM Bolsa b
                JOIN Treinador t ON b.id_treinador = t.id
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("Bolsa %d | Espaço: %d | Treinador: %s%n",
                        rs.getInt("id"),
                        rs.getInt("espaco"),
                        rs.getString("treinador"));
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN Bolsa + Treinador: " + e.getMessage());
        }
    }
}
