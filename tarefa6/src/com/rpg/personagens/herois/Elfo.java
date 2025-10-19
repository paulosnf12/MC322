// src/com/rpg/personagens/herois/Elfo.java
package com.rpg.personagens.herois;

import com.rpg.combate.AtaqueArco;
import com.rpg.combate.FlechaMagica;
import com.rpg.exceptions.NivelInsuficienteException;
import com.rpg.itens.ArcoAlpha;
import com.rpg.itens.ArcoBeta;
import com.rpg.itens.ArcoSigma;
import com.rpg.itens.Arma;
import com.rpg.personagens.Heroi;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement; // JAXB: É uma classe concreta que pode ser root

/**
 * Representa o personagem Elfo, um herói especializado em ataques à distância com arcos.
 * O Elfo pode utilizar diferentes tipos de arcos conforme seu nível, e possui habilidades
 * únicas como cura ao atacar. Ele pode equipar armas que implementam a interface Arma (atualmente arcos).
 *
 * Esta classe utiliza o conceito de agregação para suas ações de combate,
 * onde as instâncias de {@link AtaqueArco} e {@link FlechaMagica} são referenciadas,
 * mas podem existir independentemente do Elfo.
 */
@XmlRootElement(name = "elfo") // JAXB: Define o elemento raiz para esta classe
public class Elfo extends Heroi {
    @XmlElement
    private int configDanoBeta;
    @XmlElement
    private int configDanoAlpha;
    @XmlElement
    private int configDanoSigma;

    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para deserialização.
     */
    public Elfo() {
        super();
        // Inicializa as configurações de dano com valores padrão para evitar NPEs antes da deserialização
        this.configDanoBeta = 0;
        this.configDanoAlpha = 0;
        this.configDanoSigma = 0;
    }

    /**
     * Construtor do Elfo.
     *
     * @param nome Nome do Elfo.
     * @param pontosDeVida Pontos de vida iniciais do Elfo.
     * @param forca Força do Elfo, que afeta o dano físico.
     * @param agilidade Agilidade do Elfo, que pode afetar esquiva e velocidade.
     * @param nivel Nível inicial do Elfo.
     * @param experiencia Experiência inicial do Elfo.
     * @param danoBeta Dano base do arco Beta.
     * @param danoAlpha Dano base do arco Alpha.
     * @param danoSigma Dano base do arco Sigma.
     * @param mana Mana inicial do Elfo.
     */
    public Elfo(String nome, int pontosDeVida, int forca, int agilidade, int nivel,
                int experiencia, int danoBeta, int danoAlpha, int danoSigma, int mana) {
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia, mana);
        this.configDanoBeta = danoBeta;
        this.configDanoAlpha = danoAlpha;
        this.configDanoSigma = danoSigma;
        atualizarArco(); // Garante que a arma inicial seja equipada
    }

    @Override
    protected void inicializarAcoes() {
        this.acoes.add(new AtaqueArco()); // Adicionado na posição 0
        this.acoes.add(new FlechaMagica()); // Adicionado na posição 1
    }

    @Override
    public void initializeTransientFields() {
        super.initializeTransientFields(); // Chama o initializeTransientFields de Heroi
        // No caso do Elfo, os campos configDano... já são serializados.
        // Ações de combate já são repopuladas pelo Heroi.initializeTransientFields()
    }


    public void atualizarArco() {
        int nivelAtual = getNivel();
        Arma novaArmaPadrao;
        if (nivelAtual < 2) {
            novaArmaPadrao = new ArcoBeta(configDanoBeta);
        } else if (nivelAtual < 3) {
            novaArmaPadrao = new ArcoAlpha(configDanoAlpha);
        } else {
            novaArmaPadrao = new ArcoSigma(configDanoSigma);
        }

        Arma armaAtual = this.getArma();
        if (armaAtual == null) {
            System.out.println(this.getNome() + " atingiu um novo patamar e forja uma " +
                    "nova arma: " + novaArmaPadrao.getNomeCompleto() + "!");
            try {
                this.equiparArma(novaArmaPadrao);
            } catch (NivelInsuficienteException e) {
                System.out.println("Erro ao equipar arma padrão para " + this.getNome() + ": " + e.getMessage());
            }
        } else if (novaArmaPadrao.getDano() > armaAtual.getDano()) {
            System.out.println("Com sua nova experiência, " + this.getNome() + " " +
                    "aprimora seu equipamento para uma " + novaArmaPadrao.getNomeCompleto() + "!");
            try {
                this.equiparArma(novaArmaPadrao);
            } catch (NivelInsuficienteException e) {
                System.out.println("Erro ao aprimorar arma para " + this.getNome() + ": " + e.getMessage());
            }
        } else {
            System.out.println(this.getNome() + " sente que poderia forjar uma " +
                    novaArmaPadrao.getNomeCompleto() + ", mas sua arma atual (" +
                    armaAtual.getNomeCompleto() + ") ainda é superior.");
        }
    }

    @Override
    public void ganharExperiencia(int exp) {
        super.ganharExperiencia(exp);
        atualizarArco();
    }

    public String getTipoDeArco() {
        return (this.arma != null) ? this.arma.getTipoArma() : null;
    }

    public int getDanoArco() {
        return (this.arma != null ? this.arma.getDano() : 0);
    }
}