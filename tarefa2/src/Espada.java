public class Espada {

    // atributos
    
    // tipos de espada: Madeira, Ferro, Diamante

    private int danoMadeira;
    private int danoFerro;
    private int danoDiamante;

    // construtor
    
    // personaliza os danos das espadas
    public Espada(int danoMadeira, int danoFerro, int danoDiamante) {
        this.danoMadeira = danoMadeira;
        this.danoFerro = danoFerro;
        this.danoDiamante = danoDiamante;
    }

    // métodos

    // getters para retornar os danos das espadas
    public int getDanoMadeira() {
        return danoMadeira;
    }

    public int getDanoFerro() {
        return danoFerro;
    }

    public int getDanoDiamante() {
        return danoDiamante;
    }
}