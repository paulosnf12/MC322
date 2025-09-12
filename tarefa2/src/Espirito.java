// Espirito.java
import java.util.ArrayList;

public class Espirito extends Monstro {
    private int tristeza; // atributo único
    public Espirito(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, int tristeza, ArrayList<Arma> listaDeArmasParaLargar) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido, listaDeArmasParaLargar); // Passa a lista para o construtor da superclasse
        this.tristeza = tristeza;
    }

    @Override
    public void apresentarDialogoEspecial() {
        if (this.getNome().equals("Kaonashi")) {
            System.out.println("Kaonashi: \"Você... quer?\" (A voz que sai dele é um eco distorcido de suas vítimas. Ao mesmo tempo, ele estende uma mão trêmula, oferecendo pepitas de ouro que brilham com uma luz doentia. O gesto é uma armadilha, e sua fome insaciável parece entortar os traços de sua máscara.)");
            System.out.println();
        }
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