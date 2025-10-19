// src/com/rpg/itens/ArcoAlpha.java
package com.rpg.itens;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Representa o Arco Alpha, uma arma de longo alcance que pode ser utilizada por personagens
 * com nível mínimo 2 (atualmente o Elfo). O dano do arco é determinado pelo valor passado no construtor.
 */
@XmlRootElement(name = "arcoAlpha") // Melhoria: Define o nome explicitamente
public class ArcoAlpha extends Arma {
    private int danoBase;

    public ArcoAlpha() {
        super();
        this.danoBase = 0; // Inicialização padrão
    }

    public ArcoAlpha(int danoBase) {
        super("Arco", "Alpha", 2); // Nível mínimo 2
        this.danoBase = danoBase;
    }

    @Override
    @XmlElement // Anota o método getter, que representa a propriedade "dano"
    public int getDano() {
        return danoBase;
    }

    // Setter para danoBase, necessário para a desserialização do JAXB ao usar @XmlElement no getter.
    public void setDanoBase(int danoBase) { // JAXB: Adicionar este setter para que JAXB possa desserializar o 'danoBase'
        this.danoBase = danoBase;
    }
}