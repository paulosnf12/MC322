// EspadaFerro.java
package com.rpg.itens;
import com.rpg.itens.Item;
import com.rpg.itens.Arma;

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