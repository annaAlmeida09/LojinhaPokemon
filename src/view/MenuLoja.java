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
            System.out.println("3 - Listar Lojas com Treinador Responsável (JOIN)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> dao.listarLojasComTreinadorResponsavel();
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

        System.out.print("ID do Treinador Responsável: ");
        int idTrainer = lerInt();

        Loja l = new Loja(0, nome, cidade, tipo, idTrainer);
        dao.inserir(l);
    }

    private void listar() {
        List<Loja> lista = dao.listarTodas();
        System.out.println("\n--- LOJAS ---");
        for (Loja l : lista) {
            System.out.println(l);
        }
    }

    private int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
