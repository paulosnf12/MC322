// segunda arma concreta que herda de Arma (arma média)

// Garras (arma média)

public class Garras extends Arma {

    private int comprimento;

    public Garras(int dano, int minNivel) {
        super(dano, minNivel);
        this.comprimento = 8; // valor fixo para comprimento das garras
    }

    @Override
    public int getDano() {
        return super.getDano() + comprimento; // dano total inclui comprimento
    }

    public int getComprimento() {
        return comprimento;
        }

    public String getTipoArma() {
    return "Arma Média";    
    }
}

