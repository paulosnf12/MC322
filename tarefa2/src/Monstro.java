//Monstro.java

import java.util.ArrayList;
import java.util.Random;

public abstract class Monstro extends Personagem {
    protected int xpConcedido;
    protected ArrayList<Arma> listaDeArmasParaLargar;

    public Monstro(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, ArrayList<Arma> listaDeArmasParaLargar) {
        super(nome, pontosDeVida, forca, agilidade);
        this.xpConcedido = xpConcedido;
        this.listaDeArmasParaLargar = listaDeArmasParaLargar;
    }

    public int getXpConcedido() {
        return xpConcedido;
    }

    public ArrayList<Arma> getListaDeArmasParaLargar() {
        return listaDeArmasParaLargar;
    }

    public void apresentarDialogoEspecial() {
        // Implementação padrão vazia ou com uma mensagem genérica
        // Pode ser sobrescrito por subclasses para diálogos específicos
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", XP Concedido = " + xpConcedido;
    }

    // Método corrigido para largar uma arma aleatória
    public Arma largaArma() {
        if (listaDeArmasParaLargar == null || listaDeArmasParaLargar.isEmpty()) {
            System.out.println(nome + " não largou nenhuma arma.");
            return null; // Não há armas para largar
        }
        Random rand = new Random();
        int idx = rand.nextInt(listaDeArmasParaLargar.size());
        Arma armaLargada = listaDeArmasParaLargar.get(idx);
        System.out.println(nome + " largou uma arma! (Dano: " + armaLargada.getDano() + ", Nível Mínimo: " + armaLargada.getMinNivel() + ")");
        return armaLargada;
    }
}
