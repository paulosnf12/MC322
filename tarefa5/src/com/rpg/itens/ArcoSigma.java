// ArcoSigma.java
package com.rpg.itens;

/**
 * Representa o Arco Sigma, uma arma de longo alcance que pode ser utilizada por personagens
 * com nível mínimo 3 (atualmente o Elfo). O dano do arco é determinado pelo valor passado no construtor.
 */
public class ArcoSigma extends Arma {
    private int danoBase;

    public ArcoSigma(int danoBase) {
        super("Arco", "Sigma", 3); // Nível mínimo 3
        this.danoBase = danoBase;
    }

    @Override
    public int getDano() {
        return danoBase;
    }
}