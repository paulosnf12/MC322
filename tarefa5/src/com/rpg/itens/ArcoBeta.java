// ArcoBeta.java
package com.rpg.itens;

/**
 * Representa o Arco Beta, uma arma de longo alcance que pode ser utilizada por personagens
 * com nível mínimo 1 (atualmente o Elfo). O dano do arco é determinado pelo valor passado no construtor.
 */
public class ArcoBeta extends Arma {
    private int danoBase;

    public ArcoBeta(int danoBase) {
        super("Arco", "Beta", 1); // Nome do tipo, subtipo e nível mínimo
        this.danoBase = danoBase;
    }

    @Override
    public int getDano() {
        return danoBase;
    }
}