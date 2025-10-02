// EspadaMadeira.java
package com.rpg.itens;
import com.rpg.itens.Item;
import com.rpg.itens.Arma;

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