// src/com/rpg/itens/Arma.java
package com.rpg.itens;

import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlSeeAlso; // ADICIONADO PARA JAXB: Para indicar subclasses
import jakarta.xml.bind.annotation.XmlTransient; // ADICIONADO PARA JAXB: Para ignorar campos específicos

/**
 * Classe abstrata que representa uma arma equipável no jogo.
 * Define atributos essenciais como dano e nível mínimo para uso.
 * Implementa a interface {@link Item}.
 */
@XmlSeeAlso({ArcoAlpha.class, ArcoBeta.class, ArcoSigma.class, EspadaDiamante.class, EspadaFerro.class, EspadaMadeira.class}) // ADICIONADO PARA JAXB: Indica as subclasses de Arma
public abstract class Arma implements Item{
    protected int minNivel; // Nível mínimo para equipar a arma
    protected String nomeTipoArma; // Ex: "Arco", "Espada", "Machado"
    protected String nomeSubtipoArma; // Ex: "Beta", "Madeira", "Bronze"

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public Arma() {
        // Inicializações padrão para evitar NPEs caso não estejam no XML ou JAXB não consiga preencher
        this.nomeTipoArma = "";
        this.nomeSubtipoArma = "";
        this.minNivel = 0;
    }

    // Construtor para inicializar os atributos básicos de qualquer arma
    public Arma(String nomeTipoArma, String nomeSubtipoArma, int minNivel) {
        this.nomeTipoArma = nomeTipoArma;
        this.nomeSubtipoArma = nomeSubtipoArma;
        this.minNivel = minNivel;
    }

    // Método abstrato que cada arma concreta deverá implementar para retornar seu dano
    /**
     * Retorna o valor de dano base da arma.
     * @return O dano da arma como um inteiro.
     */
    // ADICIONADO PARA JAXB: Marca o dano como um elemento XML. As subclasses concretas implementarão este método.
    @XmlElement
    public abstract int getDano();

    // Getter para o nível mínimo
    @XmlElement // ADICIONADO PARA JAXB
    public int getMinNivel() {
        return minNivel;
    }
    // ADICIONADO PARA JAXB: Setter para minNivel
    public void setMinNivel(int minNivel) {
        this.minNivel = minNivel;
    }

    // Método para obter o nome completo da arma (ex: "Arco Beta", "Espada de Madeira")
    // Agora implementado da interface Item
    @Override
    @XmlTransient // ADICIONADO PARA JAXB: Este é um valor derivado, não precisa ser serializado diretamente
    public String getNomeCompleto() {
        return nomeTipoArma + " " + nomeSubtipoArma;
    }

    // Sobrescrita do método toString para uma representação amigável da arma
    @Override
    @XmlTransient // ADICIONADO PARA JAXB: toString() é para representação, não serialização de dados
    public String toString() {
        return getNomeCompleto() + " - Dano: " + getDano() + ", Nível Mínimo: " + minNivel + "";
    }

    @XmlElement // ADICIONADO PARA JAXB
    public String getTipoArma() {
        return this.nomeTipoArma;
    }
    // ADICIONADO PARA JAXB: Setter para nomeTipoArma
    public void setTipoArma(String nomeTipoArma) {
        this.nomeTipoArma = nomeTipoArma;
    }

    // ADICIONADO PARA JAXB: Setter para nomeSubtipoArma (get já é coberto por getNomeCompleto ou getTipoArma)
    @XmlElement
    public String getNomeSubtipoArma() {
        return nomeSubtipoArma;
    }
    public void setNomeSubtipoArma(String nomeSubtipoArma) {
        this.nomeSubtipoArma = nomeSubtipoArma;
    }
}