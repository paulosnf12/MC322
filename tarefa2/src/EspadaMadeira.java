// EspadaMadeira.java
public class EspadaMadeira extends Arma {
    private int danoBase;

    public EspadaMadeira(int danoBase) {
        super("Espada", "Madeira", 1);
        this.danoBase = danoBase;
    }

    @Override
    public int getDano() {
        return danoBase;
    }
}