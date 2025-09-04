public class Espirito extends Monstro {
    private int tristeza; // atributo único

    public Espirito(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, int tristeza) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido);
        this.tristeza = tristeza;
    }

    @Override
public void atacar(Personagem alvo) {
    int dano = this.forca * this.tristeza; // quanto maior a tristeza, maior o dano
    alvo.receberDano(dano);
    System.out.println(nome + " está triste, ele não gosta de ficar triste... Tristeza: " + tristeza + ". Dano causado: " + dano);
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Tristeza = " + tristeza;
    }
}