// EspadaMadeira.java
package com.rpg.itens;

/**
 * Representa a Espada de Madeira, uma arma corpo a corpo que pode ser utilizada por personagens
 * com nível mínimo 1 (atualmente o Paladino). O dano da espada é determinado pelo valor passado no construtor.
 */
public class EspadaMadeira extends Arma {
    private int danoBase;

    public EspadaMadeira(int danoBase) {
        super("Espada", "Madeira", 1);
        this.danoBase = danoBase;
    }

    @Override
    public int getDano() {
        return danoBase;
    }
}