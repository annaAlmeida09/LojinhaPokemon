package view;

import dao.LojaDAO;
import model.Loja;

import java.util.List;
import java.util.Scanner;

public class MenuLoja {

    private final Scanner sc = new Scanner(System.in);
    private final LojaDAO dao = new LojaDAO();

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== MENU LOJA ===");
            System.out.println("1 - Cadastrar Loja");
            System.out.println("2 - Listar Lojas");
            System.out.println("3 - Atualizar Loja");
            System.out.println("4 - Deletar Loja");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> atualizar();
                case 4 -> deletar();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Cidade: ");
        String cidade = sc.nextLine();

        System.out.print("Tipo: ");
        String tipo = sc.nextLine();

        Loja l = new Loja(0, nome, cidade, tipo);
        dao.inserir(l);
    }

    private void listar() {
        List<Loja> lista = dao.listarTodas();
        System.out.println("\n--- LOJAS ---");
        for (Loja l : lista) {
            System.out.println(l);
        }
    }

    private void atualizar() {
        System.out.print("ID da loja: ");
        int id = lerInt();

        Loja l = dao.buscarPorId(id);
        if (l == null) {
            System.out.println("Loja não encontrada.");
            return;
        }

        System.out.print("Novo nome (" + l.getNome() + "): ");
        String nome = sc.nextLine();
        if (!nome.isBlank()) l.setNome(nome);

        System.out.print("Nova cidade (" + l.getCidade() + "): ");
        String cidade = sc.nextLine();
        if (!cidade.isBlank()) l.setCidade(cidade);

        System.out.print("Novo tipo (" + l.getTipo() + "): ");
        String tipo = sc.nextLine();
        if (!tipo.isBlank()) l.setTipo(tipo);

        dao.atualizar(l);
    }

    private void deletar() {
        System.out.print("ID da loja para excluir: ");
        int id = lerInt();
        dao.deletar(id);
    }

    private int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
