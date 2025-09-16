// Vampiro.java
import java.util.ArrayList;

public class Vampiro extends Monstro {
    private int brilho; // atributo único
    
    public Vampiro(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, int brilho, ArrayList<Arma> listaDeArmasParaLargar) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido, listaDeArmasParaLargar); // Passa a lista para o construtor da superclasse
        this.brilho = brilho;
    }
    
        @Override
    public void apresentarDialogoEspecial() {
        if (this.getNome().equals("Edward Cullen")) {
            System.out.println("Edward Cullen: \"Eu sou um monstro, Bella! Minha pele brilha. Essa é a pele de um assassino!\"");
            System.out.println();
        }
    }
    
    @Override
    public void atacar(Personagem alvo) {
        int dano = this.forca + (this.brilho / 10); // quanto maior o brilho, maior o dano
        alvo.receberDano(dano);
        System.out.println(nome + " ataca com seu brilho! Brilho: " + brilho + ". Dano causado: " + dano);
    }
    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Brilho = " + brilho;
    }
}