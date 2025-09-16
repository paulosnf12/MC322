// EspadaDiamante.java
public class EspadaDiamante extends Arma {
    private int danoBase;

    public EspadaDiamante(int danoBase) {
        super("Espada", "Diamante", 3);
        this.danoBase = danoBase;
    }

    @Override
    public int getDano() {
        return danoBase;
    }
}