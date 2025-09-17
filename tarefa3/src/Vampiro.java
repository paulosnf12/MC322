// Vampiro.java
import java.util.ArrayList;

public class Vampiro extends Monstro {
    private int brilho; // atributo único
    
    public Vampiro(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, int brilho, ArrayList<Arma> listaDeArmasParaLargar) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido, listaDeArmasParaLargar); // Passa a lista para o construtor da superclasse
        this.brilho = brilho;
    }

    @Override
    protected void inicializarAcoes() {
        this.acoes.add(new AtaqueVampirico());
    }
    
        @Override
    public void apresentarDialogoEspecial() {
        if (this.getNome().equals("Edward Cullen")) {
            System.out.println("Edward Cullen: \"Eu sou um monstro, Bella! Minha pele brilha. Essa é a pele de um assassino!\"");
            System.out.println();
        }
    }

    public int getBrilho() {
        return brilho;
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Brilho = " + brilho;
    }
}