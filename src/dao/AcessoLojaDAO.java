package dao;

import model.AcessoLoja;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcessoLojaDAO {

    // TABELA: Acesso_Loja
    // COLUNAS:
    // id_treinador, id_loja

    public void inserir(AcessoLoja a) {
        String sql = "INSERT INTO Acesso_Loja (id_treinador, id_loja) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getIdTreinador());
            stmt.setInt(2, a.getIdLoja());

            stmt.executeUpdate();
            System.out.println("Acesso registrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir acesso: " + e.getMessage());
        }
    }

    public void deletar(int idTreinador, int idLoja) {
        String sql = "DELETE FROM Acesso_Loja WHERE id_treinador = ? AND id_loja = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTreinador);
            stmt.setInt(2, idLoja);

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Acesso deletado com sucesso!");
            } else {
                System.out.println("Acesso não encontrado.");
            }

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
                a.setIdTreinador(rs.getInt("id_treinador"));
                a.setIdLoja(rs.getInt("id_loja"));
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
                SELECT t.nome AS treinador, l.nome AS loja, l.cidade
                FROM Acesso_Loja al
                JOIN Treinador t ON al.id_treinador = t.id
                JOIN Loja l ON al.id_loja = l.id
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("Treinador: %s | Loja: %s (%s)%n",
                        rs.getString("treinador"),
                        rs.getString("loja"),
                        rs.getString("cidade"));
            }

        } catch (SQLException e) {
            System.out.println("Erro no JOIN de acessos: " + e.getMessage());
        }
    }
}
