// ArcoBeta.java
public class ArcoBeta extends Arma {
    private int danoBase;

    public ArcoBeta(int danoBase) {
        super("Arco", "Beta", 1); // Nome do tipo, subtipo e nível mínimo
        this.danoBase = danoBase;
    }

    @Override
    public int getDano() {
        return danoBase;
    }
}