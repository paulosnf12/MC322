// primeira arma concreta que herda de Arma (arma fraca)

// Adagas comuns (arma fraca)

public class Adaga extends Arma {

    private int afiacao;

    public Adaga(int dano, int minNivel) {
        super(dano, minNivel);
        this.afiacao = 3; // valor fixo de afiação para a adaga
    }

    @Override
    public int getDano() {
        return super.getDano() + afiacao; // dano total inclui afiação
    }

    public int getAfiacao() {
        return afiacao;
    }

    public String getTipoArma() {
    return "Arma Fraca";
    }

}