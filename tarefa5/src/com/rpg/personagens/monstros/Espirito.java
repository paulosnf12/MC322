// Espirito.java
package com.rpg.personagens.monstros;
import com.rpg.combate.AtaqueAssombrado;
import com.rpg.itens.Arma;
import com.rpg.personagens.Monstro;
import java.util.ArrayList;

/**
 * Representa um monstro do tipo Espírito no jogo.
 * Espíritos possuem um atributo único chamado "tristeza",
 * que pode influenciar suas ações e interações no jogo.
 */
public class Espirito extends Monstro {
    private int tristeza; // atributo único
    public Espirito(String nome, int pontosDeVida, int forca, int agilidade, int xpConcedido, int tristeza, ArrayList<Arma> listaDeArmasParaLargar) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido, listaDeArmasParaLargar); // Passa a lista para o construtor da superclasse
        this.tristeza = tristeza;
    }

    @Override
    protected void inicializarAcoes() {
        this.acoes.add(new AtaqueAssombrado());
    }

    @Override
    public void apresentarDialogoEspecial() {
        if (this.getNome().equals("Kaonashi")) {
            System.out.println("Kaonashi: \"Você... quer?\" (A voz que sai dele é um eco distorcido de suas vítimas. Ao mesmo tempo, ele estende uma mão trêmula, oferecendo pepitas de ouro que brilham com uma luz doentia. O gesto é uma armadilha, e sua fome insaciável parece entortar os traços de sua máscara.)");
            System.out.println();
        }
    }

    public int getTristeza() {
        return tristeza;
    }   

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Tristeza = " + tristeza;
    }
}