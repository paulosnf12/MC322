public class Arcos {
    
    // atributos

    // ao aumentar o nível de xp, você aumenta a força do arco
    private int danoBeta;
    private int danoAlpha;
    private int danoSigma;

    // construtor

    public Arcos(int danoBeta, int danoAlpha, int danoSigma){
        this.danoBeta = danoBeta;
        this.danoAlpha = danoAlpha;
        this.danoSigma = danoSigma;
    }

    // metodos

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
