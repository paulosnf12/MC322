// EspadaFerro.java
package com.rpg.itens;

/**
 * Representa a Espada de Ferro, uma arma corpo a corpo que pode ser utilizada por personagens
 * com nível mínimo 2 (atualmente o Paladino). O dano da espada é determinado pelo valor passado no construtor.
 */
public class EspadaFerro extends Arma {
    private int danoBase;

    public EspadaFerro(int danoBase) {
        super("Espada", "Ferro", 2);
        this.danoBase = danoBase;
    }

    @Override
    public int getDano() {
        return danoBase;
    }
}