package view;

import dao.TreinadorDAO;
import model.Treinador;

import java.util.List;
import java.util.Scanner;

public class MenuTreinador {

    private final Scanner sc = new Scanner(System.in);
    private final TreinadorDAO dao = new TreinadorDAO();

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== MENU TREINADOR ===");
            System.out.println("1 - Cadastrar Treinador");
            System.out.println("2 - Listar Treinadores");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Atualizar Treinador");
            System.out.println("5 - Deletar Treinador");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> buscarPorId();
                case 4 -> atualizar();
                case 5 -> deletar();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("PokéYens: ");
        double pokeYens = lerDouble();

        System.out.print("Insígnias: ");
        int insignias = lerInt();

        Treinador t = new Treinador(0, nome, pokeYens, insignias);
        dao.inserir(t);
    }

    private void listar() {
        List<Treinador> lista = dao.listarTodos();
        System.out.println("\n--- TREINADORES ---");
        for (Treinador t : lista) {
            System.out.println(t);
        }
    }

    private void buscarPorId() {
        System.out.print("ID do treinador: ");
        int id = lerInt();

        Treinador t = dao.buscarPorId(id);
        if (t != null) {
            System.out.println(t);
        } else {
            System.out.println("Treinador não encontrado.");
        }
    }

    private void atualizar() {
        System.out.print("ID do treinador: ");
        int id = lerInt();

        Treinador t = dao.buscarPorId(id);
        if (t == null) {
            System.out.println("Treinador não encontrado.");
            return;
        }

        System.out.print("Novo nome (" + t.getNome() + "): ");
        String nome = sc.nextLine();
        if (!nome.isBlank()) t.setNome(nome);

        System.out.print("Novo PokéYens (" + t.getPokeYens() + "): ");
        double pokeYens = lerDoubleOpcional(t.getPokeYens());
        t.setPokeYens(pokeYens);

        System.out.print("Novas Insígnias (" + t.getInsignias() + "): ");
        int insignias = lerIntOpcional(t.getInsignias());
        t.setInsignias(insignias);

        dao.atualizar(t);
    }

    private void deletar() {
        System.out.print("ID do treinador para excluir: ");
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

    private int lerIntOpcional(int valorAtual) {
        String linha = sc.nextLine();
        if (linha.isBlank()) return valorAtual;
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return valorAtual;
        }
    }

    private double lerDouble() {
        try {
            return Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double lerDoubleOpcional(double valorAtual) {
        String linha = sc.nextLine();
        if (linha.isBlank()) return valorAtual;
        try {
            return Double.parseDouble(linha);
        } catch (NumberFormatException e) {
            return valorAtual;
        }
    }
}
