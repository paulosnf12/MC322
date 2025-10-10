// src/com/rpg/personagens/herois/Paladino.java
package com.rpg.personagens.herois;

import com.rpg.combate.AtaqueEspada;
import com.rpg.combate.GolpeSagrado;
import com.rpg.exceptions.NivelInsuficienteException;
import com.rpg.itens.Arma;
import com.rpg.itens.EspadaDiamante;
import com.rpg.itens.EspadaFerro;
import com.rpg.itens.EspadaMadeira;
import com.rpg.personagens.Heroi;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement; // JAXB: É uma classe concreta que pode ser root

/**
 * Representa o personagem Paladino, um herói especializado em combate corpo a corpo com espadas.
 * O Paladino pode utilizar diferentes tipos de espadas conforme seu nível, e possui habilidades
 * únicas como o Golpe Sagrado. Ele pode equipar armas que implementam a interface Arma (atualmente espadas).
 *
 * Esta classe utiliza o conceito de agregação para suas ações de combate,
 * onde as instâncias de {@link AtaqueEspada} e {@link GolpeSagrado} são referenciadas,
 * mas podem existir independentemente do Paladino.
 */
@XmlRootElement(name = "paladino") // JAXB: Define o elemento raiz para esta classe
public class Paladino extends Heroi {
    @XmlElement
    private int carisma;
    @XmlElement
    private int configDanoMadeira;
    @XmlElement
    private int configDanoFerro;
    @XmlElement
    private int configDanoDiamante;

    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para deserialização.
     */
    public Paladino() {
        super();
        this.carisma = 0; // Inicializa com valor padrão
        this.configDanoMadeira = 0;
        this.configDanoFerro = 0;
        this.configDanoDiamante = 0;
    }

    /**
     * Construtor do Paladino.
     * Inicializa os atributos do herói e define os danos de configuração para os tipos de espada.
     *
     * @param nome Nome do Paladino.
     * @param pontosDeVida Pontos de vida iniciais do Paladino.
     * @param forca Força do Paladino, que afeta o dano físico.
     * @param agilidade Agilidade do Paladino, que pode afetar esquiva e velocidade.
     * @param nivel Nível inicial do Paladino.
     * @param experiencia Experiência inicial do Paladino.
     * @param danoMadeira Dano base da espada de madeira para configuração.
     * @param danoFerro Dano base da espada de ferro para configuração.
     * @param danoDiamante Dano base da espada de diamante para configuração.
     * @param mana Mana inicial do Paladino.
     */
    public Paladino(String nome, int pontosDeVida, int forca, int agilidade, int
            nivel, int experiencia,
                    int danoMadeira, int danoFerro, int danoDiamante, int mana) {
        super(nome, pontosDeVida, forca, agilidade, nivel, experiencia, mana);
        this.carisma = 26; // Valor inicial de carisma fixo para o Paladino
        this.configDanoMadeira = danoMadeira;
        this.configDanoFerro = danoFerro;
        this.configDanoDiamante = danoDiamante;
        atualizarEspada(); // Garante que a arma inicial seja equipada
    }

    @Override
    protected void inicializarAcoes() {
        this.acoes.add(new AtaqueEspada()); // Adicionado na posição 0
        this.acoes.add(new GolpeSagrado()); // Adicionado na posição 1
    }

    @Override
    public void initializeTransientFields() {
        super.initializeTransientFields(); // Chama o initializeTransientFields de Heroi
        // No caso do Paladino, os campos configDano... já são serializados.
        // Ações de combate já são repopuladas pelo Heroi.initializeTransientFields()
    }

    @Override
    public int getCarisma() {
        return this.carisma;
    }

    public void atualizarEspada() {
        int nivelAtual = getNivel();
        Arma novaArmaPadrao;
        if (nivelAtual < 2) {
            novaArmaPadrao = new EspadaMadeira(configDanoMadeira);
        } else if (nivelAtual < 3) {
            novaArmaPadrao = new EspadaFerro(configDanoFerro);
        } else {
            novaArmaPadrao = new EspadaDiamante(configDanoDiamante);
        }

        Arma armaAtual = this.getArma();
        if (armaAtual == null) {
            System.out.println(this.getNome() + " sente sua fé renovada e consagra uma " +
                    "nova arma: " + novaArmaPadrao.getNomeCompleto() + "!");
            try {
                this.equiparArma(novaArmaPadrao);
            } catch (NivelInsuficienteException e) {
                System.out.println("Erro ao equipar arma padrão para " +
                        this.getNome() + ": " + e.getMessage());
            }
        } else if (novaArmaPadrao.getDano() > armaAtual.getDano()) {
            System.out.println("Guiado por sua convicção " + this.getNome() + " forja " +
                    "uma arma mais poderosa: uma " + novaArmaPadrao.getNomeCompleto() + "!");
            try {
                this.equiparArma(novaArmaPadrao);
            } catch (NivelInsuficienteException e) {
                System.out.println("Erro ao aprimorar arma para Paladino" +
                        this.getNome() + ": " + e.getMessage());
            }
        } else {
            System.out.println(this.getNome() + " poderia forjar uma " +
                    novaArmaPadrao.getNomeCompleto() + ", mas sua arma atual (" +
                    armaAtual.getNomeCompleto() + ") já serve bem ao seu propósito sagrado.");
        }
    }

    @Override
    public void ganharExperiencia(int exp) {
        super.ganharExperiencia(exp);
        atualizarEspada(); // Atualiza a espada caso o nível mude
    }

    public String getTipoDeEspada() {
        return (this.arma != null) ? this.arma.getTipoArma() : null;
    }

    public int getDanoEspada() {
        return (this.arma != null ? this.arma.getDano() : 0);
    }
}