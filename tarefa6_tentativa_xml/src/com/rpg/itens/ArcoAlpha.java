// src/com/rpg/itens/ArcoAlpha.java
package com.rpg.itens;

import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlRootElement; // ADICIONADO PARA JAXB

/**
 * Representa o Arco Alpha, uma arma de longo alcance que pode ser utilizada por personagens
 * com nível mínimo 2 (atualmente o Elfo). O dano do arco é determinado pelo valor passado no construtor.
 */
@XmlRootElement // ADICIONADO PARA JAXB: Indica que pode ser a raiz de um fragmento XML, ou um elemento em uma lista
public class ArcoAlpha extends Arma {
    private int danoBase;

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public ArcoAlpha() {
        super(); // Chama o construtor vazio da superclasse Arma
        this.danoBase = 0; // Inicialização padrão
    }

    public ArcoAlpha(int danoBase) {
        super("Arco", "Alpha", 2); // Nível mínimo 2
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