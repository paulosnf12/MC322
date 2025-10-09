// src/com/rpg/itens/EspadaMadeira.java
package com.rpg.itens;

import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlRootElement; // ADICIONADO PARA JAXB

/**
 * Representa a Espada de Madeira, uma arma corpo a corpo que pode ser utilizada por personagens
 * com nível mínimo 1 (atualmente o Paladino). O dano da espada é determinado pelo valor passado no construtor.
 */
@XmlRootElement // ADICIONADO PARA JAXB: Indica que pode ser a raiz de um fragmento XML, ou um elemento em uma lista
public class EspadaMadeira extends Arma {
    private int danoBase;

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public EspadaMadeira() {
        super(); // Chama o construtor vazio da superclasse Arma
        this.danoBase = 0; // Inicialização padrão
    }

    public EspadaMadeira(int danoBase) {
        super("Espada", "Madeira", 1);
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