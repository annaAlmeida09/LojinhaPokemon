package model;

public class Compra {
    private int idCompra;
    private int idTreinador;
    private double valorTotal;
    private double precoInsignias;

    public Compra() {}

    public Compra(int idCompra, int idTreinador, double valorTotal, double precoInsignias) {
        this.idCompra = idCompra;
        this.idTreinador = idTreinador;
        this.valorTotal = valorTotal;
        this.precoInsignias = precoInsignias;
    }

    public int getIdCompra() { return idCompra; }
    public void setIdCompra(int idCompra) { this.idCompra = idCompra; }

    public int getIdTreinador() { return idTreinador; }
    public void setIdTreinador(int idTreinador) { this.idTreinador = idTreinador; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public double getPrecoInsignias() { return precoInsignias; }
    public void setPrecoInsignias(double precoInsignias) { this.precoInsignias = precoInsignias; }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra=" + idCompra +
                ", idTreinador=" + idTreinador +
                ", valorTotal=" + valorTotal +
                ", precoInsignias=" + precoInsignias +
                '}';
    }
}
