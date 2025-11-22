package model;

public class Bolsa {
    private int id;
    private int idTreinador;
    private int espaco;

    public Bolsa() {}

    public Bolsa(int id, int idTreinador, int espaco) {
        this.id = id;
        this.idTreinador = idTreinador;
        this.espaco = espaco;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdTreinador() { return idTreinador; }
    public void setIdTreinador(int idTreinador) { this.idTreinador = idTreinador; }

    public int getEspaco() { return espaco; }
    public void setEspaco(int espaco) { this.espaco = espaco; }

    @Override
    public String toString() {
        return "Bolsa{" +
                "id=" + id +
                ", idTreinador=" + idTreinador +
                ", espaco=" + espaco +
                '}';
    }
}
