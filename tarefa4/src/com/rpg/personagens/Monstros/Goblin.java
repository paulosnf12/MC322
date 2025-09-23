// Goblin.java
package com.rpg.personagens.monstros;
import com.rpg.combate.AtaqueGoblin;
import com.rpg.itens.Arma;
import com.rpg.personagens.Monstro;
import java.util.ArrayList;

public class Goblin extends Monstro {
    // atributos
    protected String tipoDeArma;
    protected int danoArma;
    protected double chanceDeRoubo;

    // construtor:
    public Goblin(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, String tipoDeArma, int danoArma, double chanceDeRoubo, ArrayList<Arma> listaDeArmasParaLargar) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido, listaDeArmasParaLargar); // Passa a lista para o construtor da superclasse
        this.tipoDeArma = tipoDeArma;
        this.danoArma = danoArma;
        this.chanceDeRoubo = chanceDeRoubo;
    }

    @Override
    protected void inicializarAcoes() {
        // Adiciona a ação específica do Goblin
        this.acoes.add(new AtaqueGoblin());
    }

    @Override
    public void apresentarDialogoEspecial() {
        if (this.getNome().equals("Goblin Guerreiro")) {
            System.out.println("Goblin Guerreiro: \"Hehehe! Você não vai passar por mim tão fácil, herói! Minha clava está sedenta por batalha!\"");
            System.out.println(); // Adiciona uma linha em branco
        }
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Arma: " + tipoDeArma + ", Dano Arma: " + danoArma + ", Chance de Roubo: " + String.format("%.2f", chanceDeRoubo);
    }

    public int getDanoArma() {
        return danoArma;
    }

    public String getTipoDeArma() {
        return tipoDeArma;
    }

    public double getChanceDeRoubo() {
        return chanceDeRoubo;
    }
}