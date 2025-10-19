// src/com/rpg/itens/EspadaFerro.java
package com.rpg.itens;

import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlRootElement; // ADICIONADO PARA JAXB

/**
 * Representa a Espada de Ferro, uma arma corpo a corpo que pode ser utilizada por personagens
 * com nível mínimo 2 (atualmente o Paladino). O dano da espada é determinado pelo valor passado no construtor.
 */
@XmlRootElement(name = "espadaFerro") // ADICIONADO PARA JAXB: Indica que pode ser a raiz de um fragmento XML, ou um elemento em uma lista
public class EspadaFerro extends Arma {
    private int danoBase;

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public EspadaFerro() {
        super(); // Chama o construtor vazio da superclasse Arma
        this.danoBase = 0; // Inicialização padrão
    }

    public EspadaFerro(int danoBase) {
        super("Espada", "Ferro", 2);
        this.danoBase = danoBase;
    }

    @Override
    @XmlElement // ADICIONADO PARA JAXB: O dano é uma propriedade a ser serializada
    public int getDano() {
        return danoBase;
    }

    // ADICIONADO PARA JAXB: Setter para danoBase, necessário para a desserialização do JAXB.
    public void setDanoBase(int danoBase) {
        this.danoBase = danoBase;
    }
}