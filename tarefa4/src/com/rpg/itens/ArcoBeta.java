// ArcoBeta.java
package com.rpg.itens;
import com.rpg.itens.Item;
import com.rpg.itens.Arma;

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