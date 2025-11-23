package view;

import dao.CompraDAO;
import dao.CompraProdutoDAO;
import model.Compra;
import model.CompraProduto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MenuCompra {

    private final Scanner sc = new Scanner(System.in);
    private final CompraDAO compraDAO = new CompraDAO();
    private final CompraProdutoDAO cpDAO = new CompraProdutoDAO();

    public void exibirMenu() {
        int opcao;

        do {
            System.out.println("\n=== MENU COMPRA ===");
            System.out.println("1 - Cadastrar Compra");
            System.out.println("2 - Listar Compras (simples)");
            System.out.println("3 - Listar Compras com Treinador e Loja (JOIN)");
            System.out.println("4 - Listar Compras com Produtos (JOIN intermediária)");
            System.out.println("5 - Adicionar Produto em Compra");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInt();

            switch (opcao) {
                case 1 -> cadastrarCompra();
                case 2 -> listarComprasSimples();
                case 3 -> compraDAO.listarComprasComTreinadorELoja();
                case 4 -> compraDAO.listarComprasComProdutos();
                case 5 -> adicionarProdutoNaCompra();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrarCompra() {
        System.out.print("ID do Treinador: ");
        int idTreinador = lerInt();

        System.out.print("ID da Loja: ");
        int idLoja = lerInt();

        // valor_total pode ser 0 e ser atualizado depois via trigger/view
        Compra c = new Compra();
        c.setIdTreinador(idTreinador);
        c.setIdLoja(idLoja);
        c.setValorTotal(0.0);
        c.setDataCompra(LocalDateTime.now());

        compraDAO.inserir(c);
        System.out.println("ID da compra gerado: " + c.getIdCompra());
    }

    private void listarComprasSimples() {
        List<Compra> lista = compraDAO.listarTodas();
        System.out.println("\n--- COMPRAS ---");
        for (Compra c : lista) {
            System.out.println(c);
        }
    }

    private void adicionarProdutoNaCompra() {
        System.out.print("ID da Compra: ");
        int idCompra = lerInt();

        System.out.print("ID do Produto: ");
        int idProduto = lerInt();

        System.out.print("Quantidade: ");
        int qtd = lerInt();

        System.out.print("Preço unitário (preco_unit): ");
        double precoUnit = lerDouble();

        CompraProduto cp = new CompraProduto(idCompra, idProduto, qtd, precoUnit);
        cpDAO.inserir(cp);
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
