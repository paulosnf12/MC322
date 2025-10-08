// ArcoAlpha.java
package com.rpg.itens;

/**
 * Representa o Arco Alpha, uma arma de longo alcance que pode ser utilizada por personagens
 * com nível mínimo 2 (atualmente o Elfo). O dano do arco é determinado pelo valor passado no construtor.
 */
public class ArcoAlpha extends Arma {
    private int danoBase;

    public ArcoAlpha(int danoBase) {
        super("Arco", "Alpha", 2); // Nível mínimo 2
        this.danoBase = danoBase;
    }

    @Override
    public int getDano() {
        return danoBase;
    }
}