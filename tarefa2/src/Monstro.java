/*
public abstract class Monstro extends Personagem {

    // atributos

    protected  int xpConcedido;

    // construtor

    public Monstro(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido) {
        super(nome, pontosDeVida, forca, agilidade);
        this.xpConcedido = xpConcedido;
    }

    // métodos

    public int getXpConcedido() {
        return xpConcedido;
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", XP Concedido = " + xpConcedido;
    }
}
 */

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

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", XP Concedido = " + xpConcedido;
    }

    public Arma largaArma() {
        if (listaDeArmasParaLargar == null || listaDeArmasParaLargar.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        int idx = rand.nextInt(listaDeArmasParaLargar.size());
        return listaDeArmasParaLargar.get(idx);
    }
}