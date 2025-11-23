package model;

public class Loja {
    private int id;
    private String nome;
    private String cidade;
    private String tipo;

    public Loja() {}

    public Loja(int id, String nome, String cidade, String tipo) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Loja{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cidade='" + cidade + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
