package model;

import java.time.LocalDateTime;

public class Compra {
    private int idCompra;          // id_compra
    private int idTreinador;       // id_trainer
    private int idLoja;            // id_loja
    private LocalDateTime dataCompra; // data_compra
    private double valorTotal;     // valor_total

    public Compra() {}

    public Compra(int idCompra, int idTreinador, int idLoja,
                  LocalDateTime dataCompra, double valorTotal) {
        this.idCompra = idCompra;
        this.idTreinador = idTreinador;
        this.idLoja = idLoja;
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdTreinador() {
        return idTreinador;
    }

    public void setIdTreinador(int idTreinador) {
        this.idTreinador = idTreinador;
    }

    public int getIdLoja() {
        return idLoja;
    }

    public void setIdLoja(int idLoja) {
        this.idLoja = idLoja;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra=" + idCompra +
                ", idTreinador=" + idTreinador +
                ", idLoja=" + idLoja +
                ", dataCompra=" + dataCompra +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
