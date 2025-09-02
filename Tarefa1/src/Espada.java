public class Espada {

    // atributos
    
    // tipos de espada: Ferro, Prata, Ouro

    private int danoFerro;
    private int danoPrata;
    private int danoOuro;

    // construtor
    
    // personaliza os danos das espadas
    public Espada(int danoFerro, int danoPrata, int danoOuro) {
        this.danoFerro = danoFerro;
        this.danoPrata = danoPrata;
        this.danoOuro = danoOuro;
    }

    // métodos

    // getters para retornar os danos das espadas
    public int getDanoFerro() {
        return danoFerro;
    }

    public int getDanoPrata() {
        return danoPrata;
    }

    public int getDanoOuro() {
        return danoOuro;
    }
}