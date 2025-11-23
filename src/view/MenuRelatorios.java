package view;

import dao.AcessoLojaDAO;
import dao.CompraDAO;
import dao.ProdutoDAO;

import java.util.Scanner;

public class MenuRelatorios {

    private final Scanner sc = new Scanner(System.in);
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final CompraDAO compraDAO = new CompraDAO();
    private final AcessoLojaDAO acessoLojaDAO = new AcessoLojaDAO();

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== RELATÓRIOS (JOINS) ===");
            System.out.println("1 - Produtos com Loja");
            System.out.println("2 - Compras com Treinador e Loja");
            System.out.println("3 - Compras com Produtos");
            System.out.println("4 - Acessos de Treinadores às Lojas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInt();

            switch (opcao) {
                case 1 -> produtoDAO.listarProdutosComLoja();
                case 2 -> compraDAO.listarComprasComTreinadorELoja();
                case 3 -> compraDAO.listarComprasComProdutos();
                case 4 -> acessoLojaDAO.listarAcessosComNomes();
                case 0 -> System.out.println("Voltando...");
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
