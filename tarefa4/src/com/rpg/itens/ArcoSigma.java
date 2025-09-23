// ArcoSigma.java
package com.rpg.itens;
import com.rpg.itens.Item;
import com.rpg.itens.Arma;

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