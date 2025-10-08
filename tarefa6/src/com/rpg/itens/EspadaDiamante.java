// EspadaDiamante.java
package com.rpg.itens;

/**
 * Representa a Espada de Diamante, uma arma corpo a corpo que pode ser utilizada por personagens
 * com nível mínimo 3 (atualmente o Paladino). O dano da espada é determinado pelo valor passado no construtor.
 */
public class EspadaDiamante extends Arma {
    private int danoBase;

    public EspadaDiamante(int danoBase) {
        super("Espada", "Diamante", 3);
        this.danoBase = danoBase;
    }

    @Override
    public int getDano() {
        return danoBase;
    }
}