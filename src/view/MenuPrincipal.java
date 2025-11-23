package view;

import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner sc = new Scanner(System.in);

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== LOJINHA POKÉMON ===");
            System.out.println("1 - Gerenciar Treinadores");
            System.out.println("2 - Gerenciar Lojas");
            System.out.println("3 - Gerenciar Produtos");
            System.out.println("4 - Gerenciar Compras");
            System.out.println("5 - Relatórios");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = lerInt();

            switch (opcao) {
                case 1 -> new MenuTreinador().exibirMenu();
                case 2 -> new MenuLoja().exibirMenu();
                case 3 -> new MenuProduto().exibirMenu();
                case 4 -> new MenuCompra().exibirMenu();
                case 5 -> new MenuRelatorios().exibirMenu();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
