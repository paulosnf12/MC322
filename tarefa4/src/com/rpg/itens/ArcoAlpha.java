// ArcoAlpha.java
package com.rpg.itens;
import com.rpg.itens.Item;
import com.rpg.itens.Arma;

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