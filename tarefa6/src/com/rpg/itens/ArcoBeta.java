// src/com/rpg/itens/ArcoBeta.java
package com.rpg.itens;

import jakarta.xml.bind.annotation.XmlElement; // ADICIONADO PARA JAXB
import jakarta.xml.bind.annotation.XmlRootElement; // ADICIONADO PARA JAXB

/**
 * Representa o Arco Beta, uma arma de longo alcance que pode ser utilizada por personagens
 * com nível mínimo 1 (atualmente o Elfo). O dano do arco é determinado pelo valor passado no construtor.
 */
@XmlRootElement(name = "arcoBeta") // ADICIONADO PARA JAXB: Indica que pode ser a raiz de um fragmento XML, ou um elemento em uma lista
public class ArcoBeta extends Arma {
    private int danoBase;

    // ADICIONADO PARA JAXB: Construtor vazio necessário para a desserialização do JAXB.
    public ArcoBeta() {
        super(); // Chama o construtor vazio da superclasse Arma
        this.danoBase = 0; // Inicialização padrão
    }

    public ArcoBeta(int danoBase) {
        super("Arco", "Beta", 1); // Nome do tipo, subtipo e nível mínimo
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