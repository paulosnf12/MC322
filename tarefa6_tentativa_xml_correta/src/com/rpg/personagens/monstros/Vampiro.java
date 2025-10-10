// src/com/rpg/personagens/monstros/Vampiro.java
package com.rpg.personagens.monstros;

import com.rpg.combate.AtaqueVampirico;
import com.rpg.itens.ArmaDropSpec;
import com.rpg.personagens.Monstro;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement; // JAXB: É uma classe concreta que pode ser root

import java.util.ArrayList;

/**
 * Representa um monstro do tipo Vampiro no jogo.
 * Vampiros possuem um atributo único chamado "brilho",
 * que pode influenciar suas ações e interações no jogo.
 * Esta classe utiliza agregação compartilhada para sua ação de combate {@link AtaqueVampirico}.
 */
@XmlRootElement(name = "vampiro") // JAXB: Define o elemento raiz para esta classe
public class Vampiro extends Monstro {
    @XmlElement
    private int brilho; // atributo único
    // JAXB: Como ATAQUE_VAMPIRICO_INSTANCE é estático e final, ele não é serializado
    private static final AtaqueVampirico ATAQUE_VAMPIRICO_INSTANCE = new AtaqueVampirico();

    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para deserialização.
     */
    public Vampiro() {
        super();
        this.brilho = 0; // Valor padrão
    }

    /**
     * Construtor da classe Vampiro.
     *
     * @param nome O nome do Vampiro.
     * @param pontosDeVida Os pontos de vida iniciais do Vampiro.
     * @param forca A força inicial do Vampiro.
     * @param agilidade A agilidade inicial do Vampiro.
     * @param xpConcedido A quantidade de experiência que o Vampiro concede.
     * @param brilho O nível de brilho inicial do Vampiro.
     * @param listaDeArmasParaLargar Uma {@link ArrayList} de {@link ArmaDropSpec} que o Vampiro pode dropar.
     * @param nivelFase O nível da fase em que o Vampiro está, usado para escalonamento de loot.
     */
    public Vampiro(String nome, int pontosDeVida, int forca, int agilidade, int
            xpConcedido, int brilho, ArrayList<ArmaDropSpec> listaDeArmasParaLargar, int
            nivelFase) {
        super(nome, pontosDeVida, forca, agilidade, xpConcedido,
                listaDeArmasParaLargar, nivelFase);
        this.brilho = brilho;
    }

    @Override
    protected void inicializarAcoes() {
        this.acoes.add(ATAQUE_VAMPIRICO_INSTANCE);
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
        if (this.getNome().equals("Edward Cullen")) {
            System.out.println("Edward Cullen: \"Eu sou um monstro, Bella! Minha pele " +
                    "brilha. Essa é a pele de um assassino!\"");
            System.out.println();
        }
    }

    @Override
    public int getBrilho() {
        return brilho;
    }

    @Override
    public String exibirStatus() {
        return super.exibirStatus() + ", Brilho = " + brilho;
    }
}