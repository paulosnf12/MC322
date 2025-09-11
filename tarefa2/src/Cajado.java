// terceira arma concreta que herda de Arma (arma média)

// Cajado mágico (item raro)

public class Cajado extends Arma {
    private int poderMagico;

    public Cajado(int dano, int minNivel) {
        super(dano, minNivel);
        this.poderMagico = 15; // Permite definir o valor ao criar o Cajado
    }

    @Override
    public int getDano() {
        return super.getDano() + poderMagico; // Soma o dano base com o poder mágico
    }

    public int getPoderMagico() {
        return poderMagico;
    }

    public String getTipoArma() {
    return "Arma Rara";
    }
}

