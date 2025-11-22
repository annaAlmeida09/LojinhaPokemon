package model;

public class AcessoLoja {
    private int idTreinador;
    private int idLoja;

    public AcessoLoja() {}

    public AcessoLoja(int idTreinador, int idLoja) {
        this.idTreinador = idTreinador;
        this.idLoja = idLoja;
    }

    public int getIdTreinador() { return idTreinador; }
    public void setIdTreinador(int idTreinador) { this.idTreinador = idTreinador; }

    public int getIdLoja() { return idLoja; }
    public void setIdLoja(int idLoja) { this.idLoja = idLoja; }

    @Override
    public String toString() {
        return "AcessoLoja{" +
                "idTreinador=" + idTreinador +
                ", idLoja=" + idLoja +
                '}';
    }
}
