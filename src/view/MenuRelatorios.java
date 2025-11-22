package view;

import dao.AcessoLojaDAO;
import dao.CompraDAO;
import dao.LojaDAO;
import dao.ProdutoDAO;

import java.util.Scanner;

public class MenuRelatorios {

    private final Scanner sc = new Scanner(System.in);
    private final LojaDAO lojaDAO = new LojaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final CompraDAO compraDAO = new CompraDAO();
    private final AcessoLojaDAO acessoLojaDAO = new AcessoLojaDAO();

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== RELATÓRIOS (JOINS) ===");
            System.out.println("1 - Lojas com Treinador Responsável");
            System.out.println("2 - Produtos com Loja");
            System.out.println("3 - Compras com Treinador");
            System.out.println("4 - Compras com Produtos");
            System.out.println("5 - Acessos de Treinadores às Lojas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInt();

            switch (opcao) {
                case 1 -> lojaDAO.listarLojasComTreinadorResponsavel();
                case 2 -> produtoDAO.listarProdutosComLoja();
                case 3 -> compraDAO.listarComprasComTreinador();
                case 4 -> compraDAO.listarComprasComProdutos();
                case 5 -> acessoLojaDAO.listarAcessosComNomes();
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
