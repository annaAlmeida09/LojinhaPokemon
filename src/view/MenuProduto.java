package view;

import dao.ProdutoDAO;
import model.Produto;

import java.util.List;
import java.util.Scanner;

public class MenuProduto {

    private final Scanner sc = new Scanner(System.in);
    private final ProdutoDAO dao = new ProdutoDAO();

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== MENU PRODUTO ===");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Listar Produtos com Loja (JOIN)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> dao.listarProdutosComLoja();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        System.out.print("Tipo: ");
        String tipo = sc.nextLine();

        System.out.print("Preço: ");
        double preco = lerDouble();

        System.out.print("ID da Loja: ");
        int idLoja = lerInt();

        Produto p = new Produto(0, nome, descricao, tipo, preco, idLoja);
        dao.inserir(p);
    }

    private void listar() {
        List<Produto> lista = dao.listarTodos();
        System.out.println("\n--- PRODUTOS ---");
        for (Produto p : lista) {
            System.out.println(p);
        }
    }

    private int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double lerDouble() {
        try {
            return Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
