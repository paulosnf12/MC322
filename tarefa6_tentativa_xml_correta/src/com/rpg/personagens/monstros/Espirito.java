// src/com/rpg/personagens/monstros/Espirito.java
package com.rpg.personagens.monstros;

import com.rpg.combate.AtaqueAssombrado;
import com.rpg.itens.ArmaDropSpec;
import com.rpg.personagens.Monstro;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement; // JAXB: É uma classe concreta que pode ser root

import java.util.ArrayList;

/**
 * Representa um monstro do tipo Espírito no jogo.
 * Espíritos possuem um atributo único chamado "tristeza",
 * que pode influenciar suas ações e interações no jogo.
 * Esta classe utiliza agregação compartilhada para sua ação de combate {@link AtaqueAssombrado}.
 */
@XmlRootElement(name = "espirito") // JAXB: Define o elemento raiz para esta classe
public class Espirito extends Monstro {
    @XmlElement
    private int tristeza; // atributo único
    // JAXB: Como ATAQUE_ASSOMBRADO_INSTANCE é estático e final, ele não é serializado
    private static final AtaqueAssombrado ATAQUE_ASSOMBRADO_INSTANCE = new AtaqueAssombrado();

    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para deserialização.
     */
    public Espirito() {
        super();
        this.tristeza = 0; // Valor padrão
    }

    /**
     * Construtor da classe Espirito.
     *
     * @param nome O nome do Espírito.
     * @param pontosDeVida Os pontos de vida iniciais do Espírito.
     * @param forca A força inicial do Espírito.
     * @param agilidade A agilidade inicial do Espírito.
     * @param xpConcedido A quantidade de experiência que o Espírito concede.
     * @param tristeza O nível de tristeza inicial do Espírito.
     * @param listaDeArmasParaLargar Uma {@link ArrayList} de {@link ArmaDropSpec} que o Espírito pode dropar.
     * @param nivelFase O nível da fase em que o Espírito está, usado para escalonamento de loot.
     */
    public Espirito(String nome, int pontosDeVida, int forca, int agilidade, int
            xpConcedido, int tristeza, ArrayList<ArmaDropSpec> listaDeArmasParaLargar, int
            nivelFase) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido,
                listaDeArmasParaLargar, nivelFase);
        this.tristeza = tristeza;
    }

    @Override
    protected void inicializarAcoes() {
        this.acoes.add(ATAQUE_ASSOMBRADO_INSTANCE);
    }

    /**
     * JAXB: Sobrescreve para garantir que as ações de combate sejam repopuladas.
     */
    @Override
    public void initializeTransientFields() {
        super.initializeTransientFields();
        // Garante que a lista de ações seja populada com a instância estática
        this.acoes.clear();
        inicializarAcoes();
    }

    @Override
    public void apresentarDialogoEspecial() {
        if (this.getNome().equals("Kaonashi")) {
            System.out.println("Kaonashi: \"Você... quer?\" (A voz que sai dele é um " +
                    "eco distorcido de suas vítimas. Ao mesmo tempo, ele estende uma mão trêmula, " +
                    "oferecendo pepitas de ouro que brilham com uma luz doentia. O gesto é uma armadilha, " +
                    "e sua fome insaciável parece entortar os traços de sua máscara.)");
            System.out.println();
        }
    }

    @Override
    public int getTristeza() {
        return tristeza;
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Tristeza = " + tristeza;
    }
}