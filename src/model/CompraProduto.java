package model;

public class CompraProduto {
    private int idCompra;      // id_compra
    private int idProduto;     // id_produto
    private int quantidade;    // quantidade
    private double precoUnit;  // preco_unit

    public CompraProduto() {}

    public CompraProduto(int idCompra, int idProduto,
                         int quantidade, double precoUnit) {
        this.idCompra = idCompra;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precoUnit = precoUnit;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnit() {
        return precoUnit;
    }

    public void setPrecoUnit(double precoUnit) {
        this.precoUnit = precoUnit;
    }

    @Override
    public String toString() {
        return "CompraProduto{" +
                "idCompra=" + idCompra +
                ", idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                ", precoUnit=" + precoUnit +
                '}';
    }
}
