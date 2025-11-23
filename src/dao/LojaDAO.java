package dao;

import model.Loja;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LojaDAO {

    // Tabela Loja:
    // id_loja, nome, cidade, tipo

    public void inserir(Loja l) {
        String sql = "INSERT INTO Loja (nome, cidade, tipo) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getNome());
            stmt.setString(2, l.getCidade());
            stmt.setString(3, l.getTipo());

            stmt.executeUpdate();
            System.out.println("Loja criada!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir loja: " + e.getMessage());
        }
    }

    public void atualizar(Loja l) {
        String sql = "UPDATE Loja SET nome = ?, cidade = ?, tipo = ? WHERE id_loja = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getNome());
            stmt.setString(2, l.getCidade());
            stmt.setString(3, l.getTipo());
            stmt.setInt(4, l.getId());

            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Loja atualizada!" : "Loja não encontrada.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar loja: " + e.getMessage());
        }
    }

    public void deletar(int idLoja) {
        String sql = "DELETE FROM Loja WHERE id_loja = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLoja);
            int linhas = stmt.executeUpdate();
            System.out.println(linhas > 0 ? "Loja deletada!" : "Loja não encontrada.");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar loja: " + e.getMessage());
        }
    }

    public Loja buscarPorId(int idLoja) {
        String sql = "SELECT * FROM Loja WHERE id_loja = ?";
        Loja l = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLoja);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                l = new Loja();
                l.setId(rs.getInt("id_loja"));
                l.setNome(rs.getString("nome"));
                l.setCidade(rs.getString("cidade"));
                l.setTipo(rs.getString("tipo"));
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
                l.setId(rs.getInt("id_loja"));
                l.setNome(rs.getString("nome"));
                l.setCidade(rs.getString("cidade"));
                l.setTipo(rs.getString("tipo"));
                lista.add(l);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar lojas: " + e.getMessage());
        }

        return lista;
    }
}
