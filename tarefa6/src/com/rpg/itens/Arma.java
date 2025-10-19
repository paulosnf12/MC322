// src/com/rpg/itens/Arma.java
package com.rpg.itens;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso; // JAXB: Para reconhecer subclasses

/**
 * Classe abstrata que representa uma arma equipável no jogo.
 * Define atributos essenciais como dano e nível mínimo para uso.
 * Implementa a interface {@link Item}.
 */
// JAXB: Indica que Arma pode ter várias subclasses concretas
@XmlSeeAlso({ArcoAlpha.class, ArcoBeta.class, ArcoSigma.class,
        EspadaDiamante.class, EspadaFerro.class, EspadaMadeira.class})
public abstract class Arma implements Item {
    @XmlElement
    protected int minNivel;
    @XmlElement
    protected String nomeTipoArma;
    @XmlElement
    protected String nomeSubtipoArma;

    /**
     * JAXB: Construtor sem argumentos exigido pelo JAXB para deserialização.
     */
    public Arma() {} // Construtor padrão

    // Construtor para inicializar os atributos básicos de qualquer arma
    public Arma(String nomeTipoArma, String nomeSubtipoArma, int minNivel) {
        this.nomeTipoArma = nomeTipoArma;
        this.nomeSubtipoArma = nomeSubtipoArma;
        this.minNivel = minNivel;
    }

    public abstract int getDano();

    public int getMinNivel() {
        return minNivel;
    }

    @Override
    public String getNomeCompleto() {
        return nomeTipoArma + " " + nomeSubtipoArma;
    }

    @Override
    public String toString() {
        return getNomeCompleto() + " - Dano: " + getDano() + ", Nível Mínimo: " +
                minNivel + "";
    }

    public String getTipoArma() {
        return this.nomeTipoArma;
    }
}