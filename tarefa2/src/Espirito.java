// Espirito.java
import java.util.ArrayList;

public class Espirito extends Monstro {
    private int tristeza; // atributo único
    public Espirito(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, int tristeza, ArrayList<Arma> listaDeArmasParaLargar) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido, listaDeArmasParaLargar); // Passa a lista para o construtor da superclasse
        this.tristeza = tristeza;
    }
    @Override
    public void atacar(Personagem alvo) {
        int dano = this.forca + (this.tristeza / 10); // quanto maior a tristeza, maior o dano
        alvo.receberDano(dano);
        System.out.println(nome + " está triste, ele não gosta de ficar triste... Tristeza: " + tristeza + ". Dano causado: " + dano);
    }
    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Tristeza = " + tristeza;
    }
}