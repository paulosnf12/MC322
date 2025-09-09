
/*Atributos:
• dano - Quanto de dano extra esta arma dá ao Personagem.
• minNivel - Nı́vel mı́nimo que o Herói precisa ter para utilizar a Arma (note que monstros não
possuem nı́vel definido, podendo usar qualquer arma).*/

public class Arma {
    private int dano;
    private int minNivel;

    public Arma(int dano, int minNivel) {
        this.dano = dano;
        this.minNivel = minNivel;
    }

    public int getDano() {
        return dano;
    }

    public int getMinNivel() {
        return minNivel;
    }
}
