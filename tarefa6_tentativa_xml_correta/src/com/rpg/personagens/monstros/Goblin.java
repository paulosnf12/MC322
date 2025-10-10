// src/com/rpg/personagens/monstros/Goblin.java
package com.rpg.personagens.monstros;

import com.rpg.combate.AtaqueGoblin;
import com.rpg.itens.ArmaDropSpec;
import com.rpg.personagens.Monstro;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement; // JAXB: É uma classe concreta que pode ser root

import java.util.ArrayList;

/**
 * Representa um monstro do tipo Goblin no jogo.
 * Goblins possuem atributos específicos relacionados ao seu estilo de combate,
 * como o tipo de arma que utilizam, o dano causado por essa arma e a chance de roubo de vida.
 * Esta classe utiliza agregação compartilhada para sua ação de combate {@link AtaqueGoblin}.
 */
@XmlRootElement(name = "goblin") // JAXB: Define o elemento raiz para esta classe
public class Goblin extends Monstro {
    @XmlElement
    protected String tipoDeArma;
    @XmlElement
    protected int danoArma;
    @XmlElement
    protected double chanceDeRoubo;
    // JAXB: Como ATAQUE_GOBLIN_INSTANCE é estático e final, ele não é serializado
    private static final AtaqueGoblin ATAQUE_GOBLIN_INSTANCE = new AtaqueGoblin();

    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para deserialização.
     */
    public Goblin() {
        super();
        this.tipoDeArma = "Desconhecido"; // Valor padrão
        this.danoArma = 0;
        this.chanceDeRoubo = 0.0;
    }

    /**
     * Construtor da classe Goblin.
     *
     * @param nome O nome do Goblin.
     * @param pontosDeVida Os pontos de vida iniciais do Goblin.
     * @param forca A força inicial do Goblin.
     * @param agilidade A agilidade inicial do Goblin.
     * @param xpConcedido A quantidade de experiência que o Goblin concede.
     * @param tipoDeArma O tipo de arma inerente do Goblin.
     * @param danoArma O dano base da arma do Goblin.
     * @param chanceDeRoubo A probabilidade de roubo de vida do Goblin.
     * @param listaDeArmasParaLargar Uma {@link ArrayList} de {@link ArmaDropSpec} que o Goblin pode dropar.
     * @param nivelFase O nível da fase em que o Goblin está, usado para escalonamento de loot.
     */
    public Goblin(String nome, int pontosDeVida, int forca, int agilidade, int
            xpConcedido, String tipoDeArma, int danoArma, double chanceDeRoubo,
                  ArrayList<ArmaDropSpec> listaDeArmasParaLargar, int nivelFase) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido,
                listaDeArmasParaLargar, nivelFase);
        this.tipoDeArma = tipoDeArma;
        this.danoArma = danoArma;
        this.chanceDeRoubo = chanceDeRoubo;
    }

    @Override
    protected void inicializarAcoes() {
        this.acoes.add(ATAQUE_GOBLIN_INSTANCE);
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
        if (this.getNome().equals("Goblin Guerreiro")) {
            System.out.println("Goblin Guerreiro: \"Hehehe! Você não vai passar por " +
                    "mim tão fácil, herói! Minha clava está sedenta por batalha!\"");
            System.out.println();
        }
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Arma: " + tipoDeArma + ", Dano Arma: " +
                danoArma + ", Chance de Roubo: " + String.format("%.2f", chanceDeRoubo);
    }

    @Override
    public int getDanoArmaBase() {
        return danoArma;
    }

    @Override
    public String getTipoDeArmaBase() {
        return tipoDeArma;
    }

    @Override
    public double getChanceDeRoubo() {
        return chanceDeRoubo;
    }
}