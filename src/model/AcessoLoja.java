package model;

import java.time.LocalDateTime;

public class AcessoLoja {
    private int idTreinador;        // id_trainer
    private int idLoja;             // id_loja
    private LocalDateTime dataAcesso; // data_acesso (opcional no insert)

    public AcessoLoja() {}

    public AcessoLoja(int idTreinador, int idLoja, LocalDateTime dataAcesso) {
        this.idTreinador = idTreinador;
        this.idLoja = idLoja;
        this.dataAcesso = dataAcesso;
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

    public LocalDateTime getDataAcesso() {
        return dataAcesso;
    }

    public void setDataAcesso(LocalDateTime dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

    @Override
    public String toString() {
        return "AcessoLoja{" +
                "idTreinador=" + idTreinador +
                ", idLoja=" + idLoja +
                ", dataAcesso=" + dataAcesso +
                '}';
    }
}
