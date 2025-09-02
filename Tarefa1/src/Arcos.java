public class Arcos {
    
    // atributos

    // ao aumentar o nível de xp, você aumenta a força do arco

    // tipos de arco (indicados pelo dano): Beta, Alpha e Sigma

    private int danoBeta;
    private int danoAlpha;
    private int danoSigma;

    // construtor

    // personaliza os danos nos respectivos arcos (manual)

    public Arcos(int danoBeta, int danoAlpha, int danoSigma){
        this.danoBeta = danoBeta;
        this.danoAlpha = danoAlpha;
        this.danoSigma = danoSigma;
    }

    // metodos

    // getters para retornar os danos dos arcos

    public int getDanoBeta(){
        return danoBeta;
    }

    public int getDanoAlpha(){
        return danoAlpha;
    }
    public int getDanoSigma(){
        return danoSigma;
    }
}
