package model;

public class Treinador {
    private int id;
    private String nome;
    private double pokeYens;
    private int insignias;

    public Treinador() {}

    public Treinador(int id, String nome, double pokeYens, int insignias) {
        this.id = id;
        this.nome = nome;
        this.pokeYens = pokeYens;
        this.insignias = insignias;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPokeYens() { return pokeYens; }
    public void setPokeYens(double pokeYens) { this.pokeYens = pokeYens; }

    public int getInsignias() { return insignias; }
    public void setInsignias(int insignias) { this.insignias = insignias; }

    @Override
    public String toString() {
        return "Treinador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", pokeYens=" + pokeYens +
                ", insignias=" + insignias +
                '}';
    }
}
