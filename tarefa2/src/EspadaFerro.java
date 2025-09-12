// EspadaFerro.java
public class EspadaFerro extends Arma {
    private int danoBase;

    public EspadaFerro(int danoBase) {
        super("Espada", "Ferro", 2);
        this.danoBase = danoBase;
    }

    @Override
    public int getDano() {
        return danoBase;
    }
}